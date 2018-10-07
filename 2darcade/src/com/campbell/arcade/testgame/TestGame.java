package com.campbell.arcade.testgame;

import java.awt.Graphics2D;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class TestGame implements Game {

	TestRenderer r;
	Graphics2D g;
	
	public TestGame(Graphics2D g) {
		this.g = g;
		this.r = new TestRenderer(g);
	}
	
	@Override
	public void initialize() {
	}

	@Override
	public void update() {
		r.x++;
		r.y++;
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Test Game";
	}

	@Override
	public void resize() {
		g = Manager.instance.modifySize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		r = new TestRenderer(g);
	}
	
	@Override
	public String getDescription() {
		return "2d arcade prototypical game (ignore this)";
	}

}
