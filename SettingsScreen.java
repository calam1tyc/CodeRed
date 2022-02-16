package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class SettingsScreen implements Screen {
    private final CodeRed game;
    private long initTimer;
    private final BitmapFont titleFont;
    private final BitmapFont buttonLabelFont;
    private final BitmapFont settingValueFont;
    private final BitmapFont buttonFont;
    private final BitmapFont returnButtonFont;
    private final Button minusBox1;
    private final Button minusBox2;
    private final Button minusBox3;
    private final Button plusBox1;
    private final Button plusBox2;
    private final Button plusBox3;
    private final Button returnButton;
    private final Button resetButton;

    public SettingsScreen(CodeRed game) {
        this.game = game;
        initTimer = TimeUtils.nanoTime();
        titleFont = game.generateFont(75, new Color(0, 0, 0, 1));
        buttonLabelFont = game.generateFont(60, new Color(1, 0, 0, 1));
        settingValueFont = game.generateFont(60, new Color(0, 0, 1, 1));
        buttonFont = game.generateFont(100, new Color(0.4f, 0.4f, 0.4f, 1));
        returnButtonFont = game.generateFont(50, new Color(0.4f, 0.4f, 0.4f, 1));
        returnButton = new Button(100, 100, 400, 100, 0, returnButtonFont, "Return to Menu");
        resetButton = new Button(game.getWidth() - 100 - 400, 100, 400, 100, 0, returnButtonFont, "Reset Settings");
        minusBox1 = new Button(game.getWidth()/2f - 100/2f - 200, game.getHeight() - 300, 100, 100, 0, buttonFont, "-");
        minusBox2 = new Button(game.getWidth()/2f - 100/2f - 200, game.getHeight() - 450, 100, 100, 0, buttonFont, "-");
        minusBox3 = new Button(game.getWidth()/2f - 100/2f - 200, game.getHeight() - 600, 100, 100, 0, buttonFont, "-");
        plusBox1 = new Button(game.getWidth()/2f - 100/2f + 200, game.getHeight() - 300, 100, 100, 0, buttonFont, "+");
        plusBox2 = new Button(game.getWidth()/2f - 100/2f + 200, game.getHeight() - 450, 100, 100, 0, buttonFont, "+");
        plusBox3 = new Button(game.getWidth()/2f - 100/2f + 200, game.getHeight() - 600, 100, 100, 0, buttonFont, "+");
    }

    @Override
    public void show() {
        initTimer = TimeUtils.nanoTime();
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
    }

    private void draw() {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        game.camera.update();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        returnButton.drawShape(game.shapeRenderer);
        resetButton.drawShape(game.shapeRenderer);
        minusBox1.drawShape(game.shapeRenderer);
        minusBox2.drawShape(game.shapeRenderer);
        minusBox3.drawShape(game.shapeRenderer);
        plusBox1.drawShape(game.shapeRenderer);
        plusBox2.drawShape(game.shapeRenderer);
        plusBox3.drawShape(game.shapeRenderer);
        game.shapeRenderer.end();

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.drawCenteredText(game.batch, titleFont, game.getWidth()/2f, game.getHeight() - 150, "SETTINGS");
        returnButton.drawText(game.batch);
        resetButton.drawText(game.batch);
        minusBox1.drawText(game.batch);
        minusBox2.drawText(game.batch);
        minusBox3.drawText(game.batch);
        plusBox1.drawText(game.batch);
        plusBox2.drawText(game.batch);
        plusBox3.drawText(game.batch);
        game.drawCenteredText(game.batch, buttonLabelFont, game.getWidth()/2f, minusBox1.getY() + minusBox1.getHeight()/2, "SPEED");
        game.drawCenteredText(game.batch, settingValueFont, game.getWidth()/2f, minusBox1.getY() - 10, "" + game.getSpeed());
        game.drawCenteredText(game.batch, buttonLabelFont, game.getWidth()/2f, minusBox2.getY() + minusBox2.getHeight()/2, "HEALTH");
        game.drawCenteredText(game.batch, settingValueFont, game.getWidth()/2f, minusBox2.getY() - 10, "" + game.getPlayerStartHealth());
        game.drawCenteredText(game.batch, buttonLabelFont, game.getWidth()/2f, minusBox3.getY() + minusBox3.getHeight()/2, "TEST");
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        draw();
        if (TimeUtils.nanoTime() - initTimer > 250000000) {
            if (returnButton.overlaps(game.getMouseBox())) {
                if (returnButton.isClicked()) {
                    game.resetLevels();
                    game.setScreen(game.getMainMenuScreen());
                }
                returnButton.hoverEffect();
            } else returnButton.reset();
            if (resetButton.overlaps(game.getMouseBox())) {
                if (resetButton.isClicked()) {
                    game.setSpeed(1);
                    game.setPlayerStartHealth(3);
                }
                resetButton.hoverEffect();
            } else resetButton.reset();
            if (minusBox1.overlaps(game.getMouseBox())) {
                if (minusBox1.isClicked()) game.setSpeed(Math.round((game.getSpeed() - 0.1f) * 10f) / 10f);
                minusBox1.hoverEffect();
            } else minusBox1.reset();
            if (plusBox1.overlaps(game.getMouseBox())) {
                if (plusBox1.isClicked()) game.setSpeed(Math.round((game.getSpeed() + 0.1f) * 10f) / 10f);
                plusBox1.hoverEffect();
            } else plusBox1.reset();
            if (minusBox2.overlaps(game.getMouseBox())) {
                if (minusBox2.isClicked()) game.setPlayerStartHealth(game.getPlayerStartHealth() - 1);
                minusBox2.hoverEffect();
            } else minusBox2.reset();
            if (plusBox2.overlaps(game.getMouseBox())) {
                if (minusBox2.isClicked()) game.setPlayerStartHealth(game.getPlayerStartHealth() + 1);
                plusBox2.hoverEffect();
            } else plusBox2.reset();
            if (minusBox3.overlaps(game.getMouseBox())) {
                minusBox3.hoverEffect();
            } else minusBox3.reset();
            if (plusBox3.overlaps(game.getMouseBox())) {
                plusBox3.hoverEffect();
            } else plusBox3.reset();
            game.checkSettings();
            game.updateMousePos();
            game.camera.update();
        }
    }

    @Override
    public void dispose() {
        titleFont.dispose();
        buttonFont.dispose();
        buttonLabelFont.dispose();
        returnButtonFont.dispose();
    }

    @Override
    public void hide() {
        returnButton.reset();
        minusBox1.reset();
        plusBox1.reset();
        minusBox2.reset();
        plusBox2.reset();
        minusBox3.reset();
        plusBox3.reset();
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
