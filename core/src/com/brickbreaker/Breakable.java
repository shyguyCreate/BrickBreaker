package com.brickbreaker;

public class Breakable extends Surface {

	protected int resistance;

	public Breakable(int resistance) {
		super();
		this.resistance = resistance;
	}

	protected void receiveDamage() {
		resistance -= 1;

		if (resistance <= 0)
			Surface.removeSurface(this);
	}

}
