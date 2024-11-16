package model;


public class Pentagon extends Polygon {
    public Pentagon(Point start, Point end){
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();


        float angle = (y2 - y1) / (float) (x2 - x1);
        float radius = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        for (int i = 0; i < 5; i++) {
            super.addPoint((int) (radius * Math.cos(angle + (i * Math.PI / 2.5f))) + x1, (int) (radius * Math.sin(angle + (i * Math.PI / 2.5f))) + y1);
        }



    }
}
