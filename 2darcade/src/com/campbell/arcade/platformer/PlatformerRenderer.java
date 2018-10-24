package com.campbell.arcade.platformer;

import java.awt.Graphics2D;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

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
		g.drawImage(Textures.get("placeholder"), 0, 0, null);
		g.drawImage(Textures.get("player"), 16, 0, null);
	}
	
}
