package com.apoapsis.core;

import java.util.HashMap;
import java.util.LinkedList;

public class EnginePlayer {
	private final String SOUND_DIR = "resources/sounds/";
	private HashMap<String, PlayClip> sounds = new HashMap<>();
	private PlayClip currentlyPlaying = null;
	private static EnginePlayer uniqueInstance = null;
	private LinkedList<PlayClip> playlist = new LinkedList<>();
	private LinkedList<Boolean> playableClips = new LinkedList<>();

	private EnginePlayer() {
	}

	public static EnginePlayer getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new EnginePlayer();
		}

		return uniqueInstance;
	}

	public void loadSound(String name, String fileName) {
		PlayClip playClip = new PlayClip(SOUND_DIR + fileName);
		sounds.put(name, playClip);
	}

	public void addToPlaylist(String clipName) {
		playlist.add(sounds.get(clipName));
		playableClips.add(true);
	}

	public void playPlaylist() {

		if (!playlist.isEmpty()) {
			PlayClip p = playlist.getFirst();

			if (playSound(p)) {
				playlist.removeFirst();
			}
		}
	}
	
	public boolean isPlaylistEmpty() {
		return playlist.isEmpty();
	}

	public void clearPlaylist() {
		playlist.clear();
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

	public boolean playSound(PlayClip name) {
		if (currentlyPlaying == null) {
			currentlyPlaying = name;
			currentlyPlaying.play();
			return true;
		} else {
			if (!currentlyPlaying.isPlaying()) {
				currentlyPlaying = null;
			}
			return false;
		}
	}

	public void changeVolume(float change, String sound) {
		PlayClip pc = sounds.get(sound);
		pc.changeVolume(change);
	}

	public boolean playConcurrently(String name) {
		PlayClip pc = sounds.get(name);
		if (pc != null) {
			pc.play();
			return true;
		}
		return false;
	}
}
