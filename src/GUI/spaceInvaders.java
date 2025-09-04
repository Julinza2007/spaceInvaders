
package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class spaceInvaders extends JFrame {

	static List<Enemigo> enemigos = new ArrayList<>();

	private boolean aPressed = false;
	private boolean dPressed = false;
	private boolean wPressed = false;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Timer colisionTimer;
	private Player player;
	private Puntaje puntaje = new Puntaje(3); // 3 vidas iniciales
	private Image fondoJuego;

	public spaceInvaders() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600);
		setTitle("Space Invaders");
		contentPane = new JPanel(null);
		
		setResizable(false);
		setFocusable(true); // Es importante agregar esto para hacer focus en la ventana del juego
		requestFocusInWindow(); // El teclado hace focus solamente en la ventana del juego.
			
		
		Image fondoJuego = new ImageIcon(getClass().getResource("/img/fondo_juego.jpeg")).getImage();
		contentPane = new FondoPanel(fondoJuego);
		setContentPane(contentPane);


		ImageIcon nave = new ImageIcon("src/img/nave.png");
		
		setContentPane(contentPane);

        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);
		
		JButton btnGameOver = new JButton("btnGameOver");
		
			btnGameOver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					llamarGameOver();
				}
			});
		
			btnGameOver.setBounds(187, 243, 169, 23);
			contentPane.add(btnGameOver);
		
			player = new Player (nave);
			player.setBounds(360, 480, 64, 64);
			player.setBackground(Color.GREEN);
			contentPane.add(player);
		
			player.setPlayerListener(new PlayerListener() {
				@Override
				public void onPlayerEliminado(Player eliminado) {
					eliminarPlayer(eliminado);
					System.out.println("¡Jugador eliminado!");
				}
			});
			
		
			generarEnemigos();
			
			colisionTimer = new Timer(50, e -> chequearColisiones());
	        colisionTimer.start();
			
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

        ImageIcon nave_enemiga = new ImageIcon("src/img/alien2.png");
        ImageIcon nave_kamikaze = new ImageIcon("src/img/nave_kamikaze.png");
        ImageIcon nave_disparadora = new ImageIcon("src/img/ship2.png");

        int delay = (filas * columnas) * 1000 ; // para que los enemigos arranquen con un desfase
        
        for (int fila = 0; fila < filas; fila++) {

            for (int col = 0; col < columnas; col++) {
                int x = inicioX + col * espaciadoX;
                int y = inicioY + fila * espaciadoY;
                
                Random random = new Random(); 
                
                int tipo = random.nextInt(3) + 1; // Se genera un numero de 1 a 3. 
                
                // 1 Es el kamikaze
                if (tipo == 1) {
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_kamikaze, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque


                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth(), delay);
                }
                
                // 2 Es el disparador
                if (tipo == 2) {
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_disparadora, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque


                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth(), delay);
                }
                
                // 3 Es el normal
                if (tipo == 3) {
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque

                    
                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth(), delay);
                }
                
                // ahora cada enemigo tiene icono


                
                

                delay -= 500; // el próximo enemigo arranca después
            }
        }
        
        
        contentPane.repaint();
    }
		    
		    
		    

		    public void eliminarPlayer(Player player) {
		        contentPane.remove(player);
		        contentPane.repaint();
		    }
		    
		    private void chequearColisiones() {      
		        for (int i = 0; i < enemigos.size(); i++) {
		            Enemigo enemigo = enemigos.get(i);
		            if (enemigo.isVisible() && colisiona(player, enemigo)) {
		                puntaje.perderVida();
		                enemigo.setVisible(false);
		                contentPane.remove(enemigo);
		                enemigos.remove(i);
		                i--;
		                
		                contentPane.repaint();

		                if (puntaje.getVidas() == 0) {
		                    eliminarPlayer(player);
		                    colisionTimer.stop();
		                }
		                break;
		            }
		        }
		        // Colisión con disparos enemigos
		        for (Component comp : contentPane.getComponents()) {
		            if (comp instanceof DisparoEnemigo) {
		                DisparoEnemigo disparo = (DisparoEnemigo) comp;
		                if (colisiona_disparo(player, disparo)) {
		                    puntaje.perderVida();
		                    contentPane.remove(disparo);
		                    contentPane.repaint();

		                    if (puntaje.getVidas() == 0) {
		                        eliminarPlayer(player);
		                        colisionTimer.stop();
		                    }
		                    break;
		                }
		            }
		        }
		    }

		    private boolean colisiona_disparo(Player p, DisparoEnemigo d) {
		        return p.getX() < d.getX() + d.getWidth() &&
		               p.getX() + p.getWidth() > d.getX() &&
		               p.getY() < d.getY() + d.getHeight() &&
		               p.getY() + p.getHeight() > d.getY();
		    }
		    
		    private boolean colisiona(Player p, Enemigo e) {
		        return p.getX() < e.getX() + e.getWidth() &&
		               p.getX() + p.getWidth() > e.getX() &&
		               p.getY() < e.getY() + e.getHeight() &&
		               p.getY() + p.getHeight() > e.getY();
		    }
		    
		    
		    public interface PlayerListener {
		        void onPlayerEliminado(Player player);
		    }
	

	public void llamarGameOver() {
		gameOverPantalla gameOverPantalla = new gameOverPantalla(); // Se crea una nueva instancia de la clase spaceInvaders.
        gameOverPantalla.setVisible(true);
        this.dispose();

	}
}