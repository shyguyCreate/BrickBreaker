package com.brickbreaker;

public class Player {
	
	private int lives = 3;

	protected boolean isAlive() {
		return lives <= 0;
	}
	
	protected void removeLife() {
		lives -= 1;
	}
}
