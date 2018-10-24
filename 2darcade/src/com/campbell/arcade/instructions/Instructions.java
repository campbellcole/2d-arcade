package com.campbell.arcade.instructions;

import java.awt.Graphics2D;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.introscene.IntroScene;

public class Instructions implements Game {
	
	public Instructions(Graphics2D g) {
	}
	
	@Override
	public void initialize() {
		Manager.instance.setGame(new IntroScene(null));
	}

	@Override
	public void update() {
	}

	@Override
	public Renderer getRenderer() {
		return null;
	}

	@Override
	public String getName() {
		return "Instructions";
	}

	@Override
	public void resize() {
	}
	
	@Override
	public String getDescription() {
		return "Choose a game with the arrow keys, press enter to select. Press escape in any game to go back to this menu.";
	}

}
