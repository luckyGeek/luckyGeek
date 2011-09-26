package de.verpeil;


public class CommandsHelper {

	private static final String SPACE = " ";
	private static final String WINDOWS_TOOLS_FOLDER = "./tools/";
	private static final String LAST_DL_JPEG = "current.jpg";
	private static final String LAST_DL_PDF = "last.pdf";
	private static final String CONVERT_COMMAND = "convert";
	private static final CharSequence WINDOWS = "Windows"; //TODO Check; better "Win"?


	public static String convertCommand() {
		return compileIndependentCommand(CONVERT_COMMAND, appendParameter(LAST_DL_JPEG)
				+ appendParameter(LAST_DL_PDF));
	}

	private static String appendSpace(String string) {
		return string.concat(SPACE);
	}

	private static String appendParameter(String string) {
		return appendSpace(string);
	}

	private static String compileIndependentCommand(String command,
			String parameters) {
		StringBuffer compiledCommand = new StringBuffer("");
		addWindowsFolderPrefixIfNeeded(compiledCommand, command);
		command = appendSpace(command);
		compiledCommand.append(command);
		compiledCommand.append(parameters);
		return compiledCommand.toString();
	}

	private static void addWindowsFolderPrefixIfNeeded(
			StringBuffer compiledCommand, String command) {
		if (isWindows()) {
			compiledCommand.append(WINDOWS_TOOLS_FOLDER + command + "/");
		}

	}

	private static boolean isWindows() {
		return System.getProperty("os.name").contains(WINDOWS);
	}
}
