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

	private Rectangle floorRectangle;
	private Texture background;
	private long ballResetTime;
	private char showMenuScreen = 'm';

	private SpriteBatch batch;
	private Sprite title;
	private Sprite playButton;
	private Sprite win;
	private Sprite gameover;
	private Sprite replayButton;

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

		batch = new SpriteBatch();
		monster = new Monster(0);
		Surface.removeSurface(monster);

		title = new Sprite(new Texture("title.png"));
		title.setScale(1.15f);
		title.setSize(title.getWidth() * title.getScaleX(), title.getHeight() * title.getScaleY());
		title.setPosition(initialScreenSize.x / 2 - title.getWidth() / 2, initialScreenSize.y / 2.25f);

		playButton = new Sprite(new Texture("play.png"));
		playButton.setScale(0.75f);
		playButton.setSize(playButton.getWidth() * playButton.getScaleX(), playButton.getHeight() * playButton.getScaleY());
		playButton.setPosition(initialScreenSize.x / 2 - playButton.getWidth() / 2, initialScreenSize.y / 7.5f);

		win = new Sprite(new Texture("win.png"));
		win.setScale(1f);
		win.setSize(win.getWidth() * win.getScaleX(), win.getHeight() * win.getScaleY());
		win.setPosition(initialScreenSize.x / 2 - win.getWidth() / 2, initialScreenSize.y / 2.25f);

		gameover = new Sprite(new Texture("gameover.png"));
		gameover.setScale(0.9f);
		gameover.setSize(gameover.getWidth() * gameover.getScaleX(), gameover.getHeight() * gameover.getScaleY());
		gameover.setPosition(initialScreenSize.x / 2 - gameover.getWidth() / 2, initialScreenSize.y / 1.75f);

		replayButton = new Sprite(new Texture("replay.png"));
		replayButton.setScale(0.8f);
		replayButton.setSize(replayButton.getWidth() * replayButton.getScaleX(), replayButton.getHeight() * replayButton.getScaleY());
		replayButton.setPosition(initialScreenSize.x / 2 - replayButton.getWidth() / 2, initialScreenSize.y / 5.5f);
	}

	private void loadGameAssets() {

		int playerLives = 3;
		int monsterResistance = 10;
		int ballInitialVelocity = 500;

		player = new Player(playerLives);
		platform = new Platform();
		ball = new Ball(platform, ballInitialVelocity);
		monster = new Monster(monsterResistance);
		leftWall = new Wall(new Rectangle(0, 0, wallWidth, initialScreenSize.y));
		rightWall = new Wall(new Rectangle(initialScreenSize.x - wallWidth, 0, wallWidth, initialScreenSize.y));

		int numBricksColumns = 7;
		int numBricksRows = 6;
		bricks = new Brick[numBricksRows * numBricksColumns];

		Sprite brickSprite = new Sprite(new Texture("brick_new.png"));
		float brickWidth = (initialScreenSize.x - wallWidth * 2) / numBricksColumns;
		float brickHeight = brickSprite.getHeight() * 0.6f;
		brickSprite.setSize(brickWidth, brickHeight);

		Vector2 brickPosition = new Vector2();
		Color[] brickColors = { Color.PINK, Color.RED, Color.PURPLE };
		int currentColor = brickColors.length - 1;
		int brickWeakestResistance = 1;

		for (int i = 0; i < numBricksRows * numBricksColumns; i++) {
			if (i == 0) {
				brickPosition.x = wallWidth;
				brickPosition.y = initialScreenSize.y * 0.725f;
				brickSprite.setColor(brickColors[currentColor]);

			} else if (i % numBricksColumns == 0) {
				brickPosition.x = wallWidth;
				brickPosition.y -= brickHeight;
				currentColor = (currentColor > 0) ? currentColor - 1 : brickColors.length - 1;
				brickSprite.setColor(brickColors[currentColor]);
			} else {
				brickPosition.x += brickWidth;
			}
			brickSprite.setPosition(brickPosition.x, brickPosition.y);

			bricks[i] = new Brick(brickSprite, brickWeakestResistance + currentColor);
		}

		ballResetTime = TimeUtils.millis();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		deltaTime = Gdx.graphics.getDeltaTime();

		batch.begin();
		batch.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
		batch.draw(background, 0, 0, initialScreenSize.x, initialScreenSize.y);
		batch.end();

		switch (showMenuScreen) {
		case 'm':
			menuScreen(playButton, title, monster.getSprite());
			break;
		case 'n':
			gameScreen();
			break;
		case 'w':
			menuScreen(replayButton, win);
			break;
		case 'l':
			menuScreen(replayButton, gameover, monster.getSprite());
			break;
		}
	}

	private void menuScreen(Sprite button, Sprite... sprites) {
		batch.begin();
		for (Sprite sprite : sprites)
			sprite.draw(batch);
		button.draw(batch);
		batch.end();

		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched() && button.getBoundingRectangle().contains(new Vector2(Gdx.input.getX(), initialScreenSize.y - Gdx.input.getY()))) {
			showMenuScreen = 'n';
			loadGameAssets();
		}
	}

	private void gameScreen() {
		batch.begin();
		platform.draw(batch);
		ball.draw(batch);
		for (Surface surface : Surface.getAllSurfaces()) {
			surface.draw(batch);
		}
		batch.end();

		if (ball.getVelocityX() == 0 || ball.getVelocityY() == 0) {

			if (TimeUtils.timeSinceMillis(ballResetTime) < 500)
				return;

			if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				ball.setVelocityX(-ball.getINITIAL_VELOCITY());
				ball.setVelocityY(ball.getINITIAL_VELOCITY());
			} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				ball.setVelocityX(ball.getINITIAL_VELOCITY());
				ball.setVelocityY(ball.getINITIAL_VELOCITY());
			}
			return;
		}

		platform.move();
		platform.eject(ball);
		ball.move();

		if (floorRectangle.overlaps(ball.getSprite().getBoundingRectangle())) {
			ball.reset(platform);
			ballResetTime = TimeUtils.millis();
			player.removeLife();

			if (!player.isAlive()) {
				showMenuScreen = 'l';
				Surface.removeAllSurfaces();
			}
		}

		if (monster.resistance == 0) {
			showMenuScreen = 'w';
			Surface.removeAllSurfaces();
		}
	}

	@Override
	public void dispose() {
		background.dispose();
		batch.dispose();
	}
}
