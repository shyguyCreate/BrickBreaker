package com.brickbreaker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * The circle that is bouncing all over the map and can destroy
 * bricks and the monster when colliding with them.
 */
public class Ball {

	// Current position of the ball.
	private Vector2 position;
	// Sprite that holds the ball image.
	private Sprite sprite;
	// Sets the scale size of the ball sprite.
	private float scale = 0.4f;
	// Velocity the was given to the ball at the beginning.
	private final int INITIAL_VELOCITY;
	// How fast the ball can move horizontally.
	private int velocityX = 0;
	// How fast the ball can move vertically.
	private int velocityY = 0;

	/**
	 * @return sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * @return INITIAL_VELOCITY
	 */
	public int getINITIAL_VELOCITY() {
		return INITIAL_VELOCITY;
	}

	/**
	 * @return velocityX
	 */
	public int getVelocityX() {
		return velocityX;
	}

	/**
	 * @param velocityX Set to class velocityX
	 */
	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	/**
	 * @return velocityY
	 */
	public int getVelocityY() {
		return velocityY;
	}

	/**
	 * @param velocityY Set to class velocityY
	 */
	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * Ball constructor assign sprite with scale
	 * call reset method and set INITIAL_VELOCITY.
	 * 
	 * @param platform        Platform object inside the game.
	 * @param initialVelocity Velocity start X and Y velocities.
	 */
	public Ball(Platform platform, int initialVelocity) {
		// Call constructor of parent.
		super();
		// Assign initialVelocity to class INITIAL_VELOCITY.
		this.INITIAL_VELOCITY = initialVelocity;
		// Set ball image to sprite.
		sprite = new Sprite(new Texture("ball.png"));
		// Set scale of sprite.
		sprite.setScale(scale);
		// Call reset method with player object.
		reset(platform);
	}

	/**
	 * Place the ball over the platform, change velocities to zero, and wait for player input.
	 * 
	 * @param platform Platform object inside the game.
	 */
	void reset(Platform platform) {
		// Set horizontal velocity to zero.
		velocityX = 0;
		// Set vertical velocity to zero.
		velocityY = 0;
		// Set ball position on top of platform.
		position = new Vector2(platform.getPosition().x + platform.getSprite().getWidth() / 4, platform.getHeight());
		// Set sprite position with saved values.
		sprite.setPosition(position.x, position.y);
	}

	/**
	 * Display the ball sprite in the screen.
	 * 
	 * @param batch SpriteBatch object inside the game.
	 */
	void draw(SpriteBatch batch) {
		// Draw sprite.
		sprite.draw(batch);
	}

	/**
	 * Control the movement of the ball based on the current velocity
	 * and checks if it has collided with a surface.
	 */
	void move() {
		// Move ball horizontally.
		moveX();
		// Iterate over all surfaces.
		for (Surface surface : Surface.getAllSurfaces()) {
			// Check for a collision in current surface.
			if (surface.hasCollision(this)) {
				// Swap horizontal direction.
				changeDirectionX();
				// Call the collision method in current surface.
				surface.collision();
				// Stop iteration.
				break;
			}
		}

		// Move ball vertically.
		moveY();
		// Iterate over all surfaces.
		for (Surface surface : Surface.getAllSurfaces()) {
			// Check for a collision in current surface.
			if (surface.hasCollision(this)) {
				// Swap vertical direction.
				changeDirectionY();
				// Call the collision method in current surface.
				surface.collision();
				// Stop iteration.
				break;
			}
		}
	}

	/**
	 * Move the ball horizontally.
	 */
	void moveX() {
		// Set horizontal position of the ball with current velocity.
		position.x += velocityX * BrickBreaker.deltaTime;
		// Set sprite position with saved values.
		sprite.setPosition(position.x, position.y);
	}

	/**
	 * Move the ball vertically.
	 */
	void moveY() {
		// Set vertical position of the ball with current velocity.
		position.y += velocityY * BrickBreaker.deltaTime;
		// Set sprite position with saved values.
		sprite.setPosition(position.x, position.y);
	}

	/**
	 * Change the horizontal direction of the ball.
	 */
	void changeDirectionX() {
		// Change horizontal velocity to the other side.
		velocityX *= -1;
		// Move ball horizontally to prevent changing direction forever.
		moveX();
	}

	/**
	 * Change the vertical direction of the ball.
	 */
	void changeDirectionY() {
		// Change vertical velocity to the other side.
		velocityY *= -1;
		// Move ball vertically to prevent changing direction forever.
		moveY();
	}
}
