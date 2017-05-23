package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apoapsis.core.EngineFileHandler;
import com.apoapsis.core.GameObject;
import com.apoapsis.core.GamePanel;

public class ContinueButton implements GameObject {
	
	private BufferedImage button;
	private int x, y;
	private Runnable r;
	
	public ContinueButton(int x, int y, Runnable r) {
		this.x = x;
		this.y = y;
		this.r = r;
		button = EngineFileHandler.loadImage("continue_btn.png");
	}

	@Override
	public void update() {
		if (GamePanel.LEFT_CLICK && GamePanel.MOUSE_X > x && GamePanel.MOUSE_X < x + button.getWidth() && GamePanel.MOUSE_Y > y && GamePanel.MOUSE_Y < y + button.getHeight()) {
			r.run();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(button, x, y, null);
	}

}