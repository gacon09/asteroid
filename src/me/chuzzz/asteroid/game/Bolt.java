package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.Sprite;

public class Bolt extends Collectible {
    public static final String BRONZE_BOLT = "res/powerup/bolt_bronze.png";
    public static final String SILVER_BOLT = "res/powerup/bolt_silver.png";
    public static final String GOLD_BOLT = "res/powerup/bolt_gold.png";

    private static final long LIVE_TIME = 10000;

    private String boltType;

    public Bolt(String boltType) {
        this.boltType = boltType;
        setSprite(new Sprite(boltType));
        setScale(0.8f);
        setLiveTime(LIVE_TIME);
    }

    @Override
    public void applyToPlayer(Player player) {
        switch (boltType) {
        case BRONZE_BOLT:
            player.setBulletsPerShoot(2);
            break;
        case SILVER_BOLT:
            player.setBulletsPerShoot(3);
            break;
        case GOLD_BOLT:
            player.setBulletsPerShoot(3);
            player.setRotationSpeed(player.getRotationSpeed() * 2);
            player.setThrustRate(player.getThrustRate() * 2);
            break;
        }
    }
}
