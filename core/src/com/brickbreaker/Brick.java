package com.brickbreaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Brick extends Breakable {

	private Sprite sprite;
	private final Vector2 POSITION;
	private final float WIDTH;
	private final float HEIGHT;
	private final Color COLOR;

	public Brick(Sprite sprite, int resistance) {
		super(resistance);
		this.sprite = new Sprite(sprite);
		this.POSITION = new Vector2(this.sprite.getX(), this.sprite.getY());
		this.WIDTH = this.sprite.getWidth();
		this.HEIGHT = this.sprite.getHeight();
		this.COLOR = this.sprite.getColor();
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
		sprite.setSize(WIDTH, HEIGHT);
		sprite.setPosition(POSITION.x, POSITION.y);
	}
}
