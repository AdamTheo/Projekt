package fill;
import model.*;
import rasterizers.LineRasterize;
import rasterizers.PolygonRasterize;
import rasterizers.LineTrivial;
import model.Polygon;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ScanLine implements Filler {
    private PolygonRasterize polygonRasterize;
    private LineRasterize lineRasterize;
    private Polygon polygon;
    private int minY;
    private int maxY;

    public ScanLine(PolygonRasterize polygonRasterize, LineRasterize lineRasterize, Polygon polygon) {
        this.polygonRasterize = polygonRasterize;
        this.lineRasterize = lineRasterize;
        this.polygon = polygon;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
    }


    @Override
    public void fill() {

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i + 1) % polygon.size());

            if(p1.getY() < minY){ // Rovnou ozkosuime min a max
                minY = p1.getY();
            }
            if(p2.getY() > maxY){
                maxY = p2.getY();
            }

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            // hranu uložím se seznamu
            if(!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }
        for(int y = minY; y <= maxY; y++) {

            List<Integer> intersections = new ArrayList<>();

            for (Edge edge : edges) {
                if(edge.isIntersection(y)){
                    intersections.add(edge.intersectionX(y));
                }
            }

            Collections.sort(intersections);

            for(int i = 0; i<intersections.size()-1; i += 2){
                lineRasterize.rasterize(new Line(intersections.get(i),y, intersections.get(i+1),y ));
            }




        }


    }
}
