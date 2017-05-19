package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apoapsis.core.EngineFileHandler;
import com.apoapsis.core.GameObject;

public class Bag implements GameObject {
	
	public int x;
	public int y;
	private BufferedImage bag;
	
	public Bag(int x, int y) {
		this.x = x;
		this.y = y;
		
		bag = EngineFileHandler.loadImage("bag.png");
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bag, x, y, null);
	}

}
