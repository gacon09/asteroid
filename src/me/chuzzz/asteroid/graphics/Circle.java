package me.chuzzz.asteroid.graphics;

public class Circle {
    private float radius;
    private float x;
    private float y;

    public Circle(float radius, float x, float y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
