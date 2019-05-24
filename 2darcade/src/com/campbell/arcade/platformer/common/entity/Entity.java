package com.campbell.arcade.platformer.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.PlatformerEvent;
import com.campbell.arcade.platformer.common.PlatformerEvent.PlatformerEventType;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.level.Level;

public class Entity extends Drawable {
	
	public static final int RIGHT = 0, DOWN = 90, LEFT = 180, UP = 270, NULL = -1;
	final int BLOCK = -3, VALID = -2, EDGE = -1;
	
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
		int res = validate(newX, newY);
		if (res != VALID) {
			if (res == EDGE) {
				handleCollideWithEdge(isTouchingEdge(newX, newY));
				return;
			} else if (res == BLOCK) {
				handleCollide((Tile)null);
			} else {
				handleCollide(touching.get(res));
				return;
			}
		}
		x = newX;
		y = newY;
	}
	
	public void moveTowards(Entity e, int steps) {
		direction = Math.atan2(e.y - y, e.x - x);
		move(steps);
	}
	
	public void jump() {
		if (!jumped && grav_vel == 0) {
			grav_vel = JUMP_HEIGHT;
			jumped = true;
		}
	}
	
	public void forceJump() {
		jumped = false;
		grav_vel = 0;
		jump();
	}
	
	public int validate(int x, int y) {
		if (isTouchingEdge(x, y) != Drawable.EDGE_NONE) return EDGE;
		Tile t;
		for (int i = 0; i < touching.size(); i++) {
			t = touching.get(i);
			if (isOverlapping(x, y, t.x, t.y)) {
				if (t.isSolid()) return i;
				else if (t.getName().contentEquals("tileblock")) return BLOCK;
			}
		}
		return VALID;
	}
	
	public void setDirection(int degrees) {
		direction = degrees * d_r;
	}
	
	public int getDegrees() {
		return (int)(direction/d_r);
	}
	
	public double getDirection() {
		return direction;
	}
	
	private void checkTouching() {
		touching.clear();
		for (Tile t : lvl.ld.getTiles()) {
			if (isOverlapping(x+16, y, t.x, t.y))
				touching.add(t);
			if (isOverlapping(x, y+16, t.x, t.y))
				touching.add(t);
			if (isOverlapping(x-16, y, t.x, t.y))
				touching.add(t);
			if (isOverlapping(x, y-16, t.x, t.y))
				touching.add(t);
		}
	}
	
	private void checkEnts() {
		for (Entity e : lvl.ld.getEntities()) {
			if (isOverlapping(x, y, e.x, e.y) && e != this) handleCollide(e);
		}
	}
	
	public void handleCollide(Entity e) {}
	
	public void handleCollide(Tile t) {}
	
	public void handleCollideWithEdge(int edge) {
		if (edge == Drawable.EDGE_BOTTOM) {
			PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.REMOVE, ""+this.hashCode());
			Platformer.eventQueue.add(ev);
		} else if (edge == Drawable.EDGE_TOP) {
			grav_vel = 0;
		}
	}
	
	protected void updatePosition() {
		double ymod = -(grav_vel -= grav_vel >= MAX_FALL_SPEED ? GRAVITY : 0);
		int res = validate(x, (int)(y+ymod));
		if (res == VALID || res == BLOCK) {
			y += ymod;
		} else {
			if (res != EDGE && res != BLOCK) {
				if (!(touching.get(res).y+PlatformerSettings.HITBOX<y)) {
					y = touching.get(res).y-(PlatformerSettings.HITBOX+1);
					jumped = false;
				}
				grav_vel = 0;
			} else {
				handleCollideWithEdge(isTouchingEdge(x, (int)(y+ymod)));
			}
		}
	}
	
	public void tick() {
		checkTouching();
		checkEnts();
		updatePosition();
	}
	
}
