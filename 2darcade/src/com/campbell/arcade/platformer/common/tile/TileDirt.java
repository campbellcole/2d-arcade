package com.campbell.arcade.platformer.common.tile;

import com.campbell.arcade.platformer.level.Level;

public class TileDirt extends Tile {
	
	public TileDirt(int x, int y, Level lvl) {
		super("dirt", x, y, true, lvl);
		this.register();
	}
	
}
