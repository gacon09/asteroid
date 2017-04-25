package me.chuzzz.asteroid.sound;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SFXPlayer {
    public static final String BUMP = "res/sounds/bump.wav";
    public static final String LASER = "res/sounds/laser4.mp3";

    private MediaPlayer mediaPlayer;

    public void play(String filename, boolean loop) {
        Media sound = new Media(Paths.get(filename).toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
        if (loop) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }
        mediaPlayer.play();
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
