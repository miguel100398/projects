package SimonSaysGame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class Secuencia extends JButton{

	private int numColores;
	private Color[] secuenciaColores;
	private final Color[] colores = {Color.RED, Color.BLUE, Color.yellow, Color.GREEN};
	private int[] secuencia;
	private Musica musica;

	
	public Secuencia (){
		super();
		this.setBackground(Color.gray);
		this.setEnabled(false);
		this.setPreferredSize(new Dimension(75, 65));
		this.setBorderPainted(false);
		this.numColores=3;
		this.secuenciaColores=new Color[this.numColores];
		this.secuencia = new int[this.numColores];
	}
	
	public int[] getSecuencia(){
		return this.secuencia;
	}
	public int getNumColores(){
		return this.numColores;
	}
	
	public void setMusica(Musica musica){
		this.musica=musica;
	}
	
	public void reset(){
		this.numColores=3;
		this.secuencia=new int[this.numColores];
		this.secuenciaColores=new Color[this.numColores];
	}
	
	//Crea el patrón de colores
	public void CrearSecuencia(){
		for (int i = 0; i < secuenciaColores.length; i++) {
			this.secuencia[i]=(int)(Math.random()*4+0);
			this.secuenciaColores[i]=this.colores[this.secuencia[i]];
		}
	}
	
	//Muestra la secuencia en el boton
	public void PonerSecuencia(int posicion){
		this.setBackground(secuenciaColores[posicion]);
		this.musica.setTocar(this.secuencia[posicion]);
		this.musica.tocar();
	}
	
	//Agrega un color a la secuencia
	public void SiguienteSecuencia(){
		this.numColores++;
		Color[] tmp = this.secuenciaColores;
		int[] tmp2 = this.secuencia;
		this.secuenciaColores=new Color[this.numColores];
		this.secuencia=new int[this.numColores];
		for (int i = 0; i < secuenciaColores.length-1; i++){
			this.secuenciaColores[i]=tmp[i];
			this.secuencia[i]=tmp2[i];
		}
		this.secuencia[this.numColores-1]=(int)(Math.random()*4+0);
		this.secuenciaColores[this.numColores-1]=this.colores[this.secuencia[this.numColores-1]];
	}

	
}
