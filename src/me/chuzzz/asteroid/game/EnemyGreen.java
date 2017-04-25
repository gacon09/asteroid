package me.chuzzz.asteroid.game;

import me.chuzzz.asteroid.core.MathUtils;
import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Sprite;

public class EnemyGreen extends Enemy {
    private static final String[] enemiesAvailable = { "res/ships/enemyGreen1.png", "res/ships/enemyGreen2.png",
            "res/ships/enemyGreen3.png", "res/ships/enemyGreen4.png", "res/ships/enemyGreen5.png" };

    private int type;

    public EnemyGreen() {
        setHp(2);
    }

    @Override
    public String chooseEnemyType() {
        type = MathUtils.randomBetween(0, enemiesAvailable.length - 1);
        return enemiesAvailable[type];
    }

    @Override
    public void decreaseStrength() {
        super.decreaseStrength();
        float scale = getScale();
        Vector position = getPosition();
        if (getHp() == 1) {
            setSprite(new Sprite("res/ships/enemyBlack" + Integer.toString(type + 1) + ".png"));
        }
        getSprite().setScale(scale);
        setPosition(position);
    }

    @Override
    public Collectible dropItem() {
        int random = MathUtils.randomBetween(0, 100);
        if (random > getDropItemChance()) {
            return null;
        }

        Collectible item;
        random = MathUtils.randomBetween(1, 90);
        if (random < 30) {
            item = new Shield();
        } else if (random >= 30 && random < 60) {
            item = new Bolt(Bolt.SILVER_BOLT);
        } else {
            item = new Heart();
        }
        item.setPosition(new Vector(getPosition().getX() + 20, getPosition().getY() + 20));
        return item;
    }
}
