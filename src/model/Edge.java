package model;

public class Edge {
    private int x1, x2, y1, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public void orientate() {
        if (y1 > y2) {
            int tmpx = x2;
            int tmpy = y2;
            x2 = x1;
            y2 = y1;
            x1 = tmpx;
            y1 = tmpy;
        }
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public boolean isIntersection(int y) {
        return y >= y1 && y < y2;
    }

    public int intersectionX(int y) {
        if (x1 == x2) {
            return x1;
        }
        float k = (y2 - y1) / (float) (x2 - x1); // Vypocitani hodnoty tangens
        float q = y1 - k * x1;
        float x = ((float) y - q) / k;
        return Math.round(x);

    }
}
