package com.brickbreaker;

import com.badlogic.gdx.math.Rectangle;

public class Floor {
	
	private Rectangle floor = new Rectangle(0, 0, BrickBreaker.initialScreenSize.x, -15);
	
	protected void isOut(Ball ball, Player player) {
		if(floor.overlaps(ball.getSprite().getBoundingRectangle())) {
			ball.resetBall();
			player.removeLife();
		}
	}

}
