package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Walls extends Surface {
	
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	private Rectangle leftWall = new Rectangle(0, 0, BrickBreaker.wallWidth, BrickBreaker.initialScreenSize.y);
	private Rectangle rightWall = new Rectangle(BrickBreaker.initialScreenSize.x-BrickBreaker.wallWidth, 0, BrickBreaker.initialScreenSize.x, BrickBreaker.initialScreenSize.y);
	
	public Rectangle getLeftWall() {
		return leftWall;
	}
	public Rectangle getRightWall() {
		return rightWall;
	}
	
	protected void drawLeftWall() {		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(leftWall.x,leftWall.y,leftWall.width,leftWall.height);
		shapeRenderer.end();
	}
	protected void drawRightWall() {		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(rightWall.x,rightWall.y,rightWall.width,rightWall.height);
		shapeRenderer.end();
	}
	
	protected void bounce(Ball ball, Rectangle wall, char side) {
		if (hasCollision(ball, wall))
			if (shouldBounce(ball, side))
				ball.changeDirectionX();
	}
}
