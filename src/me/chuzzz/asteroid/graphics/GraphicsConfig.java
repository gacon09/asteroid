package me.chuzzz.asteroid.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

public class GraphicsConfig {
    public static final String KENNEY_FONT = "KenVector Future";
    public static final String KENNEY_THIN_FONT = "KenVector Future Thin";

    private static final String KENNEY_FONT_FILE = "res/fonts/kenvector_future.ttf";
    private static final String KENNEY_THIN_FONT_FILE = "res/fonts/kenvector_future_thin.ttf";

    public void useKenneyFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(KENNEY_FONT_FILE)));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(KENNEY_THIN_FONT_FILE)));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setFont(Graphics2D g2d, String fontFamily, int fontWeight, int fontSize, Color color) {
        g2d.setFont(new Font(fontFamily, fontWeight, fontSize));
        g2d.setColor(color);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
    }

    public void setAlpha(Graphics2D g2d, float alpha) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void reset(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
    }
}
