package com.campbell.arcade.introscene;

import java.awt.Color;
import java.awt.Graphics2D;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.DataHandler;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class IntroRenderer implements Renderer {

	Graphics2D g;
	IntroScene instance;
	DataHandler idh;
	
	public IntroRenderer(Graphics2D g, IntroScene instance) {
		this.g = g;
		this.instance = instance;
		this.idh = instance.dh;
	}

	final int dyOffsetDef = 100;
	
	int xOffset = 7;
	int yOffset;
	
	@Override
	public void draw() {
		yOffset = 35;
		
		g.setColor(Settings.BG);
		g.fillRect(0, 0, Settings.POSTWIDTH, Settings.POSTHEIGHT);
		
		g.setColor(Color.decode("#850090"));
		g.fillRect(5, 5, Settings.POSTWIDTH-15, 40);
		
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(31));
		g.drawString("2D Arcade by Campbell Cole", 9, yOffset);
		
		g.setFont(Settings.getFont(16));
		yOffset = dyOffsetDef;
		int selected = (int) idh.get("sel");
		for (int i = 0; i < Manager.games.size(); i++) {
			if (i == selected) {
				drawDesc(i);
				Settings.sel(g);
			} else Settings.desel(g);
			g.drawString(Manager.games.get(i).getName(), xOffset, yOffset);
			yOffset += 30;
		}
		
		g.setColor(Color.decode("#850090"));
		g.drawRect(xOffset-5, dyOffsetDef-20, dxOffset-45, yOffset-100);
	}
	
	int dxOffset = 230;
	int dyOffset = dyOffsetDef;
	
	public void drawDesc(int i) {
		g.setFont(Settings.getFont(18));
		dyOffset = dyOffsetDef;
		g.setColor(Settings.TXT);
		String desc = Manager.games.get(i).getDescription();
		String[] split = desc.split(" ");
		String[] lines = new String[(desc.length()/30)+1];
		int line = 0;
		for (int ix = 0; ix < split.length; ix++) {
			if (lines[line] == null) lines[line] = "";
			lines[line] += split[ix] + " ";
			if (lines[line].length()+split[ix].length() > 30) {
				line++;
			}
		}
		for (String s : lines) {
			if (s == null) continue;
			g.drawString(s, dxOffset, dyOffset);
			dyOffset += 30;
		}
	}

}
