package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Player extends Rectangle {
    private final CodeRed game;
    private final TextureRegion graphicRegion;
    private final Array<Enemy> enemies;
    private float xVelocity;
    private float yVelocity;
    private float angle;
    private final Array<Bullet> bullets;
    private long bulletTimer;
    private int health;

    public Player(CodeRed game, float x, float y, Array<Enemy> enemies) {
        this.game = game;
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        this.enemies = enemies;
        Texture playerGraphic = new Texture(Gdx.files.internal("player-ship.png"));
        graphicRegion = new TextureRegion(playerGraphic, playerGraphic.getWidth(), playerGraphic.getHeight());
        xVelocity = 0;
        yVelocity = 0;
        angle = (float) Math.atan2(game.getMouseBox().y - this.y, game.getMouseBox().x - this.x) * MathUtils.radiansToDegrees - 90;
        bullets = new Array<Bullet>();
        bulletTimer = TimeUtils.nanoTime();
        health = game.getPlayerStartHealth();
    }

    public Array<Bullet> getBullets() {
        return bullets;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void update(float delta) {
        checkMovement(delta);
        checkActions();
        checkBulletCollisions();
    }
    private void checkMovement(float delta) {
        xVelocity = 0;
        yVelocity = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yVelocity += 350;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity -= 350;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yVelocity -= 350;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity += 350;
        }
        float lastX = x;
        float lastY = y;
        x += xVelocity * game.getSpeed() * delta;
        y += yVelocity * game.getSpeed() * delta;
        if (x < 0 || x + width > game.getWidth()) {
            x = lastX;
        }
        if (y < 0 || y + height > game.getHeight()) {
            y = lastY;
        }
        angle = (float) Math.atan2(game.getMouseBox().y - y, game.getMouseBox().x - x) * MathUtils.radiansToDegrees - 90;
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
            if (bullet.getBounceCount() > 2) iterator.remove();
        }
    }

    private void checkActions() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && TimeUtils.nanoTime() - bulletTimer > 200000000 / game.getSpeed()) {
            bulletTimer = TimeUtils.nanoTime();
            bullets.add(new Bullet(game, x, y, width, height, angle, new Color(1, 0, 0, 1)));
        }
    }

    private void checkBulletCollisions() {
        for (Enemy enemy : enemies) {
            for (Iterator<Bullet> iterator2 = enemy.getBullets().iterator(); iterator2.hasNext(); ) {
                Bullet bullet = iterator2.next();
                if (bullet.overlaps(this)) {
                    health--;
                    iterator2.remove();
                }
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(graphicRegion, x, y, width/2, height/2, width, height, 1, 1, angle);
    }

    public void drawHealthBar(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(new Color(1, 0, 0, 1));
        shapeRenderer.rect(x, y - 10, health * (100f/game.getPlayerStartHealth()), 10);
    }

    public void drawBullets(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            shapeRenderer.setColor(bullet.getColor());
            shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }
}
