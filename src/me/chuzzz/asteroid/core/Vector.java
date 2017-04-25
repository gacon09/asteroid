package me.chuzzz.asteroid.core;

public class Vector {
    private float x;
    private float y;

    public Vector() {
        x = 0f;
        y = 0f;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector v) {
        return new Vector(this.x + v.getX(), this.y + v.getY());
    }

    public Vector multiply(float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector identity() {
        float length = length();
        return new Vector(this.x / length, this.y / length);
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float angle() {
        return (float) Math.acos(this.x / length());
    }

    public float getX() {
        return x;
    }

    public void setX(float d) {
        this.x = d;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
