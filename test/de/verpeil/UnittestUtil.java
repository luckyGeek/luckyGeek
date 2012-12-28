/**********************************
 * UnittestUtil.java
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
 * Utility class for unittesting.
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
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;


/**
 * <b>Utility</b>-class for unittesting.
 */
class UnittestUtil {
	private static final String WORKSPACE = FileUtils.getUserDirectoryPath() + "/luckyGeek";
	private static final String WORKSPACE_UNITTESTS = WORKSPACE + "/unittest";
	
	static boolean setUp() {
		Configuration.load(getTestResource("conf.properties").getFile());
		File workspace = new File(WORKSPACE_UNITTESTS);
		if (!workspace.exists()) {
			return new File(WORKSPACE_UNITTESTS).mkdirs();
		}
		return true;
	}
	
	static File createAllPdf() {
		String fileName = Configuration.getAllFileName();
		PDDocument doc = null;
		try {
			doc = new PDDocument();
			doc.addPage(new PDPage());
			doc.save(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		} finally {
			closeDocument(doc);
		}
		return new File(fileName);
	}
	
	static URL getTestResource(String name) {
		return UnittestUtil.class.getResource("testdata/" + name);
	}
	
	static File getFileForUserHome(File file) {
		return getFileForUserHome(file.getName());
	}
	
	static File getFileForUserHome(String file) {
		return new File(WORKSPACE_UNITTESTS, file);
	}
	
	static void closeDocument(PDDocument document) {
		try {
			if (document != null) {
				document.close();
			}
		} catch (Exception e) {
			// ignore
		}
	}
	
	static void tearDown() {
		FileUtils.deleteQuietly(Configuration.getLastFile());
		FileUtils.deleteQuietly(new File(Configuration.getAllFileName()));
		try {
			FileUtils.deleteDirectory(new File(WORKSPACE_UNITTESTS));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration.load(""); // ugly workaround
	}
}
