package fill;

import model.Point;
import raster.Raster;

import java.util.LinkedList;

public class SeedFill implements Filler {

    private final Raster raster;
    private final int x;
    private final int y;
    private final int fillColor;
    private final int backgroundColor;

    public SeedFill(Raster raster, int x, int y, int fillColor) {
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = raster.getPixel(x, y);
    }

    @Override
    public void fill() {
        if (fillColor != backgroundColor) {
            seedFill();
        }
        //recursiveFill( x, y);

    }

    private void seedFill() { //NE REKURZIVNI RESENI
        LinkedList<Point> points = new LinkedList<>();
        //Uvodni iterace
        Point tmp;

        points.add(new Point(x, y));

        while (!points.isEmpty()) {
            tmp = points.getFirst();
            //System.out.println (tmp.getX() + " " + tmp.getY() + " " + raster.getPixel(tmp.getX(), tmp.getY()) + " " + fillColor );
            int tmpx = tmp.getX();
            int tmpy = tmp.getY();

            if (raster.getPixel(tmpx, tmpy) == backgroundColor) {


                if (tmpx + 1 < raster.getWidth() && raster.getPixel(tmpx + 1, tmpy) == backgroundColor) {
                    points.add(new Point(tmpx + 1, tmpy));
                }
                //points.add(new Point(tmp.getX()+1, tmp.getY()));
                if (tmpx - 1 > 0 && raster.getPixel(tmpx - 1, tmpy) == backgroundColor) {
                    points.add(new Point(tmpx - 1, tmpy));
                }
                //points.add(new Point(tmp.getX()-1, tmp.getY()));
                if (tmpy + 1 < raster.getHeight() && raster.getPixel(tmpx, tmpy + 1) == backgroundColor) {
                    points.add(new Point(tmpx, tmpy + 1));
                }
                if (tmpy - 1 > 0 && raster.getPixel(tmpx, tmpy - 1) == backgroundColor) {
                    points.add(new Point(tmpx, tmpy - 1));
                }

                raster.setPixel(tmp.getX(), tmp.getY(), fillColor);
            }
            points.removeFirst();
        }

    }

    private void recursiveFill(int x, int y) {
        System.out.println("rekuze");
        int pixelColor = raster.getPixel(x, y);
        if (pixelColor != backgroundColor)
            return;

        raster.setPixel(x, y, fillColor);
        recursiveFill(x + 1, y);
        recursiveFill(x - 1, y);
        recursiveFill(x, y + 1);
        recursiveFill(x, y - 1);
    }


}
