//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Timer
//Fecha: 6/05/19
//Comentarios

package Juego;

public class Timer implements Runnable {

	ModelJuego parent;
	private int tm;
	private boolean iniciar;
	
	public Timer (ModelJuego parent, boolean iniciar){
		this.tm = 0;
		this.iniciar=iniciar;
		this.parent=parent;
		Thread reloj = new Thread(this);
		if (this.iniciar){
			iniciaCronometro();
		}else{
			detenerCronometro();
		}
		reloj.start();
	}
	
	public int getTime(){
		return this.tm;
	}
	
	public void run() {
		while (true){
			if(this.iniciar){
				this.tm++;
				this.parent.getParent().getView().getPanelDatos().setTime(this.tm);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void iniciaCronometro(){
		this.iniciar=true;
	}
	
	public void detenerCronometro(){
		this.iniciar=false;
	}

}
