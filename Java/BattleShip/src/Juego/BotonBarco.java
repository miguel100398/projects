//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: BotonBarco
//Fecha: 6/05/19
//Comentarios

package Juego;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ListaEnlazada.ListaEnlazada;

public class BotonBarco extends JButton implements ActionListener{

	private int index;
	private boolean tieneBarco;
	private boolean disparado;
	private Color color;
	private ModelJuego model;
	private boolean jugador;
	private NodoBarcosGrafos parent;
	private boolean jugar;
	private int tipoDeBarco; // 0 sin barco, 1 destructor, 2 crucero, 3 submarino, 4 acorazado, 5 portaviones

	public BotonBarco(boolean jugador, int index, ModelJuego model, NodoBarcosGrafos parent, boolean jugar){
		super("");
		this.parent = parent;
		this.jugar = jugar;
		this.model = model;
		this.jugador=jugador;
		this.tipoDeBarco = 0;
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(37,25));
		this.index = index;
		this.tieneBarco = false;
		this.disparado = false;    
		this.color = Color.gray;
		this.addActionListener(this);
		if (jugador){
			this.setEnabled(false);
		}
	}
	
	
	
	public int getIndex(){
		return this.index;
	}
	
	public boolean getTieneBarco(){
		return this.tieneBarco;
	}
	
	public boolean getDisparado(){
		return this.disparado;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public BotonBarco[] getVecinos(){
		BotonBarco[] tmp = new BotonBarco[this.parent.getLista().size()];
		for (int i=0; i<this.parent.getLista().size();i++){
			tmp[i] = parent.getLista().getEn(i).getBotonBarco();
		}
		return tmp;
	}
	
	public void setTieneBarco(boolean barco){
		this.tieneBarco=barco;
	}
	
	public void setDisparado (boolean disparo){
		this.disparado=disparo;
	}
	
	public void setColor (Color color){
		this.color = color;
		this.setBackground(color);
	}
	
	public void setTipoDeBarco(int tipo){
		this.tipoDeBarco=tipo;
	}
	
	public int getTipoDeBarco(){
		return this.tipoDeBarco;
	}
	
	public String toString(){
		return ""+this.index;
	}
	
	public void setJugar(boolean jugar){
		this.jugar = jugar;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (jugar){
			this.model.DisparoJugador(this);
		} else{
			this.model.getParent().getColocarBarcos().colocarBarco(this);
		}
	}

}
