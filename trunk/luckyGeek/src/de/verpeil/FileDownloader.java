package de.verpeil;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

class FileDownloader {
	private static final Logger LOG = Logger.getLogger(FileDownloader.class.getCanonicalName());
	
	File download(String url, String dest) {
		File result = null;
		try {
			result = download(new URL(url), dest);
		} catch (Exception e) {
			LOG.severe(String.format("Can not establish connection to url '%s'. Message: %s.", url, e.getMessage()));
		}
		return result;
	}

	File download(URL url, String dest) {
		File result = null;
		try {
			result = new File(dest);
			FileUtils.copyURLToFile(url, result);
		} catch (Exception e) {
			LOG.severe(String.format("Can not download file from url '%s'. Message: %s.", url, e.getMessage()));
		}
		return result;
	}
}
