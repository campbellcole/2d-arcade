package com.campbell.arcade.platformer.level;

import java.util.ArrayList;
import java.util.List;

import com.campbell.arcade.platformer.common.entity.Entity;
import com.campbell.arcade.platformer.common.tile.Tile;

public class LevelData {
	
	private List<Entity> ents = new ArrayList<Entity>();
	
	private List<Tile> tiles = new ArrayList<Tile>();
	
	public LevelData() {
	}
	
	public List<Entity> entsOfType(Class<? extends Entity> type) {
		List<Entity> entL = new ArrayList<Entity>();
		for (Entity ent : ents) {
			if (ent.getClass().equals(type)) {
				entL.add(ent);
			}
		}
		return entL;
	}
	
	public List<Entity> getEntities() {
		return ents;
	}
	
	public void setEntities(List<Entity> ents) {
		this.ents = ents;
	}
	
	public void removeEntity(Entity e) {
		ents.remove(e);
	}
	
	public List<Tile> tilesOfType(Class<? extends Entity> type) {
		List<Tile> tileL = new ArrayList<Tile>();
		for (Tile tile : tiles) {
			if (tile.getClass().equals(type)) {
				tileL.add(tile);
			}
		}
		return tileL;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
}
