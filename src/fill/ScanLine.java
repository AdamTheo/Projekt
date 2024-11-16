package fill;

import model.Edge;
import model.Line;
import model.Point;
import model.Polygon;
import rasterizers.LineRasterize;
import rasterizers.PolygonRasterize;

import java.util.ArrayList;
import java.util.List;

public class ScanLine implements Filler {
    private final PolygonRasterize polygonRasterize;
    private final LineRasterize lineRasterize;
    private final Polygon polygon;
    private int minY;
    private int maxY;
    List<Integer> intersections;

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

            if (p1.getY() < minY) { // Rovnou ozkosuime min a max
                minY = p1.getY();
            }
            if (p2.getY() > maxY) {
                maxY = p2.getY();
            }

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            // hranu uložím se seznamu
            if (!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }
        for (int y = minY; y <= maxY; y++) {

            intersections = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.isIntersection(y)) {
                    intersections.add(edge.intersectionX(y));
                }
            }

            sortIntersections(0, intersections.size() - 1);

            for (int i = 0; i < intersections.size() - 1; i += 2) {
                lineRasterize.rasterize(new Line(intersections.get(i), y, intersections.get(i + 1), y));
            }
        }
    }

    public void sortIntersections(int start, int end) { // POuzijeme rekurzivni quicksort
        if (start < end) {
            int index = divide(start, end);

            sortIntersections(start, index - 1);
            sortIntersections(index + 1, end);
        }
    }

    public int divide(int start, int end) {
        int pivot = intersections.get(end);
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (intersections.get(j) <= pivot) {
                i++;

                int tmp = intersections.get(i);
                intersections.set(i, intersections.get(j));
                intersections.set(j, tmp);
            }
        }

        int tmp = intersections.get(i + 1);
        intersections.set(i + 1, intersections.get(end));
        intersections.set(end, tmp);

        return i + 1;
    }
}
