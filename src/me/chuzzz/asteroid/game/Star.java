package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.BlinkEffect;

public class Star extends Effect {
    private BlinkEffect blink;

    public Star(String filename) {
        super(filename);

        setScale(0.5f);

        float blinkSpeed = (float) (Math.random() * 0.08 + 0.02);
        float blinkBase = (float) (Math.random() * 0.01 + 0.05);
        blink = new BlinkEffect(blinkSpeed, blinkBase);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.scale(getScale(), getScale());

        blink.makeBlink(g2d);
        g2d.drawImage(getImage(), af, null);
        blink.reset(g2d);
    }

}
