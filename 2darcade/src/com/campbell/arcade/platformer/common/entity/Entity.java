package com.campbell.arcade.platformer.common.entity;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.level.Level;

public class Entity extends Drawable {
	
	public double direction;
	public int velocity;
	
	double d_r = Math.PI/180d;
	
	public Entity(String name, int x, int y, Level lvl) {
		super(name, x, y, lvl);
		this.x = x;
		this.y = y;
		velocity = 1;
		direction = 0.0f;
	}
	
	public void move() { move(2); }
	
	public void move(int steps) {
		for (Tile t : lvl.ld.getTiles()) {
			// TODO: get this to work
			if (isTouching(t)) {
				if (t.isSolid()) {
					return;
				}
			}
		}
		int newX = (int) Math.round(x + Math.cos(direction) * steps * velocity);
		int newY = (int) Math.round(y + Math.sin(direction) * steps * velocity);
		x = newX;
		y = newY;
	}
	
	public void setDirection(int degrees) {
		direction = degrees * d_r;
	}
	
	public void turn(int degrees) {
		direction += degrees * d_r;
	}
	
	public void tick() {
		this.move();
	}
	
}
