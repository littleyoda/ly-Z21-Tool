package i18n;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
	
	static public ResourceBundle bundle = getBundle();
	
	static public ResourceBundle getBundle() {  
		 return ResourceBundle.getBundle("i18n.Bundle");
		 //, new Locale("de", "DE")
	}
	
	static public String getMsg(String key) {
		return bundle.getString(key);
	}
	
	static public String getMsg(String key, Object... param) {
		return MessageFormat.format(bundle.getString(key), param);
	}
	
	static public String getDateTime(LocalDateTime x) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("de"));		
		return x.format(formatter);
	}
	
}
