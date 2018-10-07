package com.campbell.arcade.snake;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class Snake implements Game {
	
	Graphics2D g;
	SnakeRenderer r;
	
	public Snake(Graphics2D g) {
		this.g = g;
		this.r = new SnakeRenderer(g, this);
	}
	
	final int BLOCKSIZE = 25;
	
	Random rn;
	BlockType[][] grid;
	
	static enum BlockType {
		EMPTY, FOOD, BODY, HEAD;
	}
	
	@Override
	public void initialize() {
		rn = new Random();
		setupGrid();
	}

	int headX = 0, headY = 0;
	
	private void setupGrid() {
		int gridX = Settings.WIDTH / BLOCKSIZE;
		int gridY = Settings.HEIGHT / BLOCKSIZE;
		grid = new BlockType[gridX][gridY];
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = BlockType.EMPTY;
			}
		}
		headX = gridX/2;
		headY = gridY/2;
		grid[headX][headY] = BlockType.HEAD;
		Integer[] e = new Integer[2];
		e[0] = headX;
		e[1] = headY+1;
		segments.add(e);
		grid[rn.nextInt(gridX)][rn.nextInt(gridY)] = BlockType.FOOD;
	}
	
	boolean pendingUp = false, pendingDown = false, pendingLeft = false, pendingRight = false;
	
	int wait = 10;
	int waitTimer = 0;
	
	int length = 1;
	int direction = 0; // 0 - up, 1 - right, 2 - down, 3 - left
	
	List<Integer[]> segments = new ArrayList<Integer[]>(); // excludes head
	
	@Override
	public void update() {
		checkKeys();
		waitTimer++;
		if (waitTimer == wait) {
			waitTimer = 0;
			int nextX = headX, nextY = headY;
			if (pendingUp) {
				resetKeys();
				if (direction!=2) direction = 0;
			}
			if (pendingDown) {
				resetKeys();
				if (direction!=0) direction = 2;
			}
			if (pendingLeft) {
				resetKeys();
				if (direction!=1) direction = 3;
			}
			if (pendingRight) {
				resetKeys();
				if (direction!=3) direction = 1;
			}
			switch (direction) {
			case 0:
				nextY--;
				break;
			case 1:
				nextX++;
				break;
			case 2:
				nextY++;
				break;
			case 3:
				nextX--;
				break;
			}
			if (grid[nextX][nextY] == BlockType.BODY) {
				// TODO: hit
				System.out.println("hit self");
			} else if (grid[nextX][nextY] == BlockType.EMPTY || grid[nextX][nextY] == BlockType.FOOD) {
				boolean ate = grid[nextX][nextY] == BlockType.FOOD;
				grid[nextX][nextY] = BlockType.HEAD;
				int nextBodyX = headX;
				int nextBodyY = headY;
				headX = nextX;
				headY = nextY;
				int currentSeg = 0;
				int lastX = nextBodyX, lastY = nextBodyY;
				for (int i = 0; i < length-1; i++) {
					grid[nextBodyX][nextBodyY] = BlockType.BODY;
					nextBodyX = segments.get(currentSeg)[0];
					nextBodyY = segments.get(currentSeg)[1];
					Integer[] last = new Integer[2];
					last[0] = lastX;
					last[1] = lastY;
					lastX = nextBodyX;
					lastY = nextBodyY;
					segments.set(i, last);
					currentSeg++;
				}
				Integer[] last = new Integer[2];
				last[0] = lastX;
				last[1] = lastY;
				lastX = nextBodyX;
				lastY = nextBodyY;
				segments.set(length-1, last);
				int endX = segments.get(length-1)[0];
				int endY = segments.get(length-1)[1];
				grid[endX][endY] = BlockType.EMPTY;
				if (ate) {
					eat(nextBodyX, nextBodyY);
				}
			}
		}	
	}

	private void eat(int x, int y) {
		length++;
		Integer[] coords = new Integer[2];
		coords[0] = x;
		coords[1] = y;
		segments.add(coords);
		grid[rn.nextInt(grid.length)][rn.nextInt(grid[0].length)] = BlockType.FOOD;
	}
	
	private void resetKeys() {
		pendingUp = false;
		pendingDown = false;
		pendingLeft = false;
		pendingRight = false;
	}
	
	private void checkKeys() {
		ArrayList<Integer> p = GameKeyListener.getListener().getPendingKeys();
		boolean didFind = true;
		while (didFind) {
			didFind = false;
			int i;
			for (i = 0; i < p.size(); i++) {
				if (p.get(i) == KeyEvent.VK_W || p.get(i) == KeyEvent.VK_UP) {
					pendingUp = true;
					didFind = true;
					break;
				}
				if (p.get(i) == KeyEvent.VK_S || p.get(i) == KeyEvent.VK_DOWN) {
					pendingDown = true;
					didFind = true;
					break;
				}
				if (p.get(i) == KeyEvent.VK_A || p.get(i) == KeyEvent.VK_LEFT) {
					pendingLeft = true;
					didFind = true;
					break;
				}
				if (p.get(i) == KeyEvent.VK_D || p.get(i) == KeyEvent.VK_RIGHT) {
					pendingRight = true;
					didFind = true;
					break;
				}
			}
			if (didFind) {
				p.remove(i);
				GameKeyListener.getListener().setPendingKeys(p);
			}
		}
	}
	
	@Override
	public void resize() {
		g = Manager.instance.modifySize(30*BLOCKSIZE, 25*BLOCKSIZE);
		r = new SnakeRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Snake";
	}

	@Override
	public String getDescription() {
		return "The classical snake game. ------ WIP BARELY WORKS ------";
	}

}
