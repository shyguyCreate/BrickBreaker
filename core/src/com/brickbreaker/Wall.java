package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Wall extends Surface {

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private Rectangle wallRectangle;

	public Rectangle getWallRectangle() {
		return wallRectangle;
	}

	public Wall(Rectangle wallRectangle) {
		super();
		this.wallRectangle = wallRectangle;
	}

	@Override
	protected void draw(SpriteBatch batch) {
		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.GRAY);
		shapeRenderer.rect(wallRectangle.x, wallRectangle.y, wallRectangle.width, wallRectangle.height);
		shapeRenderer.end();
		batch.begin();
	}

	@Override
	protected boolean hasCollision(Ball ball) {
		return super.hasCollision(ball, wallRectangle);
	}

	@Override
	protected void collision() {
	}
}
