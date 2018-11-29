package com.campbell.arcade.platformer.level;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;

public class LevelHandler {
	
	private static List<Level> levels = new ArrayList<Level>();
	
	public static void initialize() {
		// locate (do not load) levels
		try {
			File f = new File(Manager.class.getResource("/res/platformer/levels").toURI());
			if (f.isDirectory()) {
				for (File lf : f.listFiles()) {
					levels.add(new Level(lf));
					System.out.println("level found: " + lf.getName());
				}
			}
		} catch (URISyntaxException e) {}
	}
	
	public static List<Level> getLevels() {
		return levels;
	}
	
}
