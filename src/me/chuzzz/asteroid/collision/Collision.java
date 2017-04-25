package me.chuzzz.asteroid.collision;

import java.awt.Rectangle;

import me.chuzzz.asteroid.core.Vector;
import me.chuzzz.asteroid.graphics.Circle;

public class Collision {
    public static boolean isAABBCollideAABB(Rectangle r1, Rectangle r2) {
        return r1.getX() + r1.getWidth() >= r2.getX() && r1.getX() <= r2.getX() + r2.getWidth()
                && r1.getY() + r1.getHeight() >= r2.getY() && r1.getY() <= r2.getY() + r2.getHeight();
    }

    public static boolean isAABBCollideCircle(Rectangle aabb, Circle circle) {
        Vector circleDistance = new Vector();
        circleDistance.setX(Math.abs(circle.getX() - (float) aabb.getX()));
        circleDistance.setY(Math.abs(circle.getY() - (float) aabb.getY()));

        if (circleDistance.getX() > (aabb.getWidth() / 2 + circle.getRadius()))
            return false;
        if (circleDistance.getY() > (aabb.getHeight() / 2 + circle.getRadius()))
            return false;

        if (circleDistance.getX() <= (aabb.getWidth() / 2))
            return true;
        if (circleDistance.getY() <= (aabb.getHeight() / 2))
            return true;

        double cornerDistanceSq = Math.pow(circleDistance.getX() - aabb.getWidth(), 2)
                + Math.pow(circleDistance.getY() - aabb.getHeight(), 2);
        return cornerDistanceSq <= Math.pow(circle.getRadius(), 2);
    }
}
