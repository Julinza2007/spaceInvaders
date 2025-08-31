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
			private boolean mostrarTexto = true; // booleano para la animacion parpadeante del texto para empezar a jugar.
			private Clip clip;
			
			public menuInicio() {
				 setTitle("Space Invaders G3L");
			        setBounds(0, 0, 800, 600);
			        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        setResizable(false);
			        
			        try { // Es una promesa, que si no se cumple, o sea no carga la fuente, que use de manera forzada la default que es ARIAL.
			            
			        	fuenteSAO = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/GUI/SAO.ttf"));
			        	
			        } 
			        catch (Exception e) {
			            e.printStackTrace();
			            fuenteSAO = new Font("Arial", Font.BOLD, 48);
			        }
		
			        panelInicio = new panelInicio();
			        setContentPane(panelInicio);
		
			        addKeyListener(this);
			        setFocusable(true);
		
			        fondoMenu = new ImageIcon(getClass().getResource("/GUI/menuInicio.png")).getImage(); // carga la imagen del fondo
	
			        Timer timer = new Timer(1200, e -> { // se utiliza el timer para hacer la animación de parpadeo que dura 1,2 segundos.
			            mostrarTexto = !mostrarTexto;
			            repaint();
			        });
			        timer.start();
			        
			        try {
			            // Cargar el archivo .wav desde la carpeta GUI
			            AudioInputStream audioIn = AudioSystem.getAudioInputStream(
			                getClass().getResource("/GUI/spaceTravel.wav"));
			            	clip = AudioSystem.getClip();  // crear Clip
			            	clip.open(audioIn);             // abrir audio
			            	clip.loop(Clip.LOOP_CONTINUOUSLY); // reproducir en loop infinito
			        }
			        catch (Exception e) {
			            e.printStackTrace();
			        }
			        
		    }
		
			private class panelInicio extends JPanel {
		       
				private static final long serialVersionUID = 1L;
		
				@Override
		        protected void paintComponent(Graphics g) {
		            super.paintComponent(g);
		
		            // dibujar fondo
		            g.drawImage(fondoMenu, 0, 0, getWidth(), getHeight(), this);
		
		            if (!startGame) {
		                g.setColor(Color.ORANGE);
		                g.setFont(fuenteSAO.deriveFont(Font.BOLD, 70f));
		                g.drawString("SPACE INVADERS G3L", 207, 150); // este seria el titulo
		
		                if (mostrarTexto) { // solo lo dibuja si el flag está en true
		                    g.setColor(Color.WHITE);
		                    g.setFont(fuenteSAO.deriveFont(Font.BOLD, 36f));
		                    g.drawString("Presiona ENTER para jugar", 265, 300); // mensaje para arrancar el juego
		                }
		            } 
	
		            else {
		                // bla bla bla
		            }
		        }
		    }
		
		    // eventos de teclado
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            startGame = true;
		            
		            if (clip != null && clip.isRunning()) {
		                clip.stop(); // detener música
		            }
		            
		            this.dispose(); // cierra la ventana del menú
		            spaceInvaders spaceInvaders = new spaceInvaders(); // crea una nueva instancia de la clase Juego
		            spaceInvaders.setVisible(true); // muestra la ventana del juego
		            
		            repaint(); // repinta todo el panel
		        }
		    }
		
		    public void keyReleased(KeyEvent e) {}
		    @Override public void keyTyped(KeyEvent e) {}
		}
