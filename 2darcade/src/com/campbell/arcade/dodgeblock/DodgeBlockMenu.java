package com.campbell.arcade.dodgeblock;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.DataHandler;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class DodgeBlockMenu implements Game {

	Graphics2D g;
	DodgeBlockMenuRenderer dbr;
	DataHandler dh;
	
	public DodgeBlockMenu(Graphics2D g) {
		this.g = g;
		dh = new DataHandler();
		dbr = new DodgeBlockMenuRenderer(g, this);
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	boolean choseDif = false;
	
	@Override
	public void update() {
		ArrayList<Integer> p = GameKeyListener.getPendingKeys();
		for (int i = 0; i < p.size(); i++) {
			if (!choseDif) {
				if (p.get(i) == KeyEvent.VK_DOWN) {
					if ((int)dh.get("sel") < 2) {
						dh.set("sel", (int)dh.get("sel")+1);
						GameKeyListener.reset();
					}
				}
				if (p.get(i) == KeyEvent.VK_UP) {
					if ((int)dh.get("sel") > 0) {
						dh.set("sel", (int)dh.get("sel")-1);
						GameKeyListener.reset();
					}
				}
			}
			if (p.get(i) == KeyEvent.VK_ENTER) {
				if (choseDif) {
					DodgeBlockSettings.DIFFICULTY = (int)dh.get("sel")+1;
					Manager.instance.setGame(new DodgeBlock(null));
				}
				choseDif = true;
			}
		}
	}

	@Override
	public void resize() {
		g = Manager.instance.modifySize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		dbr = new DodgeBlockMenuRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		// TODO Auto-generated method stub
		return dbr;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DodgeBlock";
	}

	@Override
	public String getDescription() {
		return "Dodge blocks coming in from the right side of the screen, and try to last as long as you can. There are 3 difficulties.";
	}

}
