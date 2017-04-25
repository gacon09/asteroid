package me.chuzzz.asteroid.game;

public enum BulletType {
    PLAYER_BLUE_LASER("res/lasers/laserBlue01.png"), ENEMY_RED_LASER("res/lasers/laserRed16.png"), MISSILES_003(
            "res/lasers/spaceMissiles_003.png"), MISSILES_006("res/lasers/spaceMissiles_006.png"), ROCKET_001(
                    "res/lasers/spaceRockets_001.png"), ROCKET_002("res/lasers/spaceRockets_002.png");

    private final String filename;

    private BulletType(final String filename) {
        this.filename = filename;
    }

    public String filename() {
        return filename;
    }
}
