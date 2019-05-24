package com.campbell.arcade.feedback;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.GameKeyListener;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.common.Settings;
import com.campbell.arcade.introscene.IntroScene;

public class FeedbackScene implements Game {

	FeedbackRenderer r;
	Graphics2D g;
	
	public FeedbackScene(Graphics2D g) {
		this.g = g;
		this.r = new FeedbackRenderer(g, this);
	}
	
	@Override
	public void initialize() {}

	ArrayList<Integer> p;
	
	String input = "";
	
	@Override
	public void update() {
		p = GameKeyListener.getPendingKeys();
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i) == KeyEvent.VK_ENTER) {
				try {
					writeInputToFile();
					Manager.instance.setGame(new IntroScene(null));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (p.get(i) == KeyEvent.VK_BACK_SPACE) {
				try {
					input = input.substring(0, input.length()-1);
				} catch (Exception ex) {input = "";}
			} else if (p.get(i) == KeyEvent.VK_SPACE) {
				input += " ";
			} else {
				char c = KeyEvent.getKeyText(p.get(i)).charAt(0);
				if (Character.isAlphabetic(c) || Character.isDigit(c)) {
					input += c;
				}
			}
		}
		GameKeyListener.reset();
	}

	public void writeInputToFile() throws IOException {
		Files.write(Paths.get("feedback.txt"), input.concat("\n").getBytes(), StandardOpenOption.APPEND);
	}
	
	@Override
	public void resize() {
		g = Manager.instance.modifySize(Settings.DEFAULT_WIDTH, Settings.DEFAULT_HEIGHT);
		r = new FeedbackRenderer(g, this);
	}

	@Override
	public Renderer getRenderer() {
		return r;
	}

	@Override
	public String getName() {
		return "Feedback";
	}

	@Override
	public String getDescription() {
		return "Leave feedback.";
	}

}
