package com.brickbreaker;

public class Player {
	
	private int lives = 3;

	boolean isAlive() {
		return lives <= 0;
	}
	
	void removeLife() {
		lives -= 1;
	}
}
