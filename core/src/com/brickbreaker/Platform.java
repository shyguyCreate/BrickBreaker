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

	protected void createPlatform() {
		sprite = new Sprite(new Texture("platform.png"));
		sprite.setScale(width, height);
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);
		sprite.setPosition(position.x, position.y);
	}
	
	protected void drawPlatform(SpriteBatch batch) {
		//System.out.println(height);
		//System.out.println(sprite.getHeight());
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
	
	protected void eject(Ball ball, int ballVelocity) {
		if(sprite.getBoundingRectangle().overlaps(ball.getSprite().getBoundingRectangle())) {
			if (ball.getVelocityY() < 0) {
				ball.changeDirectionY();
				if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
					ball.setVelocityX(Math.abs(ball.getVelocityX())*-1);
				} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
					ball.setVelocityX(Math.abs(ball.getVelocityX()));
				}
				ball.setVelocityX(random.nextInt(Integer.signum(ball.getVelocityX())*ballVelocity-50, Integer.signum(ball.getVelocityX())*ballVelocity+50));
				ball.setVelocityY(random.nextInt(Integer.signum(ball.getVelocityY())*ballVelocity-50, Integer.signum(ball.getVelocityY())*ballVelocity+50));
			}
		}
	}
}
