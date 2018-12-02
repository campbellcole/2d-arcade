package com.campbell.arcade.platformer.common.tile;

import com.campbell.arcade.platformer.level.Level;

public class TileGround extends Tile {
	
	public TileGround(int x, int y, Level lvl) {
		super("ground", x, y, true, lvl);
		this.register();
	}
	
}
