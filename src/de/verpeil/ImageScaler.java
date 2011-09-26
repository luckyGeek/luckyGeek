package de.verpeil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

class ImageScaler {
	private static final Logger LOG = Logger.getLogger(ImageScaler.class.getCanonicalName());
	
	void resizeAndOverride(File img, float scaleFactor) {
		try {
			BufferedImage originalImage = ImageIO.read(img);
			BufferedImage resized = resize(originalImage,
					getScaledSize(originalImage.getWidth(), scaleFactor),
					getScaledSize(originalImage.getHeight(), scaleFactor));
			ImageIO.write(resized, getImageFormat(img), img);
		} catch (IOException e) {
			LOG.severe("Error while IO-access for image: " + e.getMessage());
		}
	}

	void resizeAndOverride(File img, int width, int height) {
		try {
			BufferedImage originalImage = ImageIO.read(img);
			BufferedImage resized = resize(originalImage, width, height);
			ImageIO.write(resized, getImageFormat(img), img);
		} catch (IOException e) {
			LOG.severe("Error while IO-access for image: " + e.getMessage());
		}
	}

	private BufferedImage resize(BufferedImage originalImage, int width,
			int height) throws IOException {
		if (originalImage.getWidth() == width && originalImage.getHeight() == height) {
			return originalImage;
		}
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
				: originalImage.getType();
		return resizeImage(originalImage, type, width, height);
	}

	private BufferedImage resizeImage(BufferedImage originalImage, int type,
			int width, int height) {
		BufferedImage result = new BufferedImage(width, height, type);
		Graphics2D graphic = result.createGraphics();
		graphic.drawImage(originalImage, 0, 0, width, height, null);
		graphic.dispose();
		return result;
	}

	private int getScaledSize(int original, float factor) {
		return Float.valueOf(original * factor).intValue();
	}

	private String getImageFormat(File file) {
		int beginIndex = file.getName().lastIndexOf('.') + 1;
		return file.getName().substring(beginIndex);
	}
}
