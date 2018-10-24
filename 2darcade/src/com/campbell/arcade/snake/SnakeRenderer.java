package com.campbell.arcade.snake;

import java.awt.Color;
import java.awt.Graphics2D;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.snake.Snake.BlockType;

public class SnakeRenderer implements Renderer {

	Graphics2D g;
	Snake instance;
	
	public SnakeRenderer(Graphics2D g, Snake instance) {
		this.g = g;
		this.instance = instance;
	}
	
	@Override
	public void draw() {
		g.setColor(Settings.BG);
		g.fillRect(0, 0, Settings.POSTHEIGHT, Settings.POSTHEIGHT);
		BlockType[][] grid = instance.grid;
		int xOffset = 0;
		int yOffset = 0;
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				switch (grid[x][y]) {
				case HEAD:
				case BODY:
					g.setColor(new Color(80,80,80));
					break;
				case EMPTY:
					g.setColor(new Color(190,190,190));
					break;
				case FOOD:
					g.setColor(Settings.SEL);
					break;
				}
				g.fillRect(xOffset, yOffset, instance.BLOCKSIZE, instance.BLOCKSIZE);
				yOffset += instance.BLOCKSIZE;
			}
			yOffset = 0;
			xOffset += instance.BLOCKSIZE;
		}
		g.setColor(Settings.TXT);
		g.setFont(Settings.getFont(16));
		g.drawString(""+(int)instance.length, Settings.INSET_LEFT+(instance.length>=10 ? 5: 11), Settings.INSET_TOP+1);
	}

}
