package GUI;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Disparo extends JPanel {
	
	
	private JPanel panel;
	private List<Enemigo> enemigos;


	public Disparo(int posX, int posY, int ancho, int alto, JPanel panel, List<Enemigo> enemigos) {
		setBounds(posX, posY, ancho, alto);
		this.panel = panel;
		this.enemigos = enemigos;
	}
	
	int dy = 10;


	public void mover() {
		int posY = getY();
		int posX = getX();
		
		posY -= dy;
		
		setLocation(posX, posY);
	}
	
	public ArrayList<Enemigo> detectarColisiones(List<Enemigo> enemigos){							// se crea una nueva funcion para las colisiones con los bloques
		int posX = getX();
		int posY = getY();
		ArrayList<Enemigo> enemigoColisionado = new ArrayList<>();									// Se crea una nueva lista de arrays de los bloques colisionados
		for (Enemigo enemigo : enemigos) {																// Se hace un for each, donde se van a explorar cada bloque dentro de la lista de bloques
				if (posX <= enemigo.getX() + enemigo.getWidth() && posX + getWidth() >= enemigo.getX() &&
					posY <= enemigo.getY() + enemigo.getHeight() && posY + getHeight() >= enemigo.getY()) {				

							enemigoColisionado.add(enemigo);											// A la lista de colsiones se le agrega el bloque con el que colisiono.
								
						}
					
					
				}
		return enemigoColisionado;																	// La funcion devuelve la lista de colisiones
	}
	
}
