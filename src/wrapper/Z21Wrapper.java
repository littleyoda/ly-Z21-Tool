package wrapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Timer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jaxbGenerated.config.Config.Addr.Setze;
import z21Drive.LocoAddressOutOfRangeException;
import z21Drive.Z21;
import z21Drive.actions.Z21Action;
import z21Drive.actions.Z21ActionLanRailcomGetdata;
import z21Drive.broadcasts.BroadcastTypes;
import z21Drive.broadcasts.Z21Broadcast;
import z21Drive.broadcasts.Z21BroadcastListener;
import z21Drive.responses.ResponseTypes;
import z21Drive.responses.Z21Response;
import z21Drive.responses.Z21ResponseListener;

public class Z21Wrapper {

	private Logger logger = Logger.getLogger(getClass().getSimpleName());
	public enum STATUS { DISCONNECTED, CONNECTED, ERROR }
	static public Z21Wrapper instance = new Z21Wrapper();


	private boolean pom = true;


	int locoIDpom = 1;

	private Z21 z21 = Z21.instance;
	private Z21HandleBase currentAction = null;

	private LinkedList<Z21HandleBase> liste = new LinkedList<Z21HandleBase>();

	private ObjectProperty<STATUS> currentStatus = new SimpleObjectProperty<STATUS>(null);

	private Timer timeoutTimer;


	private Timer keepAliveTimer;
	public Z21Wrapper() {
		Z21BroadcastListener a = new Z21BroadcastListener() {

			@Override
			public BroadcastTypes[] getListenerTypes() {
				return BroadcastTypes.values();
			}

			@Override

			public void onBroadCast(BroadcastTypes type, Z21Broadcast broadcast) {
				resetTimer();
				System.out.println("Receiving: " + type);
				Z21Action[] next = null;
				try {
					if (currentAction != null) {
						next = currentAction.handle(broadcast);
					}
				} catch (LocoAddressOutOfRangeException e) {
					e.printStackTrace();
				}
				if (next != null) {
					sendeToZ21(next);
				} else {
					run();
				}
			}			
		};
		z21.addBroadcastListener(a);
		Z21ResponseListener b = new Z21ResponseListener() {
			@Override
			public ResponseTypes[] getListenerTypes() {
				return ResponseTypes.values();
			}

			@Override
			public void responseReceived(ResponseTypes type, Z21Response response) {
				resetTimer();
				if (!type.equals(ResponseTypes.LAN_GET_SERIAL_NUMBER_RESPONSE)) {
					logger.info("Receiving: " + type);
				}
				Z21Action[] next = null;
				try {
					if (currentAction != null) {
						next = currentAction.handle(response);
					}
				} catch (LocoAddressOutOfRangeException e) {
					e.printStackTrace();
				}
				if (next != null) {
					sendeToZ21(next);
				} else {
					run();
				}
			}

		};
		z21.addResponseListener(b);
		z21.setKeepAliveTimer(1000);

		keepAliveTimer = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("keepAliveTimer timeout");
				if (currentStatus.get() == null || currentStatus.get() == STATUS.CONNECTED) {
					currentStatus.set(STATUS.DISCONNECTED);
				}
			}
		});
		keepAliveTimer.start();


		timeoutTimer = new Timer(1500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentAction == null) {
					return;
				}
				// Restarting Action if necessary
				if (currentAction.waitingTime() > 5000) {
					logger.info("Restarting " + currentAction.getClass().getSimpleName() + " because of timeout!");
					currentAction.reset();
					sendeToZ21(currentAction.firstAction());
				}
			}
		});
		timeoutTimer.start();
	}
	public boolean isPom() {
		return pom;
	}


	
	public void requestCVGeneric(List<Setze> setze, int cvaddr, Integer length, String byteformat, Z21Callback z21Callback) {
		Z21HandleCV req = new Z21HandleCV(pom, 
										locoIDpom, 
										setze, 
										cvaddr, 
										(length == null) ? 1 : length, 
										byteformat,
										z21Callback);
		liste.add(req);
	}
	
	public void requestCV(int cvaddr, int length, Z21Callback run)  {
		if (pom) {
			liste.add(new Z21HandleCVPOM(locoIDpom, cvaddr, length, run));
		} else {
			liste.add(new Z21HandleCVPT(cvaddr, length, run));
		}
		run();
	}


	public void requestIndirectCV(Integer cvaddr, List<Setze> setze, Z21Callback run)  {
		if (pom) {
			liste.add(new Z21HandleIndirectCVPOM(setze, locoIDpom, cvaddr, run));
		} else {
			liste.add(new Z21HandleIndirectCV(setze, cvaddr, run));
		}
		run();
	}



	public void requestPowerOn(Z21Callback run) {
		liste.add(new Z21HandlePowerOn(run));
		run();

	}



	private void resetTimer() {
		currentStatus.set(STATUS.CONNECTED);
		if (keepAliveTimer != null) {
			keepAliveTimer.restart();
		}
	}

	private synchronized void run() {
		// Check for finished Actions
		if (currentAction != null && currentAction.isFinish()) {
			logger.info("Command finished! " + currentAction.getClass().getSimpleName());
			currentAction = null;
		}
		// Check if actions are stilling running or we are empty
		if (currentAction != null || liste.size() == 0) {
			return;
		}
		// Execute next Action
		currentAction = liste.removeFirst();
		logger.info("Next Command startet:" + currentAction.getClass().getSimpleName());
		currentAction.resetTimeout();
		Z21Action[] next = currentAction.firstAction();
		try {
			sendeToZ21(next);
		} catch (NullPointerException e) {
			currentStatus.set(STATUS.ERROR);
		}
		if (currentAction.isFinish()) {
			run();
		}
	}

	private void sendeToZ21(Z21Action[] actions) {
		if (actions == null) {
			return;
		}
		List<Z21Action> list = Arrays.asList(actions);
		Iterator<Z21Action> itr = list.iterator();
		while (itr.hasNext()) {
			Z21Action x = itr.next();
			logger.info("Sende: " + x);
			boolean result = z21.sendActionToZ21(x);
			if (!result) {
				logger.warning("Konnte es nicht versenden");
			}
			try {
				if (itr.hasNext()) {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPom(boolean pom) {
		this.pom = pom;
	}
	public void shutDown() {
		if (timeoutTimer != null) {
			timeoutTimer.stop();
		}
		if (keepAliveTimer != null) {
			keepAliveTimer.stop();
			keepAliveTimer = null;
		}
		z21.shutdown();
	}
	public void setLokId(Integer newValue) {
		locoIDpom = newValue;
		logger.info("New Lokid: " + locoIDpom);
	}
	public ObjectProperty<STATUS> getCurrentStatus() {
		return currentStatus;
	}


}
