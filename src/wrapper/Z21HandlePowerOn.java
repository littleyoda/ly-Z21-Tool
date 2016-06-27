package wrapper;

import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLanXTrackPowerOn;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.responses.Z21Response;

public class Z21HandlePowerOn extends Z21HandleBase {

	public Z21HandlePowerOn(Z21Callback run) {
		super(run);
	}

	@Override
	public Z21Action[] handle(Z21Broadcast broadcast) {
		if (broadcast.boundType == BroadcastTypes.LAN_X_TRACK_POWER_ON) {
			markAsFinish();
		}
		return null;
	}

	@Override
	public Z21Action[] handle(Z21Response response) {
		return null;
	}

	@Override
	public Z21Action[] firstAction() {
		return new Z21Action[] { new Z21ActionLanXTrackPowerOn() };
	}

}
