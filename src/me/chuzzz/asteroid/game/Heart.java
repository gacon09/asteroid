package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.Sprite;

public class Heart extends Collectible {
    private static final long LIVE_TIME = 10000;

    private int life;

    public Heart() {
        setSprite(new Sprite("res/powerup/things_bronze.png"));
        setScale(0.8f);
        setLiveTime(LIVE_TIME);

        life = 1;
    }

    @Override
    public void applyToPlayer(Player player) {
        player.setLife(player.getLife() + life);
    }
}
