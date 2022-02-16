package com.mygdx.codered;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    private final CodeRed game;
    private final BitmapFont levelTitleFont;
    private Level level;
    private long initTimer;

    public GameScreen(CodeRed game) {
        this.game = game;
        levelTitleFont = game.generateFont(60, new Color(1, 1, 1, 1));
        initTimer = TimeUtils.nanoTime();
        level = game.getLevels()[game.getCurrentLevel()];
    }

    public void draw() {
        Color backgroundColor = new Color(0.5f, 0.5f, 0.5f, 1);
        if (game.getCurrentLevel() == 0) {
            levelTitleFont.setColor(new Color(0, 0, 0, 1));
        }
        else if (game.getCurrentLevel() == 1) {
           levelTitleFont.setColor(new Color(0, 0, 1, 1));
           backgroundColor.set(0.2f, 0.2f, 0.2f, 1);
        } else if (game.getCurrentLevel() == 2) {
           levelTitleFont.setColor(1, 0, 0, 1);
           backgroundColor.set(0, 0, 0, 1);
        } else {
            levelTitleFont.setColor(1, 1, 0, 1);
            backgroundColor.set(1, 1, 1, 1);
        }
        ScreenUtils.clear(backgroundColor);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        level.drawPlayerHealthBar(game.shapeRenderer);
        level.drawEnemyHealthBars(game.shapeRenderer);
        level.drawPlayerBullets(game.shapeRenderer);
        level.drawEnemyBullets(game.shapeRenderer);
        game.shapeRenderer.end();

        game.batch.begin();
        game.drawCenteredText(game.batch, levelTitleFont, game.getWidth()/2f, game.getHeight() - 100, "Level " + (game.getCurrentLevel() + 1));
        level.drawPlayer(game.batch);
        level.drawEnemies(game.batch);
        game.batch.end();

    }

    @Override
    public void show() {
        ScreenUtils.clear(new Color(0.5f, 0.5f, 0.5f, 1));
        initTimer = TimeUtils.nanoTime();
        level = game.getLevels()[0];
    }

    @Override
    public void render(float delta) {
        if (level != game.getLevels()[game.getCurrentLevel()]) {
            level = game.getLevels()[game.getCurrentLevel()];
        }
        draw();
        if (TimeUtils.nanoTime() - initTimer > 1000000000) {
            level.updatePlayer(delta);
            level.updateEnemies(delta);
        }
        game.updateMousePos();
    }

    public void resetInitTimer() {
        initTimer = TimeUtils.nanoTime();
    }

    @Override
    public void hide() {

    }
    @Override
    public void dispose() {

    }
    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
}
