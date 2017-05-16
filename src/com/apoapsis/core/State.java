package com.apoapsis.core;

import java.awt.Graphics;

public interface State {
	public boolean execute();
	public void draw(Graphics g);
	public State next();
}
