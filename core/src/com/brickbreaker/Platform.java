package com.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Platform {

	private static Vector2 position;
	private Sprite sprite;
	private float width = 1f;
	private static float height = 0.75f;
	private int velocity = 300;
	
	public static Vector2 getPosition() {
		return position;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public static float getHeight() {
		return height;
	}

	protected void createPlatform() {
		sprite = new Sprite(new Texture("platform.png"));
		sprite.setScale(width, height);
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);
		sprite.setPosition(position.x, position.y);
	}
	
	protected void drawPlatform(SpriteBatch batch) {
		movePlatform();
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
	
	protected void movePlatform() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
			if(position.x-deltaTime*velocity >= BrickBreaker.wallWidth)
				position.x -= deltaTime*velocity;
		if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
			if(position.x+deltaTime*velocity <= BrickBreaker.initialScreenSize.x-BrickBreaker.wallWidth-sprite.getWidth())
				position.x += deltaTime*velocity;
	}
	
	protected void eject(Ball ball) {
		if(sprite.getBoundingRectangle().overlaps(ball.getSprite().getBoundingRectangle())) {
			if (ball.getVelocity() < 0)
				ball.changeDirection();
		}
	}
}
