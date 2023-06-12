package Display.Graphics;

import Display.Display;

public class Render { 
    // sizes for pixels
        public final int width;
        public final int height;
        public final int[] pixels; // an array

        public Render (int width, int height) { // this is a constructor with two parameter(width and height )
            this.width = width; // (this) is a keyword from the display class, meaning that the width in our class will be used to render in the display class as well
            this.height = height;
            pixels = new int[width * height]; // 480,000 pixels, meaning we want to render an area that is 800 by 600 
        }

        public void draw (Render render, int xOffSet, int yOffSet) { // calling render constructor, and other parameters being initalized
            for (int y = 0;  y < render.height; y++) { // increments through render(array) as long as it is less than it's value it will loop and increment 
                int yPix = y +  yOffSet;
                        if (yPix < 0 || yPix >= this.height) {
                            continue;
                        }

                for (int x = 0;  x < render.width; x++) { 
                        int xPix = x + xOffSet;
                        if (xPix < 0 || xPix >= this.width){
                            continue;
                        }

                        int alpha = render.pixels[x + y * render.width];
                        
                        if (alpha > 0) { // if pixels are generated then 
                            pixels[xPix + yPix * width] = alpha;
                        }
                }
            }
        }
}
