package com.campbell.arcade.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

public class Settings {
	
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;
	
	public static final int FPS = 60;
	public static int WIDTH = DEFAULT_WIDTH;
	public static int HEIGHT = DEFAULT_HEIGHT;
	
	public static int POSTWIDTH = 0;
	public static int POSTHEIGHT = 0;
	
	public static int INSET_LEFT = 0;
	public static int INSET_RIGHT = 0;
	public static int INSET_TOP = 0;
	public static int INSET_BOTTOM = 0;
	
	public static String FONT;
	
	public static Font getFont(int size) {
		return Font.decode(FONT + "-" + size);
	}
	
	public static Color BG = Color.LIGHT_GRAY;
	public static Color TXT = Color.decode("#d6d6d6");
	public static Color SEL = Color.decode("#9d34c1");
	
	public static void sel(Graphics2D g) {
		g.setColor(Settings.SEL);
		g.setFont(Settings.getFont(18));
	}
	
	public static void desel(Graphics2D g) {
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(16));
	}
	
	public static void setInsets(Insets i) {
		INSET_LEFT = i.left;
		INSET_RIGHT = i.right;
		INSET_TOP = i.top;
		INSET_BOTTOM = i.bottom;
		POSTWIDTH = WIDTH + INSET_LEFT + INSET_RIGHT;
		POSTHEIGHT = HEIGHT + INSET_TOP + INSET_BOTTOM;
	}
	
	public static void reloadPost() {
		POSTWIDTH = WIDTH + INSET_LEFT + INSET_RIGHT;
		POSTHEIGHT = HEIGHT + INSET_TOP + INSET_BOTTOM;
	}
	
	public static Image getIcon() {
		BufferedImage b = new BufferedImage(15, 15, BufferedImage.TYPE_INT_RGB);
		Graphics g = b.getGraphics();
		g.setColor(Color.RED);
		g.fillRect(0, 0, 5, 5);
		g.setColor(Color.WHITE);
		g.fillRect(5, 5, 10, 10);
		g.setColor(Color.BLUE);
		g.fillRect(10, 10, 15, 15);
		return b;
	}
	
}
