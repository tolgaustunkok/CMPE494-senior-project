package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.apoapsis.core.EngineFileHandler;
import com.apoapsis.core.EnginePlayer;
import com.apoapsis.core.GameObject;
import com.apoapsis.facedet.DetectFaces;

public class Scene2 implements GameObject {

	private BufferedImage background;
	private List<GameObject> gameObjects = new ArrayList<>();
	private Child child;
	private SchoolGirl schoolGirl;
	private boolean childStopped = false, girlStopped = false, claps = false, canMove = true;

	public Scene2() {
		// gameObjects.add(new Bag(700, 440));
		gameObjects.add(schoolGirl = new SchoolGirl(1280, 300, -2));
		gameObjects.add(child = new Child(0, 300, 2));

		background = EngineFileHandler.loadImage("school.png");
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	private int frameCounter = 0;

	@Override
	public void update() {
		for (GameObject go : gameObjects) {
			go.update();
		}

		if (canMove && child.getX() >= 435 - child.getWidth() / 2) {
			child.speed = 0;
			childStopped = true;
		}

		if (canMove && schoolGirl.getX() <= 875 - schoolGirl.getWidth() / 2) {
			schoolGirl.speed = 0;
			girlStopped = true;
		}

		if (girlStopped && childStopped) {
			// TODO Meltemin konusmasi
			// If meltemin konusmasi bittiyse
			if (frameCounter > 90) {
				String face = DetectFaces.getInstance().detectFaces();

				if (face.equalsIgnoreCase("surprise")) {
					claps = true;
					canMove = false;
					child.speed = 2;
					schoolGirl.speed = -2;
					girlStopped = false;
					childStopped = false;
				}
				frameCounter = 0;
			}
			// TODO Konusmadan sonra bir yuz ifadesi al ve degerlendir
			// TODO Basarili olunduysa alkis ve devam

			frameCounter++;
		}

		if (claps) {
			claps = !EnginePlayer.getInstance().playSound("claps");
			System.out.println("Value of claps is: " + claps);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);

		for (GameObject go : gameObjects) {
			go.draw(g);
		}
	}

}
