package me.chuzzz.asteroid.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.World;

public class GreetingScene extends Scene implements KeyListener {
    private static final long serialVersionUID = 1L;

    private Game game;

    public GreetingScene(World world, Game game) {
        super(world);
        setOpaque(false);

        addKeyListener(this);

        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.clearRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);
        g2d.setColor(new Color(58, 46, 63));
        g2d.fillRect(0, 0, (int) Game.WIDTH, (int) Game.HEIGHT);

        world.draw(g2d);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.play(null);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
