package de.verpeil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

public class Main {

	private static final String LAST_PDF = "last.pdf";
	private static final String LAST_JPEG = "last.jpg";
	private static final String TMP_FILE = "tmp.pdf";
	private static final String ALL_PDF = "all.pdf";
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
			appendToPDF();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendToPDF() {
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		mergePdf.addSource(ALL_PDF);
		mergePdf.addSource(LAST_JPEG);
		mergePdf.setDestinationFileName(TMP_FILE);
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
			new File("all.pdf").delete();
			Runtime.getRuntime().exec("mv "+TMP_FILE+" "+ALL_PDF);

			new File(LAST_PDF).delete();
			new File(LAST_JPEG).delete();
			System.out.println("Appended to PDF!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void convertPicture() throws IOException {
		System.out.println("Starting convert!");
		Process convert = Runtime.getRuntime().exec(CommandsHelper.convertCommand());
		waitTillFinisched(convert);
		System.out.println("Converted to PDF!");
	}

	private void downloadPicture() throws IOException {
		System.out.println("Download started for file " + lastPicture + "!");
		Process dl = Runtime.getRuntime().exec(CommandsHelper.DLCommand(lastPicture.toString()));
		waitTillFinisched(dl);
		System.out.println("Download finished!");
	}

	private void waitTillFinisched(Process process) {
		int NOT_FINISHED = -1000;
		int exit = NOT_FINISHED;
		while (exit == NOT_FINISHED) {
			try {
				int exitValue = process.exitValue();
				System.out.print("Exit Code: " + exitValue);
				exit = exitValue;
			} catch (IllegalThreadStateException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new Main();
	}

}
