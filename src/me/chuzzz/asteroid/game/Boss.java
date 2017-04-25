package me.chuzzz.asteroid.game;

import me.chuzzz.asteroid.core.Vector;

public class Boss extends Enemy {
    public Boss() {
        setScale(1.0f);
        setHp(100);
        setReward(5000);
    }

    @Override
    public String chooseEnemyType() {
        return "res/ships/spaceShips_007.png";
    }

    @Override
    public void shoot() {
        Bullet rbullet = new Bullet(BulletType.MISSILES_006);
        rbullet.setPosition(getPosition());
        rbullet.setOrigin(new Vector(getWidth() / 2 - 210, getHeight() / 2 - 300));
        rbullet.setRotation((float) (getRotation()));
        addBullet(rbullet);

        Bullet mbullet = new Bullet(BulletType.MISSILES_006);
        mbullet.setPosition(getPosition());
        mbullet.setOrigin(new Vector(getWidth() / 2 - 265, getHeight() / 2 - 300));
        mbullet.setRotation((float) (getRotation()));
        addBullet(mbullet);

        Bullet lbullet = new Bullet(BulletType.MISSILES_006);
        lbullet.setPosition(getPosition());
        lbullet.setOrigin(new Vector(getWidth() / 2 - 320, getHeight() / 2 - 300));
        lbullet.setRotation((float) (getRotation()));
        addBullet(lbullet);
    }

    @Override
    public boolean isBoss() {
        return true;
    }

}
