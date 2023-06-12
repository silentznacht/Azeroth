package Display;

// IMPORTS
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Display.Graphics.Render;
import Display.Graphics.Screen;
import javax.swing.plaf.DimensionUIResource;
import java.awt.Dimension;


public class Display extends Canvas implements Runnable { // creates a window
    
    private static final long serialVersionUID = 1L;
    // Title
        public static final String TITLE = "Azeroth";
    // Thread
        private Thread thread; // A thread is a thread of execution in a program, it allows multiple threads to take place.
        private boolean running = false; // counter, for when programming is running or not
    // Screen
        private Screen screen;
        private BufferedImage img;
    //Pixels
        private int [] pixels;
    // WINDOW SIZES
        public static int WIDTH = 800;
        public static int HEIGHT = 600;
    // Game
        private Game game;

        public Display (){ // Screen Display 
           Dimension size = new Dimension(WIDTH, HEIGHT); // allows width and height global variables into an object
           setPreferredSize(size);
           setMinimumSize(size);
           setMaximumSize(size);

           screen = new Screen(WIDTH, HEIGHT);
           game = new Game();
           img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
           pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        }

        public void start() { // Start method
                if (running) return; // if running(boolean) equals true then it will return (initalize game)
                running = true; 
                thread = new Thread(this);
                thread.start();

                System.out.println("Working!");
                run();
        }

        public void stop() { // Stop method, needed for game to be an applet later(runs in broswer)
                if(!running) return; // if its not running, since start makes running true to stop it, it makes running false to stop the program
                running = false;
                try {
                    thread.join(); 
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }  
        }

        public void run() { // Run method
            int frames = 0; // fps counter used to measure the amount of frames being processed and the amount of times the screen is being updated
            double unprocessedSeconds = 0;
            long previousTime = System.nanoTime(); // a long is like a int but is 64 bit and can store alot more numbers
            double secondsPerTick = 1 / 60.0; // counts every time it renders (ticks)
            int tickCount = 0;
            boolean ticked = false; // once ticked will be changed to true

            while (running) { // while running is true 
                 //render(); // rendering screen and frames, render is also a class from a sub folder under the display package
                 long currentTime = System.nanoTime();
                 long passedTime = currentTime - previousTime;
                 previousTime = currentTime;
                 unprocessedSeconds += passedTime / 1000000000.0;

                 while (unprocessedSeconds > secondsPerTick) {
                        tick(); // handles time and fps
                        unprocessedSeconds -= secondsPerTick;
                        ticked = true;
                        tickCount ++;
                        if (tickCount % 60 == 0) {
                            System.out.println(frames + "fps");
                            previousTime += 1000;
                            frames = 0;
                        }
                 } if (ticked) {
                    render(); // rendering screen and frames, render is also a class from a sub folder under the display package
                    frames++;
                 }
            }
        }

        private void tick() { // tick method for fps
            game.tick();
        }

        public void render() {
            BufferStrategy bs = this.getBufferStrategy(); // we want to start our buffer strategy once
            if (bs == null) {
                createBufferStrategy(3); //It just sets up the number of screen buffers you want. So for example createBufferStrategy(2) would mean that you have 2 buffers
                return;
            }

            screen.render(game);

            for (int i = 0; i < WIDTH * HEIGHT; i++) { // keeps looping through each pixel to create a range in the x-axis (if confused look at notes: How rendering works?)
                pixels[i] = screen.pixels[i]; 
            }

            Graphics g = bs.getDrawGraphics();
            g.drawImage(img, 0, 0, WIDTH + 10, HEIGHT + 10, null);
            g.dispose();
            bs.show();
        }

        public static void main(String[] args) {  // Main class 
                Display game = new Display(); // display object, game display
                JFrame frame = new JFrame(); // Game frames
                frame.add(game); // adding game to game frames
                frame.pack();
                frame.setResizable(true); // so that the screen doesnt resize
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cloes frames if operation is closed
                frame.setLocationRelativeTo(null); // sets screen to the middle
                System.out.print("Running....");
                game.start(); // initalizes game
        }
}