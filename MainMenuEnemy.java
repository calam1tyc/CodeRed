package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuEnemy extends Rectangle {
    private CodeRed game;
    private TextureRegion graphicRegion;
    private float xVelocity;
    private float yVelocity;
    private float angle;

    public MainMenuEnemy(CodeRed game) {
        this.game = game;
        x = 1200;
        y = game.getHeight()/2f;
        width = 100;
        height = 100;
        xVelocity = 0;
        yVelocity = 200;
        Texture graphic = new Texture(Gdx.files.internal("enemy-ship.png"));
        graphicRegion = new TextureRegion(graphic, graphic.getWidth(), graphic.getHeight());
        angle = (float) Math.atan2(game.getMouseBox().y - y, game.getMouseBox().x - x) * MathUtils.radiansToDegrees - 90;
    }
    public void update(float delta) {
        x += xVelocity * delta;
        y += yVelocity * delta;
        if (y > game.getHeight()) {
            y = -height;
        }
        angle = (float) Math.atan2(game.getMouseBox().y - y, game.getMouseBox().x - x) * MathUtils.radiansToDegrees - 90;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(graphicRegion, x, y, width/2, height/2, width, height, 1, 1, angle);
    }
}
