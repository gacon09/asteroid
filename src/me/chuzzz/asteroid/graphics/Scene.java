package me.chuzzz.asteroid.graphics;

import javax.swing.JPanel;

import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.World;

public class Scene extends JPanel {
    private static final long serialVersionUID = 1L;

    protected World world;

    public Scene(World world) {
        this.world = world;

        setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
    }
}
