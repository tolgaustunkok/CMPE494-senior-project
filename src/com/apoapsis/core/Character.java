package com.apoapsis.core;

import java.awt.Graphics;

public abstract class Character implements GameObject {

	protected SpriteAnimator spriteAnimator;

	public Character(String spritesheetName, int x, int y, int frameWidth, int frameHeight) {
		System.out.println("Loading character file...");
		spriteAnimator = new SpriteAnimator(spritesheetName, frameWidth, frameHeight);
		spriteAnimator.x = x;
		spriteAnimator.y = y;
	}

	@Override
	public void draw(Graphics g) {
		spriteAnimator.draw(g);
	}
	
	public int getWidth() {
		return spriteAnimator.getFrame().getWidth();
	}
	
	public int getHeight() {
		return spriteAnimator.getFrame().getHeight();
	}

	public int getX() {
		return spriteAnimator.x;
	}

	public void setX(int x) {
		spriteAnimator.x = x;
	}

	public int getY() {
		return spriteAnimator.y;
	}

	public void setY(int y) {
		spriteAnimator.y = y;
	}
	
	
}
