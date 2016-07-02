package tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IO {

	
	private static Logger logger = Logger.getLogger(IO.class.getSimpleName());


	/**
	 * Return rel Pathes
	 * e.g. /config/something
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getFiles(String path) {
	    try {
			URI uri = getResource(path).toURI();
		    String absPath = getResource("..").getPath();
			Path myPath;
			if (uri.getScheme().equals("jar")) {
			    FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
			    myPath = fileSystem.getPath(path);
			    
			} else {
			    myPath = Paths.get(uri);
			}
			
			 List<String> l = Files.walk(myPath)	
			 					   .map(filePath ->  pathAbs2Rel(absPath, filePath))
			 	                   .collect(Collectors.toList());
			 return l;
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	    return null;
	    
	}

	private static String pathAbs2Rel(String absPath, Path filePath) {
		String out = filePath.toString().replace(absPath,  "");
		if (!out.startsWith("/")) {
			out = "/" + out;
		}
		return out;
	}
	
	public static InputStream getIS(String path) {
		return IO.class.getResourceAsStream(path);
	}
	
	public static InputStream getIS(Path path) {
		return getIS(path.toString());
	}

	public static URL getResource(String string) {
		URL out = IO.class.getResource(string);
		if (out == null) {
			if (string.startsWith("/")) {
				out = IO.class.getClassLoader().getResource(string.substring(1));
			}
		}
		logger.info("getResource " + string + " => " + out);
		return out;
	}

}
