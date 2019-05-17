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
		g.drawString("DODGEBLOCK", Settings.POSTWIDTH/3, Settings.INSET_TOP+3);
		g.setFont(Settings.getFont(20));
		g.drawString("Choose a difficulty:", Settings.POSTWIDTH/4+10, Settings.INSET_TOP+45);
		g.setFont(Settings.getFont(18));
		if ((int)instance.dh.get("sel")==0) Settings.sel(g); else Settings.desel(g);
		g.drawString("Hard", Settings.POSTWIDTH/3+70, Settings.INSET_TOP+100);
		if ((int)instance.dh.get("sel")==1) Settings.sel(g); else Settings.desel(g);
		g.drawString("Medium", Settings.POSTWIDTH/3+62, Settings.INSET_TOP+140);
		if ((int)instance.dh.get("sel")==2) Settings.sel(g); else Settings.desel(g);
		g.drawString("Easy", Settings.POSTWIDTH/3+70, Settings.INSET_TOP+180);
		g.setFont(Settings.getFont(26));
		g.setColor(Settings.TXT);
		g.drawString("Press enter to play", Settings.POSTWIDTH/4-30, Settings.INSET_TOP+250);
	}
	
}
