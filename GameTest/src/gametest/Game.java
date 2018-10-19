package gametest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

//Main game class (main class)
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1600, HEIGHT = 900;

    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler handler;
    private HUD hud;

    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH, HEIGHT, "Overwatch RPG Test", this);

        r = new Random();

        Entity genji = new Entity(WIDTH / 2 + 150, HEIGHT / 2 - 500, ID.Genji, 400, 400, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\genji_1.png", 200, 100, "Genji");
        Entity doomfist = new Entity(WIDTH / 2 + 250, HEIGHT / 2 - 100, ID.Doom, 200, 200, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\doom_1.png", 250, 200, "Doomfist");
        Entity mercy = new Entity(WIDTH / 2 + 400, HEIGHT / 2 - 300, ID.Mercy, 400, 400, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\mercy_1.png", 200, 150, "Mercy");
        Entity reinhardt = new Entity(WIDTH / 2 - 100, HEIGHT / 2 - 250, ID.Rein, 350, 350, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\characters\\rein_1.png", 500, 150, "Reinhardt");

        Menu menu = new Menu(WIDTH / 2 - 700, 1000, ID.Menu, 1400, 300, "C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\maps\\hud_1.png");
        //Menu background = new Menu(0,0,ID.Background,WIDTH,HEIGHT,"C:\\Users\\PRO_10\\Documents\\GitHub\\OverwatchRPG\\resources\\maps\\hanamura_1.png");
        Menu background = new Menu(0, 0, ID.Background, WIDTH, HEIGHT, null);

        genji.setHP(0);
        doomfist.setHP(0);
        mercy.setHP(0);
        reinhardt.setHP(0);
        
        genji.setMP(0);
        doomfist.setMP(0);
        mercy.setMP(0);
        reinhardt.setMP(0);

        ArrayList<Entity> party = new ArrayList();
        party.add(genji);
        party.add(doomfist);
        party.add(mercy);
        party.add(reinhardt);

        handler.addObject(background);
        handler.addObject(menu);

        for (int i = 0; i < party.size(); i++) {
            handler.addObject(party.get(i));
        }

        hud = new HUD(1200, 1000, party);

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        hud.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        hud.render(g);

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    public static void main(String[] args) {
        new Game();
    }

}
