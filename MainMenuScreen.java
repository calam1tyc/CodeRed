package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MainMenuScreen implements Screen {
    private final CodeRed game;
    private long initTimer;
    private final Texture background;
    private final BitmapFont titleFont;
    private final BitmapFont buttonFont;
    private final Button startButton;
    private final Button settingsButton;
    private final MainMenuEnemy mainMenuEnemy;

    public MainMenuScreen(CodeRed game) {
        this.game = game;
        initTimer = TimeUtils.nanoTime();
        background = new Texture(Gdx.files.internal("main-menu-background.png"));
        titleFont = game.generateFont(90, Color.WHITE);
        buttonFont = game.generateFont(60, Color.WHITE);
        startButton = new Button(150, 300, 400, 100, 0, buttonFont, "Start");
        settingsButton = new Button(150, 150, 400, 100, 0, buttonFont, "Settings");
        mainMenuEnemy = new MainMenuEnemy(game);
    }

    @Override
    public void show() {
        initTimer = TimeUtils.nanoTime();
        ScreenUtils.clear(0, 0, 0, 1);
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(background, 0, 0, 1920, 1080);
        mainMenuEnemy.draw(game.batch);
        game.batch.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        startButton.drawShape(game.shapeRenderer);
        settingsButton.drawShape(game.shapeRenderer);
        game.shapeRenderer.end();

        game.batch.begin();
        titleFont.setColor(new Color(1, 1, 1, 1));
        if (game.getJustWon()) {
            game.drawCenteredText(game.batch, titleFont, game.getWidth()/2f, game.getHeight()/2f, "You did it");
        }
        game.drawCenteredText(game.batch, titleFont, startButton.getX() + startButton.getWidth()/2, game.getHeight()/2f, "Code");
        titleFont.setColor(new Color(1, 0, 0, 1));
        game.drawCenteredText(game.batch, titleFont, startButton.getX() + startButton.getWidth()/2, game.getHeight()/2f - 75, "Red");

        startButton.drawText(game.batch);
        settingsButton.drawText(game.batch);
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        draw();
        if (TimeUtils.nanoTime() - initTimer > 250000000) {
            if (game.getMouseBox().overlaps(startButton)) {
                if (Gdx.input.justTouched()) {
                    if (game.getJustWon()) {
                        game.setJustWon(false);
                    }
                    game.setScreen(game.getGameScreen());
                }
                startButton.hoverEffect();
            } else startButton.reset();
            if (game.getMouseBox().overlaps(settingsButton)) {
                if (settingsButton.isClicked()) game.setScreen(game.getSettingsScreen());
                settingsButton.hoverEffect();
            } else settingsButton.reset();
            game.updateMousePos();
            game.camera.update();
        }
        mainMenuEnemy.update(delta);
    }

    @Override
    public void dispose() {
        background.dispose();
        titleFont.dispose();
        buttonFont.dispose();
    }

    @Override
    public void hide() {
        startButton.reset();
        settingsButton.reset();
    }

    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void resize(int width, int height) {

    }
}
