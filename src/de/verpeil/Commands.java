package de.verpeil;

public class Commands {

	public static String DLCommand(String url) {
		return "./tools/wget/wget -q -O last.jpg -U Mozilla/5.0 " + url;
	}

	public static String convertCommand() {
		return "./tools/imagick/convert last.jpg last.pdf";
	}
}
