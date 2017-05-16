package com.apoapsis.core.states;

import java.awt.Graphics;

import com.apoapsis.core.GameObject;
import com.apoapsis.core.State;
import com.apoapsis.facedet.DetectFaces;
import com.apoapsis.gameelements.Scene1;

public class SceneState implements State {
	private GameObject scene;
	
	public SceneState(DetectFaces detectFaces) {
		scene = new Scene1(detectFaces);
	}

	@Override
	public boolean execute() {
		scene.update();
		return false;
	}

	@Override
	public void draw(Graphics g) {
		scene.draw(g);
	}

	@Override
	public State next() {
		return null;
	}
}
