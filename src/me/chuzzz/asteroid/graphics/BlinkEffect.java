package me.chuzzz.asteroid.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class BlinkEffect {
    private float blinkAngle;
    private float blinkSpeed;
    private float blinkBase;

    public BlinkEffect() {
        blinkSpeed = 0.05f;
        blinkBase = 0.5f;
    }

    public BlinkEffect(float blinkSpeed) {
        this.blinkSpeed = blinkSpeed;
        this.blinkBase = 0.5f;
    }

    public BlinkEffect(float blinkSpeed, float blinkBase) {
        this.blinkSpeed = blinkSpeed;
        this.blinkBase = blinkBase;
    }

    public void makeBlink(Graphics2D g2d) {
        float alpha = (float) (blinkBase + Math.sin(blinkAngle) * blinkBase);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        blinkAngle += blinkSpeed;
    }

    public void reset(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
