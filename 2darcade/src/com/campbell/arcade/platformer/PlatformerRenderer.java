package com.campbell.arcade.platformer;

import java.awt.Graphics2D;
import java.awt.Image;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.level.Dictionary;

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
		int xOffset = 0, yOffset = 0;
		for (int x = 0; x < instance.currentLevel.data.length; x++) {
			for (int y = 0; y < instance.currentLevel.data[0].length; y++) {
				char cur = (char) instance.currentLevel.data[x][y];
				String texName = Dictionary.d.get(cur).getName();
				Image img = Textures.get(texName);
				g.drawImage(img, xOffset, yOffset, null);
				xOffset += 16;
			}
			xOffset = 0;
			yOffset += 16;
		}
		g.setFont(Settings.getFont(16));
		g.setColor(Settings.TXT);
		g.drawString(instance.currentLevel.id, 0, 16);
		g.drawString(instance.currentLevel.name, 0, 32);
	}
	
}
