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

public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class
			.getCanonicalName());
	private final FileDownloader fd = new FileDownloader();
	private final DataProvider dp = new DataProvider();
	private final Memory memory = new Memory();
	private boolean merged = false;
	private String imageUrl = "";

	private void process() {
		LOG.info("Begin: " + new Date());
		extractImageUrl();
		if (isNewImage()) {
			LOG.info("New image detected. Start downloading.");
			storeToFile();
			appendToPDF();
			save();
			cleanUp();
		}
		LOG.info("End: " + new Date());
	}
	
	void extractImageUrl() {
		LOG.info("Begin: extract image url.");
		File xml = fd.download(Configuration.getDownloadUrl(),
				Configuration.getTempXmlName());
		imageUrl = dp.extractImageUrl(xml);
		FileUtils.deleteQuietly(xml);
		LOG.info("End: extract image url: " + imageUrl);
	}
	
	boolean isNewImage() {
		return !memory.getUrl().equals(imageUrl);
	}

	void storeToFile() {
		LOG.info("Begin download.");
		fd.download(imageUrl, Configuration.getLastImage());
		LOG.info("End download.");
	}

	private void appendToPDF() {
		LOG.info("Begin append to pdf.");
		String allPdf = Configuration.getAllFile();
		String lastPdf = Configuration.getLastFile();
		(new ImageToPDFConverter()).convert();
		
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
		LOG.info("End append to pdf.");
	}
	
	private boolean exists(String path) {
		File file = new File(path);
		return file != null && file.exists() && file.isFile();
	}
	
	private void save() {
		if (merged) {
			LOG.info("Saving.");
			memory.setUrl(imageUrl);
			memory.save();
			LOG.info("Saved.");
		}
	}

	private void cleanUp() {
		LOG.info("Cleaning up.");
		FileUtils.deleteQuietly(new File(Configuration.getLastFile()));
		LOG.info("Cleaned up.");
	}
	
	public static void main(String[] args) {
		new Main().process();
	}
}