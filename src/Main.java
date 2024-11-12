import visual.Controller2d;
import visual.Mainframe;

public class Main {
    public static void main(String[] args) {
        Mainframe mainframe = new Mainframe(800, 600);
        new Controller2d(mainframe.getPanel());
    }
}