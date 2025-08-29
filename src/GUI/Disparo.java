package GUI;

import javax.swing.JPanel;

public class Disparo extends JPanel {
	
	
	public Disparo(int posX, int posY, int ancho, int alto) {
		setBounds(posX, posY, ancho, alto);
		
	}
	
	int dy = 10;


	public void mover() {
		int posY = getY();
		int posX = getX();
		
		posY -= dy;
		
		setLocation(posX, posY);
	}
	
}
