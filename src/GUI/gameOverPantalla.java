package GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;

public class gameOverPantalla extends JFrame implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private boolean restart = false; // detecta si el jugador quiere reiniciar la partida
	private boolean exit = false; // detecta si el jugador quiere salir
	private JPanel panelOver;
	private Font fuenteSAO;
	private Font fuente8bit;
	private Image fondoOver;
	private Clip musicaOver;
	private Clip sonidoVolverMenu;
	private boolean mostrarTexto = true;
	
	public gameOverPantalla() {
		setBounds(0, 0, 800, 600);
		setTitle("Space Invaders G3L - Game Over");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    
	    try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(
					getClass().getResource("/sonidos/volverMenu.wav"));
			sonidoVolverMenu = AudioSystem.getClip();  // crear Clip
			sonidoVolverMenu.open(audioIn);             // abrir audio
		}
	        
		catch (Exception e) {
			e.printStackTrace();
		}
	    
	    try {
			// Cargar el audio con formato wav desde la carpeta GUI
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(
					getClass().getResource("/sonidos/determination.wav"));
			musicaOver = AudioSystem.getClip();  // crear Clip
		    musicaOver.open(audioIn);             // abrir audio
		    musicaOver.loop(Clip.LOOP_CONTINUOUSLY); // reproducir en loop infinito
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    
		
	    try { // Es una promesa, que si no se cumple, o sea no carga la fuente, que use de manera forzada la default que es ARIAL.
	            
	        	fuente8bit = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/8BITWONDER.ttf"));
	        	
	        } 
	    
	    catch (Exception e) { // Si no se cumple la promesa, entra al catch y se usa por default ARIAL.
	            e.printStackTrace();
	            fuente8bit = new Font("Arial", Font.BOLD, 48);
	        }
	    
	    
		try { // Es una promesa, que si no se cumple, o sea no carga la fuente, que use de manera forzada la default que es ARIAL.
            
			fuenteSAO = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fuentes/SAO.ttf"));
		        	
		} 
		catch (Exception e) { // Si no se cumple la promesa, entra al catch y se usa por default ARIAL.
			e.printStackTrace();
			fuenteSAO = new Font("Arial", Font.BOLD, 48);
		}
	
		
		setFocusable(true);
		addKeyListener(this);
		panelOver = new panelInicio();
        setContentPane(panelOver);
        
		fondoOver = new ImageIcon(getClass().getResource("/img/gameOver.png")).getImage();
		
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
			
			 Graphics2D g2d = (Graphics2D) g;
			
			g.drawImage(fondoOver, 0, 0, getWidth(), getHeight(), this); // dibujar la imagen de fondo del game over
			
			// frase game over o titulo principal
			g.setColor(Color.BLACK);
			g.setFont(fuente8bit.deriveFont(Font.BOLD, 70f));
			g.drawString("GAME OVER", 68, 150);
			
		    g2d.setColor(new Color(0, 0, 0, 150)); // Esto es un rectángulo negro con un poco de transparencia
		    g2d.fillRoundRect(200, 240, 385, 110, 20, 20); 
		    
		    
		    g2d.setColor(Color.WHITE);
		    if(mostrarTexto) {
				   g2d.setFont(fuenteSAO.deriveFont(Font.BOLD, 36f));
				   g2d.drawString("Presione ENTER para volver al menu", 222, 280);
		    }
		    
		    else if(!mostrarTexto) {
				   g2d.setFont(fuenteSAO.deriveFont(Font.BOLD, 36f));
				   g2d.drawString("Presione ESC para salir", 284, 330);
		    }
		    
		 
						
			if(restart) { // Si el jugador elige reiniciar la partida
				
				if (musicaOver != null && musicaOver.isRunning()) {
	                musicaOver.stop(); // Con esto se detiene la música del Game Over.
	            }

				 if (sonidoVolverMenu != null) {
		                sonidoVolverMenu.setFramePosition(0); // Se reinicia desde el principio el sonido.
		                sonidoVolverMenu.start();             // Se reproduce el sonido de que arrancó el juego.
		         }
				
				dispose();
				menuInicio menuInicio = new menuInicio();
				menuInicio.setVisible(true);
			}
			
			else if(exit) { // Si el jugador quiere salir
				System.exit(0); // Llamada al sistema para que se cierre la aplicación
			}
			
		}
    }
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { // aca se puede volver al menu o reiniciar el juego
			restart = true;
			repaint();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { // aca se puede cerrar el juego
			exit = true;
			repaint();
		}
	}
	
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}	
		
}