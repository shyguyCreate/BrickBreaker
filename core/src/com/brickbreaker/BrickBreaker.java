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

/**
 * Game where a ball keeps bouncing all the time to destroy all the bricks,
 * and you control a platform to prevent the ball from going outside of the map.
 * The objective is to destroy the monster at the top of the map.
 */
public class BrickBreaker extends ApplicationAdapter {

	// Dimensions in which the game window is started.
	public static Vector2 initialScreenSize;
	// Left and right width that will delimit the playground.
	public static float wallWidth;
	// Time that passes between each frame.
	public static float deltaTime;

	// Dimensions of the floor beneath the platform.
	private Rectangle floorRectangle;
	// Image that will be used as background of the game.
	private Texture background;
	// Time in milliseconds since the ball touched the floor.
	private long ballResetTime;
	// Decide what menu to show to the user with a char.
	private char showMenuScreen = 'm';

	// Object that draws sprites to the screen.
	private SpriteBatch batch;
	// Sprite that holds the title image of the game.
	private Sprite title;
	// Sprite that holds the play button image.
	private Sprite playButton;
	// Sprite that holds the win title image.
	private Sprite win;
	// Sprite that holds the gameover title image.
	private Sprite gameover;
	// Sprite that holds the replay button image.
	private Sprite replayButton;

	// Object of the Player class.
	private Player player;
	// Object of the Platform class.
	private Platform platform;
	// Object of the Ball class.
	private Ball ball;
	// Object of the Monster class.
	private Monster monster;
	// Object of the Wall class initialized to the left.
	private Wall leftWall;
	// Object of the Wall class initialized to the right.
	private Wall rightWall;
	// Collection of objects of the Brick class.
	private Brick[] bricks;

	/**
	 * Method from the ApplicationAdapter parent class that runs when starting.
	 */
	@Override
	public void create() {
		// Save the initial screen size.
		initialScreenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Set walls width based on screen width.
		wallWidth = initialScreenSize.x * 0.025f;

		// Set the floor beneath the visible screen.
		floorRectangle = new Rectangle(0, 0, initialScreenSize.x, -15);
		// Set background image.
		background = new Texture("space.jpg");

		// Set new SpriteBatch object.
		batch = new SpriteBatch();
		// Set new Monster object with resistance zero.
		monster = new Monster(0);
		// Remove monster from the list of surfaces.
		Surface.removeSurface(monster);

		// Set title image in sprite.
		title = new Sprite(new Texture("title.png"));
		// Set scale of title sprite.
		title.setScale(0.9f);
		// Set size of title sprite.
		title.setSize(title.getWidth() * title.getScaleX(), title.getHeight() * title.getScaleY());
		// Set position of title sprite at center of width and over the center of height.
		title.setPosition(initialScreenSize.x / 2 - title.getWidth() / 2, initialScreenSize.y / 2.5f);

		// Set play button image in sprite.
		playButton = new Sprite(new Texture("play.png"));
		// Set scale of button sprite.
		playButton.setScale(0.75f);
		// Set size of button sprite.
		playButton.setSize(playButton.getWidth() * playButton.getScaleX(), playButton.getHeight() * playButton.getScaleY());
		// Set position of button sprite at center of width and below the center of height.
		playButton.setPosition(initialScreenSize.x / 2 - playButton.getWidth() / 2, initialScreenSize.y / 7.5f);

		// Set win image in sprite.
		win = new Sprite(new Texture("win.png"));
		// Set scale of sprite.
		win.setScale(1f);
		// Set size of sprite.
		win.setSize(win.getWidth() * win.getScaleX(), win.getHeight() * win.getScaleY());
		// Set position of sprite at center of width and over the center of height.
		win.setPosition(initialScreenSize.x / 2 - win.getWidth() / 2, initialScreenSize.y / 2.25f);

		// Set gameover image in sprite.
		gameover = new Sprite(new Texture("gameover.png"));
		// Set scale of sprite.
		gameover.setScale(0.9f);
		// Set size of sprite.
		gameover.setSize(gameover.getWidth() * gameover.getScaleX(), gameover.getHeight() * gameover.getScaleY());
		// Set position of sprite at center of width and over the center of height.
		gameover.setPosition(initialScreenSize.x / 2 - gameover.getWidth() / 2, initialScreenSize.y / 1.75f);

		// Set replay button image in sprite.
		replayButton = new Sprite(new Texture("replay.png"));
		// Set scale of button sprite.
		replayButton.setScale(0.8f);
		// Set size of button sprite.
		replayButton.setSize(replayButton.getWidth() * replayButton.getScaleX(), replayButton.getHeight() * replayButton.getScaleY());
		// Set position of button sprite at center of width and below the center of height.
		replayButton.setPosition(initialScreenSize.x / 2 - replayButton.getWidth() / 2, initialScreenSize.y / 5.5f);
	}

