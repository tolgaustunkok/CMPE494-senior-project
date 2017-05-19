package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.apoapsis.core.Character;
import com.apoapsis.core.EngineFileHandler;
import com.apoapsis.core.EnginePlayer;
import com.apoapsis.core.GameObject;
import com.apoapsis.facedet.DetectFaces;

public class Scene1 implements GameObject {
	private BufferedImage background;
	private DetectFaces detectFaces;
	private Character mom;
	private Child child;
	private boolean getFace = true;
	private int frameCounter = 0;
	private boolean success = false;
	private EnginePlayer enginePlayer;
	private boolean claps = true;
	private GameObject emojiLaugh;
	private boolean drawEmoji = false;
	
	public boolean canMoveToNextScene = false;

	public Scene1(DetectFaces detectFaces) {
		this.detectFaces = detectFaces;

		child = new Child(450, 400, 2);
		mom = new Mom();
		emojiLaugh = new Emoji("happy_im.png", 800, 50);
		enginePlayer = EnginePlayer.getInstance();
		background = EngineFileHandler.loadImage("scene1.png");
		
		enginePlayer.clearPlaylist();
		enginePlayer.addToPlaylist("scene1-1");
		enginePlayer.addToPlaylist("scene1-2");
		enginePlayer.addToPlaylist("scene1-3");
	}

	@Override
	public void update() {

		enginePlayer.playPlaylist();

		if (enginePlayer.isPlaylistEmpty() && getFace) {
			drawEmoji = true;
			if (frameCounter > 90) {
				String face = detectFaces.detectFaces();

				System.out.println(face);

				if (face != null && face.equalsIgnoreCase("happy")) {
					success = true;
					getFace = false;
				}
				frameCounter = 0;
			}
			frameCounter++;
		}

		if (success) {
			child.update();
			if (claps) {
				claps = !enginePlayer.playSound("claps");
			}
			
			if (child.collide()) {
				canMoveToNextScene = true;
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
		if (drawEmoji && !success) {
			emojiLaugh.draw(g);
		}
	}
	
	public GameObject nextScene() {
		return new Scene2();
	}
}
