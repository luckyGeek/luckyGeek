package de.verpeil;

public class Commands {

	public static String DLCommand(String url) {
		return compileIndependentCommand("wget" ," -q -O last.jpg -U Mozilla/5.0 " + url);
	}

	public static String convertCommand() {
		return compileIndependentCommand("convert"," last.jpg last.pdf");
	}
	
	private static String compileIndependentCommand(String command, String parameters) {
		StringBuffer compiledCommand = new StringBuffer("");
		addWindowsFolderPrefixIfNeeded(compiledCommand, command);
		compiledCommand.append(command);		
		compiledCommand.append(parameters);
		return compiledCommand.toString();
	}
	
	private static void addWindowsFolderPrefixIfNeeded(StringBuffer compiledCommand, String command) {
		if (isWindows()) {
			compiledCommand.append("./tools/"+command+"/");
		}
		
	}

	private static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}
}
