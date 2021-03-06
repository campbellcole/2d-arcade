package com.campbell.arcade.platformer.level;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.campbell.arcade.Manager;
import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;

public class Level {
	
	public byte[][][] data = new byte[PlatformerSettings.HEIGHT/16][PlatformerSettings.WIDTH/16][2];
	
	int index;
	
	public LevelData ld;
	
	public Level(int index) {
		this.index = index;
	}
	
	public String id;
	
	public void load() {
		System.out.println("[Level] loading level...");
		Level passthrough = this;
		Thread t = new Thread() {
			
			@Override
			public void run() {
				try {
					if (index >= 0) {
						System.out.println("[Level->LoadingThread] opening level file...");
						InputStream fis = Manager.class.getResourceAsStream("/res/platformer/levels/level"+index+".level");
						BufferedReader br = new BufferedReader(new InputStreamReader(fis));
						String line;
						id = br.readLine();
						System.out.println("[Level->LoadingThread] got level index " + index);
						int ix = 0;
						System.out.println("[Level->LoadingThread] phase 1 interpreting...");
						while ((line = br.readLine()) != null) {
							data[ix++] = LevelReader.interpret(line);
						}
						br.close();
					} else {
						System.out.println("[Level->LoadingThread] level is a test level. skipping phase 1.");
					}
					System.out.println("[Level->LoadingThread] phase 2 interpreting...");
					ld = LevelReader.interpret(data, passthrough);
					System.out.println("[Level->LoadingThread] finished. calling back...");
					try {
						((Platformer) Manager.instance.currentGame).levelDidLoad();
					} catch (ClassCastException ex) {
						System.out.println("[Level->LoadingThread] game changed before loading finished. oh well.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		};
		t.start();
	}
	
}
