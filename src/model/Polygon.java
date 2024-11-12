package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Point> points = new ArrayList<Point>();

    public Polygon() {
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getNumberOfPoints() {
        return points.size();
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public Point getPoint(int i){
        return points.get(i);
    }

    public int size(){
        return points.size();
    }

    public void clearPoints() {
        points.clear();
    }
}
