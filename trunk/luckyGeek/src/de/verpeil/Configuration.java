package de.verpeil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

class Configuration {
	private static final Logger LOG = Logger.getLogger(Configuration.class.getCanonicalName());
	private static final String CONF_FILE = "./conf/conf.properties";
	private static final Properties PROPERTIES = new Properties();

	static {
		InputStream ins = null;
		try {
			ins = new FileInputStream(CONF_FILE);
			PROPERTIES.load(ins);
		} catch (Exception e) {
			LOG.severe("Error while reading configuration file: " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(ins);
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
	
	static String getDateFormat() {
		return PROPERTIES.getProperty("date.format");
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
