package com.campbell.arcade.platformer.common.entity.meat;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.entity.EntityAI;
import com.campbell.arcade.platformer.level.LevelHelper;

public class EntityMeatAI extends EntityAI {
	
	EntityMeat instance;
	
	public EntityMeatAI(EntityMeat instance) {
		this.instance = instance;
		instance.setDirection(Entity.LEFT);
	}
	
	@Override
	public void tick() {
		instance.moveTowards(LevelHelper.getPlayer(instance.lvl), 2);
	}

}
