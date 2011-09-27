package de.verpeil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImageToPDFConverterTest {

	@Test
	public void testCommand() {
		ImageToPDFConverter testMe = new ImageToPDFConverter();
		
		
		String commandResult = testMe.convertCommand();
		
		String winPrefix = "";
		if (System.getProperty("os.name").contains("Windows")) {
			winPrefix = "./tools/convert/";
		}
		assertEquals(winPrefix+"convert current.jpg last.pdf ", commandResult);
		
	}

}
