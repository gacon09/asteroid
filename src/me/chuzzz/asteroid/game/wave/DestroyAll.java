package me.chuzzz.asteroid.game.wave;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.game.Asteroid;
import me.chuzzz.asteroid.game.Enemy;
import me.chuzzz.asteroid.game.EntityFactory;
import me.chuzzz.asteroid.game.Player;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;

public class DestroyAll extends Wave {
    public static final String TITLE = "Destroy All";
    public static final String OBJECTIVE = "Destroy at least 10 enemy's ship";
    private static final int OBJECTIVE_TO_COMPLETE = 10;

    private int enemyDestroyed;

    public DestroyAll(World world) {
        super(world, TITLE, OBJECTIVE);

        enemyDestroyed = 0;
    }

    @Override
    public List<Enemy> spawnEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();

        EntityFactory.spawnCircleEnemies(enemies, new Vector(200, 200));
        EntityFactory.spawnCircleEnemies(enemies, new Vector(600, 200));

        return enemies;
    }

    @Override
    public Player spawnPlayer() {
        Player player = new Player();
        return player;
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

        return asteroids;
    }

    @Override
    public void next(RealWorld world) {
        world.setWave(new Collector(world));
    }

    @Override
    public boolean objectiveCompleted() {
        if (enemyDestroyed == OBJECTIVE_TO_COMPLETE) {
            return true;
        }
        return false;
    }

    @Override
    public void drawObjectiveProgress(Graphics2D g2d, int x, int y) {
        g2d.drawString(Integer.toString(enemyDestroyed), x, y);
    }

    @Override
    public void onEnemyDestroyed() {
        enemyDestroyed++;
    }

    @Override
    public String getNextWave() {
        return Collector.TITLE;
    }
}
