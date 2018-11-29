package com.campbell.arcade.platformer.common.entity;

import com.campbell.arcade.platformer.common.Drawable;

public class Entity extends Drawable {
	
	public double direction;
	public int velocity;
	
	double d_r = Math.PI/180d;
	
	public Entity(String name, int x, int y) {
		super(name, x, y);
		this.x = x;
		this.y = y;
		velocity = 1;
		direction = 0.0f;
	}
	
	public void move() { move(2); }
	
	public void move(int steps) {
		int newX = (int) Math.round(x + Math.cos(direction) * steps) * velocity;
		int newY = (int) Math.round(y + Math.sin(direction) * steps);
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
