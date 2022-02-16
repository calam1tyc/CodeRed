package com.mygdx.codered;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Button extends Rectangle {
    private long clickTimer;
    private float startColor;
    private float startFontColor;
    private float color;
    private float fontColor;
    private final BitmapFont font;
    private final String text;
    public Button(float x, float y, float width, float height, float color, BitmapFont font, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.font = font;
        this.text = text;
        fontColor = font.getColor().r;
        startColor = color;
        startFontColor = fontColor;
        clickTimer = TimeUtils.nanoTime();
    }
    public void drawShape(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(new Color(color, color, color, 1));
        shapeRenderer.rect(x, y, width, height);
    }
    public void drawText(SpriteBatch batch) {
        GlyphLayout labelText = new GlyphLayout(font, text);
        font.setColor(new Color(fontColor, fontColor, fontColor, 1));
        font.draw(batch, text, x + width/2 - labelText.width/2, y + height/2 + labelText.height/2);
    }
    public void reset() {
        fontColor = startFontColor;
        color = startColor;
    }
    public void hoverEffect() {
        if (fontColor >= 0) fontColor -= 0.04;
        if (color <= 0.4) color += 0.02;
    }
    public boolean isClicked() {
        if (TimeUtils.nanoTime() - clickTimer > 200000000 && Gdx.input.isTouched()) {
            clickTimer = TimeUtils.nanoTime();
            return true;
        }
        return false;
    }
}
