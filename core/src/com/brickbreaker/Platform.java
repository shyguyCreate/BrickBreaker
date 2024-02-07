package com.brickbreaker;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Platform {

	private Random random = new Random();
	private Vector2 position;
	private Sprite sprite;
	private float width = 1f;
	private float height = 1f;
	private int velocity = 300;

	public Vector2 getPosition() {
		return position;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public float getHeight() {
		return height;
	}

	public Platform() {
		super();
		sprite = new Sprite(new Texture("platform.png"));
		sprite.setScale(width, height);
		position = new Vector2((Gdx.graphics.getWidth() - sprite.getWidth()) / 2, 0);
		sprite.setPosition(position.x, position.y);
	}

	void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	void move() {
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (position.x - BrickBreaker.deltaTime * velocity >= BrickBreaker.wallWidth) {
				position.x -= BrickBreaker.deltaTime * velocity;
			} else {
				position.x = BrickBreaker.wallWidth;
			}
		} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (position.x + BrickBreaker.deltaTime * velocity <= BrickBreaker.initialScreenSize.x - BrickBreaker.wallWidth - sprite.getWidth()) {
				position.x += BrickBreaker.deltaTime * velocity;
			} else {
				position.x = BrickBreaker.initialScreenSize.x - BrickBreaker.wallWidth - sprite.getWidth();
			}
		}
		sprite.setPosition(position.x, position.y);
	}

	void eject(Ball ball) {
		if (!sprite.getBoundingRectangle().overlaps(ball.getSprite().getBoundingRectangle()))
			return;

		if (!(ball.getVelocityY() < 0))
			return;

		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			ball.setVelocityX(Math.abs(ball.getVelocityX()) * -1);
		} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			ball.setVelocityX(Math.abs(ball.getVelocityX()));
		}

		ball.setVelocityX(random.nextInt(ball.getINITIAL_VELOCITY() - ball.getINITIAL_VELOCITY() / 5, ball.getINITIAL_VELOCITY() + ball.getINITIAL_VELOCITY() / 5) * Integer.signum(ball.getVelocityX()));
		ball.setVelocityY(ball.getINITIAL_VELOCITY() * 2 - Math.abs(ball.getVelocityX()));
	}
}
