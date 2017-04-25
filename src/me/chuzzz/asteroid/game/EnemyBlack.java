package me.chuzzz.asteroid.game;

import me.chuzzz.asteroid.core.MathUtils;
import me.chuzzz.asteroid.core.Vector;

public class EnemyBlack extends Enemy {
    private static final String[] enemiesAvailable = { "res/ships/enemyBlack1.png", "res/ships/enemyBlack2.png",
            "res/ships/enemyBlack3.png", "res/ships/enemyBlack4.png", "res/ships/enemyBlack5.png" };

    private int type;

    public EnemyBlack() {
        setHp(1);
    }

    @Override
    public String chooseEnemyType() {
        type = MathUtils.randomBetween(0, enemiesAvailable.length - 1);
        return enemiesAvailable[type];
    }

    @Override
    public Collectible dropItem() {
        int random = MathUtils.randomBetween(0, 100);
        if (random > getDropItemChance()) {
            return null;
        }

        Collectible item;
        if (random % 2 == 0) {
            item = new Heart();
        } else {
            item = new Bolt(Bolt.BRONZE_BOLT);
        }
        item.setPosition(new Vector(getPosition().getX() + 20, getPosition().getY() + 20));
        return item;
    }
}
