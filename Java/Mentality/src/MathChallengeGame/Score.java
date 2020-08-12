package MathChallengeGame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Users.User;

public class Score extends JPanel implements Runnable{

	private JLabel score,
				   time,
				   nivel,
				   scoreMax;
	private int sc,
	            tm,
	            lv,
	            scMax;
	private Juego gm;
	private boolean iniciar;
	private User user;
	
	public Score(Juego gm, User user){
		super();
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(700,25));
		this.sc=0;
		this.tm=10;
		this.lv=1;
		this.gm=gm;
		this.user=user;
		this.scMax=this.user.getScore("MathGame");
		this.score = new JLabel();
		this.score.setText("Score: "+this.sc);
		this.time = new JLabel();
		this.time.setText("Tiempo: "+this.tm+" segundos           ");
		this.nivel= new JLabel();
		this.nivel.setText("Nivel: "+this.lv+"          ");
		this.scoreMax = new JLabel();
		this.scoreMax.setText("          Max Score: "+this.scMax);
		
		this.add(this.nivel);
		this.add(this.time);
		this.add(this.score);
		this.add(this.scoreMax);
	}
	
	public int getScore(){
		return this.sc;
	}
	public void Reset(){
		this.detenerCronometro();
		this.sc=0;
		this.score.setText("Score: "+sc);
		this.tm=10;
		this.time.setText("Tiempo: "+tm+" segundos           ");
		this.lv=1;
		this.nivel.setText("Nivel: "+this.lv+"          ");
		this.scoreMax.setText("          Max Score: "+this.scMax);
	}
	
	public void setNivel(int nivel){
		this.lv=nivel;
		this.nivel.setText("Nivel: "+this.lv+"          ");
	}
	
	public void setTime(int time){
		this.tm=time;
		this.time.setText("Tiempo: "+tm+" segundos           ");
	}
	
	public void agregarTiempo(int tiempo){
		this.tm+=tiempo;
		this.time.setText("Tiempo: "+tm+" segundos           ");
	}
	
	public void agregarPunto(int puntaje){
		this.sc+=puntaje+this.tm;
		this.score.setText("Score: "+sc);
		if (this.user.getScore("MathGame")<this.sc){
			this.user.setScore(this.sc, "MathGame");
			this.scMax=this.user.getScore("MathGame");
			this.scoreMax.setText("          Max Score: "+this.scMax);
		}
	}
	
	public void agregarNivel(){
		this.lv++;
		this.nivel.setText("Nivel: "+this.lv+"          ");
	}
	
	public void Cronometro(){
		while (this.iniciar==true && this.tm>0){
				try {
					Thread hilo= new Thread (this);
					Thread.sleep(1000);
					if (this.iniciar==true){
						this.tm-=1;
						this.time.setText("Tiempo: "+tm+" segundos           ");
						if (this.tm<6){
							hilo.start();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
			}	
		}
		if (this.tm>0){
		}
		else{
			this.gm.derrota();
		}
	}
	
	
	public void iniciaCronometro(){
		this.iniciar=true;
		this.Cronometro();
	}
	
	public void detenerCronometro(){
		this.iniciar=false;
	}
	

	public void run() {
		this.setBackground(Color.RED);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setBackground(Color.gray);
	}
	
}
