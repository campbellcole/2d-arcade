package com.campbell.arcade.platformer.common.tile;

import java.awt.image.BufferedImage;

import com.campbell.arcade.common.Settings;

public class TileBlank extends Tile {

	public TileBlank() {
		super("tileblank");
		BufferedImage b = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		b.getGraphics().setColor(Settings.BG);
		b.getGraphics().fillRect(0, 0, 16, 16);
	}
	
}
