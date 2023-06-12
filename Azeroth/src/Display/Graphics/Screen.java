package Display.Graphics;

import java.util.Random;

import Display.Game;

public class Screen extends Render { // this class is for generating pixels and adjusting screen 

    private Render test; // this will be used to test things out

    public Screen(int width, int height) {
            super(width, height); // means it is inheirting qualities from the render class, like width and height
            Random random = new Random(); // randomizes the pixels
            test = new Render(256, 256); // creating limits to width and height  
            for (int i = 0; i < 256 * 256; i++) {
                test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4); // pixels value will be randomized, because of this what ever random number pixels is will be converted to an rgb value
            }
    }

    public void render(Game game) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = 0; 
        }
        for (int i = 0; i < 50; i++) {
                int anim0 = (int) (Math.sin(System.currentTimeMillis() + i % 1000.0 / 1000 * Math.PI * 2) * 100);

                int anim = (int) (Math.sin((game.time + i * 2) % 1000.0 / 100) * 100);
                int anim2 = (int) (Math.cos((game.time + i * 2) % 1000.0 / 100) * 100);
                draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + anim2); // calls the draw method in the render class, then sets both y and x offsets to 0 || (width - 256) / 2 puts screen in the middle
            }
        } 
}
 