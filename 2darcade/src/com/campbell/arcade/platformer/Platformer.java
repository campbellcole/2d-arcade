package com.campbell.arcade.platformer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.introscene.IntroScene;
import com.campbell.arcade.platformer.common.PlatformerEvent;
import com.campbell.arcade.platformer.common.PlatformerEvent.PlatformerEventType;
import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.level.Dictionary;
import com.campbell.arcade.platformer.level.Level;
import com.campbell.arcade.platformer.level.LevelHandler;
import com.campbell.arcade.platformer.level.LevelHelper;

public class Platformer implements Game {
	
	public static List<PlatformerEvent> eventQueue = new ArrayList<PlatformerEvent>();
	
	Graphics2D g;
	PlatformerRenderer r;
	
	Level currentLevel;
	boolean loading = false;
	
	public Platformer(Graphics2D g) {
		this.g = g;
	}

	@Override
	public void initialize() {
		System.out.println("[Platformer] initializing...");
		
		System.out.println("[Platformer] populating dictionary...");
		// load dictionary
		Dictionary.initialize();
		
		System.out.println("[Platformer] locating levels...");
		// read levels
		LevelHandler.initialize();
		
		System.out.println("[Platformer] loading level 0...");
		// display level 0
		loadLevel(LevelHandler.getLevels().get(0));
	}

	@Override
	public void update() {
		if (loading) return;
		for (Entity ent : currentLevel.ld.getEntities()) {
			ent.tick();
		}
		while (!Platformer.eventQueue.isEmpty()) {
			PlatformerEvent ev = Platformer.eventQueue.get(0);
			System.out.println("[Platformer] new event: " + ev.type.name());
			switch (ev.type) {
			case NEXTLEVEL:
				if (loading) break;
				try {
					currentLevel = LevelHandler.getLevels().get(Integer.parseInt(ev.data));
					System.out.println("[Platformer] loading next level...");
					loadLevel(currentLevel);
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("[Platformer] no more levels, entering introscene...");
					Manager.instance.setGame(new IntroScene(null));
				}
				break;
			case RESTART:
				System.out.println("[Platformer] restarting current level...");
				loadLevel(currentLevel);
				break;
			case DEATH:
				System.out.println("[Platformer] player died. reloading current level...");
				r.displayMessage(ev.data);
				Platformer.eventQueue.add(new PlatformerEvent(PlatformerEventType.REMOVE, ""+LevelHelper.getPlayer(currentLevel).hashCode()));
				new Thread() {
					@Override
					public void run() {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						loadLevel(currentLevel);
					}
				}.start();
				break;
			case REMOVE:
				int hash = Integer.parseInt(ev.data);
				System.out.println("[Platformer] removing entity with hash " + hash);
				List<Entity> ents = currentLevel.ld.getEntities();
				for (int i = 0; i < ents.size(); i++) {
					if (ents.get(i).hashCode() == hash) {
						ents.remove(i);
					}
				}
				currentLevel.ld.setEntities(ents);
				break;
			default:
				break;
			}
			Platformer.eventQueue.remove(0);
		}
	}

	public void loadLevel(Level lvl) {
		System.out.println("[Platformer] loading level...");
		loading = true;
		currentLevel = lvl;
		currentLevel.load();
		r.displayMessageHard("Loading...");
	}
	
	public void levelDidLoad() {
		System.out.println("[Platformer] finished loading level.");
		r.hideMessageHard();
		loading = false;
	}
	
	@Override
	public void resize() {
		g = Manager.instance.modifySize(PlatformerSettings.WIDTH, PlatformerSettings.HEIGHT);
		Manager.instance.setLocationRelativeTo(null);
		r = new PlatformerRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Platformer";
	}

	@Override
	public String getDescription() {
		return "if you've played mario then you might enjoy this game. it's basically mario.";
	}
	
}
