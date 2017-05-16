package com.apoapsis.gameelements;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.apoapsis.core.Game;
import com.apoapsis.core.Utilities;
import com.apoapsis.facedet.DetectFaces;

public class StartScreen {

	private double animateValue = 0.0;
	private BufferedImage background;
	private BufferedImage face;
	private BufferedImage rainbow;
	private int countFPS = 0;
	private boolean voice1 = true, voice2 = true, voice3 = true;

	public StartScreen() {
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadImages() throws IOException {
		background = ImageIO.read(new File("resources/images/background.png"));
		face = toBufferedImage(
				ImageIO.read(new File("resources/images/face.png")).getScaledInstance(294, 299, Image.SCALE_SMOOTH));
		rainbow = ImageIO.read(new File("resources/images/rainbow.png"));
	}

	private BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public boolean update(DetectFaces detectFaces) {
		if (animateValue > Double.MAX_VALUE - 1) {
			animateValue = 0.0;
		}

		if (voice1) {
			voice1 = !Utilities.playSound("resources/voices/hosgeldin2.wav");
		}

		if (voice2) {
			voice2 = !Utilities.playSound("resources/voices/isik.wav");
		}

		if (voice3) {
			voice3 = !Utilities.playSound("resources/voices/kamera.wav");
		}

		if (countFPS > 150) {
			if (detectFaces.detectFaces() != null) {
				return true;
			}
			countFPS = 0;
		}

		if (!voice1 && !voice2 && !voice3) {
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
