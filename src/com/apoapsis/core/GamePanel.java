package com.apoapsis.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public abstract class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int PWIDTH = 1280;
	public static final int PHEIGHT = 720;
	public static double DELTA;
	public static int MOUSE_X;
	public static int MOUSE_Y;
	public static boolean LEFT_CLICK = false;
	private static final int FPS = 60;
	private static int MAX_FRAME_SKIPS = 5;

	private Thread animator;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private Graphics dbg;
	private Image dbImage;

	public GamePanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));
		setFocusable(true);
		requestFocus();
		addEvents();
	}

	private void addEvents() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ESCAPE) {
					running = false;
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				MOUSE_X = e.getX();
				MOUSE_Y = e.getY();
				System.out.println("X: " + e.getX() + " - Y: " + e.getY());
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				LEFT_CLICK = true;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				LEFT_CLICK = false;
			}
		});
	}

	// Wait for JPanel to added the frame
	@Override
	public void addNotify() {
		super.addNotify();
		startGame();
	}

	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void stopGame() {
		running = false;
	}

	@Override
	public void run() {
		long beforeTime, timeDiff = 0, sleepTime; // Nano seconds
		double period = (1.0 / FPS) * 1000000000L;
		long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;

		while (running) {
			DELTA = timeDiff / 1000000.0;
			gameUpdate(DELTA);
			gameRender();
			paintScreen();

			timeDiff = System.nanoTime() - beforeTime;
			sleepTime = ((long) period - timeDiff) / 1000000;

			if (sleepTime <= 0) {
				sleepTime = 5;
				excess -= sleepTime;
			} else {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

			beforeTime = System.nanoTime();
			
			int skips = 0;
			while (excess > period && skips < MAX_FRAME_SKIPS) {
				excess -= period;
				gameUpdate(DELTA);
				skips++;
			}
		}

		System.exit(0);
	}

	private void paintScreen() {
		Graphics g;

		try {
			g = this.getGraphics();
			if (g != null && dbImage != null) {
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (Exception e) {
			System.err.println("Graphics context error.");
		}
	}

	private void gameUpdate(double delta) {
		if (!gameOver) {
			// TODO Update the game
			update(delta);
		}
	}

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(PWIDTH, PHEIGHT);
			if (dbImage == null) {
				System.err.println("Cannot create dbImage");
				return;
			} else {
				dbg = dbImage.getGraphics();
			}
		}

		dbg.setColor(Color.BLACK);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

		// TODO Draw game elements
		Graphics gTemp = dbg.create();
		draw(gTemp);
		gTemp.dispose();

		if (gameOver) {
			gameOverMessage(dbg);
		}
	}

	private void gameOverMessage(Graphics g) {
		g.drawString("Game over", PWIDTH / 2, PHEIGHT / 2);
	}

	protected abstract void update(double delta);

	protected abstract void draw(Graphics g);
}
