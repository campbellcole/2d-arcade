package com.campbell.arcade.platformer.common.entity;

public class Player extends Entity {
	
	public Player(int x, int y) {
		super("player", x, y);
		this.register();
	}
	
	public void tick() {
		this.move();
	}

}
