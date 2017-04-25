package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.core.Vector;

public class Bullet extends Entity {
    private Vector velocity;
    private Vector origin;
    private float rotation;
    private float speed;

    public Bullet(BulletType bulletType) {
        super(bulletType.filename());

        setPosition(new Vector(100, 100));
        setScale(0.5f);

        speed = 8;
        velocity = new Vector(0, 0);
    }

    @Override
    public void update() {
        float x = (float) (speed * Math.cos(rotation));
        float y = (float) (speed * Math.sin(rotation));
        velocity.setX(x);
        velocity.setY(y);

        setPosition(getPosition().add(velocity));
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Change to coordinate system when draw the ship
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.rotate(Math.PI / 2 + rotation);
        af.scale(getScale(), getScale());

        // Move coordinate system to ship's center pointer
        af.translate(origin.getX(), origin.getY());

        g2d.drawImage(getImage(), af, null);
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setOrigin(Vector origin) {
        this.origin = origin;
    }
}
