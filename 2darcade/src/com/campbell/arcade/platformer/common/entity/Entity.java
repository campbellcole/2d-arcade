package com.campbell.arcade.platformer.common.entity;

import java.net.URL;

import com.campbell.arcade.platformer.Textures;

public class Entity {
	
	String name;
	URL texLoc;
	
	public Entity(String name) {
		texLoc = Textures.getURL(name+".png");
		this.name = name;
		Textures.register(name, texLoc);
	}
	
	public URL getTexture() {
		return texLoc;
	}
	
	public String getName() {
		return name;
	}
	
}
