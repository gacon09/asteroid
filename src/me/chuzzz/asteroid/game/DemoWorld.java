package me.chuzzz.asteroid.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.BlinkEffect;
import me.chuzzz.asteroid.graphics.GraphicsConfig;
import me.chuzzz.asteroid.graphics.Sprite;
import me.chuzzz.asteroid.sound.SFXPlayer;

public class DemoWorld extends World {
    private static final String THEME_SONG = "res/sounds/8-bit-loop.mp3";

    private GraphicsConfig gc;
    private Sprite demoShip;
    private Vector anchor;

    private String demoText;
    private String displayText;
    private int displayTextEndIdx;
    private Timer timer;
    private boolean playInform;
    private BlinkEffect blink;
    private SFXPlayer sfxPlayer;

    private List<Asteroid> asteroids;

    public DemoWorld(Game game) {
        super(game);
        gc = new GraphicsConfig();
        gc.useKenneyFont();

        anchor = new Vector(120, 252);
        demoShip = new Sprite("res/ships/playerShip3_red.png");
        demoShip.setPosition(anchor);
        demoShip.setScale(0.5f);

        demoText = "Welcome to Asteroid Clone!!!";
        displayText = "";
        displayTextEndIdx = 0;
        playInform = false;
        blink = new BlinkEffect();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayTextEndIdx = (displayTextEndIdx + 1) % (demoText.length() + 1);
                if (displayTextEndIdx == demoText.length()) {
                    playInform = true;
                    timer.cancel();
                }
            }
        }, 20, 150);

        asteroids = new ArrayList<Asteroid>();

        float maxRotationSpeed = 0.01f;
        float minRotationSpeed = -0.01f;

        Asteroid asteroid1 = new Asteroid("res/asteroids/meteorBrown_big1.png");
        asteroid1.setPosition(new Vector(60, 20));
        asteroid1.setRotationSpeed((float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed));
        asteroids.add(asteroid1);

        Asteroid asteroid2 = new Asteroid("res/asteroids/meteorGrey_big1.png");
        asteroid2.setPosition(new Vector(800, 50));
        asteroid2.setRotationSpeed((float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed));
        asteroids.add(asteroid2);

        Asteroid asteroid3 = new Asteroid("res/asteroids/meteorBrown_big4.png");
        asteroid3.setPosition(new Vector(680, 450));
        asteroid3.setRotationSpeed((float) (Math.random() * (maxRotationSpeed - minRotationSpeed) + minRotationSpeed));
        asteroids.add(asteroid3);

        sfxPlayer = new SFXPlayer();
        sfxPlayer.play(THEME_SONG, true);
    }

    @Override
    public void update() {
        super.update();

        if (!playInform) {
            demoShip.setPosition(demoShip.getPosition().add(new Vector(1.9f, 0)));
        }
        displayText = demoText.substring(0, displayTextEndIdx);

        for (Entity asteroid : asteroids) {
            asteroid.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);

        // Draw text
        gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 30, Color.WHITE);
        g2d.drawString(displayText, anchor.getX(), anchor.getY());

        // Draw ship
        AffineTransform af = AffineTransform.getTranslateInstance(demoShip.getPosition().getX(),
                demoShip.getPosition().getY() - 28);
        af.rotate(Math.PI / 2, demoShip.getWidth() / 2, demoShip.getHeight() / 2);
        af.scale(demoShip.getScale(), demoShip.getScale());
        g2d.drawImage(demoShip.getImage(), af, null);

        // Play inform
        if (playInform) {
            blink.makeBlink(g2d);
            gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 15, Color.WHITE);
            g2d.drawString("Press any key to start!", anchor.getX(), anchor.getY() + 50);
            blink.reset(g2d);
        }

        for (Entity asteroid : asteroids) {
            asteroid.draw(g2d);
        }
    }

    @Override
    public void createEffects(EffectsManager effectsManager) {
        for (int i = 0; i < 20; i++) {
            effectsManager.addRandomStar();
        }
    }

    @Override
    public void cleanUp() {
        sfxPlayer.stop();
    }
}
