package com.mygdx.codered;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CodeRed extends Game {
	private MainMenuScreen mainMenuScreen;
	private SettingsScreen settingsScreen;
	private GameScreen gameScreen;
	private Level[] levels;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public OrthographicCamera camera;

	private final String title;
	private final int screenWidth;
	private final int screenHeight;
	private final Vector3 mousePos;
	private final Rectangle mouseBox;

	private float speed;
	private int playerStartHealth;

	private int currentLevel;
	private boolean justWon;

	public CodeRed() {
		title = "CodeRed";
		screenWidth = 1920;
		screenHeight = 1080;
		mousePos = new Vector3();
		mouseBox = new Rectangle();

		//settings
		speed = 1;
		playerStartHealth = 3;

		currentLevel = 0;
		justWon = false;
	}

	@Override
	public void create() {
		levels = new Level[3];
		resetLevels();

		mainMenuScreen = new MainMenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		gameScreen = new GameScreen(this);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		updateMousePos();
		setScreen(mainMenuScreen);
	}

	//gets and sets/updates
	public boolean getJustWon() {
		return justWon;
	}
	public void setJustWon(boolean won) {
		justWon = won;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(int level) {
		currentLevel = level;
	}
	public Level[] getLevels() {
		return levels;
	}
	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}
	public SettingsScreen getSettingsScreen() {
		return settingsScreen;
	}
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	public String getTitle() {
		return title;
	}
	public int getWidth() {
		return screenWidth;
	}
	public int getHeight() {
		return screenHeight;
	}
	public Vector3 getMousePos() {
		return mousePos;
	}
	public void updateMousePos() {
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePos);
		mouseBox.set(mousePos.x, mousePos.y, 1, 1);
	}
	public Rectangle getMouseBox() {
		return mouseBox;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getPlayerStartHealth() {
		return playerStartHealth;
	}
	public void setPlayerStartHealth(int health) {
		this.playerStartHealth = health;
	}

	//helper methods
	public BitmapFont generateFont(int fontSize, Color fontColor) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Black.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.color = fontColor;
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}
	public void drawCenteredText(SpriteBatch batch, BitmapFont font, float x, float y, String text) {
		GlyphLayout textLayout = new GlyphLayout(font, text);
		font.draw(batch, text, x - textLayout.width/2, y + textLayout.height/2);
	}
	public void checkSettings() {
		if (speed < 0.1f) speed = 0.1f;
		if (speed > 2) speed = 2;
		if (playerStartHealth < 1) playerStartHealth = 1;
	}
	public void resetLevels() {
		float enemyWidth = 100;
		float enemyHeight = 100;
		Level level1 = new Level(this, 500, 500);
		level1.addEnemy(0, 20);
		Level level2 = new Level(this, 0, 0);
		level2.addEnemy(getWidth() - enemyWidth, 300);
		level2.addEnemy(0, getHeight()/2f);
		Level level3 = new Level(this, getWidth()/2f, getHeight()/2f);
		level3.addEnemy(0, 20);
		level3.addEnemy(0, getHeight() - enemyHeight);
		level3.addEnemy(getWidth() - enemyWidth, 20);
		level3.addEnemy(getWidth() - enemyWidth, getHeight() - enemyHeight);
//		Level level4 = new Level(this, getWidth()/2f, getHeight()/2f);
//		level4.addEnemy(0, 20);
//		level4.addEnemy(getWidth()/2f, 20);
//		level4.addEnemy(0, getHeight() - enemyHeight);
//		level4.addEnemy(0, getHeight()/2f - enemyHeight);
//		level4.addEnemy(getWidth() - enemyWidth, 20);
//		level4.addEnemy(getWidth() - enemyWidth, getHeight()/2f);
//		level4.addEnemy(getWidth() - enemyWidth, getHeight() - enemyHeight);
		levels[0] = level1;
		levels[1] = level2;
		levels[2] = level3;
	}
}