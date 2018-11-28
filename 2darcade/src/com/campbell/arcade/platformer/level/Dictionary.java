package com.campbell.arcade.platformer.level;

import java.util.HashMap;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Player;
import com.campbell.arcade.platformer.common.tile.TileBlank;

public class Dictionary {
	
	public static HashMap<Character, Class<? extends Drawable>> d = new HashMap<Character, Class<? extends Drawable>>();
	
	public static void initialize() {
		
		d.put('P', Player.class);
		d.put('0', TileBlank.class);
		d.put((char)0x0, TileBlank.class);
		
	}
	
}
