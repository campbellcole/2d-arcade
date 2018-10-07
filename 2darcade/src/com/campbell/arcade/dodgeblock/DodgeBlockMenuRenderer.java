package com.campbell.arcade.dodgeblock;

import java.awt.Graphics2D;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class DodgeBlockMenuRenderer implements Renderer {

	Graphics2D g;
	DodgeBlockMenu instance;
	
	public DodgeBlockMenuRenderer(Graphics2D g, DodgeBlockMenu instance) {
		this.g = g;
		this.instance = instance;
		instance.dh.set("sel", 0);
	}

	@Override
	public void draw() {
		g.setColor(Settings.BG);
		g.fillRect(0, 0, Settings.POSTWIDTH, Settings.POSTHEIGHT);
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(24));
		g.drawString("DODGEBLOCK", Settings.INSET_LEFT, Settings.INSET_TOP);
		g.drawString("Choose a difficulty:", Settings.INSET_LEFT, Settings.INSET_TOP+35);
		g.setFont(Settings.getFont(18));
		if ((int)instance.dh.get("sel")==0) Settings.sel(g); else Settings.desel(g);
		g.drawString("Hard", Settings.INSET_LEFT, Settings.INSET_TOP+70);
		if ((int)instance.dh.get("sel")==1) Settings.sel(g); else Settings.desel(g);
		g.drawString("Medium", Settings.INSET_LEFT, Settings.INSET_TOP+100);
		if ((int)instance.dh.get("sel")==2) Settings.sel(g); else Settings.desel(g);
		g.drawString("Easy", Settings.INSET_LEFT, Settings.INSET_TOP+130);
		g.setFont(Settings.getFont(26));
		g.setColor(Settings.TXT);
		String dif = "";
		switch ((int)instance.dh.get("sel")) {
		case 0:
			dif = "Hard";
			break;
		case 1:
			dif = "Medium";
			break;
		case 2:
			dif = "Easy";
			break;
		}
		g.drawString("Press enter play on " + dif + " mode.", Settings.INSET_LEFT, Settings.INSET_TOP+180);
	}
	
}
