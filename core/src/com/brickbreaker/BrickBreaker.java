package com.brickbreaker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class BrickBreaker extends ApplicationAdapter {
	
	public static Vector2 initialScreenSize;
	public static float wallWidth;
	public static float deltaTime;
	
	private Rectangle floorRectangle;
	private SpriteBatch batch;
	private Player player;
	private Platform platform;
	private Ball ball;
	private Monster monster;
	private Wall leftWall;
	private Wall rightWall;
	private Brick[] bricks;
	
	private int ballVelocity = 500;
	private int monsterResistance = 10;
	
	@Override
	public void create () {
		
		initialScreenSize = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		wallWidth = initialScreenSize.x * 0.025f;
		floorRectangle = new Rectangle(0, 0, initialScreenSize.x, -15);
		
		batch = new SpriteBatch();
		player = new Player();
		platform = new Platform();
		ball = new Ball(platform);
		monster = new Monster(monsterResistance);
		leftWall = new Wall(new Rectangle(0, 0, wallWidth, initialScreenSize.y));
		rightWall = new Wall(new Rectangle(initialScreenSize.x - wallWidth, 0, initialScreenSize.x, initialScreenSize.y));
		
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
		deltaTime = Gdx.graphics.getDeltaTime();
		
		if (ball.getVelocityX() == 0 || ball.getVelocityY() == 0) {
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				ball.setVelocityX(-ballVelocity);
				ball.setVelocityY(ballVelocity);
			} else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				ball.setVelocityX(ballVelocity);
				ball.setVelocityY(ballVelocity);
			}
		}
		
		//System.out.println(ball.getVelocityX()+"  "+ball.getVelocityY());
		batch.begin();
		platform.drawPlatform(batch);
		ball.drawBall(batch);
		for (Surface surface : Surface.getAllSurfaces()) {
			surface.draw(batch);
		}
		batch.end();
		
		ball.moveBallX();
		for (Surface surface : Surface.getAllSurfaces()) {
			if (surface.hasCollision(ball)) {
				ball.changeDirectionX();
				surface.collision();
				break;
			}
		}
		
		ball.moveBallY();
		for (Surface surface : Surface.getAllSurfaces()) {
			if (surface.hasCollision(ball)) {
				ball.changeDirectionY();
				surface.collision();
				break;
			}
		}
		
		platform.eject(ball, ballVelocity);
		
		if (floorRectangle.overlaps(ball.getSprite().getBoundingRectangle())){
			ball.resetBall(platform);
			player.removeLife();
	
			if (player.isAlive()) {
				//ball.setVelocity(0);
			}
		}
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
