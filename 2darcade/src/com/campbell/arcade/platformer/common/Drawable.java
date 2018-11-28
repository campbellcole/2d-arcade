package com.campbell.arcade.platformer.common;

import java.awt.Image;
import java.net.URL;

import com.campbell.arcade.platformer.Textures;

public class Drawable {
	
	String name;
	URL texLoc;
	
	public Drawable(String name) {
		texLoc = Textures.getURL(name+".png");
		this.name = name;
	}
	
	public void register() {
		Textures.register(this.getClass(), texLoc);
	}
	
	public void register(Image image) {
		Textures.register(this.getClass(), image);
	}
	
	public URL getTexture() {
		return texLoc;
	}
	
	public String getName() {
		return name;
	}
	
}
