package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * Rectangle object that will serve as delimiters for the map
 * in the left and right side and will make the ball bounce.
 */
public class Wall extends Surface {

	// Tool to draw the rectangles in the game.
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	// Position and dimensions of the wall.
	private Rectangle wallRectangle;

	/**
	 * @return wallRectangle
	 */
	public Rectangle getWallRectangle() {
		return wallRectangle;
	}

	/**
	 * Wall constructor assign rectangle.
	 * 
	 * @param wallRectangle Represents the wall.
	 */
	public Wall(Rectangle wallRectangle) {
		// Call constructor of parent.
		super();
		// Assign wallRectangle to class wallRectangle.
		this.wallRectangle = wallRectangle;
	}

	/**
	 * Displays the wall in the screen.
	 * 
	 * @param batch SpriteBatch object inside the game.
	 */
	@Override
	protected void draw(SpriteBatch batch) {
		// Close SpriteBatch because it should not be used together with ShapeRenderer.
		batch.end();
		// Start ShapeRenderer with drawings filled option.
		shapeRenderer.begin(ShapeType.Filled);
		// Set color of the walls gray.
		shapeRenderer.setColor(Color.GRAY);
		// Draw wall to the screen.
		shapeRenderer.rect(wallRectangle.x, wallRectangle.y, wallRectangle.width, wallRectangle.height);
		// Close ShapeRenderer.
		shapeRenderer.end();
		// Start SpriteBatch for next item in the surfaces list that needs it.
		batch.begin();
	}

	/**
	 * Specify the rectangle of this object to be used as a collider.
	 */
	@Override
	protected Rectangle getCollider() {
		// Return wall as the collider to be used.
		return wallRectangle;
	}

	/**
	 * Inherited method, do nothing.
	 */
	@Override
	protected void collision() {
	}
}
