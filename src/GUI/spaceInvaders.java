package GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
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

    public spaceInvaders() {
        instance = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 800, 600);
        setTitle("Space Invaders");
        contentPane = new JPanel(null);
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();

        ImageIcon nave = new ImageIcon("src/img/nave.png"); // 👈 ojo acá, revisá si es "GUI" o "img"
        setContentPane(contentPane);

        // Configurar puntaje en pantalla
        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);
        contentPane.setComponentZOrder(puntaje, 0); // Asegura que el puntaje esté al frente

        // Botón provisorio Game Over
        JButton btnGameOver = new JButton("btnGameOver");
        btnGameOver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                llamarGameOver();
            }
        });
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

        generarEnemigos();

        // Timer de colisiones
        colisionTimer = new Timer(50, e -> chequearColisiones());
        colisionTimer.start();

        // Controles
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

    public void generarEnemigos() {
        int filas = 3;
        int columnas = 5;
        int inicioX = 50;
        int inicioY = 50;
        int espaciadoX = 60;
        int espaciadoY = 50;

        enemigos.clear();
        ImageIcon nave_enemiga = new ImageIcon("src/img/alien2.png");

//        int delay = (filas * columnas) * 1000; // 👈 delay progresivo

//        int delay = 0;
        
        for (int fila = 0; fila < filas; fila++) {
            for (int col = 0; col < columnas; col++) {
                int x = inicioX + col * espaciadoX;
                int y = inicioY + fila * espaciadoY;

                Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga);
                contentPane.add(enemigo);
                contentPane.setComponentZOrder(enemigo, 2);
                enemigos.add(enemigo);
                Enemigo.enemigos.add(enemigo);

                enemigo.repaint();
                enemigo.movimiento(45, 35, contentPane.getWidth(), 1500);

            }
        }
        contentPane.repaint();
    }

    public void reiniciarNivel() {
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

        generarEnemigos();
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

            	enemigo = enemigos.get(i);
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
            	        reiniciarNivel();
            	    }
            	});
            	timerExplosion.setRepeats(false);
            	timerExplosion.start();
                
                
                enemigo.setVisible(false);
                contentPane.remove(enemigo);
                enemigos.remove(i);
                i--;

                contentPane.repaint();

                break;
            }
        }
    }

    private boolean colisiona(Player p, Enemigo e) {
        return p.getX() < e.getX() + e.getWidth() &&
               p.getX() + p.getWidth() > e.getX() &&
               p.getY() < e.getY() + e.getHeight() &&
               p.getY() + p.getHeight() > e.getY();
    }

    public void sumarPuntos() {
        puntaje.sumarPuntos(100);
    }

    public interface PlayerListener {
        void onPlayerEliminado(Player player);
    }
}
