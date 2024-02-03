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
	private Platform platform;

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
		this.platform = platform;
	}
	
	protected void createBall() {
		sprite = new Sprite(new Texture("ball.png"));
		sprite.setScale(scale);
		resetBall();
	}
	
	protected void resetBall() {
		velocityX = 0;
		velocityY = 0;
		position = new Vector2(platform.getPosition().x+platform.getSprite().getWidth()/4, platform.getHeight());
		sprite.setPosition(position.x, position.y);
	}
	
	protected void drawBall(SpriteBatch batch) {
		moveBall();
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
	
	protected void moveBall() {
		moveBallX();
		moveBallY();
	}
	
	protected void moveBallX() {
		position.x += velocityX*BrickBreaker.deltaTime;
	}
	protected void moveBallY() {
		position.y += velocityY*BrickBreaker.deltaTime;
	}
	
	protected void changeDirectionX() {
		velocityX *= -1;
		moveBallX();
	}
	protected void changeDirectionY() {
		velocityY *= -1;
		moveBallY();
	}
	
	protected void changeDirectionX(int i) {
		velocityX *= -1;
		moveBallX();
	}
	protected void changeDirectionY(int i) {
		velocityY *= -1;
		moveBallY();
	}
}
