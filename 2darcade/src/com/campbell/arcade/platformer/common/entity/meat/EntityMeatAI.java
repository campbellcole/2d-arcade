package com.campbell.arcade.platformer.common.entity.meat;

import java.util.Random;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.entity.EntityAI;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.level.LevelHelper;

public class EntityMeatAI extends EntityAI {
	
	EntityMeat instance;
	
	Random r;
	int mode;
	
	public EntityMeatAI(EntityMeat instance) {
		this.instance = instance;
		instance.setDirection(Entity.LEFT);
		r = new Random();
		mode = 1;//r.nextInt(2);
	}
	
	public void handleCollide(Tile t) {
		swap();
	}
	
	public void handleCollideWithEdge() {
		swap();
	}
	
	private void swap() {
		if (mode == 0) return;
		if (instance.getDegrees() == Entity.LEFT) {
			instance.setDirection(Entity.RIGHT);
		} else if (instance.getDegrees() == Entity.RIGHT) {
			instance.setDirection(Entity.LEFT);
		}
	}
	
	@Override
	public void tick() {
		if (mode == 0) {
			instance.moveTowards(LevelHelper.getPlayer(instance.lvl), 2);
		} else {
			instance.move(3);
		}
	}

}
