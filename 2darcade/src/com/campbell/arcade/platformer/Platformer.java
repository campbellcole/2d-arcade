package com.campbell.arcade.platformer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.platformer.common.PlatformerEvent;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.level.Dictionary;
import com.campbell.arcade.platformer.level.Level;
import com.campbell.arcade.platformer.level.LevelHandler;

public class Platformer implements Game {
	
	public static List<PlatformerEvent> eventQueue = new ArrayList<PlatformerEvent>();
	
	Graphics2D g;
	PlatformerRenderer r;
	
	Level currentLevel;
	
	public Platformer(Graphics2D g) {
		this.g = g;
	}

	@Override
	public void initialize() {
		// load dictionary
		Dictionary.initialize();
		
		// read levels
		LevelHandler.initialize();
		
		// temporary - display level 0
		currentLevel = LevelHandler.getLevels().get(0);
		currentLevel.load();
	}

	@Override
	public void update() {
		for (Entity ent : currentLevel.ld.getEntities()) {
			ent.tick();
		}
		while (!Platformer.eventQueue.isEmpty()) {
			PlatformerEvent ev = Platformer.eventQueue.get(0);
			switch (ev.type) {
			case NEXTLEVEL:
				currentLevel = LevelHandler.getLevels().get(Integer.parseInt(ev.data));
			case RESTART:
				currentLevel.load();
				break;
			case DEATH:
				r.displayMessage(ev.data);
				currentLevel.load();
				break;
			case REMOVE:
				int hash = Integer.parseInt(ev.data);
				List<Entity> ents = currentLevel.ld.getEntities();
				for (int i = 0; i < ents.size(); i++) {
					if (ents.get(i).hashCode() == hash) {
						ents.remove(i);
					}
				}
				currentLevel.ld.setEntities(ents);
				break;
			default:
				break;
			}
			Platformer.eventQueue.remove(0);
		}
	}

	@Override
	public void resize() {
		g = Manager.instance.modifySize(PlatformerSettings.WIDTH, PlatformerSettings.HEIGHT);
		r = new PlatformerRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Platformer";
	}

	@Override
	public String getDescription() {
		return "red plumber ripoff";
	}
	
}
