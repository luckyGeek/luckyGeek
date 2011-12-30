/**********************************
 * DataProvider.java
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
 * Class extracting the ID from the RSS-Feed.
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
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

/**
 * Downloads feed and extracts url. 
 */
class DataProvider {
	private static final Logger LOG = Logger.getLogger(DataProvider.class.getCanonicalName());
	private static final String FIRST_ENTRY = "entry";
	private static final String CONTENT = "content";
	
	String extractImageUrl(File xmlFile) {
		if (xmlFile == null || !xmlFile.isFile() || !xmlFile.getName().endsWith(".xml")) {
			LOG.warning("Invalid file detected. Retrun default");
			return "";
		}
		
		Document doc = parseXml(xmlFile);
		if (doc == null) {
			LOG.warning("Invalid document. Returning default");
			return "";
		}
		
		String result = "";
		Element atom = doc.getRootElement();
		Namespace ns = atom.getNamespace();
		try {
			String content = atom.getChild(FIRST_ENTRY, ns).getChild(CONTENT, ns)
					.getValue();
			result = content.split("href=")[1].split("\"")[1];
		} catch (Exception e) {
			LOG.severe("Can not extract from xml: " + e.getMessage());
		}
		return result;
	}
	
	private Document parseXml(File file) {
		Document result = null;
		try {
			result = new SAXBuilder().build(file);
		} catch (Exception e) {
			LOG.severe("Can not parse xml: " + e.getMessage());
		}
		return result;
	}
}
