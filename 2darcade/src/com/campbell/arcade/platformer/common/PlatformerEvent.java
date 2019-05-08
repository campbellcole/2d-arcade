package com.campbell.arcade.platformer.common;

public class PlatformerEvent {
	
	public static enum PlatformerEventType {
		RESTART, NEXTLEVEL, REMOVE;
	}
	
	public PlatformerEventType type;
	public String data;
	
	public PlatformerEvent(PlatformerEventType type, String data) {
		this.type = type;
		this.data = data;
	}
	
}
