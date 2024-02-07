package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Brick extends Breakable {

	static final float SCALE = 0.7f;

	private Sprite sprite;
	private final Color COLOR;
	private final Vector2 POSITION;

	public Brick(Sprite sprite, Color color, Vector2 position, int resistance) {
		super(resistance);
		this.POSITION = new Vector2(position);
		this.COLOR = new Color(color);
		this.sprite = new Sprite(sprite);
	}

	@Override
	protected void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	protected Rectangle getCollider() {
		return sprite.getBoundingRectangle();
	}

	@Override
	protected void collision() {
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
		default:
			return;
		}
		sprite.setColor(COLOR);
		sprite.setScale(SCALE);
		sprite.setSize(sprite.getWidth() * SCALE, sprite.getHeight() * SCALE);
		sprite.setPosition(POSITION.x, POSITION.y);
	}
}
