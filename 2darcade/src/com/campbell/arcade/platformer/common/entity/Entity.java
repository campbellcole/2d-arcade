package com.campbell.arcade.platformer.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.level.Level;

public class Entity extends Drawable {
	
	final int RIGHT = 0, DOWN = 90, LEFT = 180, UP = 270, NULL = -1;
	
	public double direction;
	public double velocity;
	
	List<Tile> touching = new ArrayList<Tile>();
	
	double d_r = Math.PI/180d;
	
	public Entity(String name, int x, int y, Level lvl) {
		super(name, x, y, lvl);
		this.x = x;
		this.y = y;
		velocity = 1;
		direction = 0.0f;
	}
	
	public void move() {
		move(2);
	}
	
	public void move(int steps) {
		checkTouching();
		if (!touching.isEmpty()) {
			int newX = (int) Math.round(x + Math.cos(direction) * steps * velocity);
			int newY = (int) Math.round(y + Math.sin(direction) * steps * velocity);
			if (isTouchingEdge(newX, newY)) return;
			for (Tile t : touching) {
				if (isOverlapping(newX, newY, t.x, t.y) && t.isSolid()) return;
			}
			x = newX;
			y = newY;
		}
	}
	
	public void setDirection(int degrees) {
		checkTouching();
		if (!touching.isEmpty()) {
			direction = degrees * d_r;
		}
	}
	
	public void turn(int degrees) {
		direction += degrees * d_r;
	}
	
	private void checkTouching() {
		touching.clear();
		for (Tile t : lvl.ld.getTiles()) {
			if (isOverlapping(x+17, y+17, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x-17, y+17, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x+17, y-17, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x-17, y-17, t.x, t.y) && t.isSolid())
				touching.add(t);
		}
	}
	
	public void tick() {
		checkTouching();
	}
	
}
