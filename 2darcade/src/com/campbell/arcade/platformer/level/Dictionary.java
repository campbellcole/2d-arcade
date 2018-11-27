package com.campbell.arcade.platformer.level;

import java.util.HashMap;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Player;
import com.campbell.arcade.platformer.common.tile.TileBlank;

public class Dictionary {
	
	public static HashMap<Character, Drawable> d = new HashMap<Character, Drawable>();
	
	public static void initialize() {
		
		d.put('P', new Player());
		d.put('0', new TileBlank());
		d.put((char)0x0, new TileBlank());
		
	}
	
}
