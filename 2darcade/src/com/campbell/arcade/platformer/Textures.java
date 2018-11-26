package com.campbell.arcade.platformer;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.campbell.arcade.Manager;

public class Textures {
	
	private static HashMap<String, Image> textures = new HashMap<String, Image>();
	
	public static void register(String name, URL loc) {
		try {
			Image img = ImageIO.read(loc);
			textures.put(name, img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void register(String name, Image image) {
		textures.put(name, image);
	}
	
	public static Image get(String name) {
		return textures.get(name);
	}
	
	public static boolean didLoadTexture(String name) {
		return textures.keySet().contains(name);
	}
	
	public static void reset() {
		textures = new HashMap<String, Image>();
	}
	
	public static URL getURL(String name) {
		return Manager.class.getResource("/res/platformer/" + name);
	}
	
}
