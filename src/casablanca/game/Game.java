package casablanca.game;

import display.Display;
import graphics.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game implements Runnable {
    private String title;
    private Thread thread;
    private Random dirChoose = new Random();


    private Display display;
    private BufferStrategy bs;
    private Graphics g;

    private boolean isRunning;

    private int lineSize = display.HEIGHT/5;
    private int[] xLine = new int[lineSize];
    private int[] yLine = new int[lineSize];

    public Game(String name) {
        this.title = name;

    }

    private int getDir() {
        int getDir = this.dirChoose.nextInt() % 2;
        if (getDir == 0) {
            getDir = -1;
        } else {
            getDir = 1;
        }
        return getDir;
    }

    private void init() {
        this.display = new Display(this.title);
        int i;
        this.yLine[0] = display.HEIGHT;
        this.xLine[0] = display.WIDTH / 2;
        for (i = 1; i < lineSize; i++) {
            this.xLine[i] = this.xLine[i - 1] + getDir();
            this.yLine[i] = this.yLine[i - 1] - display.HEIGHT/lineSize;

        }


    }

    private void tick() {
        int i;
        for (i = 0; i < lineSize - 1; i++) {
            this.xLine[i] = this.xLine[i + 1];
        }
        this.xLine[i] = this.xLine[i] = this.xLine[i - 1] + getDir();


    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();
        if (this.bs == null) {
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        this.g = this.bs.getDrawGraphics();

        g.clearRect(0, 0, Display.WIDTH, Display.HEIGHT);
        //  Random
        //start drawing/animation;


//        int[] xs = {25, 75, 125, 85, 125, 75, 25, 65};
//        int[] ys = {50, 90, 50, 100, 150, 110, 150, 100};
        g.drawImage(ImageLoader.loadImage(""), 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawLine(400, 0, 400, 600);
        g.drawOval(386, 500, 32, 32);
        g.fillOval(388, 502, 28, 28);

        g.drawPolyline(this.xLine, this.yLine, lineSize);
        //end drawing/animation;

        this.g.dispose();
        this.bs.show();
    }

    @Override
    public void run() {
        this.init();
        while (isRunning) {
            this.tick();
            this.render();
        }

        this.stop();
    }

    public synchronized void start() {
        this.thread = new Thread(this);

        this.isRunning = true;
        this.thread.start();
    }

    public synchronized void stop() {
        try {
            this.isRunning = false;
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
