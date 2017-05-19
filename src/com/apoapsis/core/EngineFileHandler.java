package com.apoapsis.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EngineFileHandler {
	public static BufferedImage loadImage(String image) {
		try {
			return ImageIO.read(new File("resources/images/" + image));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
