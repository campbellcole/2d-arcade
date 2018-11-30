package com.campbell.arcade.platformer.common.entity;

import com.campbell.arcade.platformer.level.Level;

public class Placeholder extends Entity {

	public Placeholder(int x, int y, Level lvl) {
		super("placeholder", x, y, lvl);
		this.register();
	}
	
}
