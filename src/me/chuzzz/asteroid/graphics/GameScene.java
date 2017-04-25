package me.chuzzz.asteroid.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.PlayerController;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;

public class GameScene extends Scene {
    private static final long serialVersionUID = 1L;

    private Color fillColor;

    public GameScene(World world) {
        super(world);

        fillColor = new Color(58, 46, 63);

        RealWorld realWorld = (RealWorld) (this.world);
        addKeyListener(new PlayerController(realWorld.getPlayer()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.clearRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
        g2d.setColor(fillColor);
        g2d.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        world.draw(g2d);

        g2d.dispose();
    }
}
