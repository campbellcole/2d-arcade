package com.campbell.arcade.platformer.level;

import java.util.HashMap;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Player;
import com.campbell.arcade.platformer.common.entity.meat.EntityMeat;
import com.campbell.arcade.platformer.common.tile.TileBlank;
import com.campbell.arcade.platformer.common.tile.TileBlock;
import com.campbell.arcade.platformer.common.tile.TileDirt;
import com.campbell.arcade.platformer.common.tile.TileGround;

public class Dictionary {
	
	public static HashMap<Character, Class<? extends Drawable>> d = new HashMap<Character, Class<? extends Drawable>>();
	
	public static HashMap<Class<? extends Drawable>, String> n = new HashMap<Class<? extends Drawable>, String>();
	
	public static void initialize() {
		
		System.out.println("[Dictionary] initializing...");
		
		d.put('P', Player.class);
		n.put(Player.class, "Player");
		
		d.put('M', EntityMeat.class);
		n.put(EntityMeat.class, "meatyman");
		
		d.put('0', TileBlank.class);
		d.put('B', TileBlock.class);
		d.put('G', TileGround.class);
		d.put('D', TileDirt.class);
		
		System.out.println("[Dictionary] done.");
		
	}
	
	public static char forKey(Class<? extends Drawable> c) {
		for (char ch : d.keySet()) if (d.get(ch).equals(c)) return ch;
		return 0;
	}
	
}
