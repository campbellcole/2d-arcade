package com.campbell.arcade.platformer.common.tile;

import com.campbell.arcade.platformer.level.Level;

public class TileBlock extends Tile {

	public TileBlock(int x, int y, Level lvl) {
		super("tileblock", x, y, false, lvl);
		this.register(null);
	}
	
	
	
}
