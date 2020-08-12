//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: ViewJuego
//Fecha: 6/05/19
//Comentarios

package Juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewJuego extends JFrame {

	private Juego parent;
	private ModelJuego model;
	
	private PanelDatos panelDatos;
	private PanelJugador panelJugador;
	private PanelEnemigo panelEnemigo;
	
	private JPanel panelAuxiliar;
	
	
	public ViewJuego(Juego juego, ModelJuego model){
		super ("BattleShip");
		
		this.parent = juego;
		this.model = model;
		this.setBackground(new Color (0x20726e));
		this.setForeground(new Color (0x20726e));
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("src\\brain.jpg").getImage()); //Cambiar imagen
		
		this.panelDatos = new PanelDatos(this, new Color(0x20726e));
		this.panelDatos.setForeground(new Color (0x20726e));
		this.panelJugador = new PanelJugador(this, new Color (0x18D0F8));
		this.panelEnemigo = new PanelEnemigo (this, new Color (0x18D0F8));
		this.panelAuxiliar = new JPanel ();
		this.panelAuxiliar.setBackground(new Color (0x20726e));
		this.panelAuxiliar.setPreferredSize(new Dimension (400,630));
		this.panelAuxiliar.add(this.panelEnemigo, BorderLayout.NORTH);
		this.panelAuxiliar.add(this.panelJugador, BorderLayout.SOUTH);
	
		
		JPanel panelcentral = new JPanel ();
        panelcentral.setPreferredSize(new Dimension(0,540));
        panelcentral.setBackground(new Color (0x20726e));
        
		
		this.add(this.panelDatos, BorderLayout.WEST);
		this.add(panelcentral, BorderLayout.CENTER);
		this.add(this.panelAuxiliar, BorderLayout.EAST);
		
		this.pack();
		
	        
	        
	    // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    
	    this.setVisible(true);
	    this.setResizable(false);
	}
	
	
	public ModelJuego getModel(){
		return this.model;
	}
	
	public Juego getJuego(){
		return this.parent;
	}
	
	public PanelDatos getPanelDatos(){
		return this.panelDatos;
	}

	public void esconder(){
		this.setVisible(false);
	}
	
	public void mostrar(){
		this.setVisible(true);
	}
	
	public PanelJugador getPanelJugador(){
		return this.panelJugador;
	}
	
	public void setPanelJugador(PanelJugador panelJugador){
		this.panelJugador = panelJugador;
		this.panelAuxiliar.add(this.panelJugador);
	}
}
