package com.apoapsis.core;

import java.awt.Graphics;

import com.apoapsis.core.states.StartState;

public class Game extends GamePanel {
	private static final long serialVersionUID = 1L;
	private StateScheduler stateScheduler;
	
	public Game() {
		stateScheduler = new StateScheduler(new StartState());
	}

	@Override
	protected void update(double delta) {
		stateScheduler.startExecuting();
	}

	@Override
	protected void draw(Graphics g) {
		stateScheduler.getCurrentState().draw(g);
	}

}
