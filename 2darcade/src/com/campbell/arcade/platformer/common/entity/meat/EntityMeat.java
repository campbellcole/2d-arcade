package com.campbell.arcade.platformer.common.entity.meat;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.level.Level;

public class EntityMeat extends Entity {

	EntityMeatAI ai;
	
	public EntityMeat(int x, int y, Level lvl) {
		super("meat", x, y, lvl);
		this.register();
		ai = new EntityMeatAI(this);
	}
	
	@Override
	public void tick() {
		super.tick();
		ai.tick();
	}

}
