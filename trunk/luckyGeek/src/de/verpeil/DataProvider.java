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
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * Downloads feed and extracts url. 
 */
class DataProvider {
	private static final Logger LOG = Logger.getLogger(DataProvider.class.getCanonicalName());

	String extractImageUrl(File file) {
		LOG.info("Start evaluate.");
		String value = "";
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			//				domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			Document doc = builder.parse(file);
			XPath xpath = XPathFactory.newInstance().newXPath();
			//				xpath.setNamespaceContext(new FeedburnerNamespaceContext());
			XPathExpression expr = xpath.compile(Configuration.getXpath());
			Object result = expr.evaluate(doc, XPathConstants.STRING);
			value = (String) result;
		} catch (ParserConfigurationException e) {
			LOG.severe(String.format("Can not parse %s. Message: %s.", file.getAbsoluteFile(), e.getLocalizedMessage()));
		} catch (SAXException e) {
			LOG.severe(String.format("Can not parse %s. Message: %s.", file.getAbsoluteFile(), e.getLocalizedMessage()));
		} catch (IOException e) {
			LOG.severe(String.format("Error with file %s. Message: %s.", file.getAbsoluteFile(), e.getLocalizedMessage()));
		} catch (XPathExpressionException e) {
			LOG.severe(String.format("Invalid XPath-expression '%s'. Message: %s.", Configuration.getXpath(), e.getLocalizedMessage()));
		}
		LOG.info(String.format("End evaluate '%s'.", value));
		return value;
	}
}
