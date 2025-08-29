package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel implements KeyListener {
    private boolean startGame = false;

    public Menu() {
        setBackground(Color.BLACK); // fondo negro
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!startGame) {
           
        	
        	// este seria el titulo
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("SPACE INVADERS G3L", 60, 150);

            // esto el subtitulo que te dice que apretes para que empiece el juego
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 28));
            g.drawString("Presiona ENTER para jugar", 120, 250);
        } else {
            // aca te avisa que el juego empezo
            g.setColor(Color.CYAN);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("¡Juego iniciado!", 160, 200);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame = true;
            repaint();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Space Invaders G3L - Pantalla de Título");
        Menu panel = new Menu();

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cierra el juego
        ventana.setSize(600, 400); //tamaño de la ventana
        ventana.setResizable(false);
        ventana.add(panel); 
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
