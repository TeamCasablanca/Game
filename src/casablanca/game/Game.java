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

    private int lineResolution = display.HEIGHT / 10; //MAX is display.HEIGHT
    private int[] xLine = new int[lineResolution];
    private int[] yLine = new int[lineResolution];

    public Game(String name) {
        this.title = name;

    }

    // Get Direction of line drawing left or right.
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

        // Line init
        int i;
        this.yLine[0] = display.HEIGHT;
        this.xLine[0] = display.WIDTH / 2;
        for (i = 1; i < lineResolution; i++) {
            this.xLine[i] = this.xLine[i - 1] + getDir() * display.HEIGHT / lineResolution;
            this.yLine[i] = this.yLine[i - 1] - display.HEIGHT / lineResolution;
        }
    }

    private void tick() {
        int i;
        for (i = 0; i < lineResolution - 1; i++) {
            this.xLine[i] = this.xLine[i + 1];
        }
        this.xLine[i] = this.xLine[i - 1] + getDir() * display.HEIGHT / lineResolution;


    }

    private void render() {
        this.bs = this.display.getCanvas().getBufferStrategy();
        if (this.bs == null) {
            this.display.getCanvas().createBufferStrategy(2);
            return;
        }
        this.g = this.bs.getDrawGraphics();

        g.clearRect(0, 0, Display.WIDTH, Display.HEIGHT);
        //start drawing/animation;

        //  g.drawImage(ImageLoader.loadImage(""), 0, 0, null);
        g.setColor(Color.WHITE);
        //  g.drawLine(400, 0, 400, 600);
        g.drawOval(this.xLine[lineResolution/5]-16, this.yLine[lineResolution/5]-16, 32, 32);
        //  g.fillOval(388, 502, 28, 28);

        g.drawPolyline(this.xLine, this.yLine, lineResolution);
        //end drawing/animation;

        this.g.dispose();
        this.bs.show();
    }

    @Override
    public void run() {
        this.init();
        while (isRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
