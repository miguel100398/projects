//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: BarcosGrafos
//Fecha: 6/05/19
//Comentarios


package Juego;

import java.awt.Color;

import org.omg.Messaging.SyncScopeHelper;

import ListaEnlazada.ListaEnlazada;

public class BarcosGrafos {
	private NodoBarcosGrafos [] barcos;
	private int numNodos;
	private ModelJuego model;
	private boolean jugador;
	
	public BarcosGrafos(ModelJuego model, boolean jugador, boolean jugar){
		this.numNodos = 100;
		barcos = new NodoBarcosGrafos [100];
		this.model = model;
		this.jugador = jugador;
		for (int i = 0; i<100; i++){
			barcos [i] = new NodoBarcosGrafos(this.jugador, i+1, this.model, jugar);
		}
		for (int i = 1; i<=100; i++){
			//Nodo superior
			if ((i-10)>0){
				barcos [i-1].getLista().insertarFin(barcos[i-11]);
			}
			//Nodo derecho
			if ((i%10)!=0){
				barcos [i-1].getLista().insertarFin(barcos[i]);
			}
			//Nodo inferior
			if ((i+10)<=100){
				barcos [i-1].getLista().insertarFin(barcos[i+9]);
			}
			//Nodo izquierdo
			if ((i%10)!=1){
				barcos [i-1].getLista().insertarFin(barcos[i-2]);
			}
		}
	}
	
	
	public String toString(){
		String regresar = "";
		for (int i = 0; i<100; i++){
			int index = i+1;
			regresar += "Barco: " + index + ", num_vecinos: " + this.barcos[i].getLista().size() + ", vecinos: " +this.barcos[i].getLista().toString();
			regresar += "\n";
		}
		return regresar;
	}
	
	public BotonBarco getBotonBarco(int index){
		return this.barcos[index-1].getBotonBarco();
	}
	
}

class NodoBarcosGrafos{

	private BotonBarco btBarco;
	private ListaEnlazada<NodoBarcosGrafos> lista;
	private ModelJuego model;
	private boolean jugador;
	
	public NodoBarcosGrafos(boolean jugador, int index, ModelJuego model, boolean jugar){
		this.model = model;
		this.jugador = jugador;
		this.btBarco = new BotonBarco(this.jugador, index, this.model, this, jugar);
		this.lista = new ListaEnlazada<NodoBarcosGrafos>();
	}
	
	
	public BotonBarco getBotonBarco(){
		return this.btBarco;
	}
	
	
	public ListaEnlazada<NodoBarcosGrafos> getLista(){
		return this.lista;
	}
	
	
	public String toString(){
		return this.btBarco.toString();
	}
}
