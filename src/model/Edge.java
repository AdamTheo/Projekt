package model;

import java.util.ArrayList;
import java.util.List;

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

    public void orientate(){
        if (y1 > y2){
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

    public boolean isIntersection(int y){
        if(y >= y1 && y < y2){
            return true;
        }else{
            return false;
        }
    }
    public boolean isIntersectionAdvanced(int x, int y){
        if(y >= y1 && y < y2) {
            return true;
        }else{
            return false;
        }
    }

    public int intersectionX(int y){
        if(x1 == x2){
            return x1;
        }

        float k = (y2 - y1) / (float) (x2 - x1); // Vypocitani hodnoty tangens
        float q = y1 - k * x1;
        float x = ((float) y - q) / k;
        return Math.round(x);

    }
    public Point findIntersection(List<Edge> list){
        int A1 = y2-y1;
        int B1 = x2-x1;
        int C1 = x2*y1 - x1*y2;

        for(int i = 0; i < list.size(); i++){
            int x3 = list.get(i).getX1();
            int y3 = list.get(i).getY1();
            int x4 = list.get(i).getX2();
            int y4 = list.get(i).getY2();

            int A2 = y4-y3;
            int B2 = x4-x3;
            int C2 = x4*y3 - x3*y4;

            float D =(float) A1 * B2 - (float)A2 * B1;
            int x = Math.round((float)(B1 * C2 - B2 * C1) / D);
            int y = Math.round((float)(A2 * C1 - A1 * C2) / D);

            if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
                return new Point(x,y);
            }
        }

        return null;
    }

    public Point findIntersection(Edge edge){
        int A1 = y2-y1;
        int B1 = x2-x1;
        int C1 = x2*y1 - x1*y2;


            int x3 = edge.getX1();
            int y3 = edge.getY1();
            int x4 = edge.getX2();
            int y4 = edge.getY2();

            int A2 = y4-y3;
            int B2 = x4-x3;
            int C2 = x4*y3 - x3*y4;

            float D =(float) A1 * B2 - (float)A2 * B1;
            int x = Math.round((float)(B1 * C2 - B2 * C1) / D);
            int y = Math.round((float)(A2 * C1 - A1 * C2) / D);

            if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
                return new Point(x,y);
            }


        return null;
    }



}
