package com.apoapsis.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteAnimator {
	private final String SPRITE_SHEET_DIR = "resources/images/";
	private BufferedImage spriteSheet;
	private BufferedImage frame;
	private int rows;
	private int cols;
	
	public SpriteAnimator(String spriteSheetName, int frameWidth, int frameHeight) {
		try {
			spriteSheet = ImageIO.read(new File(SPRITE_SHEET_DIR + spriteSheetName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		rows = spriteSheet.getHeight() / frameHeight;
		cols = spriteSheet.getWidth() / frameWidth;
		
		frame = spriteSheet.getSubimage(0, 0, frameWidth, frameHeight);
	}
}
