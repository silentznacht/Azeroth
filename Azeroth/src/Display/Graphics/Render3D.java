package Display.Graphics;

import java.lang.reflect.Constructor;

public class Render3D extends Render{
    public Render3D(int width, int height){
        super(width, height);
    }

    public void floor() {
        for (int y = 0; y < height; y++) {
            double yDepth = y - height / 2;
            double z = 100.0 / yDepth;

            for (int x = 0; x < width; x++){
                double depth = x - width / 2;
                depth *= z;
                int xx = (int) (depth) & 5; // xx converts depth to integer
                pixels[x + y * width] = xx * 128;
            }
        }
    }
}
