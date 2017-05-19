package com.apoapsis.gameelements;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.EnginePlayer;
import com.apoapsis.core.Game;
import com.apoapsis.core.Utilities;
import com.apoapsis.facedet.DetectFaces;

public class StartScreen {

	private double animateValue = 0.0;
	private BufferedImage background;
	private BufferedImage face;
	private BufferedImage rainbow;
	private int countFPS = 0;
	private boolean bg = true;
	private EnginePlayer enginePlayer;

	public StartScreen() {
		enginePlayer = EnginePlayer.getInstance();
		
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadSounds();
	}
	
	private void loadSounds() {
		enginePlayer.loadSound("welcome", "hosgeldin2.wav");
		enginePlayer.loadSound("lights", "isik.wav");
		enginePlayer.loadSound("camera", "kamera.wav");
		enginePlayer.loadSound("scene1-1", "scene1-1.wav");
		enginePlayer.loadSound("scene1-2", "scene1-2.wav");
		enginePlayer.loadSound("scene1-3", "scene1-3.wav");
		enginePlayer.loadSound("claps", "claps.wav");
		enginePlayer.loadSound("bg", "background.wav");
		
		enginePlayer.changeVolume(-10.0f, "bg");
		
		enginePlayer.addToPlaylist("welcome");
		enginePlayer.addToPlaylist("lights");
		enginePlayer.addToPlaylist("camera");
	}

	private void loadImages() throws IOException {
		background = ImageIO.read(new File("resources/images/background.png"));
		face = Utilities.toBufferedImage(
				ImageIO.read(new File("resources/images/face.png")).getScaledInstance(294, 299, Image.SCALE_SMOOTH));
		rainbow = ImageIO.read(new File("resources/images/rainbow.png"));
	}

	public boolean update(DetectFaces detectFaces) {
		if (animateValue > Double.MAX_VALUE - 1) {
			animateValue = 0.0;
		}
		
		if (bg) {
			bg = !enginePlayer.playConcurrently("bg");
		}
		
		enginePlayer.playPlaylist();

		if (countFPS > 300) {
			if (detectFaces.detectFaces() != null) {
				return true;
			}
			countFPS = 0;
		}
		
		if (enginePlayer.isPlaylistEmpty()) {
			countFPS++;
		}

		return false;

	}

	private double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	public void draw(Graphics g) {
		int y = (int) map(Math.sin(animateValue), -1, 1, Game.PHEIGHT / 4.0 - (100 / 2.0) - 25,
				Game.PHEIGHT / 4.0 - (100 / 2.0) + 25);
		g.drawImage(background, 0, 0, null);
		g.drawImage(rainbow, Game.PWIDTH / 2 - (511 / 2), y + 50, null);
		g.drawImage(face, Game.PWIDTH / 2 - (294 / 2), y, null);
		g.setFont(new Font("comicsansms", Font.PLAIN, 50));
		g.drawString("Team Apoapsis", Game.PWIDTH / 2 - g.getFontMetrics().stringWidth("Team Apoapsis") / 2, y + 450);

		animateValue += 0.05;
	}
}
