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

	private Player player;

	private JPanel panel;
	
	public Enemigo(int posX, int posY, int ancho, int alto, ImageIcon nave_enemiga, int tipo, Player player, JPanel panel) {
		setBounds(posX, posY, ancho, alto);
		this.naveIcon = nave_enemiga;
		this.tipo = tipo;
		this.player = player;
		this.panel = panel;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (naveIcon != null) {
	        g.drawImage(naveIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
	    }
	}
	
	public void enemigoKamikaze(ImageIcon nave_enemiga) {
		boolean lanzado = false;

		this.naveIcon = nave_enemiga;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
		
		
		
	  
	
	public void movimiento(int ancho, int alto, int ancho_panel) {

		
		new Thread(() -> {	// Es un hilo, basicamente una ejecución paralela que corre junto al programa.
			

			
			int dx = 60;
			int dy = 50;
			
			boolean lanzado = false;
			boolean disparando = false;
			int playerY = 0;
    		int playerX = 0; 
			
			
			
			try {
	            Thread.sleep(0); // cada enemigo espera distinto tiempo antes de empezar, va aumentando en 200 entre cada enemigo
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
			
		    while (true) {  // Genera un bucle infinito, (SE PODRIA CAMBIAR POR "ENEMIGOVIVO"!!!)
		    	
		    	
		    	
		    	boolean tocaronBorde = false;
		    	
		    	int posX = getX();
		    	int posY = getY();
		    	

		    	// Verificar si el enemigo salió del panel
		    	if (posX < -getWidth() || posX > panel.getWidth() ||
		    	    posY < -getHeight() || posY > panel.getHeight()) {

		    	    // Eliminar del panel y lista
		    	    panel.remove(this);
		    	    enemigos.remove(this);
		    	    panel.repaint();
		    	    break; // salir del hilo
		    	}
		    	
		    	int maxima_altura = ancho_panel / 2;

		    	if (tipo == 2 && !disparando && getY() <= 300) {
		    	    disparando = true;
		    	    new Timer(2000, e -> disparar()).start();
		    	}
		    	
		    	if (tipo == 1 && !lanzado && getY() >= 300) {
		    		lanzado = true;
		    		playerY = player.getY();
		    		playerX = player.getX();
		    		}
		    	    if (lanzado) {
			    		// Lanzarse hacia el jugador
			    		
		    	    	
			    		
			    		int dirY = (playerY > posY) ? 25 : -25;
			    		int dirX = (playerX > posX) ? 25 : -25;
			    		
			    		posX += dirX;
			    		posY += dirY;
			    		if (Math.abs(posX - playerX) < 25 && Math.abs(posY - playerY) < 25) { 
			    			// se ve si el kamikaze llego a donde buscaba ir. se usa math.abs para que no importe el signo
			    			
			    			panel.remove(this);
			    			enemigos.remove(this);
			    			panel.repaint();
			    			break;
			    		}
			    	}
		    	    else if (!lanzado) {
		    	    	// Verifica si está por salirse ANTES de mover
			            if (hacia_derecha && posX + getWidth() >= 700) {
			                hacia_derecha = false;
			                posY += dy;
			            } else if (!hacia_derecha && posX - getWidth() <= 0) {
			                hacia_derecha = true;
			                posY += dy;
			            }
				    	posX += (hacia_derecha ? dx : -dx);	 // si la variable es TRUE utiliza dx. Si la variable es FALSE utiliza -dx 

		    	    }

		    	
		    	
		    	// Aplica la nueva posición
	            setLocation(posX, posY);
	            repaint(); // fuerza el redibujado
		    	
		        try {


		            Thread.sleep(200); // Suspende el hilo por 30 ms, esta es la pausa entre cada iteración del bucle.

		        } catch (InterruptedException e) {	// Sucede si otro hilo interrumpe este hilo
		            Thread.currentThread().interrupt();	// Marca el hilo como interrumpido
		        }
		    }
		}).start();
		
	}
	private void disparar() {
        DisparoEnemigo disparoenemigo = new DisparoEnemigo(getX() + getWidth() / 2, getY() + getHeight(), 10, 10);
        
        panel.add(disparoenemigo);
        disparoenemigo.mover();
    }
	
}