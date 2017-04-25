package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.BlinkEffect;

public abstract class Collectible extends Entity {
    private long liveTime;
    private long urgentTime;
    private long spawnTime;
    private boolean urgent;
    private BlinkEffect blinkEffect;
    private boolean liveLongEnough;

    public Collectible() {
        spawnTime = System.currentTimeMillis();
        liveTime = 5000;
        urgentTime = 3000;
        urgent = false;
        blinkEffect = new BlinkEffect(0.1f);
        liveLongEnough = false;
    }

    @Override
    public void update() {
        long dt = System.currentTimeMillis() - spawnTime;
        if (dt >= liveTime - urgentTime && dt <= liveTime) {
            urgent = true;
        } else if (dt > liveTime) {
            liveLongEnough = true;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.scale(getScale(), getScale());
        if (urgent) {
            blinkEffect.makeBlink(g2d);
            g2d.drawImage(getImage(), af, null);
            blinkEffect.reset(g2d);
        } else {
            g2d.drawImage(getImage(), af, null);
        }
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    public float getLiveTime() {
        return liveTime;
    }

    public boolean liveLongEnough() {
        return liveLongEnough;
    }

    public abstract void applyToPlayer(Player player);
}
