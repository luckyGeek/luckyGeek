package de.verpeil;

import java.io.File;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

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
