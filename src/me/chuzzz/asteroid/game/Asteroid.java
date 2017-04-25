package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.core.Vector;

public class Asteroid extends Entity {
    private float rotation;
    private float rotationSpeed;
    private Vector velocity;
    private Vector accel;
    private float friction;

    private boolean isBroken;

    private long flyDuration;
    private long spawnTime;

    public Asteroid(String filename) {
        super(filename);
        setScale(0.6f);

        rotationSpeed = 0.01f;

        isBroken = false;

        velocity = new Vector();
        accel = new Vector();

        friction = 0.98f;

        spawnTime = System.currentTimeMillis();
        flyDuration = 1000;
    }

    @Override
    public void update() {
        if (isBroken && System.currentTimeMillis() - spawnTime < flyDuration) {
            velocity = velocity.multiply(friction);
            velocity = velocity.add(this.accel);
            setPosition(getPosition().add(velocity));
        }

        rotation += rotationSpeed;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.rotate(rotation, getWidth() / 2, getHeight() / 2);
        af.scale(getScale(), getScale());
        g2d.drawImage(getImage(), af, null);
    }

    public void accelerate(Vector accel) {
        this.accel = accel;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

}
