package clipper;
import model.Point;
import model.Polygon;
import model.Edge;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Clip {
    Polygon polygon;
    Polygon clipper;
    ArrayList<Edge> edgesClipper;
    ArrayList<Point> result;


    public Clip(Polygon polygon, Polygon clipper) {
        this.clipper=clipper;
        this.polygon=new Polygon(polygon);
        result = new ArrayList<>();
        edgesClipper = new ArrayList<>();
    }

    public Point intersection(int x1, int y1, int x2, int y2, int x3, int y3,int x4, int y4) {
        int den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        return new Point((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)/den,(x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)/den);
    }


    public Polygon clip(){

        for(int i = 0;i<clipper.size();i++){
            Point p1 = clipper.getPoint(i);
            Point p2 = clipper.getPoint((i + 1) % clipper.size());

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            edgesClipper.add(edge);

        }

        for(int i = 0; i < edgesClipper.size();i++){
            int i2 = (i + 1) % edgesClipper.size();
            int x1 = edgesClipper.get(i).getX1();
            int y1 = edgesClipper.get(i).getY1();
            int x2 = edgesClipper.get(i2).getX2();
            int y2 = edgesClipper.get(i2).getY2();


            for(int j = 0; j < polygon.size(); j++){
                System.out.println("a");
                int k = (j+1) % polygon.size();
                int x3 = polygon.getPoint(j).getX();
                int y3 = polygon.getPoint(j).getY();
                int x4 = polygon.getPoint(k).getX();
                int y4 = polygon.getPoint(k).getY();

                boolean position1;
                boolean position2;

                if((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1) < 0){ // orientace?
                    position1 = true;
                }else{
                    position1 = false;
                }
                if((x2 - x1) * (y4 - y1) - (y2 - y1) * (x4 - x1) < 0){
                    position2 = true;
                }else{
                    position2 = false;
                }
                // Nyni rozhodovaci cast Sutherland-Hodgemana algoritmu

                if(position1 == true && position2 == true){ // Prvni pripad, obe jsou uvnitr
                    result.add(new Point(x4,y4));
                }
                if(position1 == true && position2 == false){ // Druhy pripad, druhy je venku
                    result.add(intersection(x1,y1,x2,y2,x3,y3,x4,y4));
                }
                if(position1 == false && position2 == true ){ // Treti pripad, prvni je venku
                    result.add(intersection(x1,y1,x2,y2,x3,y3,x4,y4));
                    result.add(new Point(x4,y4));
                }

                }
            polygon.clearPoints(); // Orizneme
            for (Point point : result){
                polygon.addPoint(point);


            }


        }

            return polygon;

        }


    }

