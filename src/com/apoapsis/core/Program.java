package com.apoapsis.core;

import javax.swing.JFrame;

import org.opencv.core.Core;

public class Program {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		JFrame gameFrame = new JFrame("Team Apoapsis Simple Game Engine");
		gameFrame.add(new Game());
		gameFrame.setVisible(true);;
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.pack();
	}
}
