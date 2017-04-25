package me.chuzzz.asteroid.game;

public abstract class Effect extends Entity {
    public Effect() {
    }

    public Effect(String filename) {
        super(filename);
    }

    public boolean liveLongEnough() {
        return false;
    }
}
