package config;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

import i18n.I18n;
import jaxbGenerated.config.Config;
import jaxbGenerated.config.Config.Werte;
import jaxbGenerated.config.Config.Werte.Auswahl;

public class ConfigAuswerter {

	public static void auswerten(Config config) {
    	// The Last Values is from the CVRead. All other from CVWrite. 
    	Config c = config;
    	List<Long> values = config.getDekoderWert();
    	Long value = values.get(values.size() - 1);
    	if (c.getAddr().getStartbit() != null) {
    		value = value >> c.getAddr().getStartbit();
            long bits = c.getAddr().getStopbit() - c.getAddr().getStartbit() + 1;
            long mask = (int) Math.pow(2, bits) - 1;
            value = value & mask;
    	}
        
    	String wert = "";
        // Fall 1 => Auswahl
        Werte werte = c.getWerte();
        if (werte != null && werte.getAuswahl() != null && werte.getAuswahl().size() > 0) {
        	Auswahl gefundeneAuswahl = null;
        	for (Auswahl auswahl : c.getWerte().getAuswahl()) {
        		if (auswahl.getWert() == value) {
        			gefundeneAuswahl = auswahl;
        		}
        	}
        	wert = gefundeneAuswahl.getName();
        	if (!gefundeneAuswahl.getBeschreibung().isEmpty()) {
        		wert += " / " + gefundeneAuswahl.getBeschreibung();
        	}
        } else if (werte != null && werte.getFaktor() != null) {
        	Double valueDouble = werte.getFaktor() * value;
        	wert = valueDouble + " [" + werte.getEinheit() + "] (" + value + ")";
        } else if (config.getFormat() != null) {
        	System.out.println(config.getFormat());
        	switch (config.getFormat().toLowerCase().trim().replace(" ", "")) {
        		case "sec01012000":
        			LocalDateTime date = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
        			date = date.plus(value, ChronoUnit.SECONDS);
        			wert = I18n.getDateTime(date);
        			break;
        		case "hex":
        			wert = Long.toHexString(value);
        			break;
        		default:
                	wert = String.valueOf(value);
        	}
        } else {
        	wert = String.valueOf(value);
        }
        config.setValueastext(wert);
	}

}
