package com.apoapsis.gameelements;

import com.apoapsis.core.Character;
import com.apoapsis.core.Game;

public class Child extends Character {

	public Child(int x, int y) {
		super("child.png", x, y, 165, 270);
	}
	
	@Override
	public void update() {
		spriteAnimator.animate(0, 3);
		setX(getX() + 2);
	}
	
	public boolean collide() {
		return getX() + (165 / 2) > Game.PWIDTH;
	}
}
