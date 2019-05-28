package com.campbell.arcade.platformer.common.entity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.PlatformerEvent;
import com.campbell.arcade.platformer.common.PlatformerEvent.PlatformerEventType;
import com.campbell.arcade.platformer.level.Dictionary;
import com.campbell.arcade.platformer.level.Level;

public class Player extends Entity {
	
	public Player(int x, int y, Level lvl) {
		super("player", x, y, lvl);
		this.register();
	}
	
	public void tick() {
		ArrayList<Integer> keys = GameKeyListener.getPendingKeys();
		int degrees = NULL;
		if (keys.indexOf(KeyEvent.VK_RIGHT) != -1) {
			degrees = RIGHT;
		}
		if (keys.indexOf(KeyEvent.VK_DOWN) != -1) {
			//crouch();
		}
		if (keys.indexOf(KeyEvent.VK_LEFT) != -1) {
			degrees = LEFT;
		}
		if (keys.indexOf(KeyEvent.VK_UP) != -1) {
			jump();
		}
		if (keys.indexOf(KeyEvent.VK_R) != -1) {
			Platformer.eventQueue.add(new PlatformerEvent(PlatformerEventType.RESTART, null));
		}
		if (degrees != NULL) {
			this.setDirection(degrees);
			this.move();
		}
		super.tick();
	}
	
	@Override
	public void handleCollide(Entity e) {
		if (y <= (e.y - 10)) {
			System.out.println("[Player] landed on top of entity. sending remove event...");
			PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.REMOVE, ""+e.hashCode());
			Platformer.eventQueue.add(ev);
			forceJump();
		} else {
			if (e instanceof Player) return;
			System.out.println("[Player] killed by entity. sending death event...");
			String entName = Dictionary.n.get(e.getClass());
			PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.DEATH, "Killed by " + entName + "!");
			Platformer.eventQueue.add(ev);
		}
	}
	
	@Override
	public void handleCollideWithEdge(int edge) {
		if (edge == Drawable.EDGE_RIGHT) {
			System.out.println("[Player] touched right edge, sending nextlevel event...");
			int nextID = Integer.parseInt(""+lvl.id.charAt(lvl.id.length()-1));
			Platformer.eventQueue.add(new PlatformerEvent(PlatformerEventType.NEXTLEVEL, ""+(++nextID)));
		} else if (edge == Drawable.EDGE_BOTTOM) {
			System.out.println("[Player] touched bottom edge, sending death event...");
			PlatformerEvent ev = new PlatformerEvent(PlatformerEventType.DEATH, "You fell off the level.");
			Platformer.eventQueue.add(ev);
		}
	}

}
