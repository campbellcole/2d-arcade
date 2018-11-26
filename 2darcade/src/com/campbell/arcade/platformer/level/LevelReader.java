package com.campbell.arcade.platformer.level;

import com.campbell.arcade.platformer.PlatformerSettings;

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
				}
				for (int y = 0; y < len; y++) {
					i[ix++]=(byte)mult;
				}
				x++;
				break;
			case '-':
				ix -= 1;
				break;
			case '+':
				ix += 1;
			default:
				i[ix++]=(byte)c[x];
			}
		}
		return i;
	}
	
}
