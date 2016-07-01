package view;

import java.util.ArrayList;

import org.apache.commons.lang3.reflect.FieldUtils;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jaxbGenerated.config.Config;

public class RecursiveTreeItemContainer {
		String name;
		private Config config;
		private ObservableList<RecursiveTreeItemContainer> list;

		public ObservableValue<String> getValueByName(String fieldName) {
			if (fieldName.equals("name") && name != null) {
				return new ReadOnlyStringWrapper(name);
			}
			if (config == null) {
				return new ReadOnlyStringWrapper("");
			}
			try {
				Object x = FieldUtils.readField(config, fieldName, true);
				if (x instanceof ObservableValue) {
					return (ObservableValue<String>) x;
				}
				if (x != null) {
					return new ReadOnlyStringWrapper(x.toString());
				}
				
			} catch (IllegalArgumentException|java.lang.IllegalAccessException e) {
				//
			}	
			return new ReadOnlyStringWrapper("");
		}
		
		public Config getConfig() {
			return config;
		}
		public RecursiveTreeItemContainer(Config config) {
			this.config = config;
			this.list = FXCollections.observableList(new ArrayList<RecursiveTreeItemContainer>());
		}
		public RecursiveTreeItemContainer(String name, ObservableList<RecursiveTreeItemContainer> list) {
			this.name = name;
			this.list = list;
		}

		public String getName() {
			if (name == null) {
				return config.getName();
			}
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}


		public ObservableList<RecursiveTreeItemContainer> call() {
			return list;
		}
		
		@Override
		public String toString() {
			return getName();
		}
	}

