package com.campbell.arcade.feedback;

import java.awt.Color;
import java.awt.Graphics2D;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class FeedbackRenderer implements Renderer {

	Graphics2D g;
	FeedbackScene instance;
	
	public FeedbackRenderer(Graphics2D g, FeedbackScene instance) {
		this.g = g;
		this.instance = instance;
	}
	
	final int xOffsetDef = 9;
	
	@Override
	public void draw() {
		g.setColor(Color.black);
		g.fillRect(0, 0, Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(31));
		g.drawString("Feedback", xOffsetDef, 35);
		
		g.setFont(Settings.getFont(16));
		g.drawString("Type feedback then press enter.", xOffsetDef, 63);
		
		drawInput();
	}
	
	final int iyOffsetDef = 100;
	
	int ixOffset = xOffsetDef+5, iyOffset = 100;
	
	public void drawInput() {
		iyOffset = iyOffsetDef;
		g.setFont(Settings.getFont(18));
		
		String[] split = instance.input.split(" ");
		String[] lines = new String[(instance.input.length()/30)+1];
		int line = 0;
		for (int ix = 0; ix < split.length; ix++) {
			if (lines[line] == null) lines[line] = "";
			lines[line] += split[ix] + " ";
			if (lines[line].length()+split[ix].length() > 40) {
				line++;
			}
		}
		
		g.setColor(Settings.TXT);
		g.fillRect(xOffsetDef, 80, Settings.DEFAULT_WIDTH-xOffsetDef*2, Settings.DEFAULT_HEIGHT-90);
		
		g.setColor(Color.black);
		
		for (String s : lines) {
			if (s == null) continue;
			g.drawString(s, ixOffset, iyOffset);
			iyOffset += 35;
		}
	}
	
}
