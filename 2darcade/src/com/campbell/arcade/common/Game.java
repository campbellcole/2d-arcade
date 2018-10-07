package com.campbell.arcade.common;

public interface Game {
	
	public void initialize();
	
	public void update();
	
	public void resize();
	
	public Renderer getRenderer();
	
	public String getName();
	
	public String getDescription();
	
}
