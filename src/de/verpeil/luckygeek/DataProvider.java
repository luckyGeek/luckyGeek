/**********************************
 *DataProvider.java
 *Part of the project "luckyGeek" from
 *ctvoigt (Christian Voigt), chripo2701  2011.
 *
 *
 *Email: luckygeek@verpeil.de
 *
 *
 *
 **********************************
 *
 *Class extracting the ID from the RSS-Feed.
 **********************************
 *
 *This program is free software; you can redistribute it
 *and/or modify it under the terms of the GNU General
 *Public License as published by the Free Software
 *Foundation; either version 2 of the License, or (at your
 *option) any later version.
 *This program is distributed in the hope that it will be
 *useful, but WITHOUT ANY WARRANTY; without even the implied
 *warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *PURPOSE. See the GNU General Public License for more details.
 *You should have received a copy of the GNU General Public
 *License along with this program; if not, write to the Free
 *Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *MA 02111-1307, USA.
 */

package de.verpeil.luckygeek;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/** 
 * Downloads feed and extracts url. 
 */
class DataProvider {
    private static final Logger LOG = Logger.getLogger(DataProvider.class.getCanonicalName());
    
    String extractImageUrl(String url) {
        String result = "";
        try {
            result = extractImageUrl(URI.create(url).toURL().openStream());
        } catch (MalformedURLException e) {
            LOG.severe(String.format("Invalid url '%s'. Message: %s.", url, e.getLocalizedMessage()));
        } catch (IOException e) {
            LOG.severe(String.format("Can not open url '%s'. Message: %s.", url, e.getLocalizedMessage()));
        }
        return result;
    }

    String extractImageUrl(InputStream ins) {
        LOG.info("Start evaluate.");
        boolean contextAvailable = Configuration.isNamepsaceContextAvailable();
        String value = "";
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory
                    .newInstance();
            domFactory.setNamespaceAware(contextAvailable);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(ins);
            XPath xpath = XPathFactory.newInstance().newXPath();
            if (contextAvailable) {
                xpath.setNamespaceContext(createNamespaceContext());
            }
            XPathExpression expr = xpath.compile(Configuration.getXpath());
            Object result = expr.evaluate(doc, XPathConstants.STRING);
            value = (String) result;
        } catch (ParserConfigurationException e) {
            LOG.severe("Can not parse stream. Message: " + e.getLocalizedMessage());
        } catch (SAXException e) {
            LOG.severe("Can not parse stream. Message: " +  e.getLocalizedMessage());
        } catch (IOException e) {
            LOG.severe("Error with file stream. Message: " + e.getLocalizedMessage());
        } catch (XPathExpressionException e) {
            LOG.severe(String.format("Invalid XPath-expression '%s'. Message: %s.", Configuration.getXpath(), e.getLocalizedMessage()));
        } finally {
            IOUtils.closeQuietly(ins);
        }
        LOG.info(String.format("End evaluate '%s'.", value));
        return value;
    }

    private NamespaceContext createNamespaceContext() {
    	return new DefaultNamespaceContext();
    }

    private static class DefaultNamespaceContext implements NamespaceContext {
    	private final Map<String, String> prefixesNamespaces = Configuration.getNamespacesAndPrefixes();

    	@Override
    	public String getNamespaceURI(String prefix) {
    		String result = prefixesNamespaces.get(prefix);
    		return result != null ? result : "";
    	}

    	@Override
    	public String getPrefix(String uri) {
    		if (null == uri) {
    			return "";
    		}
    		Set<Map.Entry<String, String>> entrySet = prefixesNamespaces.entrySet();
    		for (Map.Entry<String, String> entry : entrySet) {
    			if (uri.equals(entry.getValue())) {
    				return entry.getKey();
    			}
    		}
    		return "";
    	}

    	@Override
    	public Iterator<String> getPrefixes(String uri) {
    		return prefixesNamespaces.values().iterator();
    	}
    }
}
