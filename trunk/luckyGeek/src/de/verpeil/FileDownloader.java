/**********************************
 * FileDownloader.java
 * Part of the project "luckyGeek" from
 * ctvoigt (Christian Voigt), chripo2701  2011.
 *
 * 
 * Email: luckygeek@verpeil.de
 * 
 *
 * 
 **********************************
 * 
 * Downloads an file from HTTP-Server.
 **********************************
 * 
 * This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA 02111-1307, USA.
 */

package de.verpeil;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 * Downloads a file from url. 
 */
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

	private File download(URL url, String dest) {
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
