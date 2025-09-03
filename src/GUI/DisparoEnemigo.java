package GUI;

import java.awt.Color;

import javax.swing.JPanel;

public class DisparoEnemigo extends JPanel{
	public void DisparoEnemigo(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
		setBackground(Color.GREEN);
	}
	
	
	
	public void mover() {
		int dy = 5;
		new Thread(() -> {
            while (getY() < 600) {
                setLocation(getX(), getY() + dy);
                try { Thread.sleep(30); } catch (InterruptedException e) {}
            }
            setVisible(false);
        }).start();
	}
}
