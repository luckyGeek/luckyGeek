package de.verpeil;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.pdfbox.util.PDFMergerUtility;

public class Main {
	private static final Logger LOG = Logger.getLogger(Main.class.getCanonicalName());
	private final FileDownloader fd = new FileDownloader();
	private final DataProvider dp = new DataProvider();


	private void procss() {
		storeToFile();
		
	}
	
	void storeToFile() {
		LOG.info("Begin: " + new Date());
		File xmlFeed = fd.download(Configuration.getDownloadUrl(), Configuration.getTempXmlName());
		String imageUrl = dp.extractImageUrl(xmlFeed);
		
		LOG.fine(imageUrl);
		File image = fd.download(imageUrl, Configuration.getLastImage());
		LOG.fine(image.getAbsolutePath());
		
		appendToPDF(Configuration.getLastFile(), image);
		
		xmlFeed.deleteOnExit();
		LOG.info("End: " + new Date());
	}

	private void appendToPDF(String input, File image) {
		LOG.info("Begin append to pdf.");
		String allPdf = Configuration.getAllFile();
		String lastPdf = Configuration.getLastFile();
		try {
			Runtime.getRuntime().exec(CommandsHelper.convertCommand());
		} catch (Exception e) {
			LOG.severe("Can not transform image to pdf: " + e.getMessage());
		}
		
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
	
	public static void main(String[] args) {
		new Main().procss();
	}

}
