
package GUI;

import javax.swing.JLabel;
import java.awt.Color;

public class Puntaje extends JLabel {
    private int vidas;
    private int puntos;
    

    public Puntaje(int vidasIniciales) {
        this.vidas = vidasIniciales;
        this.puntos = 0;
        actualizarTexto();
        setForeground(Color.WHITE);
    }

    private void actualizarTexto() {
        setText("Vidas: " + vidas + " | Puntos: " + puntos);
    }

    public int getVidas() {
        return vidas;
    }
    
    public void reiniciarVidas() {
    	vidas = 3;
    	actualizarTexto();
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
            actualizarTexto();
        }
    }

    public boolean estaMuerto() {
        return vidas == 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
        actualizarTexto();
    }
}
