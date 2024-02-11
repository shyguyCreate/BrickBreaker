package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Every floating rectangle in the game that can be damaged and broken by the ball object.
 */
public class Brick extends Breakable {

	// Sprite that holds the brick image.
	private Sprite sprite;
	// Current position of the brick.
	private final Vector2 POSITION;
	// How long the scale of the brick will be.
	private final float WIDTH;
	// How tall the scale of the brick will be.
	private final float HEIGHT;
	// Color the brick will be painted.
	private final Color COLOR;

	/**
	 * Brick constructor assign sprite, and width, height, color and position of sprite.
	 * And resistance to parent constructor.
	 * 
	 * @param sprite     Sprite image and properties needed for the Brick class.
	 * @param resistance Number of hits the brick can handle.
	 */
	public Brick(Sprite sprite, int resistance) {
		// Call constructor of parent with resistance.
		super(resistance);
		// Assign sprite to class sprite.
		this.sprite = new Sprite(sprite);
		// Set POSITION with class sprite position.
		this.POSITION = new Vector2(this.sprite.getX(), this.sprite.getY());
		// Set WIDTH with class sprite width.
		this.WIDTH = this.sprite.getWidth();
		// Set HEIGHT with class sprite height.
		this.HEIGHT = this.sprite.getHeight();
		// Set COLOR with class sprite color.
		this.COLOR = this.sprite.getColor();
	}

	/**
	 * Display the brick in the screen.
	 * 
	 * @param batch SpriteBatch object inside the game.
	 */
	@Override
	protected void draw(SpriteBatch batch) {
		// Draw sprite.
		sprite.draw(batch);
	}

	/**
	 * Specify the rectangle of this object to be used as a collider.
	 */
	@Override
	protected Rectangle getCollider() {
		// Return sprite rectangle
		return sprite.getBoundingRectangle();
	}

	/**
	 * Remove one from resistance and change sprite image to a more cracked brick.
	 */
	@Override
	protected void collision() {
		// Remove one from resistance.
		super.receiveDamage();
		// Check resistance integer.
		switch (resistance) {
		// Check if resistance is 3.
		case 3:
			// Change sprite image to cracked "phase 1".
			sprite = new Sprite(new Texture("brick_cracked1.png"));
			break;
		// Check if resistance is 3.
		case 2:
			// Change sprite image to cracked "phase 2".
			sprite = new Sprite(new Texture("brick_cracked2.png"));
			break;
		// Check if resistance is 3.
		case 1:
			// Change sprite image to cracked "phase 3".
			sprite = new Sprite(new Texture("brick_cracked3.png"));
			break;
		// Do nothing if resistance do not match any option.
		default:
			return;
		}
		// Set sprite color with saved value.
		sprite.setColor(COLOR);
		// Set sprite size with saved values.
		sprite.setSize(WIDTH, HEIGHT);
		// Set sprite position with saved values.
		sprite.setPosition(POSITION.x, POSITION.y);
	}
}
