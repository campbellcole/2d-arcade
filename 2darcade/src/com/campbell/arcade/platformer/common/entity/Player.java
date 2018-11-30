package com.campbell.arcade.platformer.common.entity;

import java.util.ArrayList;

import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.platformer.level.Level;
import com.sun.glass.events.KeyEvent;

public class Player extends Entity {
	
	public Player(int x, int y, Level lvl) {
		super("player", x, y, lvl);
		this.register();
	}
	
	public void tick() {
		ArrayList<Integer> keys = GameKeyListener.getListener().getPendingKeys();
		/*
		int direction = -1;
		if (keys.indexOf(KeyEvent.VK_RIGHT) != -1) {
			direction = 0;
		}
		if (keys.indexOf(KeyEvent.VK_DOWN) != -1) {
			direction = 90;
		}
		if (keys.indexOf(KeyEvent.VK_LEFT) != -1) {
			direction = 180;
		}
		if (keys.indexOf(KeyEvent.VK_UP) != -1) {
			direction = 270;
		}
		if (keys.indexOf(KeyEvent.VK_SHIFT) != -1) {
			this.velocity = 2;
		} else {
			this.velocity = 1;
		}
		if (direction != -1) {
			this.setDirection(direction);
			this.move();
		}
		*/
		if (keys.indexOf(KeyEvent.VK_RIGHT) != -1) {
			this.turn(5);
		}
		if (keys.indexOf(KeyEvent.VK_DOWN) != -1) {
			this.velocity = -1;
			this.move();
		}
		if (keys.indexOf(KeyEvent.VK_LEFT) != -1) {
			this.turn(-5);
		}
		if (keys.indexOf(KeyEvent.VK_UP) != -1) {
			this.velocity = 1;
			this.move();
		}
	}

}
