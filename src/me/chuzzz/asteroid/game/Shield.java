package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.Sprite;

public class Shield extends Collectible {
    private static final long LIVE_TIME = 10000;

    public Shield() {
        setSprite(new Sprite("res/powerup/shield_silver.png"));
        setScale(0.7f);
        setLiveTime(LIVE_TIME);
    }

    @Override
    public void applyToPlayer(Player player) {
        player.equipShield(this);
    }
}
