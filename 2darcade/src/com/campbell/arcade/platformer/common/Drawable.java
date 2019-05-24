package com.campbell.arcade.platformer.common;

import java.awt.Image;
import java.net.URL;

import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.Textures;
import com.campbell.arcade.platformer.common.PlatformerEvent.PlatformerEventType;
import com.campbell.arcade.platformer.common.entity.Player;
import com.campbell.arcade.platformer.level.Level;

public class Drawable {
	
	public static final int EDGE_TOP = 1, EDGE_LEFT = 2, EDGE_RIGHT = 3, EDGE_BOTTOM = 4, EDGE_NONE = 0;
	
	String name;
	URL texLoc;
	
	public int x, y;
	public Level lvl;
	
	public Drawable(String name, int x, int y, Level lvl) {
		texLoc = Textures.getURL(name+".png");
		this.name = name;
		this.x = x;
		this.y = y;
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
	
	final int HITBOX = PlatformerSettings.HITBOX;
	
	public boolean isOverlapping(int x1, int y1, int x2, int y2) {
		boolean x1b = x1 <= x2 + HITBOX;
		boolean x2b = x1 + HITBOX >= x2;
		boolean y1b = y1 <= y2 + HITBOX;
		boolean y2b = y1 + HITBOX >= y2;
		return x1b && x2b && y1b && y2b;
	}
	
	public int isTouchingEdge(int x, int y) {
		if (x <= 0) return EDGE_LEFT;
		if (x>=PlatformerSettings.WIDTH-HITBOX) {
			if (this instanceof Player) {
				int nextID = Integer.parseInt(""+lvl.id.charAt(lvl.id.length()-1));
				Platformer.eventQueue.add(new PlatformerEvent(PlatformerEventType.NEXTLEVEL, ""+(++nextID)));
			}
			return EDGE_RIGHT;
		}
		if (y <= 0) return EDGE_TOP;
		if (y >= PlatformerSettings.HEIGHT-16) return EDGE_BOTTOM;
		return EDGE_NONE;
	}
	
}
