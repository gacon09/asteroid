package me.chuzzz.asteroid.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.core.MathUtils;
import me.chuzzz.asteroid.core.Vector;

public class EntityFactory {
    public static String[] meteors = { "res/asteroi     ds/meteorBrown_big1.png", "res/asteroids/meteorBrown_big3.png",
            "res/asteroids/meteorBrown_big4.png", "res/asteroids/meteorGrey_big1.png",
            "res/asteroids/meteorGrey_big2.png" };

    public static Enemy spawnRanndomBlackEnemy() {
        Enemy enemy = new EnemyBlack();

        float xRadius = (float) (Math.random() * Game.WIDTH);
        float yRadius = (float) (Math.random() * Game.HEIGHT);
        enemy.setRadius(new Vector(xRadius, yRadius));

        return enemy;
    }

    public static Enemy spawnRanndomGreenEnemy() {
        Enemy enemy = new EnemyGreen();

        float xRadius = (float) (Math.random() * Game.WIDTH);
        float yRadius = (float) (Math.random() * Game.HEIGHT);
        enemy.setRadius(new Vector(xRadius, yRadius));

        return enemy;
    }

    public static Enemy spawnRanndomRedEnemy() {
        Enemy enemy = new EnemyRed();

        float xRadius = (float) (Math.random() * Game.WIDTH);
        float yRadius = (float) (Math.random() * Game.HEIGHT);
        enemy.setRadius(new Vector(xRadius, yRadius));

        return enemy;
    }

    public static Enemy spawnBoss() {
        Enemy enemy = new Boss();

        float xRadius = (float) (Math.random() * Game.WIDTH);
        float yRadius = (float) (Math.random() * Game.HEIGHT);
        enemy.setRadius(new Vector(xRadius, yRadius));

        return enemy;
    }

    public static void spawnCircleEnemies(List<Enemy> enemies, Vector basePosition) {
        float angle = 0;
        for (int i = 0; i < 11; i++) {
            Enemy enemy = new EnemyBlack();
            enemy.setBasePosition(basePosition);
            enemy.setOrbit(120, 120, 0.02f, 0.02f, angle, angle);
            enemy.setShootPeriod(3000);
            enemies.add(enemy);

            angle += 20;
        }
    }

    public static Asteroid createRandomAsteroid() {
        int idx = MathUtils.randomBetween(0, 4);
        Asteroid asteroid = new Asteroid(meteors[idx]);
        float x = (float) (Math.random() * Game.WIDTH);
        float y = (float) (Math.random() * Game.HEIGHT);
        asteroid.setPosition(new Vector(x, y));

        float maxRotationSpeed = 0.01f;
        float minRotationSpeed = -0.01f;
        asteroid.setRotationSpeed((float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed));

        return asteroid;

    }

    public static Asteroid createBrokenAsteroidAt(Vector position) {
        int idx = MathUtils.randomBetween(0, 4);
        Asteroid asteroid = new Asteroid(meteors[idx]);
        asteroid.setPosition(position);

        float maxRotationSpeed = 0.07f;
        float minRotationSpeed = -0.07f;
        asteroid.setRotationSpeed((float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed));

        asteroid.setScale(0.2f);

        asteroid.setBroken(true);

        float xAccel = (float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed);
        float yAccel = (float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed);

        asteroid.accelerate(new Vector(xAccel, yAccel));

        return asteroid;
    }

    public static CollectibleStar createRandomStar() {
        CollectibleStar star = new CollectibleStar();
        float x = (float) (Math.random() * Game.WIDTH);
        float y = (float) (Math.random() * Game.HEIGHT);
        star.setPosition(new Vector(x, y));

        int liveTime = MathUtils.randomBetween(15000, 30000);
        star.setLiveTime(liveTime);

        return star;
    }
}
