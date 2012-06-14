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

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
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

	private final FileDownloader fileDownloader = new FileDownloader();
	private final DataProvider dataProvider = new DataProvider();
	private final Memory memory = new Memory();
	private final boolean merge = Configuration.isMergeAllowed();
	private final boolean print = Configuration.isSilentPrintAllowed();
	private final boolean fullProcedure = !Configuration.isOnlyJpegDownload();

	private volatile File lastImage = Configuration.getLastImage();
	private volatile boolean success = false;
	private volatile String imageUrl = "";

	private void process() {
		LOG.info("Begin: " + new Date());
		extractImageUrl();
		if (isNewImage()) {
			LOG.info("New image detected. Begin process.");
			storeToFile();
			if (fullProcedure) {
				convert();
				appendToPDF();
				save();
				print();
			}
			cleanUp();
			LOG.info("Finished processing new image.");
		} else {
			LOG.info("No new image detected. Exit program.");
		}
		LOG.info("End: " + new Date());
	}

	void extractImageUrl() {
		LOG.fine("Begin: extract image url.");
		File xml = fileDownloader.download(Configuration.getDownloadUrl(),
				Configuration.getTempXmlName());
		imageUrl = dataProvider.extractImageUrl(xml);
		FileUtils.deleteQuietly(xml);
		LOG.info("Url extracted: " + imageUrl);
	}

	boolean isNewImage() {
		return !memory.getUrl().equals(imageUrl);
	}

	void storeToFile() {
		LOG.fine("Begin download.");
		lastImage = fileDownloader.download(imageUrl, Configuration.getLastImageName());
		LOG.info("End downloading from url.");
	}

	private void convert() {
		LOG.fine("Begin converting.");
		convertToPdf(Configuration.getConversionType());
	}

	private void print() {
		if (!print) {
			LOG.info("Printing is disabled.");
			return;
		}
		if (reconvertForPrintNecesarry()) {
			LOG.fine("Begin converting for Printing.");
			convertToPdf(ConversionTypes.PDFBOX);
			LOG.fine("End converting for Printing.");
			LOG.fine("Begin printing.");
			printLastDocument();
			LOG.info("File printed.");
		} else {
			LOG.info("File is already converted with PDF-Box: optimized for Letter-Printing; Skipping reconvert.");
		}

	}

	private boolean reconvertForPrintNecesarry() {
		return !ConversionTypes.PDFBOX
				.equals(Configuration.getConversionType());
	}

	private void convertToPdf(ConversionTypes type) {
		Converter converter = type.createConverter();
		converter.convert(lastImage);
		LOG.fine("End converting image to PDF.");
	}

	private void printLastDocument() {
		try {
			new PdfPrinter().print(Configuration.getLastFile());
		} catch (PrinterException e) {
			LOG.warning("Can not print file. Message: " + e.getMessage());
			return;
		} catch (IOException e) {
			LOG.warning("No file for printing found. Message: "
					+ e.getMessage());
			return;
		}
	}

	private void appendToPDF() {
		if (!merge) {
			LOG.info("Merging disabled.");
			success = true;
			return;
		}
		LOG.fine("Begin append to pdf.");
		String allPdf = Configuration.getAllFileName();
		File lastPdf = Configuration.getLastFile();

		if (!lastPdf.exists()) {
			LOG.warning("No pdf for merging found. Cancel merging.");
			return;
		}

		PDFMergerUtility mergePdf = new PDFMergerUtility();
		mergePdf.addSource(allPdf);
		mergePdf.addSource(lastPdf);
		mergePdf.setDestinationFileName(allPdf);
		try {
			mergePdf.mergeDocuments();
			success = true;
		} catch (Exception e) {
			LOG.severe("Can not merge pdfs: " + e.getMessage());
		}
		LOG.info("Appended to pdf.");
	}

	private void save() {
		if (success) {
			LOG.fine("Saving.");
			memory.setUrl(imageUrl);
			memory.save();
			LOG.info("Saved.");
		}
	}

	private void cleanUp() {
		LOG.fine("Cleaning up.");
		if (merge) {
			FileUtils.deleteQuietly(new File(Configuration.getLastFileName()));
		}
		LOG.info("Cleaned up.");
	}

	public static void main(String[] args) {
		new Main().process();
	}
}
