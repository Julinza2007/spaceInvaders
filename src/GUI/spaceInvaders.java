
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

	private static final long serialVersionUID = 1L;

static List<Enemigo> enemigos = new ArrayList<>();
private Puntaje puntaje = new Puntaje(3); // 3 vidas iniciales
private Player player;
private boolean aPressed = false;
private boolean dPressed = false;
private boolean wPressed = false;
private JPanel contentPane;
private Timer colisionTimer;
private boolean gameOverMostrado = false;
private static spaceInvaders instance;
	private Image fondoJuego;

	public spaceInvaders() {
		instance = this;
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


        ImageIcon nave = new ImageIcon("src/img/nave.png"); // 👈 ojo acá, revisá si es "GUI" o "img"
        setContentPane(contentPane);

        // Configurar puntaje en pantalla
        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);
        

        player = new Player(nave);
        player.setBounds(360, 480, 64, 64);
        player.setBackground(Color.GREEN);
        contentPane.add(player);

        if (contentPane.isAncestorOf(player)) {
            contentPane.setComponentZOrder(player, 1);
        }

        player.setPlayerListener(eliminado -> {
            eliminarPlayer(eliminado);
            System.out.println("¡Jugador eliminado!");
        });

        generarEnemigos();
        

        colisionTimer = new Timer(50, e -> chequearColisiones());
        colisionTimer.start();

        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();
                if (tecla == KeyEvent.VK_A) aPressed = true;
                if (tecla == KeyEvent.VK_D) dPressed = true;
                if (tecla == KeyEvent.VK_W) wPressed = true;

                if (aPressed) player.moverIzquierda();
                if (dPressed) player.moverDerecha(contentPane.getWidth());
                if (wPressed) player.Disparar(contentPane);
            }
            public void keyReleased(KeyEvent e) {
                int tecla = e.getKeyCode();
                if (tecla == KeyEvent.VK_A) aPressed = false;
                if (tecla == KeyEvent.VK_D) dPressed = false;
                if (tecla == KeyEvent.VK_W) wPressed = false;
            }
        });

		}
	
		
	
	

    public static spaceInvaders getInstance() {
        return instance;
    }

    public void generarEnemigos() {
        int filas = 3;
        int columnas = 5;
        int inicioX = 50;
        int inicioY = 50;
        int espaciadoX = 60;
        int espaciadoY = 50;

        enemigos.clear();
        
        ImageIcon nave_enemiga = new ImageIcon("src/img/alien2.png");
        ImageIcon nave_kamikaze = new ImageIcon("src/img/nave_kamikaze.png");
        ImageIcon nave_disparadora = new ImageIcon("src/img/ship2.png");

//        int delay = (filas * columnas) * 1000; // 👈 delay progresivo

//        int delay = 0;
        
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
                    enemigo.movimiento(45, 35, contentPane.getWidth());
                    contentPane.setComponentZOrder(enemigo, 2);
                }
                
                // 2 Es el disparador
                if (tipo == 2) {
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_disparadora, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque


                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth());
                    contentPane.setComponentZOrder(enemigo, 2);
                }
                
                // 3 Es el normal
                if (tipo == 3) {
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque

                    
                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth());
                    contentPane.setComponentZOrder(enemigo, 2);
                }
                
                
                // ahora cada enemigo tiene icono


                
 /*               
=======
                Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga);
                contentPane.add(enemigo);
                contentPane.setComponentZOrder(enemigo, 2);
                enemigos.add(enemigo);
                Enemigo.enemigos.add(enemigo);

                enemigo.repaint();
                enemigo.movimiento(45, 35, contentPane.getWidth(), 1500);
>>>>>>> f7e525db78c75ddfca4662670058e3032b62151d
*/
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
		                
		                JLabel explosion = new JLabel(new ImageIcon("src/img/explosionJugador.gif"));
		            	explosion.setBounds(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		            	contentPane.add(explosion);
		                
		                enemigo.setVisible(false);
		                contentPane.remove(enemigo);
		                enemigos.remove(i);
		                i--;
		                
		                if (contentPane.isAncestorOf(explosion)) {
		            	    contentPane.setComponentZOrder(explosion, 0);
		            	}
		                
		                
		                contentPane.repaint();
		                
		                Timer timerExplosion = new Timer(2000, e2 -> {
		            	    contentPane.remove(explosion);
		            	    contentPane.repaint();

		            	    if (puntaje.getVidas() == 0 && !gameOverMostrado) {
		            	        gameOverMostrado = true;
		            	        colisionTimer.stop();
		            	        llamarGameOver();
		            	    } else {
		            	        reiniciarNivel();
		            	    }
		            	});
		            	timerExplosion.setRepeats(false);
		            	timerExplosion.start();

		              
		                break;
		            }
		        }
		        // Colisión con disparos enemigos
		        for (Component comp : contentPane.getComponents()) {
		            if (comp instanceof DisparoEnemigo) {
		                DisparoEnemigo disparo = (DisparoEnemigo) comp;
		                if (colisiona_disparo(player, disparo)) {
		                    puntaje.perderVida();
		                    
		                    JLabel explosion = new JLabel(new ImageIcon("src/img/explosionJugador.gif"));
		                	explosion.setBounds(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		                	
		                	if (contentPane.isAncestorOf(explosion)) {
			            	    contentPane.setComponentZOrder(explosion, 0);
			            	}
			                
			                
			                Timer timerExplosion = new Timer(2000, e2 -> {
			            	    contentPane.remove(explosion);
			            	    contentPane.repaint();

			            	    if (puntaje.getVidas() == 0 && !gameOverMostrado) {
			            	        gameOverMostrado = true;
			            	        colisionTimer.stop();
			            	        llamarGameOver();
			            	    } else {
			            	        reiniciarNivel();
			            	    }
			            	});
			            	timerExplosion.setRepeats(false);
			            	timerExplosion.start();
			            	
		                	contentPane.add(explosion);
		                    
		                    contentPane.remove(disparo);
		                    contentPane.repaint();

		                    if (puntaje.getVidas() == 0) {
		                        eliminarPlayer(player);
		                        colisionTimer.stop();
		                        reiniciarNivel();
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
	


    

    public void reiniciarNivel() {
        colisionTimer.stop();
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof Enemigo) {
                contentPane.remove(comp);
            }
        }
        
     // Eliminar disparos enemigos
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof DisparoEnemigo) {
                contentPane.remove(comp);
            }
        }
        

        enemigos.clear();
        Enemigo.enemigos.clear();

        player.limpiarBalas(contentPane);


        if (!contentPane.isAncestorOf(player)) {
			contentPane.add(player);
		}
        
		 if (contentPane.isAncestorOf(player)) {
			 contentPane.setComponentZOrder(player, 1);
		 }
        
        player.setLocation(360, 480);
        player.setVisible(true);

        generarEnemigos();
        colisionTimer.start();
    }



    public void llamarGameOver() {
        gameOverPantalla gameOverPantalla = new gameOverPantalla();
        gameOverPantalla.setVisible(true);
        this.dispose();
    }




    


    public void sumarPuntos() {
        puntaje.sumarPuntos(100);
    }

   
}
/*
>>>>>>> f7e525db78c75ddfca4662670058e3032b62151d
*/