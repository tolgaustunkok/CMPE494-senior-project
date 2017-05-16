package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.EnginePlayer;
import com.apoapsis.core.GameObject;
import com.apoapsis.facedet.DetectFaces;

public class Scene1 implements GameObject {
	private BufferedImage background;
	private DetectFaces detectFaces;
	private Character child, mom;
	private boolean getFace = false;
	private int frameCounter = 0;
	private boolean success = false;
	private EnginePlayer enginePlayer;
	private boolean voice1 = true, voice2 = true, voice3 = true, claps = true;
	private boolean soundPlayable = true;
	private GameObject emojiLaugh;

	public Scene1(DetectFaces detectFaces) {
		this.detectFaces = detectFaces;

		child = new Child(450, 400);
		mom = new Mom(280, 300);
		emojiLaugh = new Emoji("happy_im.png", 800, 50);
		enginePlayer = EnginePlayer.getInstance();

		try {
			background = ImageIO.read(new File("resources/images/scene1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {

		if (soundPlayable) {
			if (voice1) {
				voice1 = !enginePlayer.playSound("school");
			}

			if (voice2) {
				voice2 = !enginePlayer.playSound("goodbye");
			}

			if (voice3) {
				voice3 = !enginePlayer.playSound("smile");
			}

			if (!voice1 && !voice2 && !voice3) {
				getFace = true;
			}
		}

		if (getFace) {
			if (frameCounter > 90) {
				String face = detectFaces.detectFaces();

				System.out.println(face);

				if (face != null && face.equalsIgnoreCase("happy")) {
					success = true;
					getFace = false;
					soundPlayable = false;
				}
				frameCounter = 0;
			}
			frameCounter++;
		}

		if (success) {
			child.animate("walk_right");
			if (claps) {
				claps = !enginePlayer.playSound("claps");
			}
			// TODO tasvip eden bi konusma
			// TODO konfeti tarzi alkis falan
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		mom.draw(g);
		child.draw(g);
		if (getFace) {
			emojiLaugh.draw(g);
		}
	}
}
