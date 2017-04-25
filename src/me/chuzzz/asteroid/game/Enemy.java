package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Sprite;

public abstract class Enemy extends Entity {
    private float rotation;
    private Vector prevPos;
    private Vector basePos;
    private Vector radius;
    private float xAngle;
    private float yAngle;
    private float xSpeed;
    private float ySpeed;

    private Timer timer;

    private int reward;

    private List<Bullet> bullets;
    private int shootPeriod;

    private int hp;

    private int dropItemChance;

    public Enemy() {
        setSprite(new Sprite(chooseEnemyType()));

        setScale(0.3f);
        setPosition(new Vector(0, 0));

        rotation = (float) (Math.PI / 2);
        prevPos = new Vector(0, 0);
        basePos = new Vector(0, 0);
        reward = 50;

        radius = new Vector(200, 200);
        xAngle = 0;
        yAngle = 0;
        xSpeed = -0.01f;
        ySpeed = 0.004f;

        bullets = new ArrayList<Bullet>();
        shootPeriod = 1000;

        startBulletTimer();

        hp = 1;
        dropItemChance = 80;
    }

    public abstract String chooseEnemyType();

    public void decreaseStrength() {
        hp = hp - 1;
    }

    public Collectible dropItem() {
        return null;
    }

    public void setBasePosition(Vector basePos) {
        this.basePos = basePos;
    }

    @Override
    public void update() {
        // Lissajous curve, update ship's position
        float x = basePos.getX() + radius.getX() + (float) (radius.getX() * Math.cos(xAngle));
        float y = basePos.getY() + radius.getY() + (float) (radius.getY() * Math.sin(yAngle));
        setPosition(new Vector(x, y));
        xAngle += xSpeed;
        yAngle += ySpeed;

        // Rotate the ship, update ship's rotation angle
        float dx = getPosition().getX() - prevPos.getX();
        float dy = getPosition().getY() - prevPos.getY();
        rotation = (float) Math.atan2(dy, dx);
        prevPos = getPosition();

        // Can not use edge handling...

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.update();
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.rotate(-Math.PI / 2 + rotation);
        af.scale(getScale(), getScale());

        g2d.drawImage(getImage(), af, null);

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.draw(g2d);
        }
    }

    private synchronized void startBulletTimer() {
        timer = new Timer("Enemy Shoot", true);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                shoot();
            }
        }, shootPeriod, shootPeriod);
    }

    public void shoot() {
        Bullet bullet = new Bullet(BulletType.ENEMY_RED_LASER);
        bullet.setPosition(getPosition());
        bullet.setOrigin(new Vector(getWidth() / 2 - 50, getHeight() / 2 - 50));
        bullet.setRotation((float) (rotation));
        bullets.add(bullet);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void setRadius(Vector radius) {
        this.radius = radius;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public float getRotation() {
        return rotation;
    }

    public int getDropItemChance() {
        return dropItemChance;
    }

    public void setDropItemChance(int dropItemChance) {
        this.dropItemChance = dropItemChance;
    }

    public void setOrbit(float xRadius, float yRadius, float xSpeed, float ySpeed, float xAngle, float yAngle) {
        this.radius = new Vector(xRadius, yRadius);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xAngle = xAngle;
        this.yAngle = yAngle;
    }

    public void setShootPeriod(int shootPeriod) {
        this.shootPeriod = shootPeriod;
    }

    public boolean isBoss() {
        return false;
    }
}
