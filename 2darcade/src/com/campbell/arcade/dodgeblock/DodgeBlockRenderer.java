package com.campbell.arcade.dodgeblock;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.dodgeblock.DodgeBlock.BlockType;

public class DodgeBlockRenderer implements Renderer {
	
	Graphics2D r;
	DodgeBlock instance;
	DecimalFormat df;
	
	public DodgeBlockRenderer(Graphics2D g, DodgeBlock instance) {
		this.r = g;
		this.instance = instance;
		df = new DecimalFormat("#.##");
	}

	String powerup = "default";
	int powerupTimer = 240;
	final int powerupCap = 240;
	
	@Override
	public void draw() {
		powerupTimer++;
		float time = (float) instance.scoreTimer / 60f;
		r.setColor(Settings.BG);
		r.fillRect(0, 0, Settings.POSTWIDTH, Settings.POSTHEIGHT);
		BlockType[][] grid = instance.grid;
		int xOffset = 0;
		int yOffset = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				switch (grid[x][y]) {
				case OBSTACLE:
					r.setColor(new Color(100,100,100));
					break;
				case PLAYER:
					r.setColor(Color.WHITE);
					break;
				case EMPTY:
					r.setColor(new Color(190,190,190));
					break;
				case POWERUP:
					r.setColor(Color.decode("#891880"));
					break;
				}
				r.fillRect(xOffset, yOffset, instance.BLOCKSIZE, instance.BLOCKSIZE);
				yOffset += instance.BLOCKSIZE;
			}
			yOffset = 0;
			xOffset += instance.BLOCKSIZE;
		}
		if (DodgeBlockSettings.DEBUG) {
			r.setFont(Font.decode("Arial"));
			r.setColor(Color.BLACK);
			
			// score timer
			df.applyPattern("#.##");
			r.drawString(df.format(time), Settings.INSET_LEFT, Settings.INSET_TOP-14);
			
			// wait
			r.drawString(instance.wait+"", Settings.INSET_LEFT, Settings.INSET_TOP);
			
			// acceleration
			r.drawString(instance.acceleration+"", Settings.INSET_LEFT, Settings.INSET_TOP+15);
			
			// acceleration timer
			r.drawString(instance.accelerationTimer+"", Settings.INSET_LEFT, Settings.INSET_TOP+30);
			
			// acceleration timer (seconds)
			r.drawString(instance.accelerationTimer/60+"", Settings.INSET_LEFT, Settings.INSET_TOP+45);
			
			// godmode boolean
			r.drawString(DodgeBlockSettings.GOD+"", Settings.INSET_LEFT, Settings.INSET_TOP+60);
		} else {
			r.setColor(Settings.TXT);
			r.setFont(Settings.getFont(16));
			r.drawString(""+(int)time, Settings.INSET_LEFT+(time>=10 ? 5: 11), Settings.INSET_TOP+1);
		}
		if (powerupTimer < powerupCap) {
			r.setFont(Settings.getFont(30));
			FontMetrics m = r.getFontMetrics();
			int cX = (Settings.POSTWIDTH - m.stringWidth(powerup))/2;
			int cY = ((Settings.POSTHEIGHT - m.getHeight())/2);
			r.setColor(Settings.TXT);
			r.drawString(powerup, cX, cY);
		}
	}
	
	public void powerup(String n) {
		powerup = n;
		powerupTimer = 0;
	}

}
