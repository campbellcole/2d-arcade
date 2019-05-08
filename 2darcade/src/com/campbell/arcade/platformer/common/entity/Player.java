package com.campbell.arcade.platformer.common.entity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.platformer.level.Level;

public class Player extends Entity {
	
	public Player(int x, int y, Level lvl) {
		super("player", x, y, lvl);
		this.register();
	}
	
	public void tick() {
		ArrayList<Integer> keys = GameKeyListener.getPendingKeys();
		int degrees = NULL;
		if (keys.indexOf(KeyEvent.VK_RIGHT) != -1) {
			degrees = RIGHT;
		}
		if (keys.indexOf(KeyEvent.VK_DOWN) != -1) {
			crouch();
		}
		if (keys.indexOf(KeyEvent.VK_LEFT) != -1) {
			degrees = LEFT;
		}
		if (keys.indexOf(KeyEvent.VK_UP) != -1) {
			jump();
		}
		if (degrees != NULL) {
			this.setDirection(degrees);
			this.move();
		}
		super.tick();
	}
	
	boolean crouched = false;
	
	public void crouch() {
		crouched = !crouched;
		if (crouched) {
			
		} else {
			
		}
	}

}
