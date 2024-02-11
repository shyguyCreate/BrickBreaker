package com.brickbreaker;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Surface the player controls and can move to prevent the ball from falling outside the map.
 */
public class Platform {

	// Returns a random number between to given numbers.
	private Random random = new Random();
	// Current position of the platform.
	private Vector2 position;
	// Sprite that holds the platform image.
	private Sprite sprite;
	// How long the scale of platform will be.
	private float width = 1f;
	// How tall the scale of platform will be.
	private float height = 1f;
	// How fast the platform can move.
	private int velocity = 300;

	/**
	 * @return position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @return sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @return height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Platform constructor assign sprite, scale and position of sprite.
	 */
	public Platform() {
		// Call constructor of parent.
		super();
		// Set platform image to sprite.
		sprite = new Sprite(new Texture("platform.png"));
		// Set scale of sprite.
		sprite.setScale(width, height);
		// Save position with platform to the middle and bottom of screen.
		position = new Vector2((BrickBreaker.initialScreenSize.x - sprite.getWidth()) / 2, 0);
		// Set sprite position with saved values.
		sprite.setPosition(position.x, position.y);
	}

	/**
	 * Display the platform sprite in the screen.
	 * 
	 * @param batch Object that draws sprites to the screen.
	 */
	void draw(SpriteBatch batch) {
		// Draw sprite.
		sprite.draw(batch);
	}

	/**
	 * Control the movement of the platform based on the key presses and the velocity.
	 */
	void move() {
		// Check if A or left arrow is pressed.
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			// Check if next position is not colliding with the left wall.
			if (position.x - BrickBreaker.deltaTime * velocity >= BrickBreaker.wallWidth) {
				// Move platform to the left.
				position.x -= BrickBreaker.deltaTime * velocity;
			} else {
				// Move platform next to the left wall.
				position.x = BrickBreaker.wallWidth;
			}
		}
		// Check if D or right arrow is pressed.
		else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			// Check if next position is not colliding with the right wall.
			if (position.x + BrickBreaker.deltaTime * velocity <= BrickBreaker.initialScreenSize.x - BrickBreaker.wallWidth - sprite.getWidth()) {
				// Move platform to the right.
				position.x += BrickBreaker.deltaTime * velocity;
			} else {
				// Move platform next to the right wall.
				position.x = BrickBreaker.initialScreenSize.x - BrickBreaker.wallWidth - sprite.getWidth();
			}
		}
		// Set sprite position with saved values.
		sprite.setPosition(position.x, position.y);
	}

	/**
	 * Gives random velocities to the ball when it collides with the platform.
	 * 
	 * @param ball Ball object inside the game.
	 */
	void eject(Ball ball) {
		// Do nothing if platform and ball do not collide.
		if (!sprite.getBoundingRectangle().overlaps(ball.getSprite().getBoundingRectangle()))
			return;

		// Do nothing if ball vertical velocity is greater than zero.
		if (ball.getVelocityY() > 0)
			return;

		// Check if A or left arrow is pressed.
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			// Set ball horizontal velocity to the left
			ball.setVelocityX(-1);
		}
		// Check if D or right arrow is pressed.
		else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			// Set ball horizontal velocity to the right
			ball.setVelocityX(1);
		}
		// Set ball horizontal velocity random in a range of INITIAL_VELOCITY +/- INITIAL_VELOCITY/5
		// and preserve horizontal velocity direction.
		ball.setVelocityX(random.nextInt(ball.getINITIAL_VELOCITY() - ball.getINITIAL_VELOCITY() / 5, ball.getINITIAL_VELOCITY() + ball.getINITIAL_VELOCITY() / 5) * Integer.signum(ball.getVelocityX()));
		// Set ball vertical velocity to the subtraction of INITIAL_VELOCITY*2 and the horizontal velocity.
		ball.setVelocityY(ball.getINITIAL_VELOCITY() * 2 - Math.abs(ball.getVelocityX()));
	}
}
