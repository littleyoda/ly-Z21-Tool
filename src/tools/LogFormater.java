package tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormater extends Formatter {

    private final Date datum = new Date();
    private static String formatString = "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n";

    public synchronized String format(LogRecord record) {
        datum.setTime(record.getMillis());
        
        return String.format(formatString,
                             datum,
                             getSource(record),
                             record.getLoggerName(),
                             record.getLevel().getLocalizedName(),
                             formatMessage(record),
                             getStackTraceAsString(record.getThrown()));
    }

	private String getStackTraceAsString(Throwable throwable) {
		if (throwable == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println();
		throwable.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}

	private String getSource(LogRecord record) {
		if (record.getSourceClassName() == null) {
			return record.getLoggerName();
		}
		return record.getSourceClassName() + ((record.getSourceMethodName() != null) 
				?  " " + record.getSourceMethodName() 
				: "");
	}
}
