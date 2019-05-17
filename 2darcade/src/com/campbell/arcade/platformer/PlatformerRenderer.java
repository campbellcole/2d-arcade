package com.campbell.arcade.platformer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;

public class PlatformerRenderer implements Renderer {

	Graphics2D g;
	Platformer instance;
	
	Image background;
	
	String message;
	int msgTimer;
	
	public PlatformerRenderer(Graphics2D g, Platformer instance) {
		this.g = g;
		this.instance = instance;
		this.message = "";
		this.msgTimer = 0;
		generateBackground();
	}
	
	public void generateBackground() {
		try {
			BufferedImage bg = new BufferedImage(PlatformerSettings.WIDTH, PlatformerSettings.HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D bgG = bg.createGraphics();
			Image single = ImageIO.read(Textures.getURL("mario.jpg"));
			int sx = single.getWidth(null);
			int sy = single.getHeight(null);
			int xmod = 0, ymod = 0;
			int xmult = PlatformerSettings.WIDTH;
			int ymult = PlatformerSettings.HEIGHT;
			while (ymult > 0) {
				while (xmult > 0) {
					bgG.drawImage(single, xmod, ymod, null);
					xmult -= sx;
					xmod += sx;
				}
				xmult = PlatformerSettings.WIDTH;
				xmod = 0;
				ymult -= sy;
				ymod += sy;
			}
			background = bg;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void displayMessage(String message) {
		this.message = message;
		this.msgTimer = 240;
	}
	
	public void displayMessageHard(String message) {
		this.message = message;
		this.msgTimer = -2;
	}
	
	public void hideMessageHard() {
		this.msgTimer = 0;
	}
	
	@Override
	public void draw() {
		g.drawImage(background, 0, 0, null);
		if (!instance.loading) {
			for (Tile t : instance.currentLevel.ld.getTiles()) {
				if (t.getName().equals("tileblank")) continue;
				Image img = Textures.get(t.getClass());
				g.drawImage(img, t.x, t.y, null);
			}
			for (Entity e : instance.currentLevel.ld.getEntities()) {
				Image img = Textures.get(e.getClass());
				g.drawImage(img, e.x, e.y, null);
			}
		}
		if (msgTimer > 0 || msgTimer == -2) {
			g.setFont(Settings.getFont(30));
			FontMetrics m = g.getFontMetrics();
			int cX = (Settings.POSTWIDTH - m.stringWidth(message))/2;
			int cY = ((Settings.POSTHEIGHT - m.getHeight())/2);
			g.setColor(Color.black);
			g.drawString(message, cX, cY);
			if (msgTimer != -2) msgTimer--;
		}
	}
	
}
