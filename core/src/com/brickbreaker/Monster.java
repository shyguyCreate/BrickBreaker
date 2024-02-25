package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * The enemy of the game that must be defeated by hitting it
 * with the ball object to win the game.
 */
public class Monster extends Breakable {

	/**
	 * Sprite that holds the monster image.
	 */
	private Sprite sprite;
	/**
	 * How big the scale of the monster will be.
	 */
	private float scale = 0.725f;

	/**
	 * @return sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Monster constructor assign sprite, and scale, size and position of sprite.
	 * And resistance to parent constructor.
	 * 
	 * @param resistance Number of hits the brick can handle.
	 */
	public Monster(int resistance) {
		// Call constructor of parent with resistance.
		super(resistance);
		// Set monster image to sprite.
		sprite = new Sprite(new Texture("monster.png"));
		// Set scale of sprite.
		sprite.setScale(scale);
		// Set size of sprite.
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		// Set position of sprite at the top of the screen with width of the screen.
		sprite.setPosition(0 - BrickBreaker.initialScreenSize.x * 0.25f, BrickBreaker.initialScreenSize.y * 0.8f);
	}

	/**
	 * Display the monster in the screen.
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
	 * Remove one from resistance and change sprite color to red and back.
	 */
	@Override
	protected void collision() {
		// Remove one from resistance.
		super.receiveDamage();
		// Change sprite color to red.
		sprite.setColor(Color.RED);
		// Remove previous timer calls.
		Timer.instance().clear();
		// Call timer to set the sprite color back after certain time.
		Timer.schedule(new Task() {
			@Override
			public void run() {
				// Set color back to original.
				sprite.setColor(Color.WHITE);
			}
		}, 0.3f);
	}
}
