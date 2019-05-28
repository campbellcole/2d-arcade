package com.campbell.arcade.platformer.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.platformer.PlatformerRenderer;
import com.campbell.arcade.platformer.Textures;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.tile.TileBlank;
import com.campbell.arcade.platformer.common.tile.TileBlock;
import com.campbell.arcade.platformer.level.Dictionary;

public class PlatformerEditorRenderer implements Renderer {

	Graphics2D g;
	PlatformerEditor instance;
	Image bg;
	
	public PlatformerEditorRenderer(Graphics2D g, PlatformerEditor instance) {
		this.g = g;
		this.instance = instance;
		bg = PlatformerRenderer.generateBackground();
	}
	
	/*
	 * NOTE: levels are stored incorrectly but i dont feel like fixing it so switch x and y
	 */
	
	@Override
	public void draw() {
		g.drawImage(bg, 0, 0, null);
		for (int x = 0; x < instance.data.length; x++) {
			for (int y = 0; y < instance.data[x].length; y++) {
				for (int i = 0; i < 2; i++) {
					Class<? extends Drawable> c = Dictionary.d.get((char) instance.data[x][y][i]);
					if (c == null) continue;
					if (c.equals(TileBlock.class)) {
						g.setColor(Color.red);
						g.drawRect(y*16, x*16, 15, 15);
					} else {
						g.drawImage(Textures.get(c), y*16, x*16, null);
					}
				}
			}
		}
		if (Textures.get(instance.sel) == null) {
			if (instance.sel.equals(TileBlank.class)) g.setColor(Color.black);
			else if (instance.sel.equals(TileBlock.class)) g.setColor(Color.red);
			g.drawRect(instance.x*16, instance.y*16, 15, 15);
		} else {
			g.drawImage(Textures.get(instance.sel), instance.x*16, instance.y*16, null);
		}
	}

}
