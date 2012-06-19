/**********************************
 * Configuration.java
 * Part of the project "luckyGeek" from
 * ctvoigt (Christian Voigt), chripo2701  2011.
 *
 * 
 * Email: luckygeek@verpeil.de
 * 
 *
 * 
 **********************************
 * 
 * Configuration class.
 **********************************
 * 
 * This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307, USA.
 */

package de.verpeil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

/**
 * <b>Singleton</b> for read-access of configuration. 
 */
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
	
	static int getPropertiesCount() {
		return PROPERTIES.size();
	}

	static String getDownloadUrl() {
		return PROPERTIES.getProperty("download.url");
	}

	static String getAllFileName() {
		return PROPERTIES.getProperty("file.all");
	}

	static String getLastImageName() {
		return PROPERTIES.getProperty("file.last.image");
	}
	
	static File getLastImage() {
		return new File(getLastImageName());
	}

	static String getLastFileName() {
		return PROPERTIES.getProperty("file.last");
	}
	
	static File getLastFile() {
		return new File(getLastFileName());
	}
	
	static String getTempXmlName() {
		return PROPERTIES.getProperty("xml.temp.name");
	}
	
	static ConversionTypes getConversionType() {
		return ConversionTypes.parse(PROPERTIES.getProperty("type.conversion", ""));
	}
	
	static boolean isSilentPrintAllowed() {
		return Boolean.valueOf(PROPERTIES.getProperty("file.last.print.silent", "false")).booleanValue();
	}
	
	static boolean isMergeAllowed() {
		return Boolean.valueOf(PROPERTIES.getProperty("file.all.merge", "false")).booleanValue();
	}	
	
	static boolean isOnlyJpegDownload() {
		return Boolean.valueOf(PROPERTIES.getProperty("file.jpeg.only", "false")).booleanValue();
	}
	
	static String getXpath() {
		return PROPERTIES.getProperty("xml.temp.xpath");
	}
	
	static boolean isNamepsaceContextAvailable() {
		return null != PROPERTIES.get("xml.temp.namespaces")
				&& null != PROPERTIES.get("xml.temp.namespaces.prefixes");
	}
	
	static Map<String, String> getNamespacesAndPrefixes() {
		String[] namespaces = PROPERTIES.getProperty("xml.temp.namespaces", "").split("[;]");
		String[] prefixes = PROPERTIES.getProperty("xml.temp.namespaces.prefixes", "").split("[;]");
		int size = Math.min(namespaces.length, prefixes.length);
		Map<String, String> result = new HashMap<String, String>(size);
		for(int i = 0; i < size; i++) {
			result.put(prefixes[i], namespaces[i]);
		}
		return result;
	}
}
