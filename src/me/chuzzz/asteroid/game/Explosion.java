package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Sprite;

public class Explosion extends Effect {
    private static final int LIVE_TIME = 700;
    private static final int DELAY = 5;

    private Sprite[] frames;
    private int count;
    private int currentFrame;

    private long spawnTime;

    public Explosion() {

        frames = new Sprite[] { new Sprite("res/effects/spaceEffects_012.png"),
                new Sprite("res/effects/spaceEffects_013.png"), new Sprite("res/effects/spaceEffects_014.png"),
                new Sprite("res/effects/spaceEffects_015.png"), new Sprite("res/effects/spaceEffects_016.png") };
        for (Sprite frame : frames) {
            frame.setScale(0.6f);
        }

        currentFrame = 3;
        setSprite(frames[currentFrame]);
        count = 0;

        setPosition(new Vector(0, 0));
        setScale(0.5f);

        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        count = (count + 1) % DELAY;
        if (count == 0) {
            currentFrame = (currentFrame + 1) % frames.length;
        }
        setSprite(frames[currentFrame]);
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.scale(getScale(), getScale());
        g2d.drawImage(getImage(), af, null);
    }

    @Override
    public void setPosition(Vector position) {
        super.setPosition(position);

        for (Sprite frame : frames) {
            frame.setPosition(position);
        }
    }

    @Override
    public boolean liveLongEnough() {
        return (System.currentTimeMillis() - spawnTime) > LIVE_TIME;
    }
}
