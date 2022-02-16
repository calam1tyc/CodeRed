package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
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

public class Enemy extends Rectangle {
    private final CodeRed game;
    private final Player player;
    private final TextureRegion graphicRegion;
    private float xVelocity;
    private float yVelocity;
    private float angle;
    private final Array<Bullet> bullets;
    private long bulletTimer;
    private int health;

    public Enemy(CodeRed game, float x, float y, Player player) {
        this.game = game;
        this.player = player;
        this.x = x;
        this.y = y;
        width = 100;
        height = 100;
        xVelocity = 0;
        yVelocity = 0;
        Texture enemyGraphic = new Texture(Gdx.files.internal("enemy-ship.png"));
        graphicRegion = new TextureRegion(enemyGraphic, enemyGraphic.getWidth(), enemyGraphic.getHeight());
        angle = (float) Math.atan2(player.getY() - this.y, this.player.getX() - this.x) * MathUtils.radiansToDegrees - 90;
        bullets = new Array<Bullet>();
        bulletTimer = TimeUtils.nanoTime();
        health = 3;
    }
    public Array<Bullet> getBullets() {
        return bullets;
    }
    public int getHealth() {
        return health;
    }

    public void update(float delta) {
        checkMovement(delta);
        checkActions();
        checkBulletCollisions();
    }

    public void checkMovement(float delta) {
        angle = (float) Math.atan2(player.getY() - y, player.getX() - x) * MathUtils.radiansToDegrees - 90;
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            bullet.update(delta);
            if (bullet.getBounceCount() > 1) iterator.remove();
        }
    }

    public void checkActions() {
        if (TimeUtils.nanoTime() - bulletTimer > 200000000 / game.getSpeed()) {
            bulletTimer = TimeUtils.nanoTime();
            bullets.add(new Bullet(game, x, y, width, height, angle, new Color(0, 0, 1, 1)));
        }
    }

    private void checkBulletCollisions() {
        for (Iterator<Bullet> iterator = player.getBullets().iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();
            if (bullet.overlaps(this)) {
                health--;
                iterator.remove();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(graphicRegion, x, y, width/2, height/2, width, height, 1, 1, angle);
    }

    public void drawBullets(ShapeRenderer shapeRenderer) {
        for (Bullet bullet : bullets) {
            shapeRenderer.setColor(bullet.getColor());
            shapeRenderer.rect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        }
    }

    public void drawHealthBar(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(x, y - 10, health * 33.33f, 10);
    }
}
