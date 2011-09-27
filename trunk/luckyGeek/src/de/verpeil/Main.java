package de.verpeil;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.pdfbox.util.PDFMergerUtility;

public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class
			.getCanonicalName());
	private final FileDownloader fd = new FileDownloader();
	private final DataProvider dp = new DataProvider();

	private void procss() {
		LOG.info("Begin: " + new Date());
		storeToFile();
		appendToPDF();
		cleanUp();
		LOG.info("End: " + new Date());
	}

	void storeToFile() {
		LOG.info("Begin storeToFile.");
		
		File xmlFeed = fd.download(Configuration.getDownloadUrl(),
				Configuration.getTempXmlName());
		String imageUrl = dp.extractImageUrl(xmlFeed);

		LOG.fine(imageUrl);
		File image = fd.download(imageUrl, Configuration.getLastImage());
		LOG.fine(image.getAbsolutePath());

		xmlFeed.deleteOnExit();
		
		LOG.info("End storeToFile.");
	}

	private void appendToPDF() {
		LOG.info("Begin append to pdf.");
		String allPdf = Configuration.getAllFile();
		String lastPdf = Configuration.getLastFile();
		(new ImageToPDFConverter()).convert();
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		mergePdf.addSource(allPdf);
		mergePdf.addSource(lastPdf);
		mergePdf.setDestinationFileName(allPdf);
		try {
			mergePdf.mergeDocuments();
		} catch (Exception e) {
			LOG.severe("Can not merge pdfs: " + e.getMessage());
		}
		LOG.info("End append to pdf.");
	}

	private void cleanUp() {
		// TODO Delte TMP-Files

	}

	public static void main(String[] args) {
		new Main().procss();
	}

}