	/**
	 * Load game components like player, platform, walls, monster, ball,
	 * floor and bricks to set the game ready.
	 */
	private void loadGameAssets() {
		// Set player lives.
		int playerLives = 3;
		// Set monster resistance.
		int monsterResistance = 10;
		// Set ball velocity.
		int ballInitialVelocity = 500;

		// Set new Player object with lives.
		player = new Player(playerLives);
		// Set new Platform object.
		platform = new Platform();
		// Set new Ball object with platform object and velocity.
		ball = new Ball(platform, ballInitialVelocity);
		// Set new Monster object with resistance.
		monster = new Monster(monsterResistance);
		// Set new Wall object with rectangle to the left of the screen.
		leftWall = new Wall(new Rectangle(0, 0, wallWidth, initialScreenSize.y));
		// Set new Wall object with rectangle to the right of the screen.
		rightWall = new Wall(new Rectangle(initialScreenSize.x - wallWidth, 0, wallWidth, initialScreenSize.y));

		// Set bricks number of rows.
		int numBricksRows = 6;
		// Set bricks number of columns.
		int numBricksColumns = 7;
		// Set columns x rows new Brick objects.
		bricks = new Brick[numBricksRows * numBricksColumns];

		// Set brick image to sprite.
		Sprite brickSprite = new Sprite(new Texture("brick_new.png"));
		// Save brick width based on available screen size and number of columns.
		float brickWidth = (initialScreenSize.x - wallWidth * 2) / numBricksColumns;
		// Save brick height scaling the sprite.
		float brickHeight = brickSprite.getHeight() * 0.6f;
		// Set brick sprite size with saved values.
		brickSprite.setSize(brickWidth, brickHeight);

		// Set new Vector2 object for brick position.
		Vector2 brickPosition = new Vector2();
		// Set arrays of colors to use in brick sprites.
		Color[] brickColors = { Color.PINK, Color.RED, Color.PURPLE };
		// Save current color index.
		int currentColor = brickColors.length - 1;
		// Set the minimum resistance of a brick.
		int brickWeakestResistance = 1;

		// Iterate over all bricks by row
		for (int i = 0; i < numBricksRows; i++) {
			// Set positionY of brick based on screen size and increment every row.
			brickPosition.y = initialScreenSize.y * 0.725f - brickHeight * i;
			// Save color index stating from the last and the remainder of row over length.
			currentColor = (brickColors.length - 1) - (i % brickColors.length);
			// Set color to the sprite.
			brickSprite.setColor(brickColors[currentColor]);
			// Iterate over all bricks by column
			for (int j = 0; j < numBricksColumns; j++) {
				// Set positionX of brick with wall width and increment every column.
				brickPosition.x = wallWidth + brickWidth * j;
				// Set sprite position with saved values.
				brickSprite.setPosition(brickPosition.x, brickPosition.y);
				// Set new Brick object with sprite and resistance.
				bricks[((numBricksRows + 1) * i) + j] = new Brick(brickSprite, brickWeakestResistance + currentColor);
			}
		}
		// Save current time.
		ballResetTime = TimeUtils.millis();
	}

	/**
	 * Method from the ApplicationAdapter parent class that runs when
	 * indefinitely until the window is closed.
	 */
	@Override
	public void render() {
		// Paint screen in black.
		ScreenUtils.clear(0, 0, 0, 1);
		// Save time between frames.
		deltaTime = Gdx.graphics.getDeltaTime();

		// Start SpriteBatch.
		batch.begin();
		// Change color to a darker one.
		batch.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
		// Draw background over all the screen.
		batch.draw(background, 0, 0, initialScreenSize.x, initialScreenSize.y);
		// Close SpriteBatch.
		batch.end();

		// Check char inside showMenuScreen.
		switch (showMenuScreen) {
		// Check if 'm' -> main menu.
		case 'm':
			// Show menu with play button and title with monster.
			menuScreen(playButton, title, monster.getSprite());
			break;
		// Check if 'm' -> no menu.
		case 'n':
			gameScreen();
			break;
		// Check if 'w' -> win menu.
		case 'w':
			// Show menu with replay button and win image.
			menuScreen(replayButton, win);
			break;
		// Check if 'l' -> lose menu.
		case 'l':
			// Show menu with replay button and gameover image with monster.
			menuScreen(replayButton, gameover, monster.getSprite());
			break;
		}
	}

