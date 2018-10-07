package com.campbell.arcade.testgame;

import java.awt.Graphics2D;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class TestRenderer implements Renderer {

	Graphics2D g;
	
	public int x = 0, y = 0;
	
	public TestRenderer(Graphics2D g) {
		this.g = g;
	}

	@Override
	public void draw() {
		g.setColor(Settings.BG);
		g.fillRect(0, 0, Settings.WIDTH, Settings.HEIGHT);
		g.setFont(Settings.getFont(12));
		g.setColor(Settings.TXT);
		g.drawString("testing", x, y);
	}

}
