package de.verpeil;

import java.util.logging.Logger;

public class ImageToPDFConverter {

	private static String SPACE;
	private static String WINDOWS_TOOLS_FOLDER;
	private static String LAST_DL_JPEG;
	private static String LAST_DL_PDF;
	private static String CONVERT_COMMAND;
	private static CharSequence WINDOWS;
	private static final Logger LOG = Logger
			.getLogger(ImageToPDFConverter.class.getCanonicalName());
	private String IMAGICK_FOLDER;

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
		IMAGICK_FOLDER = "imagick";
	}

	public void convert() {
		try {
			Process convert = Runtime.getRuntime().exec(convertCommand());
			waitTillFinisched(convert);
		} catch (Exception e) {
			LOG.severe("Can not transform image to pdf: " + e.getMessage());
		}

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

	private String compileIndependentCommand(String command, String parameters) {
		StringBuffer compiledCommand = new StringBuffer("");
		addWindowsFolderPrefixIfNeeded(compiledCommand, command);
		command = appendSpace(command);
		compiledCommand.append(command);
		compiledCommand.append(parameters);
		return compiledCommand.toString();
	}

	private void addWindowsFolderPrefixIfNeeded(StringBuffer compiledCommand,
			String command) {
		if (isWindows()) {
			compiledCommand.append(WINDOWS_TOOLS_FOLDER + IMAGICK_FOLDER + "/");
		}

	}

	private boolean isWindows() {
		return System.getProperty("os.name").contains(WINDOWS);
	}

	private void waitTillFinisched(Process process) throws Exception {
		int NOT_FINISHED = -1000;
		int exit = NOT_FINISHED;
		while (exit == NOT_FINISHED) {
			try {
				int exitValue = process.exitValue();
				LOG.info("Exit Code: " + exitValue + "\n");
				exit = exitValue;
				if (exitValue != -1000 && exitValue != 0) {
					throw new Exception("Process terminated with error-code: "
							+ exit);
				}
			} catch (IllegalThreadStateException e) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
