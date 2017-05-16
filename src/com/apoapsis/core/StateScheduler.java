package com.apoapsis.core;

public class StateScheduler {
	private State currentState;
	
	public StateScheduler(State startState) {
		currentState = startState;
	}
	
	public void startExecuting() {
		if (currentState.execute()) {
			currentState = currentState.next();
		}
	}
	
	public State getCurrentState() {
		return currentState;
	}
}
