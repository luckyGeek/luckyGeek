package de.verpeil;

public class ImageToPDFConverter {

	private static String SPACE;
	private static String WINDOWS_TOOLS_FOLDER;
	private static String LAST_DL_JPEG;
	private static String LAST_DL_PDF;
	private static String CONVERT_COMMAND;
	private static CharSequence WINDOWS;

	public ImageToPDFConverter() {
		setVars();
	}

	private void setVars() {
		SPACE = " ";
		WINDOWS_TOOLS_FOLDER = "./tools/";
		LAST_DL_JPEG = "current.jpg";
		LAST_DL_PDF = "last.pdf";
		CONVERT_COMMAND = "convert";
		WINDOWS = "Windows";
	}

	public String convertCommand() {
		return compileIndependentCommand(CONVERT_COMMAND,
				appendParameter(LAST_DL_JPEG) + appendParameter(LAST_DL_PDF));
	}

	private String appendSpace(String string) {
		return string.concat(SPACE);
	}

	private String appendParameter(String string) {
		return appendSpace(string);
	}

	private String compileIndependentCommand(String command,
			String parameters) {
		StringBuffer compiledCommand = new StringBuffer("");
		addWindowsFolderPrefixIfNeeded(compiledCommand, command);
		command = appendSpace(command);
		compiledCommand.append(command);
		compiledCommand.append(parameters);
		return compiledCommand.toString();
	}

	private void addWindowsFolderPrefixIfNeeded(
			StringBuffer compiledCommand, String command) {
		if (isWindows()) {
			compiledCommand.append(WINDOWS_TOOLS_FOLDER + command + "/");
		}

	}

	private boolean isWindows() {
		return System.getProperty("os.name").contains(WINDOWS);
	}
}
