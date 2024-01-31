package com.brickbreaker;

import com.badlogic.gdx.math.Rectangle;

public class Surface {
	
	protected boolean hasCollision(Ball ball, Rectangle surface) {
		return surface.overlaps(ball.getSprite().getBoundingRectangle());
	}

}
