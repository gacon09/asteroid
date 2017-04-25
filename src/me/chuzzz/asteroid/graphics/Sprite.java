package me.chuzzz.asteroid.graphics;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import me.chuzzz.asteroid.core.Vector;

public class Sprite {
    private Image image;
    private Vector position;
    private float width;
    private float height;
    private float scale;

    private Circle circleBounds;

    public Sprite(String filename) {
        ImageIcon ii = new ImageIcon(filename);
        image = ii.getImage();

        width = image.getWidth(null);
        height = image.getHeight(null);

        position = new Vector(0, 0);
        scale = 1.0f;

        circleBounds = new Circle(0, 0, 0);
    }

    public Image getImage() {
        return image;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public float getWidth() {
        return width * scale;
    }

    public float getHeight() {
        return height * scale;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.getX(), (int) position.getY(), (int) getWidth(), (int) getHeight());
    }

    public Circle getCircleBounds() {
        float radius = getWidth() * scale / 2;
        return new Circle(radius, position.getX() - 40, position.getY() + 30);
    }
}
