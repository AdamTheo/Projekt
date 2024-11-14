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

    public Clip(Polygon polygon, Polygon clipper) {
        this.clipper=clipper;
        this.polygon=polygon;
    }

    public Polygon clip(){
        Polygon result = new Polygon();
        List<Edge> edgesToClip = new ArrayList<>();
        List<Edge> edgesClipping = new ArrayList<>();

        for (int i = 0; i < polygon.size(); i++) { // Making edges out of polygon
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i + 1) % polygon.size());

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            edge.orientate();
            edgesToClip.add(edge);
            }
        for (int i = 0; i < clipper.size(); i++) { //making edges out of clipper
            Point p1 = clipper.getPoint(i);
            Point p2 = clipper.getPoint((i + 1) % clipper.size());

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            edge.orientate();
            edgesClipping.add(edge);
        }
        //Nyni zjistime pruseciky

        for(int i = 0; i < edgesToClip.size(); i++){
            Edge edge = edgesToClip.get(i);
            int intersections1 = 0;
            int intersections2 = 0;


            //Pro kazde body z jedne hrany otestujeme zda lezi v polygonu, pak se nam to rozpadne do nekolika scenaru dle Sutherland-Hodgman algoritmu
            for(int j = 0; j < edgesClipping.size(); j++){
                if(edge.isIntersectionAdvanced(edge.getX1(), edge.getY1())){
                    intersections1++;
                }
                if(intersections1 > 1){ // jestli je v konvexnim mnohouhelniku vic nez jeden prusecik, pak nelezi uvnitr
                    break;
                }
            }

            for(int j = 0; j < edgesClipping.size(); j++){
                if(edge.isIntersectionAdvanced(edge.getX2(), edge.getY2())){
                    intersections2++;
                }
                if(intersections2 > 1){ // jestli je v konvexnim mnohouhelniku vic nez jeden prusecik, pak nelezi uvnitr
                    break;
                }
            }






        }




            return result;
        }
    }

