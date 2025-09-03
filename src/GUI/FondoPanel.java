package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class FondoPanel extends JPanel {
    private Image fondo;

    public FondoPanel(Image fondo) {
        this.fondo = fondo;
        setLayout(null); // Para posicionar manualmente
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}