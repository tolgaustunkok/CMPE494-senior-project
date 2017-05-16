package com.apoapsis.gameelements;

public class Child extends Character {
	
	private int frameCounter = 0;

	public Child(int x, int y) {
		super("male", 0, x, y);
	}

	@Override
	public void animate(String animationType) {
		if (animationType.equalsIgnoreCase("walk_right")) {
			x += 1;
			
			if (frameCounter == 5) {
				currentSpriteIndex++;
				frameCounter = 0;
				if (currentSpriteIndex == 20) {
					currentSpriteIndex = 0;
				}
			}
			
			frameCounter++;
		}
	}
}
