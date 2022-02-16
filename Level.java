package com.mygdx.codered;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Level {
    private final CodeRed game;
    private final Player player;
    private final Array<Enemy> enemies;

    public Level (CodeRed game, float playerX, float playerY) {
        this.game = game;
        enemies = new Array<Enemy>();
        player = new Player(game, playerX, playerY, enemies);
    }

    public void addEnemy(float x, float y) {
        Enemy enemy = new Enemy(game, x, y, player);
        enemies.add(enemy);
    }

    public Player getPlayer() {
        return player;
    }
    public Array<Enemy> getEnemies() {
        return enemies;
    }

    public void updatePlayer(float delta) {
        player.update(delta);
        if (player.getHealth() <= 0) {
            game.setScreen(game.getMainMenuScreen());
            game.resetLevels();
        }
    }

    public void updateEnemies(float delta) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.update(delta);
            if (enemy.getHealth() <= 0) {
                iterator.remove();
            }
        }
        if (enemies.size == 0) {
            game.getGameScreen().resetInitTimer();
            game.resetLevels();
            if (game.getCurrentLevel() == game.getLevels().length - 1) {
                game.setJustWon(true);
                game.setScreen(game.getMainMenuScreen());
                game.setCurrentLevel(0);
            }
            else {
                game.setCurrentLevel(game.getCurrentLevel() + 1);
            }
        }
    }

    public void drawPlayer(SpriteBatch batch) {
        player.draw(batch);
    }

    public void drawEnemies(SpriteBatch batch) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.draw(batch);
        }
    }

    public void drawPlayerBullets(ShapeRenderer shapeRenderer) {
        player.drawBullets(shapeRenderer);
    }

    public void drawEnemyBullets(ShapeRenderer shapeRenderer) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.drawBullets(shapeRenderer);
        }
    }

    public void drawPlayerHealthBar(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(new Color(1, 0, 0, 1));
        player.drawHealthBar(shapeRenderer);
    }

    public void drawEnemyHealthBars(ShapeRenderer shapeRenderer) {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
            Enemy enemy = iterator.next();
            enemy.drawHealthBar(shapeRenderer);
        }
    }
}
