package com.campbell.arcade.platformer.common.entity;

public class Player extends Entity {
	
	public Player() {
		super("player");
		this.register();
	}
	
	public void tick() {
		this.move();
	}

}
