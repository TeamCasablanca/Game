package casablanca.game;

import display.Display;
import graphics.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private String title;
    private Thread thread;

    private Display display;
    private BufferStrategy bs;
    private Graphics g;

    private boolean isRunning;

    public Game(String name) {
        this.title = name;

    }

    private void init(){
        this.display = new Display(this.title);
    }

    private void tick(){

    }

     private void render(){
         this.bs = this.display.getCanvas().getBufferStrategy();
         if (this.bs== null){
             this.display.getCanvas().createBufferStrategy(2);
             return;
         }
        this.g = this.bs.getDrawGraphics();

         g.clearRect(0,0, Display.WIDTH, Display.HEIGHT);

         //start drawing/animation;

          g.drawImage(ImageLoader.loadImage(""),0,0,null);
         g.setColor(Color.WHITE);
         g.drawLine(400,0,400,600);
         g.drawOval(386,500,32,32);
         g.fillOval(388,502,28,28);
         //end drawing/animation;

         this.g.dispose();
         this.bs.show();
    }

    @Override
    public void run() {
        this.init();
        while (isRunning){
            this.tick();
            this.render();
        }

        this.stop();
    }

    public synchronized void start(){
        this.thread = new Thread(this);

        this.isRunning = true;
        this.thread.start();
    }

    public synchronized void stop(){
        try {
            this.isRunning = false;
            this.thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
