package display;

import javax.swing.*;
import java.awt.*;

public class Display extends Canvas {
    public static final int  WIDTH = 800;
    public static final int HEIGHT = 600;

    private String name;
    private JFrame frame;

    private Canvas canvas;

    public Display(String name){
        init(name);

    }

    public Canvas getCanvas() {

        return canvas;
    }

    private void init(String name){
        Dimension dimensions = new Dimension(WIDTH,HEIGHT);
        this.frame = new JFrame(name);
        this.frame.setSize(dimensions);
        this.frame.setFocusable(true);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        this.frame.setPreferredSize(dimensions);
        this.frame.setMaximumSize(dimensions);
        this.frame.setMinimumSize(dimensions);

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(dimensions);
        this.canvas.setMaximumSize(dimensions);
        this.canvas.setMinimumSize(dimensions);
        this.canvas.setVisible(true);
        this.canvas.setBackground(Color.BLUE);

        frame.add(canvas);
        frame.pack();
    }
}
