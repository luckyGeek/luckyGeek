/**********************************
 * Main.java
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
 * Main program.
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
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.util.PDFMergerUtility;

/**
 * Entry point of lucky geek. 
 */
public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class
			.getCanonicalName());
	private final FileDownloader fd = new FileDownloader();
	private final DataProvider dp = new DataProvider();
	private final Memory memory = new Memory();
	private volatile boolean merged = false;
	private volatile String imageUrl = "";

	private void process() {
		LOG.info("Begin: " + new Date());
		extractImageUrl();
		if (isNewImage()) {
			LOG.info("New image detected. Begin process.");
			storeToFile();
			convert();
			appendToPDF();
			save();
			cleanUp();
			LOG.info("Finished processing new image.");
		} else {
			LOG.info("No new image detected. Exit program.");
		}
		LOG.info("End: " + new Date());
	}
	
	void extractImageUrl() {
		LOG.fine("Begin: extract image url.");
		File xml = fd.download(Configuration.getDownloadUrl(),
				Configuration.getTempXmlName());
		imageUrl = dp.extractImageUrl(xml);
		FileUtils.deleteQuietly(xml);
		LOG.info("Url extracted: " + imageUrl);
	}
	
	boolean isNewImage() {
		return !memory.getUrl().equals(imageUrl);
	}

	void storeToFile() {
		LOG.fine("Begin download.");
		fd.download(imageUrl, Configuration.getLastImage());
		LOG.info("End downloading from url.");
	}
	
	private void convert() {
		LOG.fine("Begin converting.");
		ConversionTypes type = Configuration.getConversionType();
		Converter converter = type.createConverter();
		converter.convert(new File(Configuration.getLastImage()));
		LOG.fine("End converting image to PDF.");
	}

	private void appendToPDF() {
		LOG.fine("Begin append to pdf.");
		String allPdf = Configuration.getAllFile();
		String lastPdf = Configuration.getLastFile();
		
		if (!exists(lastPdf)){
			LOG.warning("No pdf for merging found. Cancel merging.");
			return;
		}
		
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		mergePdf.addSource(allPdf);
		mergePdf.addSource(lastPdf);
		mergePdf.setDestinationFileName(allPdf);
		try {
			mergePdf.mergeDocuments();
			merged = true;
		} catch (Exception e) {
			LOG.severe("Can not merge pdfs: " + e.getMessage());
		}
		LOG.info("Appended to pdf.");
	}
	
	private boolean exists(String path) {
		File file = new File(path);
		return file != null && file.exists() && file.isFile();
	}
	
	private void save() {
		if (merged) {
			LOG.fine("Saving.");
			memory.setUrl(imageUrl);
			memory.save();
			LOG.info("Saved.");
		}
	}

	private void cleanUp() {
		LOG.fine("Cleaning up.");
		FileUtils.deleteQuietly(new File(Configuration.getLastFile()));
		LOG.info("Cleaned up.");
	}
	
	public static void main(String[] args) {
		new Main().process();
	}
}
