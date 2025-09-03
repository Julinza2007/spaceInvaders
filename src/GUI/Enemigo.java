package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.ArrayList;


public class Enemigo extends JPanel {
	
	static ArrayList<Enemigo> enemigos = new ArrayList<>();
	
	private ImageIcon naveIcon;
	private boolean hacia_derecha = true;

	private int tipo;
	
	public Enemigo(int posX, int posY, int ancho, int alto, ImageIcon nave_enemiga, int tipo) {
		setBounds(posX, posY, ancho, alto);
		this.naveIcon = nave_enemiga;
		this.tipo = tipo;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (naveIcon != null) {
	        g.drawImage(naveIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
	    }
	}
	
	public void enemigoKamikaze(int posX, int posY, int ancho, int alto, ImageIcon nave_enemiga) {
		boolean lanzado = false;
		
		setBounds(posX, posY, ancho, alto);
		this.naveIcon = nave_enemiga;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
		
		
		
	  
	
	public void movimiento(int ancho, int alto, int ancho_panel, int delay) {

		
		new Thread(() -> {	// Es un hilo, basicamente una ejecución paralela que corre junto al programa.
			

			
			int dx = 60;
			int dy = 50;
			
			boolean lanzado = false;
			
			
			
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
		    	
		    	if (tipo == 1 && !lanzado && getY() >= 300) {
		    	    lanzado = true;
		    	    if (lanzado) {
			    		// Lanzarse hacia el jugador
			    		
		    	    	int playerY = SpaceInvaders.player.getY();
			    		int playerX = SpaceInvaders.player.getX();
			    		
			    		int dirX = (playerY > posX) ? 5 : -5;
			    		int dirY = (playerX > posY) ? 5 : -5;
			    		
			    		posX += dirX;
			    		posY += dirY;
			    	}
			    	
		    	}
		    	
		    	if (tipo == 2 && !lanzado && getY() >= 300) {
		    		new Timer(2000, e -> disparar()).start();
		    	}
		    	
		    	
		    	// Verifica si está por salirse ANTES de mover
	            if (hacia_derecha && posX + getWidth() >= 700) {
	                hacia_derecha = false;
	                posY += dy;
	            } else if (!hacia_derecha && posX - getWidth() <= 0) {
	                hacia_derecha = true;
	                posY += dy;
	            }

	  
		    	
		    	posX += (hacia_derecha ? dx : -dx);	 // si la variable es TRUE utiliza dx. Si la variable es FALSE utiliza -dx 
		    	
//		    	System.out.println("Enemigo en X: " + posX + " | Dirección: " + (hacia_derecha ? "Derecha" : "Izquierda"));

		    	

		    	
		    	/*
				if (posY >= maxima_altura - alto) {
		    		
		    	}
		    	*/
		    	
		    	// Aplica la nueva posición
	            setLocation(posX, posY);
	            repaint(); // fuerza el redibujado
		    	
		        try {
		            Thread.sleep(250); // Suspende el hilo por 30 ms, esta es la pausa entre cada iteración del bucle.
		        } catch (InterruptedException e) {	// Sucede si otro hilo interrumpe este hilo
		            Thread.currentThread().interrupt();	// Marca el hilo como interrumpido
		        }
		    }
		}).start();
		
	}
	private void disparar() {
        DisparoEnemigo disparoenemigo = new DisparoEnemigo(getX() + getWidth() / 2, getY() + getHeight(), 10, 10);
        
        spaceInvaders.contentPane.add(disparoenemigo);
        disparo.mover();
    }
	
}
