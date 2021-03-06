package com.campbell.arcade.platformer.common.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.level.Level;

public class TilePlaceholder extends Tile {

	public TilePlaceholder(int x, int y, Level lvl) {
		super("tileplaceholder", x, y, false, lvl);
		BufferedImage b = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setColor(Settings.SEL);
		g.fillRect(0, 0, 16, 16);
		this.register(b);
	}
	
}
