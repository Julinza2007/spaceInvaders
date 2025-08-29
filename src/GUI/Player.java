package GUI;

import java.awt.Color;
import java.awt.Desktop.Action;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private long ultimoDisparo = 0; // Momento en milisegundos del último disparo
	private int tiempoRecarga = 300; // Tiempo mínimo entre disparos (ms)
	
	public Player(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
	}
	
	public void moverDerecha(int anchoPanel) {
		int posX = getX();
		int posY = getY();
		System.out.println("Posición eje X del jugador: " + posX + "\n");
		if (posX + getWidth() < anchoPanel) {
			setLocation(posX + 15, posY);
		}
	}
	
	public void moverIzquierda() {
		int posX = getX();
		int posY = getY();
		
		System.out.println("Posición eje X del jugador: " + posX + "\n");
		
		if (posX > 0) {
			setLocation(posX - 15, posY);
		}
	}
	public void Disparar(JPanel panel) {
		long ahora = System.currentTimeMillis();
		if (ahora - ultimoDisparo < tiempoRecarga) {
		    return; // Todavía no pasó el tiempo de recarga
		}
		
		else {
			ultimoDisparo = ahora; // Actualizamos el momento del último disparo
			int disparoX = getX() + getWidth() / 2 - 5;
			int disparoY = getY() / 2 - 10;

			Disparo disparo = new Disparo (disparoX, disparoY, 10, 50);
			disparo.setBackground(Color.RED);
			panel.add(disparo);
		
		
			// Timer para mover el disparo hacia arriba
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int nuevaY = disparo.getY() - 5;
					if (nuevaY > 0) {
						disparo.setLocation(disparo.getX(), nuevaY);
					}
					else {
						((Timer) e.getSource()).stop(); // Detiene el timer
						panel.remove(disparo);          // Elimina el disparo del panel
						panel.repaint();
					}
				}
			});
			timer.start();
		}
	}
}
