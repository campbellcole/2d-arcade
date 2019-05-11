package com.campbell.arcade.platformer.level;

import java.util.HashMap;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Player;
import com.campbell.arcade.platformer.common.entity.meat.EntityMeat;
import com.campbell.arcade.platformer.common.tile.TileBlank;
import com.campbell.arcade.platformer.common.tile.TileDirt;
import com.campbell.arcade.platformer.common.tile.TileGround;

public class Dictionary {
	
	public static HashMap<Character, Class<? extends Drawable>> d = new HashMap<Character, Class<? extends Drawable>>();
	
	public static HashMap<Class<? extends Drawable>, String> n = new HashMap<Class<? extends Drawable>, String>();
	
	public static void initialize() {
		
		d.put('P', Player.class);
		n.put(Player.class, "Player");
		
		d.put('M', EntityMeat.class);
		n.put(EntityMeat.class, "meatyman");
		
		d.put('0', TileBlank.class);
		d.put('G', TileGround.class);
		d.put('D', TileDirt.class);
		
		d.put((char)0x0, TileBlank.class);
		
	}
	
}
