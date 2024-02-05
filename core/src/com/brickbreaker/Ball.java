package com.brickbreaker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	private Vector2 position;
	private Sprite sprite;
	private float scale = 0.4f;
	private int velocityX = 0;
	private int velocityY = 0;

	public Vector2 getPosition() {
		return position;
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

	public Sprite getSprite() {
		return sprite;
	}
	
	public Ball(Platform platform) {
		sprite = new Sprite(new Texture("ball.png"));
		sprite.setScale(scale);
		resetBall(platform);
	}
	
	void resetBall(Platform platform) {
		velocityX = 0;
		velocityY = 0;
		position = new Vector2(platform.getPosition().x+platform.getSprite().getWidth()/4, platform.getHeight());
		sprite.setPosition(position.x, position.y);
	}
	
	void drawBall(SpriteBatch batch) {
		sprite.draw(batch);
	}
	
	void moveBallX() {
		position.x += velocityX*BrickBreaker.deltaTime;
		sprite.setPosition(position.x, position.y);
	}
	void moveBallY() {
		position.y += velocityY*BrickBreaker.deltaTime;
		sprite.setPosition(position.x, position.y);
	}
	
	void changeDirectionX(SpriteBatch batch) {
		velocityX *= -1;
		moveBallX();
	}
	
	void changeDirectionX() {
		velocityX *= -1;
		moveBallX();
	}
	void changeDirectionY() {
		velocityY *= -1;
		moveBallY();
	}
}
