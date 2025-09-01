package GUI;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

public class gameOverPantalla extends JFrame implements KeyListener {
	private boolean restart = false; // detecta si el jugador quiere reiniciar la partida
	private boolean exit = false; // detecta si el jugador quiere salir
	private JPanel panelOver;
	private Image fondoOver;

	public gameOverPantalla() {
		setBounds(0, 0, 800, 600);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		
		panelOver = new panelInicio();
        setContentPane(panelOver);
        
        
		fondoOver = new ImageIcon(getClass().getResource("/img/gameOver.png")).getImage();

	}
	
	
	private class panelInicio extends JPanel {
	       
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			
			g.drawImage(fondoOver, 0, 0, getWidth(), getHeight(), this); // dibujar fondo
			
			// frase game over o titulo principal
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 28));
			g.drawString("GAME OVER", 120, 180);
			
			
			// frase para reiniciar o subtitulo
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.PLAIN, 28));
			g.drawString("Presione ENTER para volver al menu", 100, 280);
			
			g.setFont(new Font("Arial", Font.PLAIN, 24));
			g.drawString("Presione ESC para salir", 160, 330);
			
			
			if(restart) { // si el jugador elige reiniciar la partida
				g.setColor(Color.GREEN);
				g.setFont(new Font("Arial", Font.BOLD, 36));
				dispose();
				menuInicio menuInicio = new menuInicio();
				menuInicio.setVisible(true);
				
			} else if(exit) { // si el jugador quiere salir
				g.setColor(Color.CYAN);
				g.setFont(new Font("Arial", Font.BOLD, 36));
				System.exit(0); // Cierra la aplicacións
				
			}
			
		}
    }
	
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { // aca se puede volver al menu o reiniciar el juego
			restart = true;
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { // aca se puede cerrar el juego
			exit = true;
			repaint();
		}
	}
	
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}	
	
	
}
