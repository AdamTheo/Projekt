package rasterizers;

import model.Line;
import raster.Raster;

public abstract class LineRasterize {
    protected final Raster raster;
    protected int color;

    public LineRasterize(Raster raster) {
        this.raster = raster;
        this.color = 0xff0000;
    }

    public LineRasterize(Raster raster, int color) {
        this.raster = raster;
        this.color = color;
    }


    public void rasterize(Line line) {

    }

    public void setColor(int color) {
        this.color = color;
    }

    public Line shiftRasterize(Line line) {
        return new Line(1, 1, 1, 1);
    }

    public void thickRasterize(Line line) {
    }


}
