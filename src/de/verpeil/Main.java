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
			appendToPDF();

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
			Runtime.getRuntime().exec("rm all.pdf");
			Runtime.getRuntime().exec("mv tmp.pdf all.pdf");

			Runtime.getRuntime().exec("rm last.pdf");
			Runtime.getRuntime().exec("rm last.jpg");
			System.out.println("Appended to PDF!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void convertPicture() throws IOException {
		System.out.println("Starting convert!");
		Process convert = Runtime.getRuntime().exec(Commands.convertCommand());
		waitTillFinisched(convert);
		System.out.println("Converted to PDF!");
	}

	private void downloadPicture() throws IOException {
		System.out.println("Download started for file " + lastPicture + "!");
		Process dl = Runtime.getRuntime().exec(Commands.DLCommand(lastPicture.toString()));
		waitTillFinisched(dl);
		System.out.println("Download finished!");
	}

	private void waitTillFinisched(Process process) {
		int NOT_FINISHED = -1000;
		int exit = NOT_FINISHED;
		while (exit == NOT_FINISHED) {
			try {
				int tmp = process.exitValue();
				System.out.print("Exit Code: " + tmp);
				exit = tmp;
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
