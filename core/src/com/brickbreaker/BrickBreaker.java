package com.brickbreaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class BrickBreaker extends ApplicationAdapter {
	
	public static Vector2 initialScreenSize;
	public static float wallWidth;
	
	SpriteBatch batch;
	
	private Player player;
	private Platform platform;
	private Ball ball;
	private Floor floor;
	private Monster monster;
	private Walls walls;
	private Brick[] bricks;
	
	private int ballVelocity = 500;
	private int monsterResistance = 10;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		initialScreenSize = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		wallWidth = initialScreenSize.x * 0.025f;
		
		player = new Player();
		platform = new Platform();
		ball = new Ball(platform);
		floor = new Floor();
		monster = new Monster(monsterResistance);
		walls = new Walls();
		
		platform.createPlatform();
		ball.createBall();
		monster.createMonster();
		
		int numBricksColumns = 7;
		int numBricksRows = 5;
		int brickResistance = 3;
		
		bricks = new Brick[numBricksRows*numBricksColumns];
		Vector2 position = new Vector2(0, Gdx.graphics.getHeight()*0.75f);
		float brickScale = Brick.getScale();
		Sprite brickSprite = new Sprite(new Texture("brick_new.png"));
		brickSprite.setScale(brickScale);
		brickSprite.setSize(brickSprite.getWidth() * brickScale, brickSprite.getHeight() * brickScale);
		
		for(int i = 0; i < numBricksRows*numBricksColumns; i++)
		{
			if (i == 0) {
				position.x = 0;
				
			} else if (i % numBricksColumns == 0) {
				position.x = 0;
				position.y -= brickSprite.getHeight()*brickScale;
			} else {
				position.x += brickSprite.getWidth()*brickScale;
			}
			
			bricks[i] = new Brick(new Vector2(position), Color.RED, brickResistance);
			bricks[i].createBrick();
		}
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		
		if (ball.getVelocity() == 0) {
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT) ||
					Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				ball.setVelocity(ballVelocity);
			}
		}
		
		batch.begin();
		platform.drawPlatform(batch);
		ball.drawBall(batch);
		for (Brick brick : bricks) {
			if(! brick.isBroken()) {
				brick.drawBrick(batch);
			}
		}
		batch.end();
		
		floor.isOut(ball,player);
		
		platform.eject(ball);
		
		batch.begin();
		for (Brick brick : bricks) {
			if(! brick.isBroken()) {
				if(brick.hasCollision(ball, brick.sprite.getBoundingRectangle())) {
					ball.changeDirection();
					brick.receiveDamage();
					break;
				}
			}
		}
		if (! monster.isBroken()) {
			monster.drawMonster(batch);
			if(monster.hasCollision(ball, monster.sprite.getBoundingRectangle())) {
				ball.changeDirection();
				monster.receiveDamage();
			}
		}
		batch.end();
		
		walls.drawLeftWall();
		walls.drawRightWall();
		walls.bounce(ball, walls.getLeftWall());
		walls.bounce(ball, walls.getRightWall());

		if(player.isAlive()) {
			//ball.setVelocity(0);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
