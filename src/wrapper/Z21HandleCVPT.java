package wrapper;

import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLanXCVRead;

public class Z21HandleCVPT extends Z21HandleCVBase {


	public Z21HandleCVPT(int cvaddr, int length, Z21Callback run) {
		super(cvaddr, length, run);
	}

	@Override
	public Z21Action getCVReadCommand(int cvaddr) throws LocoAddressOutOfRangeException {
		return new Z21ActionLanXCVRead(cvaddr);
	}

}
