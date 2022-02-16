package com.mygdx.codered;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Rectangle {
    private final CodeRed game;
    private float xVelocity;
    private float yVelocity;
    private float angle;
    private int bounceCount;
    private Color color;

    public Bullet(CodeRed game, float shipX, float shipY, float shipWidth, float shipHeight, float shipAngle, Color color) {
        this.game = game;
        width = 15;
        height = 15;
        angle = shipAngle;

        Vector2 origin = new Vector2(shipX + shipWidth/2 - width/2, shipY + shipHeight/2 - height/2);
        Vector2 position = new Vector2(shipX + shipWidth/2 - width/2, shipY + shipHeight);
        position.rotateAroundDeg(origin, angle);

        x = position.x;
        y = position.y;

        xVelocity = (x - origin.x) * 15;
        yVelocity = (y - origin.y) * 15;

        bounceCount = 0;
        this.color = color;
    }

    public void update(float delta) {
        x += xVelocity * game.getSpeed() * delta;
        y += yVelocity * game.getSpeed() * delta;
        if (x < 0) {
            x = 0;
            xVelocity *= -1;
            addBounce();
        }
        if (x + width > game.getWidth()) {
            x = game.getWidth() - width;
            xVelocity *= -1;
            addBounce();
        }
        if (y < 0) {
            y = 0;
            yVelocity *= -1;
            addBounce();
        }
        if (y + height > game.getHeight()) {
            y = game.getHeight() - height;
            yVelocity *= -1;
            addBounce();
        }
    }
    public Color getColor() {
        return color;
    }
    public int getBounceCount() {
        return bounceCount;
    }
    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }

    private void addBounce() {
        bounceCount++;
        onBounce();
    }
    private void onBounce() {
        if (color.r < 1) color.r += 0.5;
        if (color.g < 1) color.g += 0.5;
        if (color.b < 1) color.b += 0.5;
    }
}
