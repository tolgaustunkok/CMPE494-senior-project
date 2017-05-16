package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.GameObject;
import com.apoapsis.facedet.DetectFaces;

public class Scene1 implements GameObject {
	private BufferedImage background;
	private DetectFaces detectFaces;
	private Character child, mom;
	private boolean getFace = true;
	private int frameCounter = 0;
	private boolean success = false;

	public Scene1(DetectFaces detectFaces) {
		this.detectFaces = detectFaces;

		child = new Child(450, 400);
		mom = new Mom(280, 300);

		try {
			background = ImageIO.read(new File("resources/images/scene1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		
		// TODO konusma bitince getFace true olacak
		// TODO gulme emojisi cikacak
		
		if (getFace) {
			if (frameCounter > 90) {
				String face = detectFaces.detectFaces();

				System.out.println(face);

				if (face.equalsIgnoreCase("happy")) {
					success = true;
					getFace = false;
				}
				frameCounter = 0;
			}
			frameCounter++;
		}

		if (success) {
			child.animate("walk_right");
			// TODO tasvip eden bi konusma
			// TODO konfeti tarzi alkis falan
			
			
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		mom.draw(g);
		child.draw(g);
	}
}
