package visual;

import model.Line;
import model.Point;
import model.Polygon;
import rasterizers.LineRasterize;
import rasterizers.LineTrivial;
import rasterizers.PolygonRasterize;
import fill.SeedFill;
import fill.ScanLine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class Controller2d {
    private final Panel panel;
    private final LineRasterize lineRasterize;
    private int x1;
    private int y1;
    private Line line;
    private int mode = 2; // Rikame, v jakem modu je aplikace. Vychozi stav je mod 1, tedy usecky

    private SeedFill seminko;
    private final Polygon polygon;
    private List<Point> polygonList = new ArrayList<>();
    private final PolygonRasterize polygonRasterize;
    private ScanLine scanline;


    public Controller2d(Panel panel) {
        this.panel = panel;
        lineRasterize = new LineTrivial(panel.getRaster(), 0xff0000);
        polygonRasterize = new PolygonRasterize(lineRasterize);
        initListeners();
        polygon = new Polygon();
        panel.setFocusable(true);
        panel.requestFocusInWindow();

    }

    private void repaintPolygon() {
        panel.clear();
        polygonList = polygon.getPoints();

        polygonRasterize.rasterize(polygon);

        panel.repaint();
    }

    private void initListeners() {

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // Ukladani zacatecniho bodu
                x1 = e.getX();
                y1 = e.getY();


            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) { //Vykresluje v realnem case novou caru, kterou tvorime
                panel.clear();
                polygonRasterize.rasterize(polygon);
                polygonList = polygon.getPoints();
                //Zde se jiz nevykresluji jiz ulozene veci, tady se resi co se ma vykreslovat dal.. kuprikladu jestli rovna cara, polygon atd.
                if (polygonList.size() == 0 && mode == 2) { //ROVNE CARY
                    line = new Line(x1, y1, e.getX(), e.getY());
                    lineRasterize.rasterize(line);
                }

                if (mode == 2 && polygonList.size() != 0) { // Vykreslovani car vedoucich k novemu vrcholu
                    /*int tmp = polygonList.size() - 1;
                    line = new Line(polygonList.get(0).getX(), polygonList.get(0).getY(), e.getX(), e.getY());
                    lineRasterize.rasterize(line);
                    line = new Line(polygonList.get(tmp).getX(), polygonList.get(tmp).getY(), e.getX(), e.getY());
                    lineRasterize.rasterize(line);
                    */
                    line = polygonRasterize.newLine(polygon,e.getX(), e.getY());
                }

                panel.repaint();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (mode == 2) { // Pridavame do polygonu
                    if (polygon.getNumberOfPoints() == 0) {
                        polygon.addPoint(x1, y1);
                        polygon.addPoint(line.getX2(), line.getY2());

                    } else {
                        polygon.addPoint(line.getX2(), line.getY2());
                        repaintPolygon();
                    }
                }
                if(mode == 3){
                    seminko = new SeedFill(panel.getRaster(),x1,y1,0xffff00);
                    seminko.fill();
                    panel.repaint();
                }
                if(mode == 4){
                    scanline = new ScanLine(polygonRasterize,lineRasterize,polygon);
                    scanline.fill();
                    panel.repaint();
                }

            }
        });

        //Zde zacinaji listenery pro jednotlive klavesy,

        panel.addKeyListener(new KeyAdapter() { // MAZANI
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();

                if (key == 'c' || key == 'C') { // vymaze platno, je treba to dal komentovat?

                    panel.clear();
                    panel.repaint();
                    polygon.clearPoints();

                }
                if(key == 's' || key == 'S') {
                    System.out.println("Jste v rezimu seedfill");
                    mode = 3;
                }

                if(key == 'p' || key == 'P') {
                    System.out.println("Jste v rezimu kresleni polygonu");
                    mode = 2;
                }
                if(key == 'f' || key == 'F') {
                    System.out.println("Jste v rezimu scanline");
                        mode = 4;

                }



            }
        });


    }

}

