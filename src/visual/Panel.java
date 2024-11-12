package visual;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import raster.RasterBufferedImage;
public class Panel extends JPanel {
    private final RasterBufferedImage raster;

    public Panel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        raster = new RasterBufferedImage(width, height);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        raster.paint(g);
    }

    public void clear() {
        Graphics g = raster.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public RasterBufferedImage getRaster() {
        return raster;
    }

}
