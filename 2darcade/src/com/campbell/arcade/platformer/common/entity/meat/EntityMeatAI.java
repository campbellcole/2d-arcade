package com.campbell.arcade.platformer.common.entity.meat;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.entity.EntityAI;

public class EntityMeatAI extends EntityAI {
	
	EntityMeat instance;
	
	public EntityMeatAI(EntityMeat instance) {
		this.instance = instance;
		instance.setDirection(Entity.LEFT);
	}
	
	int counter = 0;
	
	@Override
	public void tick() {
		counter++;
		if (counter > 100) {
			if (instance.getDirection() == Entity.RIGHT) {
				instance.setDirection(Entity.LEFT);
				counter = 0;
			} else {
				instance.setDirection(Entity.RIGHT);
				counter = 0;
			}
		}
		instance.move(1);
		instance.jump();
	}

}
