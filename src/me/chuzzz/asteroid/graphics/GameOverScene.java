package me.chuzzz.asteroid.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.InputEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;

import me.chuzzz.asteroid.game.Game;
import me.chuzzz.asteroid.game.World;
import me.chuzzz.asteroid.sound.SFXPlayer;

public class GameOverScene extends Scene {
    private static final long serialVersionUID = 1L;

    private GraphicsConfig gc;
    private BlinkEffect blink;
    private Game game;

    public GameOverScene(World world, Game game) {
        super(world);

        this.game = game;

        setOpaque(false);
        setFocusable(true);
        requestFocus();

        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        gc = new GraphicsConfig();
        gc.useKenneyFont();
        blink = new BlinkEffect(0.03f);

        addControlButtons();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawGameOver(g2d);
    }

    public void drawGameOver(Graphics2D g2d) {
        gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 40, Color.WHITE);
        blink.makeBlink(g2d);
        g2d.drawString("Game Over", (int) (Game.WIDTH * 0.5) - 150, (int) (Game.HEIGHT * 0.32));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        blink.reset(g2d);

        gc.setFont(g2d, GraphicsConfig.KENNEY_THIN_FONT, Font.ITALIC, 20, Color.WHITE);
        g2d.drawString("Assets Credit: http://kenney.nl", (int) (Game.WIDTH * 0.5) - 165, (int) (Game.HEIGHT * 0.92));
    }

    private void addControlButtons() {
        // Draw game over menu
        add(Box.createRigidArea(new Dimension(0, 272)));
        Button replayBtn = new Button(gc, "RePlay");
        replayBtn.setMaximumSize(replayBtn.getPreferredSize());
        replayBtn.setOnClickListener(new ButtonListener() {
            @Override
            public void onClick(InputEvent e) {
                game.play(null);
            }
        });
        add(replayBtn);
        add(Box.createRigidArea(new Dimension(0, 25)));

        Button highScoreBtn = new Button(gc, "High Score");
        highScoreBtn.setMaximumSize(highScoreBtn.getPreferredSize());
        add(highScoreBtn);
        add(Box.createRigidArea(new Dimension(0, 25)));

        Button exitBtn = new Button(gc, "Exit");
        exitBtn.setMaximumSize(replayBtn.getPreferredSize());
        exitBtn.setOnClickListener(new ButtonListener() {
            @Override
            public void onClick(InputEvent e) {
                game.stop();
            }
        });
        add(exitBtn);
    }
}
