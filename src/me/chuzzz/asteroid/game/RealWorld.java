package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;

import me.chuzzz.asteroid.game.wave.DestroyAll;
import me.chuzzz.asteroid.game.wave.Wave;
import me.chuzzz.asteroid.sound.SFXPlayer;

public class RealWorld extends World {
    private static final String THEME_SONG = "res/sounds/Anachronist_Oddities.mp3";

    private Wave wave;
    private boolean waitingForNextWave;
    private boolean win;
    private String nextWave;
    private long nextWaveStartIn;
    private long nextWaveCheckPoint;
    private SFXPlayer sfxPlayer;

    public RealWorld(Game game) {
        super(game);

        wave = new DestroyAll(this);

        waitingForNextWave = false;
        nextWaveStartIn = 3000;
        win = false;

        sfxPlayer = new SFXPlayer();
        sfxPlayer.play(THEME_SONG, true);
    }

    @Override
    public void update() {
        super.update();

        boolean gameOver = wave.update(effectsManager);
        if (game.getState() == GameState.RUNNING && gameOver) {
            sfxPlayer.stop();
            game.over();
        }

        if (waitingForNextWave && System.currentTimeMillis() - nextWaveCheckPoint >= nextWaveStartIn) {
            if (nextWave == null) {
                game.win();
            } else {
                sfxPlayer.play(THEME_SONG, true);
                wave.next(RealWorld.this);
                game.play(RealWorld.this);
            }
            waitingForNextWave = false;
            nextWaveCheckPoint = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);

        wave.draw(g2d);
    }

    @Override
    public void createEffects(EffectsManager effectsManager) {
        for (int i = 0; i < 20; i++) {
            effectsManager.addRandomStar();
        }
    }

    @Override
    public void nextWave() {
        nextWaveCheckPoint = System.currentTimeMillis();
        waitingForNextWave = true;
        nextWave = wave.getNextWave();

        if (nextWave == null) {
            win = true;
        }
    }

    public boolean waitingForNextWave() {
        return waitingForNextWave;
    }

    public String getNextWave() {
        return nextWave;
    }

    public void setWave(Wave wave) {
        this.wave = wave;
    }

    public Player getPlayer() {
        return wave.getPlayer();
    }

    public boolean isWin() {
        return win;
    }

    @Override
    public void cleanUp() {
        sfxPlayer.stop();
    }
}
