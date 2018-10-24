package com.campbell.arcade.platformer;

import java.awt.Graphics2D;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.platformer.common.Entity;
import com.campbell.arcade.platformer.common.Player;

public class Platformer implements Game {
	
	Graphics2D g;
	PlatformerRenderer r;
	
	public Platformer(Graphics2D g) {
		this.g = g;
	}

	@Override
	public void initialize() {
		// temp - load textures for testing
		new Entity("placeholder");
		new Player();
		// read levels
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
