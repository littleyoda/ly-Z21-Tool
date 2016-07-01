package view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import jaxbGenerated.config.Config;

public class GridPaneTabController {

	@FXML
	private TreeTableView table;

	@FXML
	private TextField tfFilter;

	
	final ObservableList<RecursiveTreeItemContainer> allConfigItems = FXCollections.observableList(new ArrayList<RecursiveTreeItemContainer>());
	List<String> createdSubNodes = new ArrayList<>();
	
	private MainLayoutController mainLayoutController;



	private FilteredList<RecursiveTreeItemContainer> filteredData;

	public void addRow(Config config) {
		String subkat = config.getSubkategorie();
		RecursiveTreeItemContainer b = new RecursiveTreeItemContainer(config);
		allConfigItems.add(b);
		if (config.getSubkategorie() == null || config.getSubkategorie().isEmpty()) {
				//
		} else {
			if (!createdSubNodes.contains(subkat)) {
				FilteredList<RecursiveTreeItemContainer> katFilter = new FilteredList<>(allConfigItems, p -> p != null && p.getConfig() != null && subkat.equals(p.getConfig().getSubkategorie()) );
				allConfigItems.add(new RecursiveTreeItemContainer(subkat, katFilter));
				createdSubNodes.add(subkat);
			}
		}
	}

	public void init(Tab tab, String tabname) {
		tab.setText(tabname);
		createColumns();
		createFilterableList();
		setTableRootElement(tabname);

	}

	private void setTableRootElement(String tabname) {
		ObservableList<RecursiveTreeItemContainer> rootList = new FilteredList<>(filteredData, p -> p.getConfig() == null || p.getConfig().getSubkategorie() == null || p.getConfig().getSubkategorie().isEmpty() );		
		RecursiveTreeItemContainer root = new RecursiveTreeItemContainer(tabname, rootList);
		TreeItem<RecursiveTreeItemContainer> rootItem = new RecursiveTreeItem<>(root, RecursiveTreeItemContainer::call);
		rootItem.setExpanded(true);
		table.setRoot(rootItem);
	}

	private void createFilterableList() {
		filteredData = new FilteredList<>(allConfigItems, p -> true);
		tfFilter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(config -> {
				if (config.getConfig() == null) {
					return true;
				}
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String cmp = (config.getConfig().getBeschreibung() + " " + config.getConfig().getName() + " "
						+ config.getConfig().valueastextProperty().get()).toLowerCase();
				return cmp.contains(newValue.toLowerCase());
			});
		});
	}

	private void createColumns() {
		table.getColumns().clear();
		for (String[] x : new String[][] { {"Name", "name" }, { "Zustand", "valueastext"}, {"Beschreibung", "beschreibung"} }) {
			TreeTableColumn<RecursiveTreeItemContainer, String> col = new TreeTableColumn<>(x[0]);
			col.setMinWidth(100);
			col.setCellValueFactory(
					(TreeTableColumn.CellDataFeatures<RecursiveTreeItemContainer, String> param) ->
						param.getValue().getValue().getValueByName(x[1])
					);
			table.getColumns().add(col);
		}
	}

	@FXML
	private void buttonUpdatePressed(ActionEvent event) {
		System.out.println("Pressed");
		List<Config> cList = new ArrayList<>();
		for (RecursiveTreeItemContainer x : filteredData) {
			if (x.getConfig() == null) {
				continue;
			}
			cList.add(x.getConfig());
			
		}
		mainLayoutController.update(cList);
	}

	public void setMainController(MainLayoutController mainLayoutController) {
		this.mainLayoutController = mainLayoutController;
	}


}
