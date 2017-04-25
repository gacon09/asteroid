package me.chuzzz.asteroid.game.wave;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import me.chuzzz.asteroid.collision.Collision;
import me.chuzzz.asteroid.core.MathUtils;
import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.game.Asteroid;
import me.chuzzz.asteroid.game.Bolt;
import me.chuzzz.asteroid.game.Bullet;
import me.chuzzz.asteroid.game.Collectible;
import me.chuzzz.asteroid.game.CollectibleStar;
import me.chuzzz.asteroid.game.EffectsManager;
import me.chuzzz.asteroid.game.Enemy;
import me.chuzzz.asteroid.game.EntityFactory;
import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.Player;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;
import me.chuzzz.asteroid.graphics.GraphicsConfig;
import me.chuzzz.asteroid.sound.SFXPlayer;

public abstract class Wave {
    private String title;
    private String objective;
    private String displayObjective;
    private int objectiveIdx;
    private Timer objectiveTimer;
    private boolean start;
    private Image doneCheckmark;

    private Player player;
    private List<Enemy> enemies;
    private List<Collectible> items;
    private List<Asteroid> asteroids;
    private List<CollectibleStar> stars;
    private boolean waitingForNextWave;

    private GraphicsConfig gc;
    private World world;
    private SFXPlayer sfxPlayer;

