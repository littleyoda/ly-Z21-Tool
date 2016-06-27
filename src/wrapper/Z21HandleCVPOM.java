package wrapper;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLANXCVPomReadByte;

public class Z21HandleCVPOM extends Z21HandleCVBase {

	private int lokid;

	public Z21HandleCVPOM(int lokid, int cvaddr, int length, Z21Callback run) {
		super(cvaddr, length, run);
		this.lokid = lokid;
	}

	@Override
	public Z21Action getCVReadCommand(int cvaddr) throws LocoAddressOutOfRangeException {
		return new Z21ActionLANXCVPomReadByte(lokid, cvaddr);
	}


}
