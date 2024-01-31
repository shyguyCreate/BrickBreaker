package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Brick extends Breakable {

	private Vector2 position;
	private Color color;
	protected Sprite sprite;
	private static float scale = 0.7f;
	
	public static float getScale() {
		return scale;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Brick(Vector2 position, Color color, int resistance) {
		this.position = position;
		this.color = color;
		this.resistance = resistance;
	}

	protected void createBrick() {
		sprite = new Sprite(new Texture("brick_new.png"));
		sprite.setColor(color);
		sprite.setScale(scale);
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition(position.x, position.y);
	}
	
	protected void drawBrick(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	protected void receiveDamage() {
		super.receiveDamage();
		switch (resistance) {
		case 3:
			sprite = new Sprite(new Texture("brick_cracked1.png"));
			break;
		case 2:
			sprite = new Sprite(new Texture("brick_cracked2.png"));
			break;
		case 1:
			sprite = new Sprite(new Texture("brick_cracked3.png"));
			break;
		}
		sprite.setColor(color);
		sprite.setScale(scale);
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition(position.x, position.y);
	}
	
}
