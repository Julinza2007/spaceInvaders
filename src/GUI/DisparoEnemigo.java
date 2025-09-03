package GUI;

import java.awt.Color;

import javax.swing.JPanel;

public class DisparoEnemigo extends JPanel{
	
	private long ultimoDisparo = 0; // Momento en milisegundos del último disparo
	private int tiempoRecarga = 500; // Tiempo mínimo entre disparos (ms)
	
	public DisparoEnemigo(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
		setBackground(Color.GREEN);
		setOpaque(true);
	}
	
	
	
	public void mover() {
		int dy = 10;
		new Thread(() -> {
            while (isVisible()) {
                setLocation(getX(), getY() + dy);
                try { Thread.sleep(30); } catch (InterruptedException e) {}
                
                
            }
            setVisible(false);
        }).start();
	}
}
