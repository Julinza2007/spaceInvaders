package GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class spaceInvaders extends JFrame {

	static List<Enemigo> enemigos = new ArrayList<>();

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
		

		ImageIcon nave = new ImageIcon("src/GUI/nave.png");
		
//		ImageIcon nave_enemiga = new ImageIcon("src/GUI/nave_enemiga.jpg");
//		ImageIcon nave_enemiga = new ImageIcon("src/GUI/alien2.png");
		
		setContentPane(contentPane);
		
		Player player = new Player (nave);
		player.setBounds(360, 480, 64, 64);
		player.setBackground(Color.GREEN);
		contentPane.add(player);
		
		generarEnemigos();
		
//		int delay = 0;
		
//		for (int parte : panel_partes) {
//			Enemigo enemigo = new Enemigo (nave_enemiga);
//			enemigo.setBounds(parte, 50, 45, 35);
//			enemigo.setLocation(parte, 50);
//			contentPane.add(enemigo);
//			enemigo.repaint();
//			System.out.println(nave_enemiga.getImage());
//			delay += 200; // aumenta el delay entre enemigos en 200
//			enemigo.movimiento(83, 73, contentPane.getWidth(), delay);
//			Enemigo.enemigos.add(enemigo);
//		}
		

		
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
	
	public void generarEnemigos() {
	    int filas = 3;        // cantidad de filas de enemigos
	    int columnas = 5;    // cantidad de columnas
	    int inicioX = 50;     // punto inicial en X
	    int inicioY = 50;     // punto inicial en Y
	    int espaciadoX = 60;  // separación horizontal
	    int espaciadoY = 50;  // separación vertical

	    enemigos.clear(); // limpiar por si reiniciamos el juego

	    ImageIcon nave_enemiga = new ImageIcon("src/GUI/alien2.png");

	    int delay = (filas * columnas) * 1000 ; // para que los enemigos arranquen con un desfase
	    
	    for (int fila = 0; fila < filas; fila++) {

	        for (int col = 0; col < columnas; col++) {
	            int x = inicioX + col * espaciadoX;
	            int y = inicioY + fila * espaciadoY;

	            // ahora cada enemigo tiene icono
	            Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga);

	            contentPane.add(enemigo);   // 👈 agregar al panel
	            enemigos.add(enemigo);      // guardarlo en la lista
	            Enemigo.enemigos.add(enemigo); // para que se muevan en bloque

	            enemigo.repaint();
	            enemigo.movimiento(45, 35, contentPane.getWidth(), delay);

	            delay -= 500; // el próximo enemigo arranca después
	        }
	    }
	    
	    

	    contentPane.repaint();
	}


}
