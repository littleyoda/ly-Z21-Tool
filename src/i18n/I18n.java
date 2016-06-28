package i18n;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import jaxbGenerated.config.Config;
import jaxbGenerated.config.Config.Werte.Auswahl;

public class I18n {

	static private Logger logger = Logger.getLogger(I18n.class.getSimpleName());

	static public ResourceBundle bundle = getBundle();

	static public ResourceBundle getBundle() {
		return ResourceBundle.getBundle("i18n.Bundle");
	}

	static public String getMsg(String key) {
		if (key.isEmpty()) {
			return key;
		}
		if (!bundle.containsKey(key)) {
			logger.warning("I18N missing: " + key + " for " + Locale.getDefault());
			return key;
		}
		return bundle.getString(key);
	}

	static public String getMsg(String key, Object... param) {
		return MessageFormat.format(bundle.getString(key), param);
	}

	static public String getDateTime(LocalDateTime x) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
				.withLocale(new Locale("de"));
		return x.format(formatter);
	}

	public static void translate(Config x) {
		x.setName(getMsg(x.getName()));
		x.setBeschreibung(getMsg(x.getBeschreibung()));
		x.setKategorie(getMsg(x.getKategorie()));
		if (x.getWerte() != null && x.getWerte().getAuswahl() != null) {
			for (Auswahl w : x.getWerte().getAuswahl()) {
				w.setBeschreibung(getMsg(w.getBeschreibung()));
				w.setName(getMsg(w.getName()));
			}
		}
	}

}
