package de.verpeil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

class Memory {
	private static final Logger LOG = Logger.getLogger(Memory.class
			.getCanonicalName());
	private static final File MEMORY_FILE = new File("./conf/memory.properties");
	private final Properties memory = new Properties();

	Memory() {
		InputStream ins = null;
		try {
			ins = new FileInputStream(MEMORY_FILE);
			memory.load(ins);
		} catch (Exception e) {
			LOG.severe("Can not load memory. Message: " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(ins);
		}
	}

	String getUrl() {
		return memory.getProperty("url", "");
	}

	void setUrl(String url) {
		memory.setProperty("url", url);
	}
	
	void save() {
		OutputStream out = null;
		try {
			out = new FileOutputStream(MEMORY_FILE);
			memory.store(out, "");
		} catch (Exception e) {
			LOG.severe("Can not save memory. Message: " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
}
