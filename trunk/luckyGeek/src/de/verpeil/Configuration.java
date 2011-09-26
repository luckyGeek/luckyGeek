package de.verpeil;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

class Configuration {
	private static final Logger LOG = Logger.getLogger(Configuration.class.getCanonicalName());
	private static final String CONF_FILE = "conf.properties";
	private static final Properties PROPERTIES = new Properties();

	static {
		InputStream ins = null;
		try {
			ins = Configuration.class.getResourceAsStream(CONF_FILE);
			PROPERTIES.load(ins);
		} catch (Exception e) {
			LOG.severe("Error while reading configuration file: " + e.getMessage());
		} finally {
			closeStream(ins);
		}
	}

	private static void closeStream(InputStream ins) {
		try {
			if (ins != null) {
				ins.close();
			}
		} catch (Exception e) {
			// ignore
		}
	}
	
	static String getDownloadUrl() {
		return PROPERTIES.getProperty("download.url");
	}

	static String getAllFile() {
		return PROPERTIES.getProperty("file.all");
	}

	static String getLastImage() {
		return PROPERTIES.getProperty("file.last.image");
	}

	static String getLastFile() {
		return PROPERTIES.getProperty("file.last");
	}
	
	static String getTempXmlName() {
		return PROPERTIES.getProperty("xml.temp.name");
	}
	
	static float getScaleFactor() {
		try {
			return Float.valueOf(PROPERTIES.getProperty("file.scale.factor")).floatValue();
		} catch (NumberFormatException e) {
			LOG.warning("Can not read scale factor. Using default. Message: " + e.getMessage());
			return Float.valueOf(1);
		}
	}
}
