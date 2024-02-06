package com.brickbreaker;

public class Player {

	private int lives;

	public Player(int lives) {
		super();
		this.lives = lives;
	}

	boolean isAlive() {
		return lives <= 0;
	}

	void removeLife() {
		lives -= 1;
	}
}
