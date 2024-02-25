package com.brickbreaker;

/**
 * Class the holds the remaining lives of the player.
 */
public class Player {

	/**
	 * Chances to keep playing the game.
	 */
	private int lives;

	/**
	 * Player constructor assign lives.
	 * 
	 * @param lives Lives the player will have starting the game.
	 */
	public Player(int lives) {
		// Call constructor of parent.
		super();
		// Assign lives to class lives.
		this.lives = lives;
	}

	/**
	 * Checks if the player still has lives left to continue playing.
	 * 
	 * @return true when lives is bigger than zero
	 */
	boolean isAlive() {
		return lives > 0;
	}

	/**
	 * Remove a life from the player.
	 */
	void removeLife() {
		lives -= 1;
	}
}
