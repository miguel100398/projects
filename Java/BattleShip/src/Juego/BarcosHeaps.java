//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: BarcosHeaps
//Fecha: 6/05/19
//Comentarios


package Juego;

public class BarcosHeaps implements Comparable <BarcosHeaps>{
	private BotonBarco botonBarco;
	private int index;
	
	public BarcosHeaps(BotonBarco botonBarco, int index){
		this.botonBarco=botonBarco;
		this.index=index;
	}

	public int compareTo(BarcosHeaps barcosHeaps) {
		if (this.index==barcosHeaps.index){
			return 0;
		}else if (this.index<barcosHeaps.index){
			return -1;
		}else{
			return 1;
		}
	}
	
	public void setIndex(int index){
		this.index=index;
	}
	
	public void setBotonBarco(BotonBarco botonBarco){
		this.botonBarco=botonBarco;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public BotonBarco getBotonBarco(){
		return this.botonBarco;
	}
	
	public String toString(){
		return "aleatorio: "+this.index+" casilla: "+this.botonBarco.getIndex();
	}
}
