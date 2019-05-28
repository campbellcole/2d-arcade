package com.campbell.arcade.platformer.editor;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.platformer.Platformer;
import com.campbell.arcade.platformer.PlatformerSettings;
import com.campbell.arcade.platformer.common.Drawable;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;
import com.campbell.arcade.platformer.common.tile.TileBlank;
import com.campbell.arcade.platformer.level.Dictionary;
import com.campbell.arcade.platformer.level.Level;
import com.campbell.arcade.platformer.level.LevelReader;

public class PlatformerEditor implements Game {

	Graphics2D g;
	PlatformerEditorRenderer r;
	
	public static PlatformerEditor stash;
	
	public PlatformerEditor(Graphics2D g) {
		this.g = g;
	}
	
	Class<? extends Drawable> sel;
	int selInd;
	Class<? extends Drawable> sels[];
	byte[][][] data = new byte[PlatformerSettings.HEIGHT/16][PlatformerSettings.WIDTH/16][2];
	
	int x, y;
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() {
		System.out.println("[PlatformerEditor] initializing...");
		
		System.out.println("[PlatformerEditor] populating dictionary...");
		Dictionary.initialize();
		sels = Dictionary.d.values().toArray(new Class[0]);
		sel = sels[0];
		x = y = 0;
		
		System.out.println("[PlatformerEditor] loading textures...");
		for (Class<? extends Drawable> c : Dictionary.d.values()) {
			Constructor<?> ctor;
			try {
				ctor = c.getConstructor(int.class, int.class, Level.class);
				ctor.newInstance(new Object[] { 0, 0, null });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	ArrayList<Integer> p;
	
	@Override
	public void update() {
		p = GameKeyListener.getPendingKeys();
		for (int k : p) {
			if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) x++;
			else if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) y++;
			else if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) x--;
			else if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) y--;
			if (x < 0) x = 0;
			if (y < 0) y = 0;
			if (x > PlatformerSettings.WIDTH/16 - 1) x = PlatformerSettings.WIDTH/16 - 1;
			if (y > PlatformerSettings.HEIGHT/16 - 1) y = PlatformerSettings.HEIGHT/16 - 1;
			
			if (k == KeyEvent.VK_SPACE) sel = sels[selInd + 1 == sels.length ? selInd = 0 : ++selInd];
			
			if (k == KeyEvent.VK_ENTER) {
				// x and y are switched intentionally
				if (sel.equals(TileBlank.class)) data[y][x][0] = 0;
				else if (sel.getSuperclass().equals(Tile.class)) data[y][x][0] = (byte) Dictionary.forKey(sel);
				else if (sel.getSuperclass().equals(Entity.class)) data[y][x][1] = (byte) Dictionary.forKey(sel);
			}
			
			if (k == KeyEvent.VK_I) {
				System.out.println("[PlatformerEditor] opening file open dialog...");
				openLevel();
				break; // prevents comodification exception
			}
			
			if (k == KeyEvent.VK_O) {
				System.out.println("[PlatformerEditor] opening file save dialog...");
				saveLevel();
				break;
			}
			
			if (k == KeyEvent.VK_P) {
				System.out.println("[PlatformerEditor] stashing self and opening Platformer with current level data...");
				PlatformerSettings.isTesting = true;
				PlatformerSettings.testingLevel = data;
				stash = this;
				Manager.instance.setGame(new Platformer(null));
				break;
			}
		}
		GameKeyListener.reset();
	}

	public void openLevel() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int res = fc.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			try {
				FileInputStream fis = new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String line;
				br.readLine();
				int ix = 0;
				while ((line = br.readLine()) != null) {
					data[ix++] = LevelReader.interpret(line);
				}
				br.close();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Unable to read level. Probably not a level.");
				System.out.println("[PlatformerEditor] unable to read level.");
			}
		}
	}
	
	public void saveLevel() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));
		int res = fc.showSaveDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			try {
				PrintWriter pw = new PrintWriter(new FileOutputStream(fc.getSelectedFile()));
				pw.append("level\n");
				for (int x = 0; x < data.length; x++) {
					for (int y = 0; y < data[x].length; y++) {
						pw.append((char)data[x][y][0] == 0x0 ? '0' : (char) data[x][y][0]);
					}
					pw.append('&');
					for (int y = 0; y < data[x].length-1; y++) { // length-1 fixes this and i have no idea why and i really dont care
						pw.append((char)data[x][y][1] == 0x0 ? '0' : (char) data[x][y][1]);
					}
					pw.append('\n');
				}
				pw.close();
				System.out.println("[PlatformerEditor] wrote file.");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Couldn't save file: " + e.getMessage());
				System.out.println("[PlatformerEditor] couldn't save file: " + e.getMessage());
			}
		}
	}
	
	@Override
	public void resize() {
		g = Manager.instance.modifySize(PlatformerSettings.WIDTH, PlatformerSettings.HEIGHT);
		Manager.instance.setLocationRelativeTo(null);
		r = new PlatformerEditorRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Level Editor";
	}

	@Override
	public String getDescription() {
		return "Platformer Level Editor";
	}

}
