package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;

import me.chuzzz.asteroid.game.wave.Wave;

public abstract class World {
    protected Game game;
    protected EffectsManager effectsManager;

    public World(Game game) {
        effectsManager = new EffectsManager();
        this.game = game;

        createEffects(effectsManager);
    }

    public void update() {
        effectsManager.update();
    }

    public void draw(Graphics2D g2d) {
        effectsManager.draw(g2d);
    }

    public void cleanUp() {
    }

    public void nextWave() {
    }

    public abstract void createEffects(EffectsManager effectsManager);
}
