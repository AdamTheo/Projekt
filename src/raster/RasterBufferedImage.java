package raster;


import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster {

    private final BufferedImage image;
    private final int width;
    private final int height;

    public RasterBufferedImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.width = width;
        this.height = height;
    }

    @Override
    public void setPixel(int x, int y, int value) {
        if ((x > 0 && x < width) && (y > 0 && y < height)) {
            image.setRGB(x, y, value);
        }
    }

    @Override
    public int getPixel(int x, int y) {
        return image.getRGB(x, y) & 0x00ffffff;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void clear() {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public Graphics getGraphics() {
        return image.getGraphics();
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}