    public Wave(World world, String title, String objective) {
        this.world = world;

        this.title = title;
        this.objective = objective;
        displayObjective = "";
        objectiveIdx = 0;
        objectiveTimer = new Timer();
        start = false;
        objectiveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                objectiveIdx = (objectiveIdx + 1) % (objective.length() + 1);
                if (objectiveIdx == objective.length()) {
                    start = true;
                    sfxPlayer.stop();
                    objectiveTimer.cancel();
                }
            }
        }, 20, 100);

        enemies = spawnEnemies();
        player = spawnPlayer();

        gc = new GraphicsConfig();
        gc.useKenneyFont();
        sfxPlayer = new SFXPlayer();
        sfxPlayer.play("res/sounds/typing.mp3", false);
        ImageIcon ii = new ImageIcon("res/ui/green_checkmark.png");
        doneCheckmark = ii.getImage();

        items = new ArrayList<Collectible>();
        Collectible h = new Bolt(Bolt.GOLD_BOLT);
        h.setPosition(new Vector(150, 100));
        items.add(h);

        asteroids = createAsteroids();
        stars = createCollectibleStars();
    }

    public abstract void next(RealWorld world);

    public abstract List<Enemy> spawnEnemies();

    public abstract Player spawnPlayer();

    public abstract List<Asteroid> createAsteroids();

    public abstract boolean objectiveCompleted();

    public abstract void drawObjectiveProgress(Graphics2D g2d, int x, int y);

    public abstract String getNextWave();

    public void onBossDestroyed() {
    };

    public void onEnemyDestroyed() {
    };

    public void onStarCollected() {
    };

    public List<CollectibleStar> createCollectibleStars() {
        return new ArrayList<CollectibleStar>();
    }

    public boolean update(EffectsManager effectsManager) {
        displayObjective = objective.substring(0, objectiveIdx);

        updatePlayer(effectsManager);
        updateEnemies(effectsManager);
        updateItems();
        updateAsteroids(effectsManager);
        updateStars();

        boolean gameOver = false;
        if (player.getLife() == 0) {
            gameOver = true;
        }

        return gameOver;
    }

    public void draw(Graphics2D g2d) {
        drawAsteroids(g2d);

        if (player.alive()) {
            player.draw(g2d);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }

        drawItems(g2d);
        drawStars(g2d);

        drawObjective(g2d);
    }

    public void updateStars() {
        List<CollectibleStar> starsToRemove = new ArrayList<CollectibleStar>();
        for (int i = 0; i < stars.size(); i++) {
            CollectibleStar star = stars.get(i);
            star.update();
            if (star.liveLongEnough()) {
                starsToRemove.add(star);
            } else if (Collision.isAABBCollideAABB(star.getSprite().getBounds(), player.getSprite().getBounds())) {
                starsToRemove.add(star);
                new SFXPlayer().play("res/powerup/powerUp9.mp3", false);

                onStarCollected();
            }
        }
        stars.removeAll(starsToRemove);
    }

    public void drawStars(Graphics2D g2d) {
        for (int i = 0; i < stars.size(); i++) {
            CollectibleStar star = stars.get(i);
            star.draw(g2d);
        }
    }

    public void updateAsteroids(EffectsManager effectsManager) {
        List<Asteroid> asteroidsToRemove = new ArrayList<Asteroid>();
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.update();

            if (!player.isImmortal()
                    && Collision.isAABBCollideAABB(asteroid.getSprite().getBounds(), player.getSprite().getBounds())) {
                asteroidsToRemove.add(asteroid);
                player.destroy();
                effectsManager.playExplosion(player.getPosition());
                new SFXPlayer().play(SFXPlayer.BUMP, false);
            }
        }
        asteroids.removeAll(asteroidsToRemove);

        List<Bullet> bullets = player.getBullets();
        asteroidsToRemove = new ArrayList<Asteroid>();
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                if (Collision.isAABBCollideAABB(asteroid.getSprite().getBounds(), bullet.getSprite().getBounds())) {
                    if (!asteroid.isBroken()) {
                        asteroids.add(EntityFactory.createBrokenAsteroidAt(asteroid.getPosition()));
                        asteroids.add(EntityFactory.createBrokenAsteroidAt(asteroid.getPosition()));
                        int ran = MathUtils.randomBetween(0, 100);
                        if (ran % 2 == 0) {
                            asteroids.add(EntityFactory.createBrokenAsteroidAt(asteroid.getPosition()));
                        }
                    }
                    asteroidsToRemove.add(asteroid);
                    effectsManager.playExplosion(asteroid.getPosition());
                    bulletsToRemove.add(bullet);

                    new SFXPlayer().play(SFXPlayer.BUMP, false);
                    break;
                }
            }
            bullets.removeAll(bulletsToRemove);
        }
        asteroids.removeAll(asteroidsToRemove);
    }

    public void drawAsteroids(Graphics2D g2d) {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.draw(g2d);
        }
    }

    public void updateItems() {
        List<Collectible> itemsToRemove = new ArrayList<Collectible>();
        for (int i = 0; i < items.size(); i++) {
            Collectible item = items.get(i);
            if (item.liveLongEnough()) {
                itemsToRemove.add(item);
            } else {
                item.update();
                if (Collision.isAABBCollideAABB(player.getSprite().getBounds(), item.getSprite().getBounds())) {
                    itemsToRemove.add(item);
                    item.applyToPlayer(player);
                    sfxPlayer.play("res/powerup/powerUp9.mp3", false);
                }
            }
        }
        items.removeAll(itemsToRemove);
    }

    public void drawItems(Graphics2D g2d) {
        for (int i = 0; i < items.size(); i++) {
            Collectible item = items.get(i);
            item.draw(g2d);
        }
    }

    public void drawObjective(Graphics2D g2d) {
        int x = 25;
        int y = Game.HEIGHT - 50;
        gc.setFont(g2d, GraphicsConfig.KENNEY_THIN_FONT, Font.PLAIN, 12, Color.WHITE);
        g2d.drawString(displayObjective, x, y);
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int objectiveWidth = metrics.stringWidth(objective);
        if (start) {
            g2d.drawString("  ( " + "  )", x + objectiveWidth, y);
            gc.setFont(g2d, GraphicsConfig.KENNEY_THIN_FONT, Font.PLAIN, 12, Color.GREEN);
            drawObjectiveProgress(g2d, x + objectiveWidth + 12, y);
        }
        if (objectiveCompleted()) {
            AffineTransform af = AffineTransform.getTranslateInstance(x - 20, y - doneCheckmark.getHeight(null) * 0.7);
            af.scale(0.8, 0.8);
            g2d.drawImage(doneCheckmark, af, null);
        }
    }

    public void updatePlayer(EffectsManager effectsManager) {
        if (objectiveCompleted() && !waitingForNextWave) {
            world.nextWave();
            waitingForNextWave = true;
        }

        updatePlayerBullets(effectsManager);

        if (player.alive()) {
            player.update();
        }

        // Check if player is collide with enemies
        if (player.alive() && !player.isImmortal()) {
            List<Enemy> enemiesToRemove = new ArrayList<Enemy>();
            for (Enemy enemy : enemies) {
                if (player.hasShield() && Collision.isAABBCollideCircle(enemy.getSprite().getBounds(),
                        player.getShield().getCircleBounds())) {
                    enemiesToRemove.add(enemy);
                    effectsManager.playExplosion(enemy.getPosition());
                    player.destroyShield();
                } else if (checkCollideWithPlayer(enemy)) {
                    if (!enemy.isBoss()) {
                        enemiesToRemove.add(enemy);
                        effectsManager.playExplosion(enemy.getPosition());
                    }
                    player.destroy();
                    effectsManager.playExplosion(player.getPosition());
                    new SFXPlayer().play(SFXPlayer.BUMP, false);
                }
            }
            enemies.removeAll(enemiesToRemove);
        }

        if (!player.alive() && player.getLife() > 0) {
            player.spawn(Player.RESPAWN_TIME);
        }
    }

    public void updateEnemies(EffectsManager effectsManager) {
        for (Enemy enemy : enemies) {
            enemy.update();

            if (player.alive()) {
                updateEnemyBullets(enemy.getBullets(), effectsManager);
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    private void updatePlayerBullets(EffectsManager effectsManager) {
        List<Bullet> bullets = player.getBullets();

        List<Bullet> playerBulletsToRemove = new ArrayList<Bullet>();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet playerBullet = bullets.get(i);

            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                List<Bullet> enemyBullets = enemy.getBullets();
                List<Bullet> enemyBulletsToRemove = new ArrayList<Bullet>();
                for (int k = 0; k < enemyBullets.size(); k++) {
                    Bullet enemyBullet = enemyBullets.get(k);

                    if (Collision.isAABBCollideAABB(playerBullet.getSprite().getBounds(),
                            enemyBullet.getSprite().getBounds())) {
                        playerBulletsToRemove.add(playerBullet);
                        enemyBulletsToRemove.add(enemyBullet);
                    }
                }
                enemyBullets.removeAll(enemyBulletsToRemove);
            }
        }
        bullets.removeAll(playerBulletsToRemove);

        List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (isInvisibleBullet(bullet)) {
                bulletsToRemove.add(bullet);
            } else {
                boolean isCollide = false;
                // Check if bullet is collide with enemies
                List<Enemy> enemiesToRemove = new ArrayList<Enemy>();
                for (Enemy enemy : enemies) {
                    isCollide = Collision.isAABBCollideAABB(bullet.getSprite().getBounds(),
                            enemy.getSprite().getBounds());
                    if (isCollide) {
                        enemy.decreaseStrength();
                        if (enemy.getHp() <= 0) {
                            // Enemy destroyed
                            enemiesToRemove.add(enemy);
                            // Update player's points
                            player.setPoint(player.getPoint() + enemy.getReward());
                            onEnemyDestroyed();

                            Collectible dropItem = enemy.dropItem();
                            if (dropItem != null) {
                                items.add(dropItem);
                            }

                            if (enemy.isBoss()) {
                                onBossDestroyed();
                            }

                            effectsManager.playExplosion(enemy.getPosition());
                            new SFXPlayer().play(SFXPlayer.BUMP, false);
                        } else {
                            effectsManager.playSmallExplosion(enemy.getPosition());

                        }
                        bulletsToRemove.add(bullet);

                        new SFXPlayer().play(SFXPlayer.BUMP, false);

                        break;
                    }
                }
                enemies.removeAll(enemiesToRemove);
            }
        }
        bullets.removeAll(bulletsToRemove);

        player.updateBullets();
    }

    private void updateEnemyBullets(List<Bullet> bullets, EffectsManager effectsManager) {
        List<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (isInvisibleBullet(bullet)) {
                bulletsToRemove.add(bullet);
            } else {
                if (player.hasShield() && Collision.isAABBCollideCircle(bullet.getSprite().getBounds(),
                        player.getShield().getCircleBounds())) {
                    bulletsToRemove.add(bullet);
                    effectsManager.playSmallExplosion(bullet.getPosition());
                    player.destroyShield();
                    new SFXPlayer().play(SFXPlayer.BUMP, false);
                } else {
                    if (!player.isImmortal()) {
                        boolean isCollideWithPlayer = Collision.isAABBCollideAABB(player.getSprite().getBounds(),
                                bullet.getSprite().getBounds());
                        if (isCollideWithPlayer) {
                            player.destroy();
                            bulletsToRemove.add(bullet);

                            effectsManager.playExplosion(player.getPosition());
                            new SFXPlayer().play(SFXPlayer.BUMP, false);
                        }
                    }

                }

            }
        }
        bullets.removeAll(bulletsToRemove);
    }

    public void addStar(CollectibleStar star) {
        stars.add(star);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    private boolean isInvisibleBullet(Bullet bullet) {
        boolean isInvisible = false;

        if (bullet.getPosition().getX() < 0 || bullet.getPosition().getX() > Game.WIDTH
                || bullet.getPosition().getY() < 0 || bullet.getPosition().getY() > Game.HEIGHT) {
            isInvisible = true;
        }

        return isInvisible;
    }

    private boolean checkCollideWithPlayer(Enemy enemy) {
        return Collision.isAABBCollideAABB(player.getSprite().getBounds(), enemy.getSprite().getBounds());
    }
}
