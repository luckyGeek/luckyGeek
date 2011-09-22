package de.verpeil;

public class Commands {

	private static final String SPACE = " ";

	public static String DLCommand(String url) {
		return compileIndependentCommand("wget", appendParameter("-q")
				+ appendParameter("-O last.jpg")
				+ appendParameter("-U Mozilla/5.0") + appendParameter(url));
	}

	public static String convertCommand() {
		return compileIndependentCommand("convert", appendParameter("last.jpg")
				+ appendParameter("last.pdf"));
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
			compiledCommand.append("./tools/" + command + "/");
		}

	}

	private static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}
}