	/**
	 * Display either the main, win or gameover menu in the screen.
	 * 
	 * @param button  Sprite that should be click
	 * @param sprites Sprite that only should be drawn
	 */
	private void menuScreen(Sprite button, Sprite... sprites) {
		// Start SpriteBatch.
		batch.begin();
		// For each sprite, draw it.
		for (Sprite sprite : sprites)
			sprite.draw(batch);
		// Draw button.
		button.draw(batch);
		// Close SpriteBatch.
		batch.end();

		// Check if space is pressed or if mouse click was in the button sprite.
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isTouched() && button.getBoundingRectangle().contains(new Vector2(Gdx.input.getX(), initialScreenSize.y - Gdx.input.getY()))) {
			// Change menu to 'no menu'.
			showMenuScreen = 'n';
			// Load new assets for the game screen.
			loadGameAssets();
		}
	}

	/**
	 * Display the actual game to the screen.
	 */
	private void gameScreen() {
		// Start SpriteBatch.
		batch.begin();
		// Draw platform sprite.
		platform.draw(batch);
		// Draw ball sprite.
		ball.draw(batch);
		// Iterate over all surfaces in static list.
		for (Surface surface : Surface.getAllSurfaces()) {
			// Draw current surface.
			surface.draw(batch);
		}
		// Close SpriteBatch.
		batch.end();

		// Check if velocity X or Y is zero.
		if (ball.getVelocityX() == 0 || ball.getVelocityY() == 0) {

			// If reset time is smaller, do nothing.
			if (TimeUtils.timeSinceMillis(ballResetTime) < 500)
				return;

			// Check if A or left arrow is pressed
			if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				// Start velocityX pointing to the left
				ball.setVelocityX(-ball.getINITIAL_VELOCITY());
				// Start velocityY with INITIAL_VELOCITY
				ball.setVelocityY(ball.getINITIAL_VELOCITY());
			}
			// Check if D or right arrow is pressed
			else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				// Start velocityX pointing to the right
				ball.setVelocityX(ball.getINITIAL_VELOCITY());
				// Start velocityY with INITIAL_VELOCITY
				ball.setVelocityY(ball.getINITIAL_VELOCITY());
			}

			// Return if velocity X or Y is zero.
			return;
		}

		// Move the platform with keys.
		platform.move();
		// Make ball go up when colliding with platform.
		platform.eject(ball);
		// Move ball and check for collisions.
		ball.move();

		// Check if the ball collides with the floor.
		if (floorRectangle.overlaps(ball.getSprite().getBoundingRectangle())) {
			// Reset the ball on top of the platform.
			ball.reset(platform);
			// Save current time of collision.
			ballResetTime = TimeUtils.millis();
			// Remove a life from the player.
			player.removeLife();

			// Check if the player has no lives.
			if (!player.isAlive()) {
				// Set menu to 'lose menu'.
				showMenuScreen = 'l';
				// Clear list of surface.
				Surface.removeAllSurfaces();
			}
		}

		// Check if monster resistance is zero.
		if (monster.resistance == 0) {
			// Set menu to 'win menu'.
			showMenuScreen = 'w';
			// Clear list of surface.
			Surface.removeAllSurfaces();
		}
	}

	/**
	 * Method from the ApplicationAdapter parent class that runs when the window is closed.
	 */
	@Override
	public void dispose() {
		// Try the following
		try {
			// Remove background texture from memory.
			background.dispose();
			// Remove SpriteBatch from memory.
			batch.dispose();
		}
		// Catch any error.
		catch (Exception e) {
			// Print when the error occurred.
			System.err.println("Error ocurred when closing game.");
			// Print the exception thrown.
			System.err.println(e);
		}
	}
}
