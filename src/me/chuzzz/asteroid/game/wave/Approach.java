package me.chuzzz.asteroid.game.wave;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.game.EntityFactory;
import me.chuzzz.asteroid.game.Asteroid;
import me.chuzzz.asteroid.game.Enemy;
import me.chuzzz.asteroid.game.Player;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;

public class Approach extends Wave {
    public static final String TITLE = "Approach";
    public static final String OBJECTIVE = "Kill the BOSS!";

    private int destroyedBoss;

    public Approach(World world) {
        super(world, TITLE, OBJECTIVE);

        destroyedBoss = 0;
    }

    @Override
    public List<Enemy> spawnEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();

        enemies.add(EntityFactory.spawnBoss());

        enemies.add(EntityFactory.spawnRanndomBlackEnemy());
        enemies.add(EntityFactory.spawnRanndomBlackEnemy());
        enemies.add(EntityFactory.spawnRanndomBlackEnemy());
        enemies.add(EntityFactory.spawnRanndomBlackEnemy());
        enemies.add(EntityFactory.spawnRanndomBlackEnemy());
        enemies.add(EntityFactory.spawnRanndomBlackEnemy());

        enemies.add(EntityFactory.spawnRanndomGreenEnemy());
        enemies.add(EntityFactory.spawnRanndomGreenEnemy());
        enemies.add(EntityFactory.spawnRanndomGreenEnemy());
        enemies.add(EntityFactory.spawnRanndomGreenEnemy());
        enemies.add(EntityFactory.spawnRanndomGreenEnemy());
        enemies.add(EntityFactory.spawnRanndomGreenEnemy());

        enemies.add(EntityFactory.spawnRanndomRedEnemy());
        enemies.add(EntityFactory.spawnRanndomRedEnemy());
        enemies.add(EntityFactory.spawnRanndomRedEnemy());
        enemies.add(EntityFactory.spawnRanndomRedEnemy());
        enemies.add(EntityFactory.spawnRanndomRedEnemy());
        enemies.add(EntityFactory.spawnRanndomRedEnemy());

        createSomeEnemiesRegularly();

        return enemies;
    }

    @Override
    public Player spawnPlayer() {
        Player player = new Player();
        return player;
    }

    @Override
    public void next(RealWorld world) {

    }

    @Override
    public List<Asteroid> createAsteroids() {
        List<Asteroid> asteroids = new ArrayList<>();

        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());
        asteroids.add(EntityFactory.createRandomAsteroid());

        return asteroids;
    }

    @Override
    public boolean objectiveCompleted() {
        return destroyedBoss == 1;
    }

    @Override
    public void drawObjectiveProgress(Graphics2D g2d, int x, int y) {
        g2d.drawString(Integer.toString(destroyedBoss), x, y);
    }

    @Override
    public void onBossDestroyed() {
        destroyedBoss++;
    }

    @Override
    public String getNextWave() {
        return null;
    }

    private void createSomeEnemiesRegularly() {
        Timer starsTimer = new Timer();
        starsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                addEnemy(EntityFactory.spawnRanndomRedEnemy());
                addEnemy(EntityFactory.spawnRanndomRedEnemy());
            }
        }, 4000, 5000);
    }
}
