package com.campbell.arcade.introscene;

import java.awt.Color;
import java.awt.Graphics2D;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.DataHandler;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.platformer.PlatformerSettings;

public class IntroRenderer implements Renderer {

	Graphics2D g;
	IntroScene instance;
	DataHandler idh;
	
	public IntroRenderer(Graphics2D g, IntroScene instance) {
		this.g = g;
		this.instance = instance;
		this.idh = instance.dh;
	}

	final int gyOffsetDef = 100;
	final int dyOffsetDef = 110;
	
	int xOffset = 7;
	int yOffset;
	
	@Override
	public void draw() {
		yOffset = 35;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, Settings.POSTWIDTH, Settings.POSTHEIGHT);
		
		g.setColor(Color.decode("#850090"));
		g.fillRect(5, 5, Settings.POSTWIDTH-15, 42);
		
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(31));
		g.drawString("2D Arcade by Campbell Cole", 9, yOffset);
		
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(16));
		
		yOffset = gyOffsetDef;
		int selected = (int) idh.get("sel");
		for (int i = 0; i < Manager.games.size(); i++) {
			if (i == selected) {
				drawDesc(i);
				Settings.sel(g);
			} else Settings.desel(g);
			g.drawString(Manager.games.get(i).getName(), xOffset+2, yOffset);
			g.drawLine(xOffset+2, yOffset+10, xOffset+30, yOffset+10);
			g.drawLine(xOffset+2, yOffset+15, xOffset+15, yOffset+15);
			yOffset += 45;
		}
		g.setFont(Settings.getFont(10));
		g.setColor(Color.white);
		g.drawString("Campbell Cole", 3, PlatformerSettings.HEIGHT-290);
		g.drawString("Period 3 - Spring", 3, PlatformerSettings.HEIGHT-275);
		g.drawString("AP computer science A", 3, PlatformerSettings.HEIGHT-260);
		g.drawString("v"+Manager.version, 3, PlatformerSettings.HEIGHT-245);
	}
	
	int dxOffset = 230;
	int dyOffset = dyOffsetDef;
	
	public void drawDesc(int i) {
		g.setFont(Settings.getFont(18));
		dxOffset = 220;
		dyOffset = dyOffsetDef;
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
		g.setColor(Settings.TXT);
		g.fillRect(dxOffset-5, dyOffset-23, 413, (lines.length*35));
		
		g.setColor(Color.black);
		for (String s : lines) {
			if (s == null) continue;
			g.drawString(s, dxOffset, dyOffset);
			dyOffset += 35;
		}
	}

}
