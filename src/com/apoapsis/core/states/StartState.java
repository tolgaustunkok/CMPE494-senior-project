package com.apoapsis.core.states;

import java.awt.Graphics;

import com.apoapsis.core.State;
import com.apoapsis.facedet.DetectFaces;
import com.apoapsis.gameelements.StartScreen;

public class StartState implements State {
	
	private DetectFaces detectFaces;
	private StartScreen startScreen;

	public StartState() {
		detectFaces = DetectFaces.getInstance();
		startScreen = new StartScreen();
		
		detectFaces.train();
	}

	@Override
	public boolean execute() {
		return startScreen.update(detectFaces);
	}

	@Override
	public State next() {
		return new SceneState(detectFaces);
	}

	@Override
	public void draw(Graphics g) {
		startScreen.draw(g);
	}

}
