package com.test.game;

import com.test.IO.Input;
import com.test.display.Display;
import com.test.game.level.Level;
import com.test.graphics.Sprite;
import com.test.graphics.SpriteSheet;
import com.test.graphics.TextureAtlas;
import com.test.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String title = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;//miliseconds
    public static final String ATLAS_FILE_NAME = "texture_atlas.png";

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas atlas;
    private Player player;
    private Level level;

    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, title, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        level = new Level(atlas);
        player = new Player(300, 300, 2, 3, atlas, level);
    }

    public synchronized void start(){
        if (running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        if (!running) return;
        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        player.update(input);
        level.update();
    }

    public void render(){
        Display.clear();
        level.render(graphics);
        player.render(graphics);
        level.renderGrass(graphics);
        Display.swapBuffers();
    }

    public void run(){
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;
        float delta = 0;

        long lastTime = Time.get();
        while (running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;

            delta += ( elapsedTime / UPDATE_INTERVAL );

            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= Time.SECOND) {
                Display.setTitle(title + " || Fps: " + fps + " | Update: " + upd + " | UpdL: " + updl);
                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }
        }
    }

    public void cleanUp(){
        Display.destroy();
    }
}
