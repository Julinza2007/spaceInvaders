package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class spaceInvaders extends JFrame {

    private static final long serialVersionUID = 1L;
    public static List<Enemigo> enemigos = new ArrayList<>();

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

    public spaceInvaders() {
        instance = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);
        setTitle("Space Invaders");
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();

       
        contentPane = new JPanel(null);
        setContentPane(contentPane);

        ImageIcon nave = new ImageIcon("src/img/nave.png");

        // puntaje 
        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);

        // Nivel
        nivelLabel = new JLabel("Nivel: " + nivel);
        nivelLabel.setBounds(700, 10, 100, 30);
        contentPane.add(nivelLabel);

        // Botón de prueba GameOver
        JButton btnGameOver = new JButton("btnGameOver");
        btnGameOver.addActionListener(e -> llamarGameOver());
        btnGameOver.setBounds(187, 243, 169, 23);
        contentPane.add(btnGameOver);

        // Player
        player = new Player(nave);
        player.setBounds(360, 480, 64, 64);
        player.setBackground(Color.GREEN);
        contentPane.add(player);

        if (contentPane.isAncestorOf(player)) {
            contentPane.setComponentZOrder(player, 1);
        }

        player.setPlayerListener(new PlayerListener() {
            @Override
            public void onPlayerEliminado(Player eliminado) {
                eliminarPlayer(eliminado);
                System.out.println("¡Jugador eliminado!");
            }
        });

        
        generarEnemigos(nivel);

        // Timer de colisiones
        colisionTimer = new Timer(50, e -> chequearColisiones());
        colisionTimer.start();

        // aca estan los controles del jugador
        addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                int teclaPresionada = e.getKeyCode();
                if (teclaPresionada == KeyEvent.VK_A) { aPressed = true; }
                if (teclaPresionada == KeyEvent.VK_D) { dPressed = true; }
                if (teclaPresionada == KeyEvent.VK_W) { wPressed = true; }

                if (aPressed) { player.moverIzquierda(); }
                if (dPressed) { player.moverDerecha(contentPane.getWidth()); }
                if (wPressed) { player.Disparar(contentPane); }
            }

            public void keyReleased(KeyEvent e) {
                int teclaPresionada = e.getKeyCode();
                if (teclaPresionada == KeyEvent.VK_A) { aPressed = false; }
                if (teclaPresionada == KeyEvent.VK_D) { dPressed = false; }
                if (teclaPresionada == KeyEvent.VK_W) { wPressed = false; }
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
            }
        }
        // esto actualiza el texto para ver en que nivel estas
        nivelLabel.setText("Nivel: " + nivel);
        contentPane.repaint();
    }

    // reinicia el nivel actual
    public void reiniciarNivel(int nivel) {
        colisionTimer.stop();
        for (Enemigo enemigo : enemigos) {
            contentPane.remove(enemigo);
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

    public void eliminarPlayer(Player player) {
        contentPane.remove(player);
        contentPane.repaint();
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
            if (enemigo.isVisible() && colisiona(player, enemigo)) {
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
        for (Component comp : contentPane.getComponents()) {
            if (comp instanceof DisparoEnemigo) {
                DisparoEnemigo disparo = (DisparoEnemigo) comp;
                if (colisionaDisparo(player, disparo)) {
                    puntaje.perderVida();
                    contentPane.remove(disparo);
                    contentPane.repaint();

                    if (puntaje.getVidas() == 0) {
                        eliminarPlayer(player);
                        colisionTimer.stop();
                        llamarGameOver();
                    }
                    break;
                }
            }
        }

        
        if(enemigos.isEmpty()) {
            nivel++;
            System.out.println("Nivel superado! ahora estás en el nivel " + nivel);
            puntaje.reiniciarVidas();
            reiniciarNivel(nivel);
           
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

    // Listener del Player
    public interface PlayerListener {
        void onPlayerEliminado(Player player);
    }
}
