package com.campbell.arcade.platformer.level;

import java.lang.reflect.Constructor;
import java.util.List;

import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;

public class LevelReader {
	
	public static byte[] interpret(String s) {
		byte[] i = new byte[PlatformerSettings.WIDTH/16];
		char[] c = s.toCharArray();
		int ix = 0;
		for (int x = 0; x < c.length; x++) {
			switch (c[x]) {
			case 'x':
				char mult = c[x-1];
				char lenc = c[x+1];
				int len = 0;
				switch (lenc) {
				case 'c':
					len = (PlatformerSettings.WIDTH/2)/16;
					break;
				case 'r':
					len = (PlatformerSettings.WIDTH/16)-(ix);
					break;
				case '(':
					int[] nPtr = countFrom(x+2, c);
					len = nPtr[0]-1;
					x = nPtr[1];
				}
				for (int y = 0; y < len; y++) {
					i[ix++]=(byte)mult;
				}
				x++;
				break;
			case '-':
				len = 1;
				if (c[x+1]=='x') {
					int[] nPtr = countFrom(x+3, c);
					len = nPtr[0];
					x = nPtr[1];
				}
				ix -= len;
				break;
			case '+':
				ix += 1;
			default:
				i[ix++]=(byte)c[x];
			}
		}
		return i;
	}
	
	private static int[] countFrom(int ptr, char[] arr) {
		String lenS = "";
		while (arr[ptr] != ')') {
			lenS += arr[ptr++];
		}
		return new int[] { Integer.parseInt(lenS), ptr};
	}
	
	public static LevelData interpret(byte[][] data) throws Exception {
		LevelData dat = new LevelData();
		List<Entity> ents = dat.getEntities();
		List<Tile> tiles = dat.getTiles();
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				Class<? extends Drawable> type = Dictionary.d.get((char)data[x][y]);
				Constructor<?> ct = type.getConstructor(int.class, int.class);
				Drawable inst = (Drawable) ct.newInstance(new Object[] { (y*16), (x*16) });
				if (type.getSuperclass().equals(Entity.class)) {
					ents.add((Entity)inst);
				} else if (type.getSuperclass().equals(Tile.class)) {
					tiles.add((Tile)inst);
				}
			}
		}
		dat.setEntities(ents);
		dat.setTiles(tiles);
		return dat;
	}
	
}
