package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.GameObject;

public class Scene2 implements GameObject {

	private BufferedImage background;
	
	public Scene2() {
		try {
			background = ImageIO.read(new File("resources/images/scene2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}

}
