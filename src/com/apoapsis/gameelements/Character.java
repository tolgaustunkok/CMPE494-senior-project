package com.apoapsis.gameelements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.apoapsis.core.GameObject;

public abstract class Character implements GameObject {

	protected List<String> spriteFiles = new ArrayList<>();
	protected List<BufferedImage> sprites = new ArrayList<>();
	protected int x, y;
	protected int currentSpriteIndex;

	public Character(String spritePrefix, int defaultSpriteIndex, int x, int y) {
		System.out.println("Loading character files...");

		Collection<File> files = FileUtils.listFiles(new File("resources/images/"), new String[] { "png" }, false);

		for (File f : files) {
			if (f.toString().matches("resources/images/" + spritePrefix + "[0-9]+.png")) {
				// spriteFiles.add(ImageIO.read(f));
				spriteFiles.add(f.toString());
				System.out.println(f);
			}
		}

		spriteFiles.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		for (int i = 0; i < spriteFiles.size(); i++) {
			try {
				sprites.add(ImageIO.read(new File(spriteFiles.get(i))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		currentSpriteIndex = defaultSpriteIndex;
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
	}

	public abstract void animate(String animationType);

	@Override
	public void draw(Graphics g) {
		g.drawImage(sprites.get(currentSpriteIndex), x, y, null);
	}
}
