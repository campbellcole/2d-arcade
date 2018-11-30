package com.campbell.arcade.platformer.common.tile;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.level.Level;

public class Tile extends Drawable {
	
	boolean solid;
	
	public Tile(String name, int x, int y, boolean solid, Level lvl) {
		super(name, x, y, lvl);
		this.solid = solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
}
