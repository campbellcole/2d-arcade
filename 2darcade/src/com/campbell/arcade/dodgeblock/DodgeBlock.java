package com.campbell.arcade.dodgeblock;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;

public class DodgeBlock implements Game {

	DodgeBlockRenderer rn;
	Graphics2D g;

	public DodgeBlock(Graphics2D g) {
		this.g = g;
	}

	final int BLOCKSIZE = 45;

	Random r;
	BlockType[][] grid;

	static enum BlockType {
		EMPTY, OBSTACLE, PLAYER, POWERUP;
	}

	@Override
	public void initialize() {
		r = new Random();
		setupGrid();
	}

	private void setupGrid() {
		int gridX = Settings.WIDTH / BLOCKSIZE;
		int gridY = Settings.HEIGHT / BLOCKSIZE;
		grid = new BlockType[gridX][gridY];
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = BlockType.EMPTY;
			}
		}
		grid[0][0] = BlockType.PLAYER;
	}

	int wait = DodgeBlockSettings.WAIT;
	int difficulty = DodgeBlockSettings.DIFFICULTY;
	int acceleration = DodgeBlockSettings.ACCELERATION;
	int step_amount = DodgeBlockSettings.STEP_AMOUNT;

	int waitTimer = 0;
	int accelerationTimer = 0;
	int scoreTimer = 0;
	int difficultyTimer = 0;

	boolean pendingUp = false, pendingDown = false, pendingLeft = false, pendingRight = false;

	@Override
	public void update() {
		checkKeys();
		waitTimer++;
		accelerationTimer++;
		scoreTimer++;
		for (int x = 0; x < grid.length; x++) { // iterate through grid
			for (int y = 0; y < grid[x].length; y++) { // iterate through grid[x]
				if (grid[x][y] == BlockType.PLAYER) { // if block at current location is a player
					if (pendingUp) { // if up was pressed
						if (y > 0 && y <= grid[x].length - 1) { // make sure they're not gonna move off the screen and
																// cause an IndexArrayOutOfBoundsException
							if (grid[x][y - 1] == BlockType.POWERUP) {
								powerup();
								grid[x][y] = BlockType.EMPTY; // set current block to empty
								grid[x][y - 1] = BlockType.PLAYER; // set block one above to the player
							} else if (grid[x][y - 1] != BlockType.OBSTACLE) {
								grid[x][y] = BlockType.EMPTY;
								grid[x][y - 1] = BlockType.PLAYER;
							}
						}
						pendingUp = false; // reset pending up to stop movement
						pendingDown = false;
						pendingLeft = false;
						pendingRight = false;
					}
					if (pendingDown) { // if down was pressed
						if (y >= 0 && y < grid[x].length - 1) { // make sure they're not gonna move off the screen and
																// cause an IndexArrayOutOfBoundsException
							if (grid[x][y + 1] == BlockType.POWERUP) {
								powerup();
								grid[x][y] = BlockType.EMPTY; // set current block to empty
								grid[x][y + 1] = BlockType.PLAYER; // set block one above to the player
							} else if (grid[x][y + 1] != BlockType.OBSTACLE) {
								grid[x][y] = BlockType.EMPTY;
								grid[x][y + 1] = BlockType.PLAYER;
							}
						}
						pendingUp = false; // reset pending up to stop movement
						pendingDown = false;
						pendingLeft = false;
						pendingRight = false;
					}
					if (pendingLeft) { // you get the point
						if (x > 0 && x <= grid.length - 1) {
							if (grid[x - 1][y] == BlockType.POWERUP) {
								powerup();
								grid[x][y] = BlockType.EMPTY;
								grid[x - 1][y] = BlockType.PLAYER;
							} else if (grid[x - 1][y] != BlockType.OBSTACLE) {
								grid[x][y] = BlockType.EMPTY;
								grid[x - 1][y] = BlockType.PLAYER;
							}
						}
						pendingUp = false; // reset pending up to stop movement
						pendingDown = false;
						pendingLeft = false;
						pendingRight = false;
					}
					if (pendingRight) { // you get the point
						if (x >= 0 && x < grid.length - 1) {
							if (grid[x + 1][y] == BlockType.POWERUP) {
								powerup();
								grid[x][y] = BlockType.EMPTY;
								grid[x + 1][y] = BlockType.PLAYER;
							} else if (grid[x + 1][y] != BlockType.OBSTACLE) {
								grid[x][y] = BlockType.EMPTY;
								grid[x + 1][y] = BlockType.PLAYER;
							}
						}
						pendingUp = false; // reset pending up to stop movement
						pendingDown = false;
						pendingLeft = false;
						pendingRight = false;
					}
					// NOTE: we reset all pending because the player will move but the current index
					// will be different and will spawn multiple players
				} else if ((grid[x][y] == BlockType.OBSTACLE || grid[x][y] == BlockType.POWERUP) && waitTimer >= wait) { // for
																															// each
																															// obstacle
					BlockType next = grid[x][y];
					if (x == 0) { // if obstacle is at the end of the screen
						grid[x][y] = BlockType.EMPTY; // remove obstacle
					} else {
						if (grid[x - 1][y] == BlockType.EMPTY) { // if next block left is empty
							grid[x][y] = BlockType.EMPTY; // remove current block
							grid[x - 1][y] = next; // set left block to obstacle
						} else if (grid[x - 1][y] == BlockType.PLAYER) { // if the next block is a player
							if (next == BlockType.OBSTACLE)
								playerHit(); // do the grid thing yet again
							if (next == BlockType.POWERUP) {
								grid[x][y] = BlockType.EMPTY;
								powerup();
							}
						}
					}
				}
			}
		}
		if (waitTimer >= wait) { // if the wait timer is equal to the amount of time to wait, spawn a new
									// obstacle and reset wait timer
			if (difficulty == 1) {
				spawnObstacle();
				spawnObstacle();
			} else if (++difficultyTimer == difficulty - 1) {
				spawnObstacle();
				difficultyTimer = 0;
			}
			waitTimer = 0;
		}
		if ((accelerationTimer / 60) == acceleration) { // time to accelerate
			wait -= step_amount; // subtract stepAmount from wait time
			if (wait <= 6) { // the game stops working at 4 for some reason so cap it at 6
				wait = 6;
			}
			accelerationTimer = 0; // reset timer
		}
	}

	private void spawnObstacle() {
		int x = grid.length - 1;
		int y = r.nextInt(grid[grid.length - 1].length);
		if (grid[x][y] == BlockType.PLAYER) {
			playerHit();
		} else {
			if (r.nextInt(60) == 1) {
				grid[x][y] = BlockType.POWERUP;
			} else {
				grid[x][y] = BlockType.OBSTACLE;
			}
		}
	}

	private void powerup() {
		int p = r.nextInt(3);
		switch (p) {
		case 0:
			wait = 20;
			accelerationTimer = 0;
			rn.powerup("Slow Down");
			break;
		case 1:
			scoreTimer += 10 * 60;
			rn.powerup("Score Up");
			break;
		case 2:
			waitTimer = -100;
			rn.powerup("Freeze");
			break;
		}
	}

	private void playerHit() {
		if (!DodgeBlockSettings.GOD || !DodgeBlockSettings.DEBUG) {
			int score = scoreTimer / 60;
			DecimalFormat df = new DecimalFormat("#.##");
			JOptionPane.showMessageDialog(Manager.instance, "You got hit! Your score was: " + df.format(score));
			Manager.instance.setGame(this);
		}
	}

	private void checkKeys() {
		ArrayList<Integer> p = GameKeyListener.getPendingKeys();
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i) == KeyEvent.VK_W || p.get(i) == KeyEvent.VK_UP) {
				pendingUp = true;
				break;
			}
			if (p.get(i) == KeyEvent.VK_S || p.get(i) == KeyEvent.VK_DOWN) {
				pendingDown = true;
				break;
			}
			if (p.get(i) == KeyEvent.VK_A || p.get(i) == KeyEvent.VK_LEFT) {
				pendingLeft = true;
				break;
			}
			if (p.get(i) == KeyEvent.VK_D || p.get(i) == KeyEvent.VK_RIGHT) {
				pendingRight = true;
				break;
			}
			if (p.get(i) == KeyEvent.VK_E) {
				DodgeBlockSettings.DEBUG = !DodgeBlockSettings.DEBUG;
				break;
			}
			if (p.get(i) == KeyEvent.VK_Q) {
				DodgeBlockSettings.GOD = !DodgeBlockSettings.GOD;
				break;
			}
		}
	}

	@Override
	public Renderer getRenderer() {
		return rn;
	}

	@Override
	public String getName() {
		return "DodgeBlock-GAME";
	}

	@Override
	public void resize() {
		g = Manager.instance.modifySize(15 * BLOCKSIZE, 10 * BLOCKSIZE);
		rn = new DodgeBlockRenderer(g, this);
	}

	@Override
	public String getDescription() {
		return "non-selectable. if you see this fuck you.";
	}

}
