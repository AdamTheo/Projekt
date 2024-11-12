package visual;

import javax.swing.*;

public class Mainframe extends JFrame {
    private final Panel panel;

    public Mainframe(int width, int height) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("PGRF1 2024/2025");
        setVisible(true);
        panel = new Panel(width, height);
        add(panel);
        pack();
    }

    public Panel getPanel() {
        return panel;

    }
}
