package com.brickbreaker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private Vector2 position;
	private Sprite sprite;
	private float scale = 0.4f;
	private final int INITIAL_VELOCITY;
	private int velocityX = 0;
	private int velocityY = 0;

	public Sprite getSprite() {
		return sprite;
	}

	public int getINITIAL_VELOCITY() {
		return INITIAL_VELOCITY;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public Ball(Platform platform, int initialVelocity) {
		super();
		this.INITIAL_VELOCITY = initialVelocity;
		sprite = new Sprite(new Texture("ball.png"));
		sprite.setScale(scale);
		reset(platform);
	}

	void reset(Platform platform) {
		velocityX = 0;
		velocityY = 0;
		position = new Vector2(platform.getPosition().x + platform.getSprite().getWidth() / 4, platform.getHeight());
		sprite.setPosition(position.x, position.y);
	}

	void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	void moveX() {
		position.x += velocityX * BrickBreaker.deltaTime;
		sprite.setPosition(position.x, position.y);
	}

	void moveY() {
		position.y += velocityY * BrickBreaker.deltaTime;
		sprite.setPosition(position.x, position.y);
	}

	void changeDirectionX(SpriteBatch batch) {
		velocityX *= -1;
		moveX();
	}

	void changeDirectionX() {
		velocityX *= -1;
		moveX();
	}

	void changeDirectionY() {
		velocityY *= -1;
		moveY();
	}
}
