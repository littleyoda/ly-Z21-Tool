package tools;

import java.util.HashMap;
import java.util.logging.Logger;

public class CVCache {

	private Logger logger = Logger.getLogger(getClass().getSimpleName());

	static public CVCache instance = new CVCache();
	HashMap<String, Integer> values = new HashMap<String, Integer>();
	HashMap<String, Integer> indexedarea = new HashMap<String, Integer>();
	
	public void put(int cVadr, int value) {
		String addr = String.valueOf(cVadr);
		if (isIndexedArea(cVadr)) {
			addr = get(31) + ";" + get(32) + ";" + cVadr; 
		}
		values.put(addr, value);
	}

	public Integer get(Integer cVadr) {
		String addr = String.valueOf(cVadr);
		if (isIndexedArea(cVadr)) {
			addr = get(31) + ";" + get(32) + ";" + cVadr; 
		}
		return values.get(addr);
	}

	private boolean isIndexedArea(Integer cv) {
		return cv >= 257 && cv<= 512;
	}
	public void reset() {
		logger.info("Cache cleared");
		values.clear();
	}
	
	
}
 