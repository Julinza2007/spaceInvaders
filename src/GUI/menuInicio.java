package GUI;
		
import java.awt.Color;
		import java.awt.Font;
		import java.awt.Graphics;
		import java.awt.Image;
		import java.awt.event.KeyEvent;
		import java.awt.event.KeyListener;
		
		import javax.swing.ImageIcon;
		import javax.swing.JFrame;
		import javax.swing.JPanel;
		import javax.swing.Timer;
		
		import javax.sound.sampled.AudioInputStream;
		import javax.sound.sampled.AudioSystem;
		import javax.sound.sampled.Clip;
		
public class menuInicio extends JFrame implements KeyListener {
		
	private static final long serialVersionUID = 1L;
	private boolean startGame = false;
	private Image fondoMenu;
	private panelInicio panelInicio;
	private Font fuenteSAO;
	private boolean mostrarTexto = true; // Booleano para la animacion parpadeante del texto para empezar a jugar.
	private Clip musicaMenu;
	private Clip sonidoInicio;
			
	public menuInicio() {
			setTitle("Space Invaders G3L - Menú Inicio");
			setBounds(0, 0, 800, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			
			try {
				// Cargar el archivo .wav desde la carpeta GUI
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(
						getClass().getResource("/sonidos/spaceTravel.wav"));
				musicaMenu = AudioSystem.getClip();  // crear Clip
			    musicaMenu.open(audioIn);             // abrir audio
			    musicaMenu.loop(Clip.LOOP_CONTINUOUSLY); // reproducir en loop infinito
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		
			try {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(
						getClass().getResource("/sonidos/empezarJuego1.wav"));
				sonidoInicio = AudioSystem.getClip();  // crear Clip
				sonidoInicio.open(audioIn);             // abrir audio
			}
		        
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try { // Es una promesa, que si no se cumple, o sea no carga la fuente, que use de manera forzada la default que es ARIAL.
			            
				fuenteSAO = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/SAO.ttf"));
			        	
			} 
			catch (Exception e) { // Si no se cumple la promesa, entra al catch y se usa por default ARIAL.
				e.printStackTrace();
				fuenteSAO = new Font("Arial", Font.BOLD, 48);
			}
		
				panelInicio = new panelInicio();
				setContentPane(panelInicio);
		
				addKeyListener(this); // Se abre el listener para escuchar los eventos del teclado.
				setFocusable(true); // Se hace focus en la ventana del menú.
		
				fondoMenu = new ImageIcon(getClass().getResource("/img/menuInicio.png")).getImage(); // Se carga la imagen del fondo
	
				Timer parpadearTexto = new Timer(1200, e -> { // Se utiliza el timer para hacer la animación de parpadeo que dura 1,2 segundos.
					mostrarTexto = !mostrarTexto;
					repaint();
				});
				parpadearTexto.start();
			        
	}
		
			private class panelInicio extends JPanel {
		       
				private static final long serialVersionUID = 1L;
		
				@Override
		        protected void paintComponent(Graphics g) {
		            super.paintComponent(g);
		
		            g.drawImage(fondoMenu, 0, 0, getWidth(), getHeight(), this); // dibujar fondo
		
		            if (!startGame) {
		                g.setColor(Color.ORANGE);
		                g.setFont(fuenteSAO.deriveFont(Font.BOLD, 70f));
		                g.drawString("SPACE INVADERS G3L", 207, 150); // este seria el titulo
		
		                if (mostrarTexto) { // solo lo dibuja si el booleano es true
		                    g.setColor(Color.WHITE);
		                    g.setFont(fuenteSAO.deriveFont(Font.BOLD, 36f));
		                    g.drawString("Presiona ENTER para jugar", 265, 300); // mensaje para arrancar el juego
		                }
		            } 
		        }
		    }
		
		   
		    public void keyPressed(KeyEvent e) {  // Se manejan los eventos de teclado
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Si se presiona la tecla ENTER se inicia el juego.
		            startGame = true;
		            
		            if (musicaMenu != null && musicaMenu.isRunning()) {
		                musicaMenu.stop(); // Con esto se detiene la música del menú.
		            }
		            
		            if (sonidoInicio != null) {
		                sonidoInicio.setFramePosition(0); // Se reinicia desde el principio el sonido.
		                sonidoInicio.start();             // Se reproduce el sonido de que arrancó el juego.
		            }
		            
		            repaint(); // Esto repinta la ventana en general para refrescar el contenido de manera dinámica.
		            
		            dispose(); // Se cierra la ventana del menú.
		            spaceInvaders spaceInvaders = new spaceInvaders(); // Se crea una nueva instancia de la clase spaceInvaders.
		            spaceInvaders.setVisible(true); // Se muestra la ventana del juego.		            
		        }
		    }
		
		    public void keyReleased(KeyEvent e) {}
		    @Override public void keyTyped(KeyEvent e) {}
		}
