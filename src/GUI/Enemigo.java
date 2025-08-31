package GUI;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Enemigo extends JPanel {
	
	static ArrayList<Enemigo> enemigos = new ArrayList<>();
	
	private ImageIcon naveIcon;
	private boolean hacia_derecha = false;
	
	public Enemigo(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
	}

	public Enemigo(ImageIcon nave_enemiga) {
		this.naveIcon = nave_enemiga;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (naveIcon != null) {
	        g.drawImage(naveIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
	    }
	}
	
	public void movimiento(int ancho, int alto, int ancho_panel, int delay) {

		
		new Thread(() -> {	// Es un hilo, basicamente una ejecución paralela que corre junto al programa.
			

			
			int dx = 10;
			int dy = 50;
			
			try {
	            Thread.sleep(delay); // cada enemigo espera distinto tiempo antes de empezar, va aumentando en 200 entre cada enemigo
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
			
		    while (true) {  // Genera un bucle infinito, (SE PODRIA CAMBIAR POR "ENEMIGOVIVO"!!!)
		    	
		    	boolean tocaronBorde = false;
		    	
		    	int posX = getX();
		    	int posY = getY();
		    	
		    	int maxima_altura = ancho_panel / 2;
		    	
		    	
		    	for (Enemigo enemigo : enemigos) {
		    		if (enemigo.hacia_derecha && posX >= ancho_panel - ancho ) {

		    			tocaronBorde = true;
		    		}
		    		
		    		else if (!enemigo.hacia_derecha && posX <= 0) {

		    			tocaronBorde = true;
		    		}
		    	}
		    	
		    	if (tocaronBorde) {
		    		for (Enemigo enemigo : enemigos) {
		            enemigo.hacia_derecha = !enemigo.hacia_derecha;
		                enemigo.setLocation(enemigo.getX(), enemigo.getY() + dy);
		            }
		        } else {
		            for (Enemigo enemigo : enemigos) {
		                int nuevaX = enemigo.getX() + (enemigo.hacia_derecha ? dx : -dx);
		                enemigo.setLocation(nuevaX, enemigo.getY());
		            }
		        }
		    	
		    	posX += (hacia_derecha ? dx : -dx);	 // si la variable es TRUE utiliza dx. Si la variable es FALSE utiliza -dx 
		    	
		    	System.out.println("Enemigo en X: " + posX + " | Dirección: " + (hacia_derecha ? "Derecha" : "Izquierda"));

		    	

		    	
		    	/*
				if (posY >= maxima_altura - alto) {
		    		
		    	}
		    	*/
		    	
		    	// Aplica la nueva posición
	            setLocation(posX, posY);
	            repaint(); // fuerza el redibujado
		    	
		        try {
		            Thread.sleep(500); // Suspende el hilo por 30 ms, esta es la pausa entre cada iteración del bucle.
		        } catch (InterruptedException e) {	// Sucede si otro hilo interrumpe este hilo
		            Thread.currentThread().interrupt();	// Marca el hilo como interrumpido
		        }
		    }
		}).start();
		
	}
	
}
