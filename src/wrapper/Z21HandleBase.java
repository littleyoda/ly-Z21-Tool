package wrapper;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.responses.Z21Response;

public abstract class Z21HandleBase {
	
	boolean finish = false;
	private long lastChange;
	private Z21Callback run;
	public boolean isFinish() {
		return finish;
	}

	protected void resetTimeout() {
		lastChange = System.currentTimeMillis();
	}
	
	public long waitingTime() {
		return System.currentTimeMillis() - lastChange;
	}
	
	protected void setUserData(Object value) {
		if (run != null) {
			run.setUserdata(value);
		}
	}
	
	public Z21HandleBase(Z21Callback run) {
		this.run = run;
	}
	
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
	public abstract Z21Action[] handle(Z21Broadcast broadcast) throws LocoAddressOutOfRangeException;
	public abstract Z21Action[] handle(Z21Response response) throws LocoAddressOutOfRangeException;

	public abstract Z21Action[] firstAction();

	public void markAsFinish() {
		setFinish(true);
		if (run != null) {
			run.run();
		}
	}
	
	public void reset() {
		resetTimeout();
	}
}
