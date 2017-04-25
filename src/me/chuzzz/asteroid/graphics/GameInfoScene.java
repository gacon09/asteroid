package me.chuzzz.asteroid.graphics;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.Player;
import me.chuzzz.asteroid.game.RealWorld;
import me.chuzzz.asteroid.game.World;

public class GameInfoScene extends Scene {
    private static final long serialVersionUID = 1L;

    private Image playerIcon;
    private Image xIcon;
    private Image shieldIcon;

    private GraphicsConfig gc;

    public GameInfoScene(World world) {
        super(world);
        setOpaque(false);
        setFocusable(true);
        setLayout(new FlowLayout());

        gc = new GraphicsConfig();
        gc.useKenneyFont();

        loadIcons();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawLife(g2d);
        drawPoint(g2d);
        drawShields(g2d);

        RealWorld rw = (RealWorld) world;
        if (rw.waitingForNextWave()) {
            gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 40, Color.WHITE);
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            String text = "";
            if (!rw.isWin()) {
                text = "Next wave: " + rw.getNextWave();
            } else {
                text = "You win!!!";
            }
            int x = (Game.WIDTH - metrics.stringWidth(text)) / 2;
            int y = ((Game.HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();
            g2d.drawString(text, x, y);
        }

        g2d.dispose();
    }

    private void drawShields(Graphics2D g2d) {
        Player player = ((RealWorld) world).getPlayer();
        int shieldsCount = player.getShieldsCount();
        if (shieldsCount > 0) {
            float x = (float) (Game.WIDTH * 0.5);
            float y = 8;
            AffineTransform af = AffineTransform.getTranslateInstance(x - 24, y);
            af.scale(0.6, 0.6);
            g2d.drawImage(shieldIcon, af, null);
            g2d.drawImage(xIcon, (int) x, (int) y, null);
            g2d.drawString(Integer.toString(shieldsCount), (int) (x + 25), (int) (y + 15));
        }
    }

    private void drawLife(Graphics2D g2d) {
        gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 25, Color.WHITE);

        Player player = ((RealWorld) world).getPlayer();
        int life = player.getLife();

        g2d.drawString(Integer.toString(life), 15, 25);

        g2d.drawImage(xIcon, 38, 8, null);

        AffineTransform af = AffineTransform.getTranslateInstance(62, 6);
        af.scale(0.8, 0.8);
        g2d.drawImage(playerIcon, af, null);
    }

    private void drawPoint(Graphics2D g2d) {
        gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 25, Color.WHITE);

        Player player = ((RealWorld) world).getPlayer();
        int point = player.getPoint();
        String pointToDraw = Integer.toString(point);

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        int x = Game.WIDTH - metrics.stringWidth(pointToDraw) - 20;

        g2d.drawString(pointToDraw, x, 25);
    }

    private void loadIcons() {
        ImageIcon ii = new ImageIcon("res/ui/playerLife1_blue.png");
        playerIcon = ii.getImage();

        ii = new ImageIcon("res/ui/numeralX.png");
        xIcon = ii.getImage();

        ii = new ImageIcon("res/powerup/shield_silver.png");
        shieldIcon = ii.getImage();
    }
}
