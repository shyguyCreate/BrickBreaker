package com.brickbreaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Monster extends Breakable {

	protected Sprite sprite;
	private float scale = 0.725f;
	
	public Monster(int resistance) {
		this.resistance = resistance;
	}

	protected void createMonster() {
		sprite = new Sprite(new Texture("monster.png"));
		sprite.setScale(scale);
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition(0-Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight()*0.8f);
	}
	
	protected void drawMonster(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	protected void receiveDamage() {
		super.receiveDamage();
		sprite.setColor(Color.RED);
		Timer.schedule(new Task(){
			@Override
			public void run() {
				sprite.setColor(Color.WHITE);
			}
		}, 0.3f);
	}
	
}
