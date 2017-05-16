package com.apoapsis.core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Utilities {
	public static volatile boolean SOUND_FREE = true;

	public static boolean playSound(String filename) {
		if (SOUND_FREE) {
			SOUND_FREE = false;
			Thread t = new Thread(() -> {
				try {
					AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename));
					Clip test = AudioSystem.getClip();

					test.open(ais);
					test.start();

					while (!test.isRunning())
						Thread.sleep(10);
					while (test.isRunning())
						Thread.sleep(10);

					test.close();
					SOUND_FREE = true;
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			t.start();
			return true;
		} else {
			return false;
		}
	}
}
