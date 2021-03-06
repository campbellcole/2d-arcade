package com.campbell.arcade.platformer.level;

import java.lang.reflect.Constructor;
import java.util.List;

import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.common.tile.TileBlank;

public class LevelReader {
	
	public static byte[][] interpret(String s) {
		//System.out.println("[LevelReader] converting row to byte array...");
		byte[][] i = new byte[PlatformerSettings.WIDTH/16][2];
		int tLen = s.indexOf('&');
		char[] tc = s.substring(0, tLen).toCharArray();
		byte[] tdata = parse(tc);
		char[] ec = s.substring(tLen+1).toCharArray();
		byte[] edata = parse(ec);
		for (int y = 0; y < tdata.length; y++) {
			i[y][0]=tdata[y];
		}
		for (int y = 0; y < edata.length; y++) {
			i[y][1]=edata[y];
		}
		return i;
	}
	
	private static byte[] parse(char[] c) {
		byte[] i = new byte[PlatformerSettings.WIDTH/16];
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
				if (c[x+1]=='x') {
					int[] nPtr = countFrom(x+3, c);
					len = nPtr[0];
					x = nPtr[1];
				}
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
		return new int[] { Integer.parseInt(lenS), ptr };
	}
	
	public static LevelData interpret(byte[][][] data, Level instance) throws Exception {
		System.out.println("[LevelReader] interpreting level data...");
		LevelData dat = new LevelData();
		List<Entity> ents = dat.getEntities();
		List<Tile> tiles = dat.getTiles();
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				if ((char)data[x][y][0] != '0') {
					Drawable tile = newInstance((char)data[x][y][0], y, x, instance);
					if (tile instanceof Tile && !(tile instanceof TileBlank)) tiles.add((Tile)tile);
				}
				Drawable ent = newInstance((char)data[x][y][1], y, x, instance);
				if (ent instanceof Entity) ents.add((Entity)ent);
			}
		}
		dat.setEntities(ents);
		dat.setTiles(tiles);
		return dat;
	}
	
	private static Drawable newInstance(char type, int y, int x, Level lvl) throws Exception {
		Class<? extends Drawable> c = Dictionary.d.get(type);
		if (c == null) {
			return null;
		}
		Constructor<?> ct = c.getConstructor(int.class, int.class, Level.class);
		return (Drawable) ct.newInstance(new Object[] { (y*16), (x*16), lvl } );
	}
	
}
