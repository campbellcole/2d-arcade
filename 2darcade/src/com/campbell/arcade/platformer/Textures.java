package com.campbell.arcade.platformer;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.campbell.arcade.Manager;
import com.campbell.arcade.platformer.common.Drawable;

public class Textures {
	
	private static HashMap<Class<? extends Drawable>, Image> textures = new HashMap<Class<? extends Drawable>, Image>();
	
	public static void register(Class<? extends Drawable> clazz, URL loc) {
		try {
			if (!textures.containsKey(clazz)) {
				System.out.println("[Textures] registering texture for class \"" + clazz.getCanonicalName() + "\"...");
				Image img = ImageIO.read(loc);
				textures.put(clazz, img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void register(Class<? extends Drawable> clazz, Image image) {
		if (!textures.containsKey(clazz)) {
			System.out.println("[Textures] registering texture for class \"" + clazz.getCanonicalName() + "\"...");
			textures.put(clazz, image);
		}
	}
	
	public static Image get(Class<? extends Drawable> clazz) {
		return textures.get(clazz);
	}
	
	public static boolean didLoadTexture(Class<? extends Drawable> clazz) {
		return textures.keySet().contains(clazz);
	}
	
	public static void reset() {
		textures = new HashMap<Class<? extends Drawable>, Image>();
	}
	
	public static URL getURL(String name) {
		return Manager.class.getResource("/res/platformer/" + name);
	}
	
}
