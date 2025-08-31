package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class spaceInvaders extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public spaceInvaders() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		setTitle("Space Invaders");
		contentPane = new JPanel(null);
		setResizable(false);
		setFocusable(true); // Es importante agregar esto para hacer focus en la ventana del juego
		requestFocusInWindow(); // El teclado hace focus solamente en la ventana del juego.

				
		
		setContentPane(contentPane);

	}

}