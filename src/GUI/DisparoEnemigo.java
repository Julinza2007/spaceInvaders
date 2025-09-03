package GUI;

import java.awt.Color;

import javax.swing.JPanel;

public class DisparoEnemigo extends JPanel{
	
	private long ultimoDisparo = 0; // Momento en milisegundos del último disparo
	private int tiempoRecarga = 500; // Tiempo mínimo entre disparos (ms)
	
	public DisparoEnemigo(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
		setBackground(Color.GREEN);
	}
	
	
	
	public void mover() {
		int dy = 5;
		new Thread(() -> {
            while (getY() > 600) {
                setLocation(getX(), getY() + dy);
                try { Thread.sleep(30); } catch (InterruptedException e) {}
                
                long ahora = System.currentTimeMillis();
        		if (ahora - ultimoDisparo < tiempoRecarga) {
        		    return; // Todavía no pasó el tiempo de recarga
        		}
        		
        		else {
        			ultimoDisparo = ahora; // Actualizamos el momento del último disparo
        		}
                
            }
            setVisible(false);
        }).start();
	}
}
