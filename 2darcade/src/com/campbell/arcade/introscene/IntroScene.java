package com.campbell.arcade.introscene;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.DataHandler;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class IntroScene implements Game {

	IntroRenderer r;
	Graphics2D g;
	DataHandler dh = new DataHandler();

	public IntroScene(Graphics2D g) {
		this.g = g;
		this.r = new IntroRenderer(g, this);
	}

	@Override
	public void initialize() {
		dh.set("sel", 0);
		System.out.println("[IntroScene] initializing...");
	}

	@Override
	public void update() {
		ArrayList<Integer> p = GameKeyListener.getPendingKeys();
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i) == KeyEvent.VK_DOWN) {
				if ((int) dh.get("sel") < Manager.games.size() - 1) {
					dh.set("sel", (int) dh.get("sel") + 1);
					GameKeyListener.reset();
				}
			}
			if (p.get(i) == KeyEvent.VK_UP) {
				if ((int) dh.get("sel") > 0) {
					dh.set("sel", (int) dh.get("sel") - 1);
					GameKeyListener.reset();
				}
			}
			if (p.get(i) == KeyEvent.VK_ENTER) {
				if (!Manager.games.get((int) dh.get("sel")).getName().equals("Instructions")) {
					System.out.println("[IntroScene] game selected. loading...");
					Manager.instance.setGame(Manager.games.get((int) dh.get("sel")));
				}
			}
		}
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "INTROSCENE";
	}

	@Override
	public void resize() {
		g = Manager.instance.modifySize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		r = new IntroRenderer(g, this);
	}

	@Override
	public String getDescription() {
		return "you should not see this";
	}

}
