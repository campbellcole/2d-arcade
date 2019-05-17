package com.campbell.arcade.platformer.level;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;

public class LevelHandler {
	
	private static List<Level> levels = new ArrayList<Level>();
	
	public static void initialize() {
		System.out.println("[LevelHandler] initializing...");
		// locate (do not load) levels
		try {
			File f = new File(Manager.class.getResource("/res/platformer/levels").toURI());
			if (f.isDirectory()) {
				for (File lf : f.listFiles()) {
					System.out.println("[LevelHandler] located level " + lf.getName());
					levels.add(new Level(lf));
				}
			}
		} catch (URISyntaxException e) {}
	}
	
	public static List<Level> getLevels() {
		return levels;
	}
	
}
