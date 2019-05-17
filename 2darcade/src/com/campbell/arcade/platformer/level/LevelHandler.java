package com.campbell.arcade.platformer.level;

import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;

public class LevelHandler {
	
	private static List<Level> levels = new ArrayList<Level>();
	
	public static void initialize() {
		System.out.println("[LevelHandler] initializing...");
		// locate (do not load) levels
		int index = 0;
		while (Manager.class.getResourceAsStream("/res/platformer/levels/level"+(index)+".level") != null) {
			levels.add(new Level(index));
			System.out.println("[LevelHandler] located level level"+index+".level");
			index++;
		}
	}
	
	public static List<Level> getLevels() {
		return levels;
	}
	
}
