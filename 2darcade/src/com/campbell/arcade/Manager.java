package com.campbell.arcade;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.dodgeblock.DodgeBlockMenu;
import com.campbell.arcade.feedback.FeedbackScene;
import com.campbell.arcade.instructions.Instructions;
import com.campbell.arcade.introscene.IntroScene;
import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.editor.PlatformerEditor;
import com.campbell.arcade.snake.Snake;

public class Manager extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final String version = "1.8.2-release";

	public static List<Game> games = new ArrayList<Game>();
	public static Manager instance;

	Thread thread;

	public Game currentGame;

	BufferedImage img;
	Graphics2D g;
	Graphics gr;

	boolean running = false;

	public Manager() {
		thread = new Thread(this);
		instance = this;
	}

	public synchronized void begin() {
		System.out.println("[Manager] initializing...");
		initialize();
		System.out.println("[Manager] registering games...");
		registerGames();
		System.out.println("[Manager] loading intro scene...");
		setGame(new IntroScene(g));
		running = true;
		thread.start();
	}

	public synchronized void end() {
		System.out.println("[Manager] goodbye :)");
		System.exit(0);
	}

	public Graphics2D modifySize(int x, int y) {
		System.out.println("[Manager] resizing window...");
		Settings.WIDTH = x;
		Settings.HEIGHT = y;
		img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) img.getGraphics();
		Settings.reloadPost();
		setSize(Settings.POSTWIDTH, Settings.POSTHEIGHT);
		return g;
	}

	private void initialize() {
		System.out.println("[Manager] registering font...");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			Font fn = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/res/ka1.ttf"));
			ge.registerFont(fn);
			Settings.FONT = fn.getFontName();
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("[Manager] initializing key listener...");
		GameKeyListener.initialize();
		addKeyListener(GameKeyListener.getListener());
		
		System.out.println("[Manager] creating image buffer...");
		img = new BufferedImage(Settings.WIDTH, Settings.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) img.getGraphics();
		
		System.out.println("[Manager] setting up window...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setSize(Settings.WIDTH, Settings.HEIGHT);
		setIconImage(Settings.getIcon());
		setResizable(false);
		setTitle("2D Arcade");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		setVisible(true);
		gr = getGraphics();
		Settings.setInsets(getInsets());
		setSize(Settings.POSTWIDTH, Settings.POSTHEIGHT);
	}

	private void registerGames() {
		games.add(new Instructions(null));
		games.add(new DodgeBlockMenu(null));
		games.add(new Snake(null));
		games.add(new Platformer(null));
		games.add(new PlatformerEditor(null));
		games.add(new FeedbackScene(null));
		for (Game g : games) {
			System.out.println("[Manager] registered " + g.getName());
		}
	}

	boolean justSwitched = true;

	public void setGame(Game g) {
		System.out.println("[Manager] loading new game instance...");
		try {
			Class<?> c = Class.forName(g.getClass().getName());
			Constructor<?> ct = c.getConstructor(Graphics2D.class);
			g = (Game) ct.newInstance(new Object[] { this.g });
		} catch (Exception e) {
			e.printStackTrace();
			end();
		}
		GameKeyListener.reset();
		currentGame = g;
		currentGame.resize();
		currentGame.initialize();
		System.gc();
	}
	
	public void setGameFromInstance(Game g) {
		System.out.println("[Manager] loading stashed game instance...");
		GameKeyListener.reset();
		currentGame = g;
		currentGame.resize();
		currentGame.initialize();
	}

	@Override
	public void run() {
		ArrayList<Integer> p;
		while (running) {
			long time = System.currentTimeMillis();
			p = GameKeyListener.getPendingKeys();
			if (p.indexOf(KeyEvent.VK_ESCAPE) != -1) {
				if (currentGame instanceof IntroScene) {
					end();
				} else if (currentGame instanceof Platformer && PlatformerSettings.isTesting) {
					setGameFromInstance(PlatformerEditor.stash);
					PlatformerSettings.isTesting = false;
				} else {
					GameKeyListener.reset();
					setGame(new IntroScene(g));
				}
			}
			currentGame.update();
			currentGame.getRenderer().draw();
			gr.drawImage(img, Settings.INSET_LEFT, Settings.INSET_TOP, this);
			time = (1000 / 60) - (System.currentTimeMillis() - time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

}
