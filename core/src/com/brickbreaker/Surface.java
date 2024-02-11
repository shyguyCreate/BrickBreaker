package com.brickbreaker;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Every object that has the property of making the ball bounce
 * like walls, bricks and the monster.
 */
public abstract class Surface {

	// Holds all the object and child objects of this class.
	private static List<Surface> instancesOfSurface = new ArrayList<Surface>();

	/**
	 * Surface constructor.
	 */
	public Surface() {
		// Call constructor of parent.
		super();
		// Add this object to the list.
		instancesOfSurface.add(this);
	}

	/**
	 * Returns the list of instancesOfSurface.
	 * 
	 * @return The list of all active surfaces.
	 */
	public static List<Surface> getAllSurfaces() {
		return instancesOfSurface;
	}

	/**
	 * Remove a given object from the list.
	 * 
	 * @param surface Remove this one.
	 */
	public static void removeSurface(Surface surface) {
		instancesOfSurface.remove(surface);
	}

	/**
	 * Clears the list of instancesOfSurface.
	 */
	public static void removeAllSurfaces() {
		instancesOfSurface.clear();
	}

	/**
	 * Display this object in the screen.
	 * 
	 * @param batch SpriteBatch object inside the game.
	 */
	protected abstract void draw(SpriteBatch batch);

	/**
	 * Specify the rectangle of this object to be used as a collider.
	 * 
	 * @return Rectangle object.
	 */
	protected abstract Rectangle getCollider();

	/**
	 * Specify what must be done when the ball collides with this object.
	 */
	protected abstract void collision();

	/**
	 * Detect collision with the ball object and this object.
	 * 
	 * @param ball Ball object inside the game.
	 * @return true if ball and this object collide.
	 */
	protected boolean hasCollision(Ball ball) {
		return ball.getSprite().getBoundingRectangle().overlaps(getCollider());
	}
}
