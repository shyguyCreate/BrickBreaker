package com.brickbreaker;

import com.badlogic.gdx.math.Rectangle;

public class Surface {
	
	protected boolean hasCollision(Ball ball, Rectangle surface) {
		return surface.overlaps(ball.getSprite().getBoundingRectangle());
	}
	
	protected boolean shouldBounce(Ball ball, char side) {
		boolean _return = false;
		switch (side) {
			case 'n':
				_return = ball.getVelocityY() < 0;
				break;
			case 's':
				_return = ball.getVelocityY() > 0;
				break;
			case 'w':
				_return = ball.getVelocityX() < 0;
				break;
			case 'e':
				_return = ball.getVelocityX() > 0;
				break;
		}
		return _return;
	}

}
