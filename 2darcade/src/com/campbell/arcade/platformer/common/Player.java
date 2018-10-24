package com.campbell.arcade.platformer.common;

import com.campbell.arcade.platformer.Textures;

public class Player extends Entity {
	
	public Player() {
		super("player");
		texLoc = Textures.getURL("test.png");
		Textures.register(name, texLoc);
	}

}
