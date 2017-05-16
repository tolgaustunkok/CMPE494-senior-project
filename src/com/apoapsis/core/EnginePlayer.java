package com.apoapsis.core;

import java.util.HashMap;

public class EnginePlayer {
	private final String SOUND_DIR = "resources/sounds/";
	private HashMap<String, PlayClip> sounds = new HashMap<>();
	private PlayClip currentlyPlaying = null;

	public void loadSound(String name, String fileName) {
		PlayClip playClip = new PlayClip(SOUND_DIR + fileName);
		sounds.put(name, playClip);
	}

	public boolean playSound(String name) {		
		if (currentlyPlaying == null) {
			currentlyPlaying = sounds.get(name);
			currentlyPlaying.play();
			return true;
		} else {
			if (!currentlyPlaying.isPlaying()) {
				currentlyPlaying = null;
			}
			return false;
		}
	}
}
