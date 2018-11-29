package com.campbell.arcade.platformer.common.entity;

import java.util.ArrayList;

import com.campbell.arcade.common.GameKeyListener;
import com.sun.glass.events.KeyEvent;

public class Player extends Entity {
	
	public Player(int x, int y) {
		super("player", x, y);
		this.register();
	}
	
	public void tick() {
		ArrayList<Integer> keys = GameKeyListener.getListener().getPendingKeys();
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
	}

}
