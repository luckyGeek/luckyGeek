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
	private final Memory memory = new Memory();
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
		File xmlFeed = fd.download(Configuration.getDownloadUrl(),
				Configuration.getTempXmlName());
		imageUrl = dp.extractImageUrl(xmlFeed);
		xmlFeed.deleteOnExit();
		LOG.info("End: extract image url: " + imageUrl);
	}
	
	boolean isNewImage() {
		return !memory.getUrl().equals(imageUrl);
	}

	void storeToFile() {
		LOG.info("Begin storeToFile.");
		fd.download(imageUrl, Configuration.getLastImage());
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
	
	private void save() {
		memory.setUrl(imageUrl);
		memory.save();
	}

	private void cleanUp() {
		// TODO Delte TMP-Files
	}

	public static void main(String[] args) {
		new Main().process();
	}
}
