package com.campbell.arcade.platformer.common;

import java.awt.Image;
import java.net.URL;

import com.campbell.arcade.platformer.Textures;

public class Drawable {
	
	String name;
	URL texLoc;
	
	public int x, y;
	
	public Drawable(String name, int x, int y) {
		texLoc = Textures.getURL(name+".png");
		this.name = name;
		this.x = x;
		this.y = y;
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
