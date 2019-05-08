package com.campbell.arcade.platformer.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.PlatformerEvent;
import com.campbell.arcade.platformer.common.PlatformerEvent.PlatformerEventType;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.level.Level;

public class Entity extends Drawable {
	
	public static final int RIGHT = 0, DOWN = 90, LEFT = 180, UP = 270, NULL = -1;
	final int VALID = -2, EDGE = -1;
	
	final int JUMP_HEIGHT = 5, MAX_FALL_SPEED = -5;
	final double GRAVITY = 0.2;
	
	private double direction;
	private double velocity;
	
	protected double grav_vel;
	protected boolean jumped;
	
	List<Tile> touching = new ArrayList<Tile>();
	
	double d_r = Math.PI/180d;
	
	public Entity(String name, int x, int y, Level lvl) {
		super(name, x, y, lvl);
		this.x = x;
		this.y = y;
		velocity = 1;
		direction = 0.0f;
		grav_vel = 0.0f;
		jumped = false;
	}
	
	public void move() {
		move(2);
	}
	
	public void move(int steps) {
		checkTouching();
		int newX = (int) Math.round(x + Math.cos(direction) * steps * velocity);
		int newY = (int) Math.round(y + Math.sin(direction) * steps * velocity);
		if (validate(newX, newY) != VALID) return;
		x = newX;
		y = newY;
	}
	
	public void jump() {
		if (!jumped) {
			grav_vel = JUMP_HEIGHT;
			jumped = true;
		}
	}
	
	public int validate(int x, int y) {
		if (isTouchingEdge(x, y)) return -1;
		Tile t;
		for (int i = 0; i < touching.size(); i++) {
			t = touching.get(i);
			if (isOverlapping(x, y, t.x, t.y) && t.isSolid()) return i;
		}
		return -2;
	}
	
	public void setDirection(int degrees) {
		direction = degrees * d_r;
	}
	
	public double getDirection() {
		return direction;
	}
	
	private void checkTouching() {
		touching.clear();
		for (Tile t : lvl.ld.getTiles()) {
			if (isOverlapping(x+16, y, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x, y+16, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x-16, y, t.x, t.y) && t.isSolid())
				touching.add(t);
			if (isOverlapping(x, y-16, t.x, t.y) && t.isSolid())
				touching.add(t);
		}
	}
	
	protected void updatePosition() {
		double ymod = -(grav_vel -= grav_vel >= MAX_FALL_SPEED ? GRAVITY : 0);
		int res = validate(x, (int)(y+ymod));
		if (res == VALID) {
			y += ymod;
		} else {
			if (res != EDGE) {
				if (!(touching.get(res).y+16<y)) {
					y = touching.get(res).y-17;
					grav_vel = 0;
					jumped = false;
				}
			} else {
				if (this instanceof Player) {
					PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.RESTART, "You died.");
					Platformer.eventQueue.add(ev);
				} else {
					PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.REMOVE, ""+this.hashCode());
					Platformer.eventQueue.add(ev);
				}
				// goodbye :)
			}
		}
	}
	
	public void tick() {
		checkTouching();
		updatePosition();
	}
	
}
