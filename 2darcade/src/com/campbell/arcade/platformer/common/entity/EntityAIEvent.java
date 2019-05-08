package com.campbell.arcade.platformer.common.entity;

public class EntityAIEvent {
	
	public static enum EntityAIEventType {
		MOVE, DIRECTION, JUMP, ATTACK;
	}
	
	public EntityAIEventType type;
	public int data;
	
	public EntityAIEvent(EntityAIEventType type, int data) {
		this.type = type;
		this.data = data;
	}
	
}
