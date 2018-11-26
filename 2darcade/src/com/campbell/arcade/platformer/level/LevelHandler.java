package com.campbell.arcade.platformer.level;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;

public class LevelHandler {
	
	private static List<File> levels = new ArrayList<File>();
	
	public static void initialize() {
		// load textures
		Dictionary.initialize();
		// locate (do not load) levels
		try {
			File f = new File(Manager.class.getResource("/res/platformer/levels").toURI());
			if (f.isDirectory()) {
				for (File lf : f.listFiles()) {
					levels.add(lf);
					System.out.println("level added: " + lf.getName());
				}
			}
		} catch (URISyntaxException e) {}
	}
	
	public static List<File> getLevels() {
		return levels;
	}
	
}
