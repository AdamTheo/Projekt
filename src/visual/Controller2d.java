package visual;

import clipper.Clip;
import fill.ScanLine;
import fill.SeedFill;
import model.Line;
import model.Pentagon;
import model.Point;
import model.Polygon;
import rasterizers.LineRasterize;
import rasterizers.LineTrivial;
import rasterizers.PolygonRasterize;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Controller2d {
    private final Panel panel;
    private final LineRasterize lineRasterize;
    private final Polygon polygon;
    private final PolygonRasterize polygonRasterize;
    private int x1;
    private int y1;
    private Line line;
    private int mode = 2; // Rikame, v jakem modu je aplikace. Vychozi stav je mod 1, tedy usecky
    private SeedFill seminko;
    private ScanLine scanline;
    private Pentagon pentagon;
    private final Polygon clippingPolygon;
    private final Polygon clippedPolygon;
    private Clip clipper;
    private final PolygonRasterize clipperRasterize;
    private final LineRasterize clipLine;


    public Controller2d(Panel panel) {
        this.panel = panel;
        lineRasterize = new LineTrivial(panel.getRaster(), 0xff0000);
        polygonRasterize = new PolygonRasterize(lineRasterize);
        initListeners();
        polygon = new Polygon();
        clippingPolygon = new Polygon();
        clippedPolygon = new Polygon();
        pentagon = new Pentagon();
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        clipLine = new LineTrivial(panel.getRaster(), 0x0000ff);
        clipperRasterize = new PolygonRasterize(clipLine);

    }

    private void repaintPolygon() {
        panel.clear();
        polygonRasterize.rasterize(polygon);
        clipperRasterize.rasterize(clippingPolygon);
        polygonRasterize.rasterize(pentagon);
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
                repaintPolygon();
                //Zde se jiz nevykresluji jiz ulozene veci, tady se resi co se ma vykreslovat dal.. kuprikladu jestli rovna cara, polygon atd.
                if (polygon.size() == 0 && mode == 2) { //ROVNE CARY
                    line = new Line(x1, y1, e.getX(), e.getY());
                    lineRasterize.rasterize(line);
                }

                if (mode == 2 && polygon.size() != 0) { // Vykreslovani car vedoucich k novemu vrcholu
                    line = polygonRasterize.newLine(polygon, e.getX(), e.getY());
                }

                if (mode == 5) {
                    pentagon = new Pentagon(new Point(x1, y1), new Point(e.getX(), e.getY()));
                }

                if (mode == 6 && (clippingPolygon.size() !=0 || polygon.size() !=0)) {
                    if(polygon.getDistance(e.getX(),e.getY()) < clippingPolygon.getDistance(e.getX(),e.getY())) {
                        int tmp = polygon.getClosestPoint(e.getX(), e.getY());
                        polygon.editPoint(tmp, e.getX(), e.getY());
                    }else{
                        int tmp = clippingPolygon.getClosestPoint(e.getX(), e.getY());
                        clippingPolygon.editPoint(tmp, e.getX(), e.getY());
                    }
                    repaintPolygon();
                }

                if (clippingPolygon.size() == 0 && mode == 8) {
                    line = new Line(x1, y1, e.getX(), e.getY());
                    clipLine.rasterize(line);
                }

                if (mode == 8 && clippingPolygon.size() != 0) {
                    line = clipperRasterize.newLine(clippingPolygon, e.getX(), e.getY());
                }

                panel.repaint();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                if (mode == 2) { // Pridavame do polygonu
                    if (polygon.size() == 0) {
                        polygon.addPoint(x1, y1);
                        polygon.addPoint(line.getX2(), line.getY2());

                    } else {
                        polygon.addPoint(line.getX2(), line.getY2());
                        repaintPolygon();
                    }
                }
                if (mode == 3) { // Pouzije seedfill
                    seminko = new SeedFill(panel.getRaster(), x1, y1, 0xffff00);
                    seminko.fill();

                }
                if (mode == 4) { // Pouzije Scanline
                    scanline = new ScanLine(polygonRasterize, clipLine, polygon);
                    scanline.fill();
                    polygonRasterize.rasterize(polygon);

                }

                if (mode == 7 && (clippingPolygon.size() !=0 || polygon.size() !=0)) { // Smazani vrcholu

                    if(polygon.getDistance(e.getX(),e.getY()) < clippingPolygon.getDistance(e.getX(),e.getY())) {
                        int tmp = polygon.getClosestPoint(e.getX(), e.getY());
                        polygon.removePoint(tmp);
                    }else{
                        int tmp = clippingPolygon.getClosestPoint(e.getX(), e.getY());
                        clippingPolygon.removePoint(tmp);
                    }

                    repaintPolygon();
                }
                if (mode == 8) {
                    if (clippingPolygon.size() == 0) {
                        clippingPolygon.addPoint(x1, y1);
                        clippingPolygon.addPoint(line.getX2(), line.getY2());
                    } else {
                        clippingPolygon.addPoint(line.getX2(), line.getY2());
                        repaintPolygon();
                    }
                }
                if(mode ==9 && (clippingPolygon.size() !=0 || polygon.size() !=0)){
                    if(polygon.getDistance(e.getX(),e.getY()) < clippingPolygon.getDistance(e.getX(),e.getY())) {
                        int tmp = polygon.getClosestPoint(e.getX(), e.getY());
                        polygon.addPointEdit(tmp,e.getX(),e.getY());
                    }else{
                        int tmp = clippingPolygon.getClosestPoint(e.getX(), e.getY());
                        clippingPolygon.addPointEdit(tmp,e.getX(),e.getY());
                    }
                    repaintPolygon();
                }
                //Clipovani
                /*if(polygon.size() > 2 && clippingPolygon.size() > 2){
                    clipper = new Clip(polygon,clippingPolygon);
                    clippedPolygon = clipper.clip();
                    LineRasterize line2 = new LineTrivial(panel.getRaster());
                    line2.setColor(0x0000ff);
                    PolygonRasterize polygon2= new PolygonRasterize(line2);
                    polygon2.rasterize(clippedPolygon);

                    //ScanLine clippedFill = new ScanLine(polygonRasterize,lineRasterize,clippedPolygon);
                    //clippedFill.fill();
                }*/

                panel.repaint();

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
                    pentagon.clearPoints();
                    clippedPolygon.clearPoints();
                    clippingPolygon.clearPoints();

                }
                if (key == 's' || key == 'S') {
                    System.out.println("Jste v rezimu seedfill");
                    mode = 3;
                }

                if (key == 'p' || key == 'P') {
                    System.out.println("Jste v rezimu kresleni polygonu");
                    mode = 2;
                }
                if (key == 'f' || key == 'F') {
                    System.out.println("Jste v rezimu scanline");
                    mode = 4;

                }
                if (key == 'e' || key == 'E') {
                    System.out.println("Jste v rezimu Pentagon");
                    mode = 5;
                }
                if (key == 'o' || key == 'O') {
                    System.out.println("Jste v rezimu editace vrcholu");
                    mode = 6;
                }
                if (key == 'i' || key == 'I') {
                    System.out.println("Jste v rezimu mazani vrcholu");
                    mode = 7;
                }
                if (key == 'u' || key == 'U') {
                    System.out.println("Kreslite orezavaci polygon");
                    mode = 8;
                }
                if(key == 'l' || key == 'L') {
                    System.out.println("Pridavete vrcholy do polygonu ");
                    mode = 9;
                }
            }
        });


    }

}

