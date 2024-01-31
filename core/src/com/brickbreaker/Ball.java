package com.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private Vector2 position;
	private static Sprite sprite;
	private float scale = 0.4f;
	private int velocity = 0;
	private Platform platform;	
	
	public int getVelocity() {
		return velocity;
	}
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public Ball(Platform platform) {
		this.platform = platform;
	}
	
	protected void createBall() {
		sprite = new Sprite(new Texture("ball.png"));
		sprite.setScale(scale);
		resetBall();
	}
	
	protected void resetBall() {
		velocity = 0;
		position = new Vector2(Platform.getPosition().x+platform.getSprite().getWidth()/4, Platform.getHeight());
		sprite.setPosition(position.x, position.y);
	}
	
	protected void drawBall(SpriteBatch batch) {
		moveBall();
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
	
	protected void moveBall() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		position.y += deltaTime*velocity;
	}
	
	protected void changeDirection() {
		velocity *= -1;
	}
}
