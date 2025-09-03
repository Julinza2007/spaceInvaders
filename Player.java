package GUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.spaceInvaders.PlayerListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Player extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private long ultimoDisparo = 0; // Momento en milisegundos del último disparo
	private int tiempoRecarga = 300; // Tiempo mínimo entre disparos (ms)
	private ImageIcon naveIcon;
	private PlayerListener listener;
    private ArrayList<Disparo> disparos = new ArrayList<>();

	
	public Player(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
	}
	
	public Player(ImageIcon nave) {
		this.naveIcon = nave;
		setOpaque(false); // esto sirve para que el fondo no tape la imagen
	}
	
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);	// Limpia el fondo antes de dibujar, util para que no se superpongan dibujos viejos
		if (naveIcon != null) {
			g.drawImage(naveIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
			// naveIcon.getImage(): trae la imagen cruda de la nave
			// 0, 0: indica la posicion donde se va a dibujar la imagen
			// getWidth(), getHeight(): escala la imagen segun el tamaño del panel 
			// this: referencia al componente actual
		}
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
	
	public void setPlayerListener(PlayerListener listener) {
	    this.listener = listener;
	    

	}
	
	public ArrayList<Enemigo> detectarChoques(List<Enemigo> enemigos) {
		
		ArrayList<Enemigo> enemigoChocado = new ArrayList<>();
		for (Enemigo enemigo : enemigos) {
			if (this.getBounds().intersects(enemigo.getBounds())) {
					enemigoChocado.add(enemigo);
			}
		}
		return enemigoChocado;
	}
	
	
	
	public void Disparar(JPanel panel) {
		long ahora = System.currentTimeMillis();
		if (ahora - ultimoDisparo < tiempoRecarga) {
		    return; // Todavía no pasó el tiempo de recarga
		}
		
		else {
			ultimoDisparo = ahora; // Actualizamos el momento del último disparo
			int disparoX = getX() + getWidth() / 2 - 5;
			int disparoY = getY() - 50;

			Disparo disparo = new Disparo (disparoX, disparoY, 5, 25, panel, spaceInvaders.enemigos);
			disparo.setBackground(Color.RED);
			panel.add(disparo);
			disparos.add(disparo); // se agrega el disparo a la lista
			
		
			// Timer para mover el disparo hacia arriba
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int nuevaY = disparo.getY() - 25;
					if (nuevaY > 0) {
						disparo.setLocation(disparo.getX(), nuevaY);
						
						
						ArrayList<Enemigo> aEliminar = new ArrayList<>();
						ArrayList<Enemigo> colisionados = disparo.detectarColisiones(spaceInvaders.enemigos);
						for (Enemigo enemigo : colisionados) {
							panel.remove(enemigo);
							aEliminar.add(enemigo);
//
							panel.remove(disparo);
							disparos.remove(disparo); // se elimina el disparo de la lista cuando colisiona
						}
						colisionados.removeAll(aEliminar);
						panel.repaint();
						
						
						
						spaceInvaders.enemigos.removeAll(colisionados);
						panel.repaint();
						
						ArrayList<Enemigo> choquePlayer = detectarChoques(spaceInvaders.enemigos);
						if (!choquePlayer.isEmpty() && listener != null) {
						    listener.onPlayerEliminado(Player.this);
						}

						
						/*
						ArrayList<Enemigo> choquePlayer = detectarChoques(spaceInvaders.enemigos);
						for (Enemigo enemigo : choquePlayer) {
							spaceInvaders.eliminarPlayer(this);
						}
						*/
						
					}
					else {
						((Timer) e.getSource()).stop(); // Detiene el timer
						panel.remove(disparo);          // Elimina el disparo del panel
					    disparos.remove(disparo);  // Se elimina el disparo del array cuando llega al tope
					    panel.repaint();
					}
				}
			});
			timer.start();
		}
		
	}
	public void limpiarBalas(JPanel panel) {
	    for (Disparo disparo : disparos) {
	        if (disparo.isVisible()) {
	            panel.remove(disparo);
	        }
	    }
	    disparos.clear();
	    panel.repaint();
}
}