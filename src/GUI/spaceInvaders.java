package GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class spaceInvaders extends JFrame {
	
	private int anchoPanel=0;
	private boolean aPressed = false;
	private boolean dPressed = false;
	private boolean wPressed = false;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public spaceInvaders() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel(null);
		setResizable(false);
		setFocusable(true); // Es importante agregar esto para hacer focus en la ventana del juego
		requestFocusInWindow(); // El teclado hace focus solamente en la ventana del juego.

		int anchoPanel = contentPane.getWidth();
		
		ImageIcon nave = new ImageIcon("src/GUI/nave.png");
		
		setContentPane(contentPane);
		
		Player player = new Player (nave);
		player.setBounds(360, 470, 64, 64);
		player.setLocation(365, 480);
		player.setBackground(Color.GREEN);
		contentPane.add(player);
		
		addKeyListener(new KeyListener() {
			
		    public void keyTyped(KeyEvent e) {} // Se abre el listener para poder escuchar input del teclado en el juego.

		    public void keyPressed(KeyEvent e) {
		        int teclaPresionada = e.getKeyCode();
		        
		        if (teclaPresionada == KeyEvent.VK_A) { aPressed = true; }      // Actualiza los booleanos de las teclas cuando están presionadas.
		        if (teclaPresionada == KeyEvent.VK_D) { dPressed = true; }
		        if (teclaPresionada == KeyEvent.VK_W) { wPressed = true; }

		        if (aPressed) { player.moverIzquierda(); }
		        if (dPressed) { player.moverDerecha(contentPane.getWidth()); }
		        if (wPressed) { player.Disparar(contentPane); }
		    }

		    @Override
		    public void keyReleased(KeyEvent e) {
		        int teclaPresionada = e.getKeyCode();
		        if (teclaPresionada == KeyEvent.VK_A) { aPressed = false; }     // Actualiza los booleanos cuando una tecla es soltada.
		        if (teclaPresionada == KeyEvent.VK_D) { dPressed = false; }
		        if (teclaPresionada == KeyEvent.VK_W) { wPressed = false; }
		    }
			});
		

		


	}

}
