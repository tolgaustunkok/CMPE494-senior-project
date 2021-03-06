package com.apoapsis.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SpriteAnimator {
	private BufferedImage spriteSheet;
	private BufferedImage frame;
	private int rows;
	private int cols;
	private int currentCol;
	private int currentRow;
	private int frameCount = 0;

	public int x;
	public int y;

	public SpriteAnimator(String spriteSheetName, int frameWidth, int frameHeight) {
		
		spriteSheet = EngineFileHandler.loadImage(spriteSheetName);

		rows = spriteSheet.getHeight() / frameHeight;
		cols = spriteSheet.getWidth() / frameWidth;

		frame = spriteSheet.getSubimage(0, 0, frameWidth, frameHeight);
	}

	public void animate(int startingRow, int finishRow) {
		if (frameCount > 1) {
			if (finishRow <= rows) {
				frame = spriteSheet.getSubimage(currentCol++ * frame.getWidth(), currentRow * frame.getHeight(),
						frame.getWidth(), frame.getHeight());

				if (currentCol >= cols) {
					currentCol = 0;
					currentRow++;
				}

				if (currentRow >= finishRow) {
					currentRow = startingRow;
					currentCol = 0;
				}
			}
			frameCount = 0;
		}
		frameCount++;
	}

	public void draw(Graphics g) {
		g.drawImage(frame, x, y, null);
	}
	
	public BufferedImage getFrame() {
		return frame;
	}
}
