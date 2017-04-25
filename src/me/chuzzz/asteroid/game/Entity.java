package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.Image;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Sprite;

public abstract class Entity {
    private Sprite sprite;

    public Entity() {
    }

    public Entity(String filename) {
        sprite = new Sprite(filename);
    }

    public abstract void update();

    public abstract void draw(Graphics2D g2d);

    public Image getImage() {
        return sprite.getImage();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector getPosition() {
        return sprite.getPosition();
    }

    public void setPosition(Vector position) {
        sprite.setPosition(position);
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() {
        return sprite.getHeight();
    }

    public float getScale() {
        return sprite.getScale();
    }

    public void setScale(float scale) {
        sprite.setScale(scale);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
