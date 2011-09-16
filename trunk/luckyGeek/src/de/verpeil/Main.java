package de.verpeil;

import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

public class Main {

	private DataProvider dp;
	private URL lastPicture;

	public Main() {
		initialize();
		extractData();
		storeToFile();
	}

	private void initialize() {
		dp = new DataProvider();
	}

	private void extractData() {
		lastPicture = dp.getLastPicture();
	}

	private void storeToFile() {
		try {
			downloadPicture();
			convertPicture();
			//appendToPDF();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendToPDF() {
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		mergePdf.addSource("all.pdf");
		mergePdf.addSource("last.pdf");
		mergePdf.setDestinationFileName("tmp.pdf");
		try {
			mergePdf.mergeDocuments();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("del all.pdf");
			Runtime.getRuntime().exec("move tmp.pdf all.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void convertPicture() throws IOException {
		Runtime.getRuntime().exec("./tools/imagick/convert last.jpg last.pdf");

	}

	private void downloadPicture() throws IOException {
		Runtime.getRuntime().exec(
				"./tools/wget/wget -o last.jpg " + lastPicture);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new Main();
	}

}
