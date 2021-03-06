package com.campbell.arcade.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

public class GameKeyListener implements KeyListener {

	private static GameKeyListener gkl;
	
	public static void initialize() {
		gkl = new GameKeyListener();
	}
	
	public static GameKeyListener getListener() {
		return gkl;
	}
	
	private static ArrayList<Integer> pending = new ArrayList<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		pending.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pending.removeAll(Collections.singleton(e.getKeyCode()));
	}

	public static ArrayList<Integer> getPendingKeys() {
		return pending;
	}
	
	public static void setPendingKeys(ArrayList<Integer> p) {
		pending = p;
	}
	
	public static void reset() {
		pending = new ArrayList<Integer>();
	}

}
