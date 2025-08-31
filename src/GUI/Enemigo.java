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


public class Enemigo extends JPanel {
	
	private ImageIcon naveIcon;
	
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
	
}
