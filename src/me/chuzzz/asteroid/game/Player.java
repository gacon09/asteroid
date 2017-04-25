package me.chuzzz.asteroid.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.BlinkEffect;
import me.chuzzz.asteroid.graphics.Circle;
import me.chuzzz.asteroid.graphics.Sprite;
import me.chuzzz.asteroid.sound.SFXPlayer;

public class Player extends Entity {
    public static final int RESPAWN_TIME = 2000;
    public static final int IMMORTAL_TIME = 2000;

    private Vector velocity;
    private Vector accel;
    private float friction;
    private int point;

    private Vector thrust;
    private float angle;
    private boolean turningLeft;
    private boolean turningRight;
    private boolean thrusting;
    private float rotationSpeed;
    private float thrustRate;

    private List<Bullet> bullets;
    private long lastFireTime;

    private boolean alive;
    private int life;

    private boolean immortal;
    private BlinkEffect blink;

    private List<Shield> shields;
    private Sprite shield;
    private boolean useShield;

    private int bulletsPerShoot;

    public Player() {
        super("res/playerShip1_blue.png");

        setPosition(new Vector(100, 100));
        setScale(0.5f);

        velocity = new Vector(0, 0);
        accel = new Vector(0, 0);
        friction = 0.998f;
        thrust = new Vector(0, 0);
        point = 0;
        bullets = new ArrayList<Bullet>();
        life = 3;
        blink = new BlinkEffect(0.06f);
        rotationSpeed = 0.02f;
        thrustRate = 0.02f;

        spawn(0);

        shields = new ArrayList<Shield>();
        useShield = false;
        shield = new Sprite("res/powerup/shield1.png");

        bulletsPerShoot = 1;
    }

    @Override
    public void update() {
        if (this.turningLeft) {
            angle -= rotationSpeed;
        }
        if (this.turningRight) {
            angle += rotationSpeed;
        }

        if (thrusting) {
            float x = (float) (thrustRate * Math.cos(angle));
            float y = (float) (thrustRate * Math.sin(angle));
            thrust.setX(x);
            thrust.setY(y);
        } else {
            thrust = new Vector(0, 0);
        }

        accelerate(thrust);

        setPosition(getPosition().add(velocity));

        velocity = velocity.multiply(friction);

        if (getPosition().getX() + getWidth() < 0) {
            getPosition().setX(Game.WIDTH);
        }
        if (getPosition().getX() > Game.WIDTH) {
            getPosition().setX(0);
        }
        if (getPosition().getY() + getHeight() < 0) {
            getPosition().setY(Game.HEIGHT);
        }
        if (getPosition().getY() > Game.HEIGHT) {
            getPosition().setY(0);
        }

        if (useShield) {
            shield.setPosition(new Vector(getPosition().getX(), getPosition().getY()));
        }
    }

    public void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = AffineTransform.getTranslateInstance(getPosition().getX(), getPosition().getY());
        af.rotate(angle + Math.PI / 2);
        af.scale(getScale(), getScale());

        if (immortal) {
            blink.makeBlink(g2d);
        }

        g2d.drawImage(getImage(), af, null);
        blink.reset(g2d);

        for (Bullet bullet : bullets) {
            bullet.draw(g2d);
        }

        if (useShield) {
            AffineTransform saf = AffineTransform.getTranslateInstance(shield.getPosition().getX(),
                    shield.getPosition().getY());
            saf.rotate(angle + Math.PI / 2);
            saf.scale(shield.getScale(), shield.getScale());
            saf.translate(-40, -28);
            g2d.drawImage(shield.getImage(), saf, null);
        }
    }

    public void shoot() {
        if (alive) {
            long dt = System.currentTimeMillis() - lastFireTime;
            if (dt >= 100) {
                if (bulletsPerShoot == 1) {
                    Bullet bullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    bullet.setPosition(getPosition());
                    bullet.setOrigin(new Vector(getWidth() / 2 + 20, getHeight() / 2));
                    bullet.setRotation(angle);
                    bullets.add(bullet);
                } else if (bulletsPerShoot == 2) {
                    Bullet lbullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    lbullet.setPosition(getPosition());
                    lbullet.setOrigin(new Vector(getWidth() / 2 - 20, getHeight() / 2));
                    lbullet.setRotation(angle);
                    bullets.add(lbullet);

                    Bullet rbullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    rbullet.setPosition(getPosition());
                    rbullet.setOrigin(new Vector(getWidth() / 2 + 62, getHeight() / 2));
                    rbullet.setRotation(angle);
                    bullets.add(rbullet);
                } else if (bulletsPerShoot == 3) {
                    Bullet lbullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    lbullet.setPosition(getPosition());
                    lbullet.setOrigin(new Vector(getWidth() / 2 - 20, getHeight() / 2));
                    lbullet.setRotation(angle);
                    bullets.add(lbullet);

                    Bullet mbullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    mbullet.setPosition(getPosition());
                    mbullet.setOrigin(new Vector(getWidth() / 2 + 20, getHeight() / 2));
                    mbullet.setRotation(angle);
                    bullets.add(mbullet);

                    Bullet rbullet = new Bullet(BulletType.PLAYER_BLUE_LASER);
                    rbullet.setPosition(getPosition());
                    rbullet.setOrigin(new Vector(getWidth() / 2 + 62, getHeight() / 2));
                    rbullet.setRotation(angle);
                    bullets.add(rbullet);
                }

                lastFireTime = System.currentTimeMillis();

                new SFXPlayer().play(SFXPlayer.LASER, false);
            }
        }
    }

    public void useShield() {
        if (getShieldsCount() > 0) {
            useShield = true;
            shields.remove(0);
        }
    }

    public void destroyShield() {
        useShield = false;
    }

    public boolean hasShield() {
        return useShield;
    }

    public void accelerate(Vector accel) {
        this.accel = accel;
        velocity = velocity.add(this.accel);
    }

    public void turnLeft(boolean turningLeft) {
        this.turningLeft = turningLeft;
    }

    public void turnRight(boolean turningRight) {
        this.turningRight = turningRight;
    }

    public void thrust(boolean thrusting) {
        this.thrusting = thrusting;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void destroy() {
        alive = false;
        life = life - 1;

        if (life <= 0) {
            life = 0;
        }
    }

    public boolean alive() {
        return alive;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Shield> getShields() {
        return shields;
    }

    public int getShieldsCount() {
        return shields.size();
    }

    public Sprite getShield() {
        return shield;
    }

    public void equipShield(Shield shield) {
        shields.add(shield);
    }

    public void setBulletsPerShoot(int bulletsPerShoot) {
        this.bulletsPerShoot = bulletsPerShoot;
    }

    public int getBulletsPerShoot() {
        return bulletsPerShoot;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getThrustRate() {
        return thrustRate;
    }

    public void setThrustRate(float thrustRate) {
        this.thrustRate = thrustRate;
    }

    public synchronized void spawn(int spawnTime) {
        Timer timer = new Timer("Respawn", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                alive = true;
                immortal = true;
                useShield = false;
                rotationSpeed = 0.02f;
                thrustRate = 0.02f;

                startImmortalTimer();
            }
        }, spawnTime);
    }

    public synchronized void startImmortalTimer() {
        Timer timer = new Timer("Immortal", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                immortal = false;
            }
        }, IMMORTAL_TIME);
    }
}
