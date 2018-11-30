package com.campbell.arcade.platformer.common;

import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import com.campbell.arcade.platformer.Textures;
import com.campbell.arcade.platformer.level.Level;

public class Drawable {
	
	String name;
	URL texLoc;
	
	public int x, y;
	public Level lvl;
	
	public Rectangle bounds;
	
	public Drawable(String name, int x, int y, Level lvl) {
		texLoc = Textures.getURL(name+".png");
		this.name = name;
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, 16, 16);
		this.lvl = lvl;
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
	
	public boolean isTouching(Drawable b) {
		//System.out.println(b.getName());
		boolean x1 = bounds.x < b.bounds.x + b.bounds.height;
		boolean x2 = bounds.x + bounds.width > b.bounds.x;
		boolean y1 = bounds.y < b.bounds.y + b.bounds.height;
		boolean y2 = bounds.y + bounds.height > b.bounds.y;
		return x1 && x2 && y1 && y2;
	}
	
}
