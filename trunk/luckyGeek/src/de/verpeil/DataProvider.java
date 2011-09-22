package de.verpeil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class DataProvider {
	private static final String FIRST_ENTRY = "entry";
	private static final String CONTENT = "content";
	private URL url;
	private Document doc;


	public DataProvider() {
		setURL();
		configureJDOM();
	}

	private void setURL() {
		try {
			url = new URL(Configuration.URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void configureJDOM() {
		try {
			doc = new SAXBuilder().build(url);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public URL getLastPicture() {
		Element atom = doc.getRootElement();
		Namespace ns = atom.getNamespace();
		try {
			String content = atom.getChild(FIRST_ENTRY, ns).getChild(CONTENT, ns)
					.getValue();
			String croppedContent = content.split("href=")[1].split("\"")[1];
			url = new URL(croppedContent);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return url;

	}
}
