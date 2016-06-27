package wrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jaxbGenerated.config.Config.Addr.Setze;
import tools.CVCache;
import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLanXCVRead;
import z21Drive.actions.Z21ActionLanXCVWrite;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseLanXCVResult;

public class Z21HandleIndirectCV extends Z21HandleBase {

	private ArrayList<Setze> setze;
	private Integer cvaddr;
	private List<Integer> results = new ArrayList<Integer>();

	public Z21HandleIndirectCV(List<Setze> setze, Integer cvaddr, Z21Callback run) {
		super(run);
		this.setze = new ArrayList<Setze>(setze);
		this.cvaddr = cvaddr;
	}

	@Override
	public void reset() {
		super.reset();
		results.clear();
	}
	
	@Override
	public Z21Action[] handle(Z21Broadcast broadcast) {
		return null;
	}

	@Override
	public Z21Action[] handle(Z21Response response) throws LocoAddressOutOfRangeException {
		if (response.boundType == ResponseTypes.LAN_X_CV_RESULT) {
			resetTimeout();
			Z21ResponseLanXCVResult r = (Z21ResponseLanXCVResult) response;
			CVCache.instance.put(r.getCVadr(), r.getValue());
			results.add(r.getValue());
			Z21Action[] next = nextAction();
			if (next == null) {
				// 3. Report the CV
				setUserData(r.getValue());
				markAsFinish();
			}
			return next;
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
		// 1. Set all neccessary CV Values
		if (results.size() < setze.size()) {
			int nextIdx = results.size();
			Integer cachedValue = CVCache.instance.get(setze.get(nextIdx).getCv());
			if (setze.get(nextIdx).getWert().equals(cachedValue)) {
				System.out.println("Value cached: " + setze.get(nextIdx).getCv() + " => " + cachedValue);
				results.add(cachedValue);
				return nextAction();
			}
			System.out.println("Value Requested: " + setze.get(nextIdx).getCv());
			return new Z21Action[] { new Z21ActionLanXCVWrite(setze.get(nextIdx).getCv(), setze.get(nextIdx).getWert()) };
		}
		// 2. Request the CV
		if (results.size() == setze.size()) {
			return new Z21Action[] { new Z21ActionLanXCVRead(cvaddr) };
		}
		return null;
	}

}
