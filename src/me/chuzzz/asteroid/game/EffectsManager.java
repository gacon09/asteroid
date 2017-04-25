package me.chuzzz.asteroid.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.chuzzz.asteroid.core.Vector;

public class EffectsManager {
    public static final String[] stars = { "res/effects/star1.png", "res/effects/star2.png", "res/effects/star3.png" };

    private List<Effect> effects;

    public EffectsManager() {
        effects = new ArrayList<Effect>();
    }

    public void playExplosion(Vector position) {
        Effect bumb = new Explosion();
        bumb.setPosition(position);
        effects.add(bumb);
    }

    public void addRandomStar() {
        int max = 2;
        int min = 0;
        int starIdx = (int) Math.floor(Math.random() * (max - min + 1) + min);

        Effect star = new Star(stars[starIdx]);
        float x = (float) (Math.random() * Game.WIDTH);
        float y = (float) (Math.random() * Game.HEIGHT);
        star.setPosition(new Vector(x, y));
        effects.add(star);
    }

    public void update() {
        // Iterate through all effects and update it
        Iterator<Effect> iter = effects.iterator();
        while (iter.hasNext()) {
            Effect effect = iter.next();

            effect.update();

            // Check if effect need to be removed
            if (effect.liveLongEnough()) {
                iter.remove();
            }
        }
    }

    public void draw(Graphics2D g2d) {
        for (int i = 0; i < effects.size(); i++) {
            Effect effect = effects.get(i);
            effect.draw(g2d);
        }
    }

    public void playSmallExplosion(Vector position) {
        Effect bumb = new SmallExplosion();
        bumb.setPosition(position);
        effects.add(bumb);
    }
}
