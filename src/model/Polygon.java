package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    protected final List<Point> points = new ArrayList<Point>();

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

    public Point getPoint(int i) {
        return points.get(i);
    }

    public int size() {
        return points.size();
    }

    public void clearPoints() {
        points.clear();
    }


    public int getClosestPoint(int x, int y) {
        float distance = (float) Math.sqrt((Math.pow(x - points.get(0).getX(), 2) + Math.pow(y - points.get(0).getY(), 2)));
        int indexResult = 0;

        for (int i = 1; i < getNumberOfPoints(); i++) {
            float tmp = (float) Math.sqrt((Math.pow(x - points.get(i).getX(), 2) + Math.pow(y - points.get(i).getY(), 2)));
            if (tmp < distance) {
                distance = tmp;
                indexResult = i;
            }

        }
        return indexResult;
    }

    public void editPoint(int i,int x, int y) {
        points.set(i, new Point(x, y));
    }
    public void removePoint(int i) {
        points.remove(i);

    }


}

