package de.verpeil.luckygeek;

import java.io.File;

public class Folder {
	File folder;

	public Folder(String folder) {
		this.folder = new File(folder);
	}

	public Folder(File folder) {
		this.folder = new File(folder.getPath());
	}

	public String getAbsolutPath() {
		return folder.getAbsolutePath();
	}

	public File getAbsolutFile() {
		return folder.getAbsoluteFile();
	}

	@Override
	public String toString() {
		return folder.toString();
	}

}
