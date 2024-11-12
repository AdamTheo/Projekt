package rasterizers;

import model.Line;

import java.awt.image.BufferedImage;
import raster.Raster;

public class LineTrivial extends LineRasterize {

    public LineTrivial(Raster raster) {
        super(raster);
    }

    public LineTrivial(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void rasterize(Line line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        if (x1 == x2) { // Specialni pripad, pokud je to kolma cara
            if (y2 < y1) {
                int tmp = y2;
                y2 = y1;
                y1 = tmp;
            }
            for (int i = y1; i <= y2; i++) {
                raster.setPixel(x1, i, color);
            }

        } else {
            //Tady pokud se nejedna o linku y
            float k = (y2 - y1) / (float) (x2 - x1); // Vypocitani hodnoty tangens
            float q = y1 - k * x1;
            if (x2 < x1) {
                int tmp = x2;
                x2 = x1;
                x1 = tmp;
            }
            if (y2 < y1) {
                int tmp = y2;
                y2 = y1;
                y1 = tmp;
            }
            if (k > -1 && k < 1) {
                for (int x = x1; x <= x2; x++) {
                    float y = k * x + q;
                    raster.setPixel(x, Math.round(y), color);
                }
            } else {
                for (int y = y1; y <= y2; y++) {
                    float x = ((float) y - q) / k;
                    raster.setPixel(Math.round(x), y, color);
                }
            }
        }
    }




}





