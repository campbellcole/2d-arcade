package com.campbell.arcade.platformer.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.campbell.arcade.platformer.PlatformerSettings;

public class Level {
	
	public byte[][] data = new byte[PlatformerSettings.HEIGHT/16][PlatformerSettings.WIDTH/16];
	
	File f;
	
	public Level(File f) {
		this.f = f;
	}
	
	public String id, name;
	
	public void load() {
		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line;
			id = br.readLine();
			name = br.readLine();
			int ix = 0;
			while ((line = br.readLine()) != null) {
				data[ix++] = LevelReader.interpret(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
