package com.campbell.arcade.platformer;

import java.awt.Graphics2D;
import java.awt.Image;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.common.entity.Entity;

public class PlatformerRenderer implements Renderer {

	Graphics2D g;
	Platformer instance;
	
	public PlatformerRenderer(Graphics2D g, Platformer instance) {
		this.g = g;
		this.instance = instance;
	}
	
	@Override
	public void draw() {
		g.setColor(Settings.BG);
		g.fillRect(0, 0, Settings.POSTWIDTH, Settings.POSTHEIGHT);
		for (Entity e : instance.currentLevel.ld.getEntities()) {
			Image img = Textures.get(e.getClass());
			g.drawImage(img, e.x, e.y, null);
		}
	}
	
}
