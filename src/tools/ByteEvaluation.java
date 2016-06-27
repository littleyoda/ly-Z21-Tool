package tools;

import java.util.List;
import java.util.logging.Logger;

public class ByteEvaluation {

	private static Logger logger = Logger.getLogger(ByteEvaluation.class.getSimpleName());

	public static Long calcResult(List<Integer> results, String byteorder) {
		Long l = getNumber(results, byteorder);
		return l;
	}

	private static Long getNumber(List<Integer> results, String format) {
		if (results.size() == 1) {
			return Long.valueOf(results.get(0));
		}
		if (format == null) {
			format = "big endian";
		}
		String niceformat = format.toLowerCase().replace(" ", "");
		switch (niceformat) {
		case "bigendian":
			return bigendian(results);
		case "littleendian":
			return littleendian(results);
		default:
			logger.info("Unknown Byte-Order: " + format);
			return littleendian(results);
		}

	}

	private static Long bigendian(List<Integer> r) {
		long out = 0;
		for (Integer wert : r) {
			out = out << 8;
			out = out + (long) wert;
		}
		return out;
	}

	private static Long littleendian(List<Integer> r) {
		long out = 0;
		long idx = 0;
		for (Integer wert : r) {
			out = out + ((long) wert << idx);
			idx = idx + 8;
		}
		return out;
	}

}
