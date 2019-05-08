package com.campbell.arcade.platformer;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;

public class PlatformerRenderer implements Renderer {

	Graphics2D g;
	Platformer instance;
	
	String message;
	int msgTimer;
	
	public PlatformerRenderer(Graphics2D g, Platformer instance) {
		this.g = g;
		this.instance = instance;
		this.message = "";
		this.msgTimer = 0;
	}
	
	public void displayMessage(String message) {
		this.message = message;
		this.msgTimer = 240;
	}
	
	@Override
	public void draw() {
		for (Tile t : instance.currentLevel.ld.getTiles()) {
			Image img = Textures.get(t.getClass());
			g.drawImage(img, t.x, t.y, null);
		}
		for (Entity e : instance.currentLevel.ld.getEntities()) {
			Image img = Textures.get(e.getClass());
			g.drawImage(img, e.x, e.y, null);
		}
		if (msgTimer-- > 0) {
			g.setFont(Settings.getFont(30));
			FontMetrics m = g.getFontMetrics();
			int cX = (Settings.POSTWIDTH - m.stringWidth(message))/2;
			int cY = ((Settings.POSTHEIGHT - m.getHeight())/2);
			g.setColor(Settings.TXT);
			g.drawString(message, cX, cY);
		}
	}
	
}
