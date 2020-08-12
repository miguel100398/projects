//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: PanelEnemigo
//Fecha: 6/05/19
//Comentarios

package Juego;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelEnemigo extends JPanel{

	private ViewJuego parent;
	private JButton btBarco[];
	
	public PanelEnemigo (ViewJuego view, Color color){
		super();
		this.parent=view;
		this.setPreferredSize(new Dimension (450,320));
		this.setBackground(color);
		this.setBorder(new EmptyBorder(0,0,0,0));
		
		//Inicializar botones de barco
			this.btBarco = new JButton [100];
			for (int i = 0; i<100; i++){
				this.btBarco[i] = this.parent.getModel().getBarcosGrafosEnemigo().getBotonBarco(i+1);
				this.add(btBarco[i]);
			}
	}
	
}
