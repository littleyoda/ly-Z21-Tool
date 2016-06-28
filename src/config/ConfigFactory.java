package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.SerializationUtils;

import jaxbGenerated.config.Config;
import jaxbGenerated.config.Lokdekoder;
import jaxbGenerated.config.Lokdekoder.Repeater;
import tools.IO;

public class ConfigFactory {
	private static Logger logger = Logger.getLogger(ConfigFactory.class.getSimpleName());

	public static ConfigFactory instance = new ConfigFactory();

	public HashMap<String, String> hersteller = new HashMap<>();
	public List<Lokdekoder> configs = new ArrayList<>();

	public String getHersteller(String id) {
		return hersteller.get(id);
	}

	public ConfigFactory() {

		try (InputStream fis = IO.getIS("/config/hersteller.csv")) {
			BufferedReader in = new BufferedReader(new InputStreamReader(fis, "ISO-8859-1"));
			String line;
			while ((line = in.readLine()) != null) {
				String[] splited = line.split(";");
				hersteller.put(splited[1].trim(), splited[0].trim());
			}
			IO.getFiles("/config").forEach(new Consumer<String>() {

				@Override
				public void accept(String p) {
					if (p.toLowerCase().endsWith(".xml")) {
						configs.add(getConfig(p));
					}

				}

			});
			System.out.println("FInish");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Lokdekoder getConfig(String filePath) {
		logger.info("Öffne Datei " + filePath);
		Lokdekoder daten = null;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Lokdekoder.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			InputStream is = IO.getIS(filePath);
			daten = (Lokdekoder) jaxbUnmarshaller.unmarshal(is);
			translate(daten);
			handleRepeaters(daten);

		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		logger.info("Fertig " + daten.getName());
		return daten;

	}

	/**
	 * Tralnslates the Config Items
	 * 
	 * @param daten
	 */
	private static void translate(Lokdekoder daten) {
		for (Config config : daten.getConfig()) {
			i18n.I18n.translate(config);
		}
	}

	private static void handleRepeaters(Lokdekoder daten) {
		for (Repeater r : daten.getRepeater()) {
			int cvoffset = r.getCvoffset();
			int cverhoehung = r.getCverhoehung();

			// Die gesamten Config-Elemente werden in einer Schleife immer
			// wieder durchlaufen und die CV-Addr angepasst
			for (int i = r.getStart(); i <= r.getStop(); i++) {
				logger.info("Durchlauf " + i + " Offset: " + cvoffset);
				for (Config c : r.getConfig()) {
					Config copy = SerializationUtils.clone(c);
					// Config copy = deepCopyJAXB(c);
					// Config copy = deepCopyBeanUtils(c);
					int newcv = copy.getAddr().getCv() + cvoffset;
					copy.getAddr().setCv(newcv);
					adjust(copy, i);
					// daten.getConfig().add(copy);
					daten.getConfig().add(copy);
				}
				cvoffset += cverhoehung;

			}
		}
	}

	// private static Config deepCopyBeanUtils(Config c) {
	// try {
	// return (Config) BeanUtils.cloneBean(c);
	// } catch (IllegalAccessException | InstantiationException |
	// InvocationTargetException
	// | NoSuchMethodException e) {
	// logger.log(Level.SEVERE, "", e);
	// e.printStackTrace();
	// return null;
	// }
	// }
	private static void adjust(Config copy, int idx) {
		copy.setBeschreibung(copy.getBeschreibung().replace("${idx}", String.valueOf(idx)).replace("${cv}",
				String.valueOf(copy.getAddr().getCv())));
		copy.setName(copy.getName().replace("${idx}", String.valueOf(idx)).replace("${cv}",
				String.valueOf(copy.getAddr().getCv())));
		copy.setKategorie(copy.getKategorie().replace("${idx}", String.valueOf(idx)).replace("${cv}",
				String.valueOf(copy.getAddr().getCv())));
	}

	public static <T> T deepCopyJAXB(T object, Class<T> clazz) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			JAXBElement<T> contentObject = new JAXBElement<>(new QName(clazz.getSimpleName()), clazz, object);
			JAXBSource source = new JAXBSource(jaxbContext, contentObject);
			return jaxbContext.createUnmarshaller().unmarshal(source, clazz).getValue();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T deepCopyJAXB(T object) {
		if (object == null)
			throw new RuntimeException("Can't guess at class");
		return deepCopyJAXB(object, (Class<T>) object.getClass());
	}

	public List<Lokdekoder> getConfigForHersller(String id) {
		List<Lokdekoder> l = configs.stream().filter(p -> p.getId().toString().equals(id)).collect(Collectors.toList());
		if (l.size() == 0) {
			l = configs.stream().filter(p -> p.getId().toString().equals("-1")).collect(Collectors.toList());
		}
		return l;
	}
}
