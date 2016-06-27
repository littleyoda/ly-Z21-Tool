package jaxbGenerated.config;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

public class ConfigBase {

	protected List<Long> dekoderwert;
	protected SimpleStringProperty valueastext = new SimpleStringProperty("?"); 

	
	public ConfigBase() {
		dekoderwert = new ArrayList<Long>();
	}
	
	public String getValueastext() {
		return valueastext.get();
	}

	public void setValueastext(String valueastext) {
		this.valueastext.set(valueastext);
	}

	public SimpleStringProperty valueastextProperty() {
		return valueastext;
	}

    public List<Long> getDekoderWert() {
		return dekoderwert;
	}

	public void addDekoderWert(Long wert) {
		dekoderwert.add(wert);
		updateValueAsText();
	}
	
	public void updateValueAsText() {
		valueastext.set("");
		if (dekoderwert.size() == 0) {
			valueastext.set("?");
		}
	}
	
	
	

}
