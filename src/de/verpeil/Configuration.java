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
	
	static float getScaleFactor() {
		try {
			return Float.valueOf(PROPERTIES.getProperty("file.scale.factor")).floatValue();
		} catch (NumberFormatException e) {
			LOG.warning("Can not read scale factor. Using default. Message: " + e.getMessage());
			return Float.valueOf(1);
		}
	}
}
