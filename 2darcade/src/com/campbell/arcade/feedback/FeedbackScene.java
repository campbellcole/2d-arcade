package com.campbell.arcade.feedback;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import com.campbell.arcade.Manager;
import com.campbell.arcade.common.Game;
import com.campbell.arcade.common.Renderer;
import com.campbell.arcade.introscene.IntroScene;

public class FeedbackScene implements Game {

	FileOutputStream fis;
	PrintWriter pw;
	
	public FeedbackScene(Graphics2D g) {}
	
	@Override
	public void initialize() {
		try {
			fis = new FileOutputStream(new File("feedback.txt"));
			pw = new PrintWriter(fis);
			String f = JOptionPane.showInputDialog("Enter feedback:");
			pw.append(f+"\n");
			pw.close();
			fis.close();
			Manager.instance.setGame(new IntroScene(null));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {}

	@Override
	public void resize() {}

	@Override
	public Renderer getRenderer() {
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Feedback";
	}

	@Override
	public String getDescription() {
		return "Leave feedback.";
	}

}
