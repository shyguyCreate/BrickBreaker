package com.brickbreaker;

public class Breakable extends Surface {
	
	protected int resistance;
	
	protected boolean isBroken() {
		return resistance <= 0;
	}
	
	protected void receiveDamage() {
		resistance -= 1;
	}
	
}
