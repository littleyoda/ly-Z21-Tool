package view;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jaxbGenerated.config.Config;
import z21Drive.LocoAddressOutOfRangeException;

public class GridPaneTabController {

	@FXML
	private TableView table;

	final ObservableList<Config> data = FXCollections.observableList(new ArrayList<Config>());

	private MainLayoutController mainLayoutController;
	@FXML
	private TextField tfFilter;

	private Tab filterField;

	private FilteredList<Config> filteredData;

	public void addRow(Config config) {
		data.add(config);

	}

	public void setTab(Tab tab) {
		TableColumn<Config, String> firstNameCol = new TableColumn<Config, String>("Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(
				new PropertyValueFactory<Config, String>("name"));

		TableColumn<Config, String> lastNameCol = new TableColumn<Config, String>("Zustand");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(
				new PropertyValueFactory<Config, String>("valueastext"));

		TableColumn<Config, String> emailCol = new TableColumn<Config, String>("Beschreibung");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(
				new PropertyValueFactory<Config, String>("beschreibung"));

		table.getColumns().clear();
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		filteredData = new FilteredList<>(data, p -> true);
		tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(config -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String cmp = (config.getBeschreibung() + " " + config.getName() + " " + config.getValueastext()).toLowerCase();
				return cmp.contains(newValue.toLowerCase());
			});
		});

		table.setItems(filteredData);
	}

	@FXML
	private void buttonUpdatePressed(ActionEvent event) {
		System.out.println("Pressed");
		mainLayoutController.update(filteredData);
	}

	public void setMainController(MainLayoutController mainLayoutController) {
		this.mainLayoutController = mainLayoutController;
	}



}
