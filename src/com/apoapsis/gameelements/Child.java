package com.apoapsis.gameelements;

import com.apoapsis.core.Character;
import com.apoapsis.core.Game;

public class Child extends Character {

	public int speed;

	public Child(int x, int y, int speed) {
		super("child.png", x, y, 165, 270);
		this.speed = speed;
	}

	@Override
	public void update() {
		if (speed != 0) {
			spriteAnimator.animate(0, 3);
		}

		setX(getX() + speed);
	}

	public boolean collide() {
		return getX() + (165 / 2) > Game.PWIDTH;
	}
}
