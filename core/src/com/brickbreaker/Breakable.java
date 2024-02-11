package com.brickbreaker;

/**
 * Every object that can be damaged and eventually break (or die)
 * like the bricks and the monster.
 */
public abstract class Breakable extends Surface {

	// Number of hits the object has left before “breaking”.
	protected int resistance;

	/**
	 * Breakable constructor assign resistance.
	 * 
	 * @param resistance Resistance of the object starting the game.
	 */
	public Breakable(int resistance) {
		// Call constructor of parent.
		super();
		// Assign resistance to class resistance.
		this.resistance = resistance;
	}

	/**
	 * Subtract one from the resistance and test if this object has resistance left.
	 */
	protected void receiveDamage() {
		// Remove one from resistance.
		resistance -= 1;

		// If resistance is less than or equal zero.
		// remove this object from surfaces list.
		if (resistance <= 0)
			Surface.removeSurface(this);
	}
}
