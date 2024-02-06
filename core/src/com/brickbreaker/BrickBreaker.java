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
import com.badlogic.gdx.utils.TimeUtils;

public class BrickBreaker extends ApplicationAdapter {

	public static Vector2 initialScreenSize;
	public static float wallWidth;
	public static float deltaTime;

	private Texture background;
	long ballResetTime;

	private Rectangle floorRectangle;
	private SpriteBatch batch;
	private Player player;
	private Platform platform;
	private Ball ball;
	private Monster monster;
	private Wall leftWall;
	private Wall rightWall;
	private Brick[] bricks;

	@Override
	public void create() {

		initialScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		wallWidth = initialScreenSize.x * 0.025f;
		floorRectangle = new Rectangle(0, 0, initialScreenSize.x, -15);

		background = new Texture("space.jpg");

		int playerLives = 3;
		int monsterResistance = 10;
		int ballInitialVelocity = 500;

		batch = new SpriteBatch();
		player = new Player(playerLives);
		platform = new Platform();
		ball = new Ball(platform, ballInitialVelocity);
		monster = new Monster(monsterResistance);
		leftWall = new Wall(new Rectangle(0, 0, wallWidth, initialScreenSize.y));
		rightWall = new Wall(new Rectangle(initialScreenSize.x - wallWidth, 0, initialScreenSize.x, initialScreenSize.y));

		int numBricksColumns = 7;
		int numBricksRows = 6;
		bricks = new Brick[numBricksRows * numBricksColumns];

		Sprite brickSprite = new Sprite(new Texture("brick_new.png"));
		brickSprite.setScale(Brick.SCALE);
		brickSprite.setSize(brickSprite.getWidth() * Brick.SCALE, brickSprite.getHeight() * Brick.SCALE);

		Vector2 position = new Vector2(0, Gdx.graphics.getHeight() * 0.8f);
		Color[] brickColors = { Color.PINK, Color.RED, Color.PURPLE };
		int currentColor = brickColors.length;
		int brickWeakestResistance = 1;

		for (int i = 0; i < numBricksRows * numBricksColumns; i++) {
			if (i % numBricksColumns == 0) {
				position.x = 0;
				position.y -= brickSprite.getHeight() * Brick.SCALE;
				currentColor = (currentColor > 0) ? currentColor - 1 : brickColors.length - 1;
				brickSprite.setColor(brickColors[currentColor]);
			} else {
				position.x += brickSprite.getWidth() * Brick.SCALE;
			}
			brickSprite.setPosition(position.x, position.y);

			bricks[i] = new Brick(brickSprite, brickSprite.getColor(), position, brickWeakestResistance + currentColor);
		}

		ballResetTime = TimeUtils.millis();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		deltaTime = Gdx.graphics.getDeltaTime();

		if (ball.getVelocityX() == 0 || ball.getVelocityY() == 0) {
			if (TimeUtils.timeSinceMillis(ballResetTime) > 500) {
				if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
					ball.setVelocityX(-ball.getINITIAL_VELOCITY());
					ball.setVelocityY(ball.getINITIAL_VELOCITY());
				} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
					ball.setVelocityX(ball.getINITIAL_VELOCITY());
					ball.setVelocityY(ball.getINITIAL_VELOCITY());
				}
			}
		} else {
			platform.move();
		}

		batch.begin();
		batch.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
		batch.draw(background, 0, 0, initialScreenSize.x, initialScreenSize.y);
		batch.setColor(new Color(1, 1, 1, 1));
		platform.draw(batch);
		ball.draw(batch);
		for (Surface surface : Surface.getAllSurfaces()) {
			surface.draw(batch);
		}
		batch.end();

		ball.moveX();
		for (Surface surface : Surface.getAllSurfaces()) {
			if (surface.hasCollision(ball)) {
				ball.changeDirectionX();
				surface.collision();
				break;
			}
		}

		ball.moveY();
		for (Surface surface : Surface.getAllSurfaces()) {
			if (surface.hasCollision(ball)) {
				ball.changeDirectionY();
				surface.collision();
				break;
			}
		}

		platform.eject(ball);

		if (floorRectangle.overlaps(ball.getSprite().getBoundingRectangle())) {
			ball.reset(platform);
			ballResetTime = TimeUtils.millis();
			player.removeLife();

			if (player.isAlive()) {
				// ball.setVelocity(0);
			}
		}
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
	}
}
