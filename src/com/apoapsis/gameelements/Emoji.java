package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.GameObject;

public class Emoji implements GameObject {
	
	private BufferedImage image;
	private int x, y;
	
	public Emoji(String emojiName, int x, int y) {
		this.x = x;
		this.y = y;
		
		try {
			image = ImageIO.read(new File("resources/images/" + emojiName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() { }

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}

}
