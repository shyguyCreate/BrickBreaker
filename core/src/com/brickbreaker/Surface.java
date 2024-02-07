package com.brickbreaker;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Surface {

	private static List<Surface> instancesOfSurface = new ArrayList<Surface>();

	public Surface() {
		super();
		instancesOfSurface.add(this);
	}

	public static List<Surface> getAllSurfaces() {
		return instancesOfSurface;
	}

	public static void removeSurface(Surface surface) {
		instancesOfSurface.remove(surface);
	}

	protected abstract void draw(SpriteBatch batch);

	protected abstract Rectangle getCollider();

	protected abstract void collision();

	protected boolean hasCollision(Ball ball) {
		return ball.getSprite().getBoundingRectangle().overlaps(getCollider());
	}
}
