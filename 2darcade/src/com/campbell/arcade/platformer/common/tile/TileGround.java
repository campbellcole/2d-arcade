package com.campbell.arcade.platformer.common.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.campbell.arcade.platformer.level.Level;

public class TileGround extends Tile {
	
	public TileGround(int x, int y, Level lvl) {
		super("ground", x, y, true, lvl);
		BufferedImage b = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) b.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 16, 16);
		this.register(b);
	}
	
}
