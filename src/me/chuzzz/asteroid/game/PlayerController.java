package me.chuzzz.asteroid.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerController extends KeyAdapter {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            player.turnLeft(true);
            break;
        case KeyEvent.VK_RIGHT:
            player.turnRight(true);
            break;
        case KeyEvent.VK_UP:
            player.thrust(true);
            break;
        case KeyEvent.VK_SPACE:
            player.shoot();
            break;
        case KeyEvent.VK_S:
            player.useShield();
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            player.turnLeft(false);
            break;
        case KeyEvent.VK_RIGHT:
            player.turnRight(false);
            break;
        case KeyEvent.VK_UP:
            player.thrust(false);
            break;
        }
    }
}
