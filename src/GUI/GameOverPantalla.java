package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverPantalla extends JPanel implements KeyListener {
	private boolean restart = false; // detecta si el jugador quiere reiniciar la partida
	private boolean exit = false; // detecta si el jugador quiere salir
	
	
	
	public GameOverPantalla() {
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
	}
	
	
	@Override
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		// frase game over o titulo principal
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 28));
		g.drawString("GAME OVER", 120, 180);
		
		
		// frase para reiniciar o subtitulo
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.PLAIN, 28));
		g.drawString("Presione ENTER para reiniciar", 100, 280);
		
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		g.drawString("Presione ESC para salir", 160, 330);
		
		
		if(restart) { // si el jugador elige reiniciar la partida
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString("Reiniciando juego...", 140, 380);
			
		} else if(exit) { // si el jugador quiere salir
			g.setColor(Color.CYAN);
			g.setFont(new Font("Arial", Font.BOLD, 36));
			g.drawString("Saliendo del juego...", 140, 380);
			
		}
		
	}
	
	@Override
	
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
	
	public static void main(String[] args) {
		JFrame ventana = new JFrame("Space Invaders G3L - Game Over");
		GameOverPantalla panel = new GameOverPantalla();
		
		
		
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(600, 450);
		ventana.setResizable(false);
		ventana.add(panel);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		
		
		
	}
	
}
