package me.chuzzz.asteroid.game;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import javafx.embed.swing.JFXPanel;
import me.chuzzz.asteroid.graphics.GameInfoScene;
import me.chuzzz.asteroid.graphics.GameOverScene;
import me.chuzzz.asteroid.graphics.GameScene;
import me.chuzzz.asteroid.graphics.GreetingScene;
import me.chuzzz.asteroid.graphics.Scene;
import me.chuzzz.asteroid.sound.SFXPlayer;

public class Game implements Runnable {
    public static final String TITLE = "Asteroid";
    public static final int WIDTH = 1024;
    public static final int HEIGHT = WIDTH / 16 * 10;

    private static final int GREETING_SCENE_INDEX = 0;
    private static final int GAME_SCENE_INDEX = 1;
    private static final int GAME_INFO_SCENE_INDEX = 2;
    private static final int GAME_OVER_SCENE_INDEX = 3;

    private JFrame frame;
    private JLayeredPane sceneStack;
    private Scene greetingScene;
    private Thread thread;

    private boolean running;
    private GameState currentState;
    private World world;

    private SFXPlayer sfxPlayer;

    public Game() {
        running = false;
        currentState = GameState.GREETING;

        initGraphics();

        sfxPlayer = new SFXPlayer();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        world.cleanUp();
        running = false;
    }

    public void play(World w) {
        currentState = GameState.RUNNING;

        sceneStack.removeAll();

        world.cleanUp();

        if (w == null) {
            world = new RealWorld(this);
        } else {
            world = w;
        }

        GameScene gameScene = new GameScene(world);
        sceneStack.add(gameScene, GAME_SCENE_INDEX);
        gameScene.setFocusable(true);
        gameScene.requestFocus();

        GameInfoScene infoScene = new GameInfoScene(world);
        sceneStack.add(infoScene, new Integer(GAME_INFO_SCENE_INDEX));

        sceneStack.revalidate();
        sceneStack.repaint();

        sfxPlayer.stop();
    }

    public synchronized void over() {
        currentState = GameState.OVER;
        world.cleanUp();

        GameOverScene gameOverScene = new GameOverScene(world, this);
        sceneStack.add(gameOverScene, new Integer(GAME_OVER_SCENE_INDEX));

        sfxPlayer.stop();
        sfxPlayer.play("res/sounds/Soliloquy.mp3", false);
    }

    public void exit() {
        frame.dispose();
        sfxPlayer.stop();
    }

    public GameState getState() {
        return currentState;
    }

    public void win() {
        currentState = GameState.GREETING;
        sceneStack.removeAll();

        world = new DemoWorld(this);
        greetingScene = new GreetingScene(world, this);
        sceneStack.add(greetingScene, new Integer(GREETING_SCENE_INDEX));
        greetingScene.setFocusable(true);
        greetingScene.requestFocus();

        sceneStack.revalidate();
        sceneStack.repaint();
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (currentState) {
            case GREETING:
                world.update();
                sceneStack.repaint();
                break;
            case PAUSE:
                break;
            case RUNNING:
                world.update();
                sceneStack.repaint();
                break;
            case OVER:
                world.update();
                sceneStack.repaint();
                break;
            default:
                sceneStack.repaint();
            }
        }

        exit();
    }

    private void initGraphics() {
        frame = new JFrame(TITLE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        sceneStack = new JLayeredPane();
        sceneStack.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        world = new DemoWorld(this);
        greetingScene = new GreetingScene(world, this);
        greetingScene.setFocusable(true);
        sceneStack.add(greetingScene, new Integer(GREETING_SCENE_INDEX));

        frame.add(sceneStack);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFXPanel(); // Initialize JavaFX environment
                Game asteroid = new Game();
                asteroid.start();
            }
        });
    }
}
