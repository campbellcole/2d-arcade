package com.campbell.arcade.platformer;

import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.level.Dictionary;
import com.campbell.arcade.platformer.level.Level;
import com.campbell.arcade.platformer.level.LevelHandler;

public class Platformer implements Game {
	
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
		
		// temporary - load all textures
		for (Class<? extends Drawable> c : Dictionary.d.values()) {
			Constructor<?> ctor;
			try {
				ctor = c.getConstructor();
				ctor.newInstance();
			} catch (Exception e) { // bad but temporary
				e.printStackTrace();
			}
		}
		
		// temporary - display level 0
		currentLevel = new Level(LevelHandler.getLevels().get(0));
		currentLevel.load();
	}

	@Override
	public void update() {
		
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
		return "wip platformer";
	}
	
}
