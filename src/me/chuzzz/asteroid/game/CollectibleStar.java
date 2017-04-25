package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import me.chuzzz.asteroid.graphics.Sprite;

public class CollectibleStar extends Collectible {
    public CollectibleStar() {
        setSprite(new Sprite("res/powerup/star_gold.png"));
        setScale(0.7f);
    }

    @Override
    public void applyToPlayer(Player player) {

    }

}
