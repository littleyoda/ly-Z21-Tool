package wrapper;

import java.util.ArrayList;
import java.util.List;

import tools.CVCache;
import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseLanXCVResult;

public abstract class Z21HandleCVBase extends Z21HandleBase {

	protected int cvaddr;
	private int length;
	private List<Integer> results = new ArrayList<Integer>();

	public Z21HandleCVBase(int cvaddr, int length, Z21Callback run) {
		super(run);
		this.cvaddr = cvaddr;
		this.length = length;
	}

	@Override
	public Z21Action[] handle(Z21Broadcast broadcast) {
		return null;
	}

	// @Override
	// public Z21Action[] handle(Z21Response response) {
	// if (response.boundType == ResponseTypes.LAN_X_CV_RESULT) {
	// Z21ResponseLanXCVResult r = (Z21ResponseLanXCVResult) response;
	// results.add(r.getValue());
	// if (!allReceived()) {
	// return getNextAction();
	// }
	// }
	// if (response.boundType == ResponseTypes.LAN_X_CV_NACK) {
	// setUserData(null);
	// markAsFinish();
	// }
	// return null;
	// }

	private void setValue() {
		setUserData(calcResult(results));
		markAsFinish();
	}

	private Integer calcResult(List<Integer> r) {
		if (length == 1) {
			return r.get(0);
		}
		int out = 0;
		for (Integer wert : r) {
			out = out << 8;
			out = out + wert;
		}
		return out;
	}

	// public Z21Action[] getNextAction() {
	// Integer value = CVCache.instance.get(cvaddr);
	// if (value != null) {
	// setUserData(calcResult(results));
	// markAsFinish();
	// return null;
	// }
	// try {
	// return new Z21Action[] { new Z21ActionLanXCVRead(cvaddr) };
	// } catch (LocoAddressOutOfRangeException e) {
	// return null;
	// }
	// }
	//
	// @Override
	// public Z21Action[] firstAction() {
	// return getNextAction();
	//// Integer value = CVCache.instance.get(cvaddr);
	//// if (value != null) {
	//// results.add(r.getValue());
	//// if (results.size() < length) {
	//// return getNextAction();
	//// }
	//// setUserData(calcResult(results));
	//// markAsFinish();
	//// }
	//// try {
	//// return new Z21Action[] { new Z21ActionLanXCVRead(cvaddr) };
	//// } catch (LocoAddressOutOfRangeException e) {
	//// return null;
	//// }
	// }

	@Override
	public void reset() {
		super.reset();
		results.clear();
	}

	public abstract Z21Action getCVReadCommand(int cvaddr) throws LocoAddressOutOfRangeException;

	@Override
	public Z21Action[] handle(Z21Response response) throws LocoAddressOutOfRangeException {
		if (response.boundType == ResponseTypes.LAN_X_CV_RESULT) {
			resetTimeout();
			Z21ResponseLanXCVResult r = (Z21ResponseLanXCVResult) response;
			CVCache.instance.put(r.getCVadr(), r.getValue());
			results.add(r.getValue());
			Z21Action[] next = nextAction();
			return next;
		}
		if (response.boundType == ResponseTypes.LAN_X_CV_NACK) {
			setUserData(null);
			markAsFinish();
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
		if (results.size() < length) {
			int nextCV = cvaddr + results.size();
			Integer cachedValue = CVCache.instance.get(nextCV);
			if (cachedValue != null) {
				results.add(cachedValue);
				return nextAction();
			}
			return new Z21Action[] { getCVReadCommand(nextCV) };
		}
		if (results.size() == length) {
			setValue();
		}
		return null;
	}

}
