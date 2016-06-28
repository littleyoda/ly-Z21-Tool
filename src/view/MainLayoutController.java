package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import application.Main;
import config.ConfigAuswerter;
import config.ConfigFactory;
import i18n.I18n;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jaxbGenerated.config.Config;
import jaxbGenerated.config.Lokdekoder;
import tools.CVCache;
import wrapper.Z21Callback;
import wrapper.Z21Wrapper;
import z21Drive.LocoAddressOutOfRangeException;

public class MainLayoutController {

	@FXML
	private TabPane tabpane;

	@FXML
	private Label vendorLabel;

	@FXML
	private Label profileLabel;

	@FXML
	private ToggleGroup progMode;

	@FXML
	private RadioButton rbPOM;

	@FXML
	private RadioButton rbPT;

	@FXML
	private Spinner<Integer> lokSpinner;

	@FXML
	private Circle circle;

	@FXML
	private Label lbStatus;

	private Map<String, GridPaneTabController> tabs = new HashMap<>();

	private Main mainApp;
	private SimpleStringProperty vendorLabelString;

	private SimpleStringProperty profileLabelString;

	private SimpleStringProperty statusLabelString;

	private Logger logger = Logger.getLogger(getClass().getSimpleName());

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		ConfigFactory cf = ConfigFactory.instance;

