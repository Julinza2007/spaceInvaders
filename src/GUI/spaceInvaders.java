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
import javax.swing.Timer;

public class spaceInvaders extends JFrame {

    static List<Enemigo> enemigos = new ArrayList<>();
    private Puntaje puntaje = new Puntaje(3); // 3 vidas iniciales
    private Player player;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean wPressed = false;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Timer colisionTimer;

    public spaceInvaders() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel(null);
        setResizable(false);
        setFocusable(true); // Es importante agregar esto para hacer focus en la ventana del juego
        requestFocusInWindow(); // El teclado hace focus solamente en la ventana del juego.

        ImageIcon nave = new ImageIcon("src/GUI/nave.png");
        setContentPane(contentPane);
        
        // Configurar puntaje en pantalla
        puntaje.setBounds(10, 10, 200, 30);
        contentPane.add(puntaje);

        player = new Player(nave);
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
        
        // Timer para verificar colisiones continuamente
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

        ImageIcon nave_enemiga = new ImageIcon("src/GUI/alien2.png");

        int delay = (filas * columnas) * 1000 ; // para que los enemigos arranquen con un desfase
        
        for (int fila = 0; fila < filas; fila++) {

            for (int col = 0; col < columnas; col++) {
                int x = inicioX + col * espaciadoX;
                int y = inicioY + fila * espaciadoY;

                // ahora cada enemigo tiene icono
                Enemigo enemigo = new Enemigo(x, y, 45, 35, nave_enemiga);

                contentPane.add(enemigo);   //  agregar al panel
                enemigos.add(enemigo);      // guardarlo en la lista
                Enemigo.enemigos.add(enemigo); // para que se muevan en bloque

                enemigo.repaint();
                enemigo.movimiento(45, 35, contentPane.getWidth(), delay);

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
}