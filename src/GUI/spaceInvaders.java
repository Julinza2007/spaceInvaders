/* Lista de errores:

1. El nivel, al completarlo, no avanza correctamente, posee retrasos a la hora de cambiarlo. Además, como error fatal sucede que en el  mismo momento de retraso de cambiar de nivelnuevos 
se agregan enemigos random otra vez, y ni siquiera el nivel había avanzado. A lo mejor es un error de solamente que no se cambia a tiempo en la etiqueta, o quizá sea realmente un problema
real de la estructura.

2. Otra cosa es que yo programé que la nave explote por cada muerte del jugador, y no se estaría haciendo. A veces funciona, pero solo a lo último funciona (cuando al jugador se le quitan todas las vidas), pero a veces sucede.

*/


package GUI;

import java.awt.Color;
import java.awt.Component;
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
private int nivel = 1; // nivel actual
private JLabel nivelLabel; // etiqueta para mostrar nivel en pantalla
private boolean jugadorInvulnerable = false;
	


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


        // puntaje 
        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);


        // Nivel
        nivelLabel = new JLabel("Nivel: " + nivel);
        nivelLabel.setBounds(700, 10, 100, 30);
        nivelLabel.setForeground(Color.WHITE);
        contentPane.add(nivelLabel);

        

        // Player
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

        generarEnemigos(nivel);
        

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

    // generamos enemigos segun el nivel 
    public void generarEnemigos(int nivel) {
        int filas = 3 + (nivel - 1);
        int columnas = 5 + (nivel - 1);
        int inicioX = 50;
        int inicioY = 50;
        int espaciadoX = 60;
        int espaciadoY = 50;

        enemigos.clear();
        
        ImageIcon nave_enemiga = new ImageIcon("src/img/alien2.png");
        ImageIcon nave_kamikaze = new ImageIcon("src/img/nave_kamikaze.png");
        ImageIcon nave_disparadora = new ImageIcon("src/img/ship2.png");

        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                int x = inicioX + col * espaciadoX;
                int y = inicioY + fila * espaciadoY;

                Random random = new Random();
                int tipo = random.nextInt(3) + 1;

                Enemigo enemigo = null;
                if (tipo == 1) {
                	/*
<<<<<<< HEAD
                	Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_kamikaze, tipo, player, contentPane);
                	contentPane.add(enemigo);   //  agregar al panel
                    enemigos.add(enemigo);      // guardarlo en la lista
                    Enemigo.enemigos.add(enemigo); // para que se muevan en bloque


                    
                    enemigo.repaint();
                    enemigo.movimiento(45, 35, contentPane.getWidth());
                    contentPane.setComponentZOrder(enemigo, 2);
=======
*/
                    enemigo = new Enemigo(x, y, 45, 35, nave_kamikaze, tipo, player, contentPane);
                } else if (tipo == 2) {
                    enemigo = new Enemigo(x, y, 45, 35, nave_disparadora, tipo, player, contentPane);
                } else {
                    enemigo = new Enemigo(x, y, 45, 35, nave_enemiga, tipo, player, contentPane);
                }
                contentPane.add(enemigo);
                contentPane.setComponentZOrder(enemigo, 2);
                enemigos.add(enemigo);
                Enemigo.enemigos.add(enemigo);

                enemigo.repaint();
                
                // aumentamos la velocidad por cada nivel
                int velocidad = Math.max(200, 1500 - (nivel * 100));
                enemigo.movimiento(45, 35, contentPane.getWidth(), velocidad);
                contentPane.setComponentZOrder(enemigo, 2);
            }
        }
     // esto actualiza el texto para ver en que nivel estas
        nivelLabel.setText("Nivel: " + nivel);
        contentPane.repaint();
    }

  
        

		    public void eliminarPlayer(Player player) {
		        contentPane.remove(player);
		        contentPane.repaint();
		    }
		    
		    
		    
		    public interface PlayerListener {
		        void onPlayerEliminado(Player player);
		    }
	


    


    // reinicia el nivel actual
    public void reiniciarNivel(int nivel) {
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

        generarEnemigos(nivel);
        colisionTimer.start();
    }



    public void llamarGameOver() {
        gameOverPantalla gameOverPantalla = new gameOverPantalla();
        gameOverPantalla.setVisible(true);
        this.dispose();
    }


    // Detección de colisiones
    private void chequearColisiones() {
        for (int i = 0; i < enemigos.size(); i++) {
            if (contentPane.isAncestorOf(player)) {
                contentPane.setComponentZOrder(player, 1);
            }
            Enemigo enemigo = enemigos.get(i);
            if (/*enemigo.isVisible() && */colisiona(player, enemigo)) {
            	
            	System.out.println("\n\n\n\nHOLA ENTRÉ EN ESTA COLISIÓN\n\n\n\n");
                colisionTimer.stop();
                puntaje.perderVida();

                JLabel explosion = new JLabel(new ImageIcon("src/img/explosionJugador.gif"));
                explosion.setBounds(player.getX(), player.getY(), player.getWidth(), player.getHeight());
                contentPane.add(explosion);

                if (contentPane.isAncestorOf(explosion)) {
                    contentPane.setComponentZOrder(explosion, 0);
                }

                contentPane.repaint();
                player.setVisible(false);

                enemigo.setVisible(false);
                contentPane.remove(enemigo);
                enemigos.remove(i);
                i--;

                Timer timerExplosion = new Timer(2000, e2 -> {
                    contentPane.remove(explosion);
                    contentPane.repaint();

                    if (puntaje.getVidas() == 0 && !gameOverMostrado) {
                        gameOverMostrado = true;
                        colisionTimer.stop();
                        llamarGameOver();
                    } else {
                        reiniciarNivel(nivel);
                    }
                });
                timerExplosion.setRepeats(false);
                timerExplosion.start();

                break;
            }
        }

        // Colisión con disparos enemigos
        
        
   if(!jugadorInvulnerable) {
        	
        
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof DisparoEnemigo) {
                DisparoEnemigo disparo = (DisparoEnemigo) comp;
                
                if (colisionaDisparo(player, disparo)) {
                    puntaje.perderVida();
                    contentPane.remove(disparo);
                    JLabel explosion = new JLabel(new ImageIcon("src/img/explosionJugador.gif"));
                    explosion.setBounds(player.getX(), player.getY(), player.getWidth(), player.getHeight());
                    contentPane.add(explosion);

                    if (contentPane.isAncestorOf(explosion)) {
                        contentPane.setComponentZOrder(explosion, 0);
                    }

                    contentPane.repaint();
                    player.setVisible(false);

                    
                    jugadorInvulnerable = true;
                    
                    Timer timerExplosion = new Timer(2000, e2 -> {
                        contentPane.remove(explosion);
                        contentPane.repaint();
                        jugadorInvulnerable = false;

                        if (puntaje.getVidas() == 0 && !gameOverMostrado) {
                            gameOverMostrado = true;
                            colisionTimer.stop();
                            llamarGameOver();
                        } else {
                            reiniciarNivel(nivel);
                        }
                    });
                    timerExplosion.setRepeats(false);
                    timerExplosion.start();

                    break;
                }
            }
        }
        
   }

        
        if(enemigos.isEmpty()) {
            nivel++;
            System.out.println("Nivel superado! ahora estás en el nivel " + nivel);
            puntaje.reiniciarVidas();
            
            Timer delayNivel = new Timer(1000, e3 -> reiniciarNivel(nivel));
            delayNivel.start();
        }            
           
        }

    // Colisiones
    private boolean colisionaDisparo(Player p, DisparoEnemigo d) {
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


    // Puntaje
    public void sumarPuntos() {
        puntaje.sumarPuntos(100);
    }


    
}

