package rasterizers;

import model.Line;
import model.Point;
import model.Polygon;

import java.util.ArrayList;
import java.util.List;


public class PolygonRasterize {
    private final LineRasterize lineRasterizer;
    private List<Point> polygonList = new ArrayList<Point>();

    public PolygonRasterize(LineRasterize lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public void rasterize(Polygon polygon) {
        polygonList = polygon.getPoints();
        for (int i = 0; i < polygonList.size(); i++) {
            if (i <= polygonList.size() - 2) {
                lineRasterizer.rasterize(new Line(polygonList.get(i).getX(), polygonList.get(i).getY(), polygonList.get(i + 1).getX(), polygonList.get(i + 1).getY()));
            }
            if (i == polygonList.size() - 1) {
                lineRasterizer.rasterize(new Line(polygonList.get(0).getX(), polygonList.get(0).getY(), polygonList.get(i).getX(), polygonList.get(i).getY()));
            }

        }

    }

    public Line newLine(Polygon polygon, int x, int y) {
        polygonList = polygon.getPoints();
        int tmp = polygonList.size() - 1;
        Line line;
        line = new Line(polygonList.get(0).getX(), polygonList.get(0).getY(), x, y);
        lineRasterizer.rasterize(line);
        line = new Line(polygonList.get(tmp).getX(), polygonList.get(tmp).getY(), x, y);
        lineRasterizer.rasterize(line);
        return line;
    }

}