		// see
		// http://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user
		IntegerSpinnerValueFactory spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 127, 1);
		lokSpinner.setValueFactory(spinnerFactory);
		TextFormatter formatter = new TextFormatter(spinnerFactory.getConverter(), spinnerFactory.getValue());
		lokSpinner.getEditor().setTextFormatter(formatter);
		spinnerFactory.valueProperty().bindBidirectional(formatter.valueProperty());

		lokSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			logger.info("Setting Lokid to " + newValue);
			CVCache.instance.reset();
			Z21Wrapper.instance.setLokId(newValue);
		});

		statusLabelString = new SimpleStringProperty("");
		lbStatus.textProperty().bind(statusLabelString);

		vendorLabelString = new SimpleStringProperty("");
		vendorLabel.textProperty().bind(vendorLabelString);

		profileLabelString = new SimpleStringProperty("");
		profileLabel.textProperty().bind(profileLabelString);

		progMode.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				boolean pomActive = newValue.getToggleGroup().getSelectedToggle().equals(rbPOM);
				lokSpinner.setDisable(!pomActive);
				Z21Wrapper.instance.setPom(pomActive);
				if (pomActive) {
					Z21Wrapper.instance.requestPowerOn(null);
				}
				CVCache.instance.reset();
			}
		});
		progMode.selectToggle(rbPOM);

		Z21Wrapper.instance.getCurrentStatus().addListener(new ChangeListener<Z21Wrapper.STATUS>() {

			@Override
			public void changed(ObservableValue<? extends wrapper.Z21Wrapper.STATUS> observable,
					wrapper.Z21Wrapper.STATUS oldValue, wrapper.Z21Wrapper.STATUS newValue) {
				Color newcolor;
				String text;
				switch (newValue) {
				case CONNECTED:
					newcolor = Color.GREEN;
					text = I18n.getMsg("main.z21connected");
					break;
				case DISCONNECTED:
					newcolor = Color.RED;
					text = I18n.getMsg("main.z21disconnected");
					break;
				case ERROR:
					newcolor = Color.RED;
					text = I18n.getMsg("main.z21error");
					break;
				default:
					newcolor = Color.GREY;
					text = "";
				}
				Platform.runLater(() -> {
					circle.setFill(newcolor);
					statusLabelString.set(text);
				});
			}

		});
		circle.setFill(Color.RED);
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 * @throws LocoAddressOutOfRangeException
	 */
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
		Z21Wrapper.instance.requestPowerOn(new Z21Callback() {

			@Override
			public void run() {
				System.out.println("PowerOn!");
			}

		});
		System.out.println();
		;
		// commands.add(new Z21ActionLanXTrackPowerOn());
		// next(z21);
	}

	private void fillTabs(Lokdekoder lokdekoder) {
		tabpane.getTabs().clear();
		tabs.clear();
		// Cache reset
		for (Config x : lokdekoder.getConfig()) {
			i18n.I18n.translate(x);
			String kat = (x.getKategorie() == null) ? "Nicht zugeordnet" : x.getKategorie();
			GridPaneTabController controller = tabs.get(kat);
			if (controller == null) {
				FXMLLoader loader = new FXMLLoader();
				loader.setResources(I18n.getBundle());
				loader.setLocation(Main.class.getResource("/view/GridPaneTab.fxml"));
				TabPane dummytabpane = null;
				try {
					dummytabpane = (TabPane) loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Tab tab = dummytabpane.getTabs().get(0);
				tab.setClosable(false);
				controller = loader.getController();
				controller.setMainController(this);
				controller.setTab(tab);
				tabs.put(kat, controller);
				tab.setText(kat);
				tabpane.getTabs().add(tab);
			}
			controller.addRow(x);

		}

		// Work'A'round for strange tabpane
		tabpane.getSelectionModel().clearSelection();
		for (Tab x : tabpane.getTabs()) {
			tabpane.getSelectionModel().select(x);
		}
		tabpane.getSelectionModel().select(0);
		tabpane.setDisable(false);
	}

	@FXML
	private void helpPressed(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setResources(I18n.getBundle());
		loader.setLocation(Main.class.getResource("/view/HelpView.fxml"));
		DialogPane dialog = null;
		try {
			dialog = (DialogPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dialog d = new Dialog();
		ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
		d.setDialogPane(dialog);
		d.getDialogPane().getButtonTypes().add(buttonTypeOk);
		d.show();

	}

	@FXML
	private void connectPressed(ActionEvent event) {
		clearAll();
		Z21Wrapper.instance.requestCV(8, 1, new Z21Callback() {

			@Override
			public void run() {
				if (getUserdata() == null) {
					Platform.runLater(() -> {
						// vendorLabelString.set(I18n.getMsg("Keine Lok gefunden
						// (NACK)"));
						vendorLabelString.set("Keine Lok gefunden (NACK)");
					});
					return;
				}
				String id = getUserdata().toString();

				System.out.println("Hersteller: " + ConfigFactory.instance.getHersteller("" + id));
				List<Lokdekoder> l = ConfigFactory.instance.getConfigForHersller(id);
				if (l.size() == 0) {
					l = ConfigFactory.instance.getConfigForHersller("-1");
				}
				final Lokdekoder configToShow = l.get(0);
				Platform.runLater(() -> {
					vendorLabelString.set(I18n.getMsg("main.vendor", ConfigFactory.instance.getHersteller("" + id)));
					profileLabelString.set(I18n.getMsg("main.profile", configToShow.getName()));
					fillTabs(configToShow);
					Z21Wrapper.instance.requestPowerOn(null);
				});
			}

		});
	}

	public void clearAll() {
		Platform.runLater(() -> {
			CVCache.instance.reset();
			profileLabelString.set("");
			vendorLabelString.set("");
			tabpane.getTabs().clear();
			tabs.clear();
			tabpane.setDisable(true);
		});
	}

	public void update(List<Config> data) {
		for (Config c : data) {
			if (c.valueastextProperty().get().equals("?")) {
				request(c);
			}
		}
	}

	private void request(Config config) {
		Z21Wrapper z = Z21Wrapper.instance;

		z.requestCVGeneric(config.getAddr().getSetze(), config.getAddr().getCv(), config.getAddr().getLength(),
				config.getAddr().getByteorder(), new Z21Callback() {

					@Override
					public void run() {
						if (getUserdata() == null) {
							logger.warning("Receiving null");
							return;
						}
						Platform.runLater(() -> {
							config.addDekoderWert((Long) getUserdata());
							ConfigAuswerter.auswerten(config);
						});
					}

				});

	}

	public void shutDown() {
		Z21Wrapper.instance.shutDown();
	}

}
