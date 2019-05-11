package com.campbell.arcade.platformer.level;

import com.campbell.arcade.platformer.common.entity.Player;

public class LevelHelper {

	public static Player getPlayer(Level lvl) {
		return (Player) lvl.ld.entsOfType(Player.class).get(0);
	}
	
	
	
}
