package com.apoapsis.gameelements;

import com.apoapsis.core.Character;

public class SchoolGirl extends Character {

	public int speed;

	public SchoolGirl(int x, int y, int speed) {
		super("school_girl.png", x, y, 128, 269);
		this.speed = speed;
	}

	@Override
	public void update() {
		if (speed != 0) {
			spriteAnimator.animate(1, 2);
		}
		setX(getX() + speed);
	}

}
