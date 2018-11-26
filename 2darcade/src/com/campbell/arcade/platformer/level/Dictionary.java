package com.campbell.arcade.platformer.level;

import java.util.HashMap;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.entity.Placeholder;
import com.campbell.arcade.platformer.common.entity.Player;

public class Dictionary {
	
	public static HashMap<Character, Entity> d = new HashMap<Character, Entity>();
	
	public static void initialize() {
		
		d.put('P', new Player());
		d.put('0', new Placeholder());
		d.put((char)0x0, new Placeholder());
		
	}
	
}
