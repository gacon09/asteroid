package me.chuzzz.asteroid.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import me.chuzzz.asteroid.sound.SFXPlayer;

public class Button extends JPanel implements MouseInputListener {
    private static final long serialVersionUID = 1L;
    private static final String BUTTON_FILE = "res/ui/buttonRed.png";

    private GraphicsConfig gc;
    private Image buttonImage;
    private Image cursor;
    private String text;

    private boolean hovering;
    private boolean clicking;

    private int width;
    private int height;

    private ButtonListener listener;

    public Button(GraphicsConfig gc, String text) {
        this.gc = gc;
        this.text = text;
        setOpaque(false);
        setFocusable(true);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);

        ImageIcon ii = new ImageIcon(BUTTON_FILE);
        buttonImage = ii.getImage();
        width = buttonImage.getWidth(null);
        height = buttonImage.getHeight(null);

        setPreferredSize(new Dimension(width, height));

        ImageIcon icon = new ImageIcon("res/ui/cursor_pointerFlat.png");
        cursor = icon.getImage();

        hovering = false;
        clicking = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (clicking) {
            gc.setAlpha(g2d, 0.8f);
        } else if (hovering) {
            gc.setAlpha(g2d, 0.95f);
        }

        g2d.drawImage(buttonImage, 0, 0, null);
        gc.reset(g2d);

        gc.setFont(g2d, GraphicsConfig.KENNEY_FONT, Font.BOLD, 20, Color.RED);
        drawCenteredString(g2d, text, getBounds(), g2d.getFont());
    }

    public void setOnClickListener(ButtonListener listener) {
        this.listener = listener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        listener.onClick(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        hovering = true;
        new SFXPlayer().play("res/sounds/rollover1.wav", false);
        setHandCursor();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        hovering = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clicking = true;
        new SFXPlayer().play("res/sounds/click1.wav", false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clicking = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void setHandCursor() {
        if (cursor.getHeight(null) > 0) {
            Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(cursor,
                    new Point(cursor.getWidth(null), cursor.getHeight(null)), "CUSTOM_HAND_CURSOR");
            setCursor(c);
        }
    }

    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g
     *            The Graphics instance.
     * @param text
     *            The String to draw.
     * @param rect
     *            The Rectangle to center the text in.
     */
    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as
        // in java 2d 0 is top of the screen)
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }
}
