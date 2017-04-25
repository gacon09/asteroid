package me.chuzzz.asteroid.game.wave;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.game.Asteroid;
import me.chuzzz.asteroid.game.CollectibleStar;
import me.chuzzz.asteroid.game.Enemy;
import me.chuzzz.asteroid.game.EntityFactory;
import me.chuzzz.asteroid.game.Player;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;

public class Collector extends Wave {
    public static final String TITLE = "Collector";
    public static final String OBJECTIVE = "Collect 10 stars.";
    private static final int OBJECTIVE_TO_COMPLETE = 10;

    private int starCollected;

    public Collector(World world) {
        super(world, TITLE, OBJECTIVE);

        starCollected = 0;
    }

    @Override
    public List<Enemy> spawnEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();
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
        world.setWave(new TimeWizard(world));
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

    public List<CollectibleStar> createCollectibleStars() {
        List<CollectibleStar> stars = new ArrayList<>();

        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());
        stars.add(EntityFactory.createRandomStar());

        createSomeStarsRegularly();

        return stars;
    }

    @Override
    public boolean objectiveCompleted() {
        return starCollected == OBJECTIVE_TO_COMPLETE;
    }

    @Override
    public void drawObjectiveProgress(Graphics2D g2d, int x, int y) {
        g2d.drawString(Integer.toString(starCollected), x, y);
    }

    @Override
    public void onStarCollected() {
        starCollected++;
    }

    @Override
    public String getNextWave() {
        return TimeWizard.TITLE;
    }

    private void createSomeStarsRegularly() {
        Timer starsTimer = new Timer();
        starsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                addStar(EntityFactory.createRandomStar());
            }
        }, 5000, 7000);
    }

    private void createSomeEnemiesRegularly() {
        Timer starsTimer = new Timer();
        starsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                addEnemy(EntityFactory.spawnRanndomGreenEnemy());
                addEnemy(EntityFactory.spawnRanndomGreenEnemy());
                addEnemy(EntityFactory.spawnRanndomGreenEnemy());
            }
        }, 4000, 5000);
    }
}
