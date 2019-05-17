package com.campbell.arcade.platformer.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.campbell.arcade.Manager;
import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;

public class Level {
	
	public byte[][][] data = new byte[PlatformerSettings.HEIGHT/16][PlatformerSettings.WIDTH/16][2];
	
	File f;
	
	public LevelData ld;
	
	public Level(File f) {
		this.f = f;
	}
	
	public String id, name;
	
	public void load() {
		System.out.println("[Level] loading level...");
		Level passthrough = this;
		Thread t = new Thread() {
			
			@Override
			public void run() {
				try {
					System.out.println("[Level->LoadingThread] opening level file...");
					FileInputStream fis = new FileInputStream(f);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					String line;
					id = br.readLine();
					name = br.readLine();
					System.out.println("[Level->LoadingThread] got level " + name + " with id " + id);
					int ix = 0;
					System.out.println("[Level->LoadingThread] phase 1 interpreting...");
					while ((line = br.readLine()) != null) {
						data[ix++] = LevelReader.interpret(line);
					}
					System.out.println("[Level->LoadingThread] phase 2 interpreting...");
					ld = LevelReader.interpret(data, passthrough);
					br.close();
					System.out.println("[Level->LoadingThread] finished. calling back...");
					((Platformer) Manager.instance.currentGame).levelDidLoad();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		t.start();
	}
	
}
