package MathChallengeGame;

import java.util.Random;

public class JuegoCombinadas {

		private int nivel,
				    juego;
		private Juego gm;
		private JuegoSumas juegoSumas;
		private JuegoRestas juegoRestas;
		private JuegoMultiplicaciones juegoMultiplicaciones;
		private JuegoDivisiones juegoDivisiones;
		private Random rand = new Random();
		
		public JuegoCombinadas(Juego gm, JuegoSumas juegoSumas, JuegoRestas juegoRestas, JuegoMultiplicaciones juegoMultiplicaciones, JuegoDivisiones juegoDivisiones){
			this.gm=gm;
			this.juegoSumas=juegoSumas;
			this.juegoRestas=juegoRestas;
			this.juegoMultiplicaciones=juegoMultiplicaciones;
			this.juegoDivisiones=juegoDivisiones;
		}
		
		public void setNivel(int nivel){
			this.nivel=nivel;
		}
		
		public void start(){
			juego = rand.nextInt(4);
			if (juego==0){
				this.gm.residuo(false);
				this.juegoSumas.setNivel(this.nivel);
				this.gm.setOperador("+");
				this.gm.setJuegoC("Sumas");
				this.juegoSumas.start();
			}
			else if (juego==1){
				this.gm.residuo(false);
				this.juegoRestas.setNivel(this.nivel);
				this.gm.setOperador("-");
				this.gm.setJuegoC("Restas");
				this.juegoRestas.start();
			}
			else if (juego==2){
				this.gm.residuo(false);
				this.juegoMultiplicaciones.setNivel(this.nivel);
				this.gm.setOperador("*");
				this.gm.setJuegoC("Multiplicaciones");
				this.juegoMultiplicaciones.start();
			}
			else{
				this.juegoDivisiones.setNivel(this.nivel);
				this.gm.setOperador("/");
				this.gm.setJuegoC("Divisiones");
				this.juegoDivisiones.start();
			}
		}
		
		public void aumentarNivel(){
			this.nivel++;
			this.start();
		}
		
		public void disminuirNiver(){
			this.nivel-=1;
			this.start();
		}
}
