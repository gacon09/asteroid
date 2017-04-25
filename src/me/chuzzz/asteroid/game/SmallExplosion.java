package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Sprite;

public class SmallExplosion extends Effect {
    private static final int LIVE_TIME = 700;

    private long spawnTime;

    public SmallExplosion() {
        Sprite sprite = new Sprite("res/effects/laserBlue08.png");
        setSprite(sprite);
        setPosition(new Vector(0, 0));
        setScale(0.5f);

        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.scale(getScale(), getScale());
        g2d.drawImage(getImage(), af, null);
    }

    @Override
    public boolean liveLongEnough() {
        return (System.currentTimeMillis() - spawnTime) > LIVE_TIME;
    }
}
