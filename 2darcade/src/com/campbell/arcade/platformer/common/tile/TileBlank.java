package com.campbell.arcade.platformer.common.tile;

import com.campbell.arcade.platformer.level.Level;

public class TileBlank extends Tile {

	public TileBlank(int x, int y, Level lvl) {
		super("tileblank", x, y, false, lvl);
		this.register(null);
	}
	
}
