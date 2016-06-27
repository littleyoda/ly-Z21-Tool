package wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import jaxbGenerated.config.Config.Addr.Setze;
import tools.ByteEvaluation;
import tools.CVCache;
import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLANXCVPomReadByte;
import z21Drive.actions.Z21ActionLanXCVPomWriteByte;
import z21Drive.actions.Z21ActionLanXCVRead;
import z21Drive.actions.Z21ActionLanXCVWrite;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseLanXCVResult;

public class Z21HandleCV extends Z21HandleBase {
	private enum STATUS {
		SETTINGINDEXBYTES, READINGCV, FINISH
	}

	private STATUS currentSTATUS = null;
	protected int cvaddr;
	private int length;

	// Used while ReadingCV
	private List<Integer> results = new ArrayList<Integer>();

	// Used while SettingIndexBytes
	private List<Integer> currentCV = new ArrayList<Integer>();

	// IndexBytes
	private List<Setze> setze;
	private boolean pom;
	private int lokid;
	private Logger logger = Logger.getLogger(getClass().getSimpleName());
	private String format;

	public Z21HandleCV(boolean pom, int lokid, List<Setze> setze, int cvaddr, int length, String format,
			Z21Callback run) {
		super(run);
		this.lokid = lokid;
		this.pom = pom;
		this.setze = setze;
		this.cvaddr = cvaddr;
		this.length = length;
		this.format = format;
		reset();
	}

	@Override
	public Z21Action[] handle(Z21Broadcast broadcast) {
		return null;
	}

	private void setValue() {
		Long l = ByteEvaluation.calcResult(results, format);
		setUserData(l);
		markAsFinish();
	}

	@Override
	public void reset() {
		super.reset();
		if (setze != null && setze.size() > 0) {
			currentSTATUS = STATUS.SETTINGINDEXBYTES;
		} else {
			currentSTATUS = STATUS.READINGCV;
		}
		currentCV.clear();
		results.clear();
	}

	public Z21Action getCVReadCommand(int cvaddr) throws LocoAddressOutOfRangeException {
		if (pom) {
			return new Z21ActionLANXCVPomReadByte(lokid, cvaddr);
		} else {
			return new Z21ActionLanXCVRead(cvaddr);
		}
	}

	@Override
	public Z21Action[] handle(Z21Response response) throws LocoAddressOutOfRangeException {
		if (response.boundType == ResponseTypes.LAN_X_CV_RESULT) {
			resetTimeout();
			Z21ResponseLanXCVResult r = (Z21ResponseLanXCVResult) response;
			System.out.println("Receiving CV_Result: " + r.getValue());
			CVCache.instance.put(r.getCVadr(), r.getValue());
			switch (currentSTATUS) {
			case READINGCV:
				results.add(r.getValue());
				return nextAction();

			case SETTINGINDEXBYTES:
				int currentPos = currentCV.size();
				int expectedValue = setze.get(currentPos).getWert();
				if (expectedValue != r.getValue()) {
					setUserData(null);
					markAsFinish();
					throw new IllegalStateException("Erwartung: " + expectedValue + " Tatsächlich: " + r.getValue());
				}
				currentCV.add(r.getValue());
				if (currentCV.size() == setze.size()) {
					currentSTATUS = STATUS.READINGCV;
				}
				return nextAction();

			default:
				System.out.println("Nack");
				setUserData(null);
				markAsFinish();
				throw new IllegalStateException("Unbekannter Status " + currentSTATUS);
			}
		}
		if (response.boundType == ResponseTypes.LAN_X_CV_NACK) {
			System.out.println("Nack");
			setUserData(null);
			markAsFinish();
			return null;
		}
		return null;
	}

	@Override
	public Z21Action[] firstAction() {
		try {
			return nextAction();
		} catch (LocoAddressOutOfRangeException e) {
			return null;
		}
	}

	private Z21Action[] nextAction() throws LocoAddressOutOfRangeException {
		switch (currentSTATUS) {
		case SETTINGINDEXBYTES:
			if (setze.size() == currentCV.size()) {
				currentSTATUS = STATUS.READINGCV;
				return nextAction();
			}
			logger.info("Working on SETTINGCV Pos " + currentCV.size() + " (Size: " + setze.size() + ")");
			if (currentCV.size() < setze.size()) {
				int currentPos = currentCV.size();
				int nextCV = setze.get(currentPos).getCv();
				int nextWert = setze.get(currentPos).getWert();
				Integer cachedValue = CVCache.instance.get(nextCV);
				logger.info("CV: " + nextCV + " Value: " + nextWert + " Cached: " + cachedValue);
				if (cachedValue != null && cachedValue == nextWert) {
					currentCV.add(cachedValue);
					return nextAction();
				}
				return getCVWriteCommandWithResult(nextCV, nextWert);
			}
		case READINGCV:
			if (results.size() == length) {
				setValue();
				currentSTATUS = STATUS.FINISH;
				return null;
			}
			logger.info("Working on READINGCV Pos " + results.size() + " (Size: " + length + ")");
			if (results.size() < length) {
				int nextCV = cvaddr + results.size();
				Integer cachedValue = CVCache.instance.get(nextCV);
				if (cachedValue != null) {
					results.add(cachedValue);
					return nextAction();
				}
				return new Z21Action[] { getCVReadCommand(nextCV) };
			}
			break;
		default:
			System.out.println("Unbekannter Zustand");
		}
		return null;
	}

	private Z21Action[] getCVWriteCommandWithResult(int nextCV, int nextWert) throws LocoAddressOutOfRangeException {
		if (pom) {
			return new Z21Action[] { new Z21ActionLanXCVPomWriteByte(lokid, nextCV, nextWert), // Write
																								// on
																								// POM
																								// does
																								// not
																								// send
																								// a
																								// response.
																								// ...
					new Z21ActionLANXCVPomReadByte(lokid, nextCV) }; // ... so a
																		// Read
																		// Command
																		// is
																		// necessary
		} else {
			return new Z21Action[] { new Z21ActionLanXCVWrite(nextCV, nextWert) };

		}
	}

}
