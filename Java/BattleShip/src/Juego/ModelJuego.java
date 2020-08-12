//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: ModelJuego
//Fecha: 6/05/19
//Comentarios


package Juego;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.Random;
import Stack.Stack;
import Usuarios.Usuario;
import Usuarios.UsuarioRegistrado;

import javax.swing.JOptionPane;

import HEAPS.Min_Heap;
import ListaEnlazada.ListaEnlazada;
import Queue.MyQueue;

public class ModelJuego implements Runnable{

	private Juego parent;
	private BarcosGrafos barcosGrafosJugador, barcosGrafosEnemigo;
	private int barcosEnemigos []= new int[17],
				barcosJugador[] = new int [17];
	private BarcosHeaps[] barcosHeapsEnemigo = new BarcosHeaps[100];
	private	BarcosHeaps[] barcosHeapsJugador = new BarcosHeaps[100];
	private Min_Heap<BarcosHeaps>  heapOrdenDisparoEnemigo,
								   heapOrdenDisparoJugador;
	private boolean turnoJugador;
	private int vidaJugador, vidaEnemigo;
	private int destructorJugador, destructorEnemigo,
				cruceroJugador, cruceroEnemigo,
				submarinoJugador, submarinoEnemigo,
				acorazadoJugador, acorazadoEnemigo,
				portavionesJugador, portavionesEnemigo;
	private Usuario usuario;
	private int disparos, aciertos, fallos, barcos_perdidos, barcos_destruidos;
	
	private Timer timer;
	
	private boolean fin;
	
	private MyQueue<String> filaLog;
	
	private EfectosSonido efectos;
	
	private Stack <ListaEnlazada<BotonBarco>>pilaDisparosContiguosEnemigoDestructor,
											 pilaDisparosContiguosEnemigoCrucero,
											 pilaDisparosContiguosEnemigoSubmarino,
											 pilaDisparosContiguosEnemigoAcorazado,
											 pilaDisparosContiguosEnemigoPortaviones;
	
	private Date date;
	DateFormat fecha;
	
	public ModelJuego (Juego juego){
		this.parent=juego;
		this.fin = false;
		this.usuario=this.parent.getMenuPrincipal().getModel().getUsuarios().get(this.parent.getMenuPrincipal().getModel().getUsuarioActual());
		this.timer = new Timer(this, false);
		this.filaLog = new MyQueue<String> ();
		this.date = new Date();
		this.fecha = new SimpleDateFormat ("HH:mm:ss dd/MM/yyyy");
		this.filaLog.enqueue("¡La batalla ha iniciado! " + fecha.format(date));
		this.disparos=this.aciertos=this.fallos=this.barcos_perdidos=this.barcos_destruidos=0;
		this.efectos = new EfectosSonido();
		this.pilaDisparosContiguosEnemigoDestructor = new Stack<ListaEnlazada<BotonBarco>>();
		this.pilaDisparosContiguosEnemigoCrucero = new Stack<ListaEnlazada<BotonBarco>>();
		this.pilaDisparosContiguosEnemigoSubmarino = new Stack<ListaEnlazada<BotonBarco>>();
		this.pilaDisparosContiguosEnemigoAcorazado = new Stack<ListaEnlazada<BotonBarco>>();
		this.pilaDisparosContiguosEnemigoPortaviones = new Stack<ListaEnlazada<BotonBarco>>();
		this.vidaJugador=this.vidaEnemigo=17;;
		this.destructorJugador=this.destructorEnemigo=2;
		this.cruceroJugador=this.cruceroEnemigo=3;
		this.submarinoJugador=this.submarinoEnemigo=3;
		this.acorazadoJugador=this.acorazadoEnemigo=4;
		this.portavionesJugador=this.portavionesEnemigo=5;
	//	generarBarcosJugadorAleatorio();
		this.barcosGrafosJugador = new BarcosGrafos(this,true, false);
		this.barcosGrafosEnemigo = new BarcosGrafos(this,false, false);
		generarBarcosAleatorio(this.barcosEnemigos, this.barcosGrafosEnemigo, false);
		//Probar barcos de jugador en aleatorio
		//generarBarcosAleatorio(this.barcosJugador, this.barcosGrafosJugador, true);
		//generarDisparosAleatorio();
		this.heapOrdenDisparoEnemigo=generarOrdenDisparos(this.barcosHeapsEnemigo,  this.barcosGrafosJugador);
		this.heapOrdenDisparoJugador=generarOrdenDisparos(this.barcosHeapsJugador,  this.barcosGrafosEnemigo);
		
	}
	
	
	
	private Min_Heap<BarcosHeaps> generarOrdenDisparos(BarcosHeaps[] barcosHeaps, BarcosGrafos objetivo){
		Random random = new Random (System.nanoTime());
		for (int i = 0; i<100; i++){
			barcosHeaps[i] = new BarcosHeaps(objetivo.getBotonBarco(i+1), random.nextInt(1000));
		}
		Min_Heap<BarcosHeaps> tmp = new Min_Heap<BarcosHeaps>(barcosHeaps, 100);
		return tmp;
	}
	
	public void jugar(){
		Thread prueba = new Thread(this);
		prueba.start();
		iniciaCronometro();
		
	}
	
	private void generarBarcosAleatorio(int[] barcos, BarcosGrafos grafos, boolean jugador){
		generarBarcosAleatorio(barcos,0,2); //destructor 2 casillas
		generarBarcosAleatorio(barcos,2,3); //crucero 3 casillas
		generarBarcosAleatorio(barcos,5,3); //Submarino 3 casillas
		generarBarcosAleatorio(barcos,8,4); //Acorazado 4 casillas
		generarBarcosAleatorio(barcos,12,5); //portaviones 5 casillas
		for (int i = 0; i<barcos.length;i++){
			if (jugador){
				grafos.getBotonBarco(barcos[i]).setColor(Color.red);
			}
			grafos.getBotonBarco(barcos[i]).setTieneBarco(true);
			if (i<2){
				grafos.getBotonBarco(barcos[i]).setTipoDeBarco(1);
			}else if (i<5){
				grafos.getBotonBarco(barcos[i]).setTipoDeBarco(2);
			}else if (i<8){
				grafos.getBotonBarco(barcos[i]).setTipoDeBarco(3);
			}else if (i<12){
				grafos.getBotonBarco(barcos[i]).setTipoDeBarco(4);
			}else {
				grafos.getBotonBarco(barcos[i]).setTipoDeBarco(5);
			}
		}
		
	}
	
	private void generarBarcosAleatorio(int[] barcos, int casillasOcupadas, int size){
		boolean repetir;
		Random random = new Random (System.nanoTime());
		int orientacion= 0;
		int casillaInicial = 0;
		do{
			repetir = false;
			orientacion = random.nextInt(4);// 0 derecha, 1 abajo, 2 izquierda, 3 arriba
			casillaInicial = random.nextInt(100) + 1;
			barcos[casillasOcupadas] = casillaInicial;
			switch (orientacion){
				case 0:
					if (((casillaInicial+size-1)/10)>(casillaInicial-1)/10){
						repetir = true;
						break;
					}
					for (int i = 1; i<size;i++){
						barcos[casillasOcupadas+i]=casillaInicial+i;
					}
					break;
				case 1:
					if (((casillaInicial+((size-1)*10)))>100){
						repetir = true;
						break;
					}
					for (int i = 1; i<size;i++){
						barcos[casillasOcupadas+i]=casillaInicial+(i*10);
					}
					break;
				case 2:
					if ((((casillaInicial-size)/10)<casillaInicial/10)|| (casillaInicial-size+1<1)){
						repetir = true;
						break;
					}
					for (int i = 1; i<size;i++){
						barcos[casillasOcupadas+i]=casillaInicial-(i);
					}
					break;
				case 3:
					if (((casillaInicial-((size-1)*10)))<1){
						repetir = true;
						break;
					}
					for (int i = 1; i<size;i++){
						barcos[casillasOcupadas+i]=casillaInicial-(i*10);
					}
					break;
				default:
					throw new IllegalStateException("valor de orientacion ilegal generado");	
			}
			if (!repetir){
				for (int i = 0; i<casillasOcupadas; i++){
					for (int j = 0; j<size; j++){
						if (barcos[casillasOcupadas+j]==barcos[i]){
							repetir = true;
							break;
						}
					}
					if (repetir){
						break;
					}
				}
			}
		}while(repetir);
		
	}
	
	public void DisparoEnemigo (BotonBarco casilla){
		if (!casilla.getDisparado()){
			casilla.setDisparado(true);
			System.out.println(casilla.getIndex());
			if (casilla.getTieneBarco()){
				ListaEnlazada<BotonBarco> listatmp = new ListaEnlazada<BotonBarco> (casilla.getVecinos());
				this.filaLog.enqueue("Disparo del Enemigo acertado en la casilla: "+formatoCasilla(casilla)+" En el tiempo de juego: " + formatoTiempo());
				casilla.setColor(Color.RED);
				this.vidaJugador --;
				this.efectos.setTocar(2);
				this.efectos.tocar();
				switch (casilla.getTipoDeBarco()){
				case 1:
					this.destructorJugador --;
					this.pilaDisparosContiguosEnemigoDestructor.push(listatmp);
					if (this.destructorJugador==0){
						if (!this.pilaDisparosContiguosEnemigoDestructor.isEmpty()){
							this.pilaDisparosContiguosEnemigoDestructor.flush();
						}
						this.parent.getView().getPanelDatos().setDestruido(true, 1);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_perdidos++;
						this.filaLog.enqueue("Destructor perdido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Destructor destruido!");
					}
					break;
				case 2:
					this.cruceroJugador --;
					this.pilaDisparosContiguosEnemigoCrucero.push(listatmp);
					if (this.cruceroJugador==0){
						if (!this.pilaDisparosContiguosEnemigoCrucero.isEmpty()){
							this.pilaDisparosContiguosEnemigoCrucero.flush();
						}
						this.parent.getView().getPanelDatos().setDestruido(true, 2);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_perdidos++;
						this.filaLog.enqueue("Crucero perdido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Crucero destruido!");
					}
					break;
				case 3:
					this.submarinoJugador --;
					this.pilaDisparosContiguosEnemigoSubmarino.push(listatmp);
					if (this.submarinoJugador==0){
						if (!this.pilaDisparosContiguosEnemigoSubmarino.isEmpty()){
							this.pilaDisparosContiguosEnemigoSubmarino.flush();
						}
						this.parent.getView().getPanelDatos().setDestruido(true, 3);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_perdidos++;
						this.filaLog.enqueue("Submarino perdido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Submarino destruido!");
					}
					break;
				case 4:
					this.acorazadoJugador --;
					this.pilaDisparosContiguosEnemigoAcorazado.push(listatmp);
					if (this.acorazadoJugador==0){
						if (!this.pilaDisparosContiguosEnemigoAcorazado.isEmpty()){
							this.pilaDisparosContiguosEnemigoAcorazado.flush();
						}
						this.parent.getView().getPanelDatos().setDestruido(true, 4);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_perdidos++;
						this.filaLog.enqueue("Acorazado perdido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Acorazado destruido!");
					}
					break;
				case 5:
					this.portavionesJugador --;
					this.pilaDisparosContiguosEnemigoPortaviones.push(listatmp);
					if (this.portavionesJugador==0){
						if (!this.pilaDisparosContiguosEnemigoPortaviones.isEmpty()){
							this.pilaDisparosContiguosEnemigoPortaviones.flush();
						}
						this.parent.getView().getPanelDatos().setDestruido(true, 5);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_perdidos++;
						this.filaLog.enqueue("Porta Aviones perdido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Porta aviones destruido!");
					}
					break;
				}
				if (this.vidaJugador==0){
					this.efectos.setTocar(5);
					this.efectos.tocar();
					this.usuario.setDerrotas();
					this.filaLog.enqueue("Derrota en el tiempo de juego: " + formatoTiempo());
					terminarJuego();
					String[] options = {"Aceptar", "Log"};
					int seleccion = JOptionPane.showOptionDialog(null, "¡Derrota!", "Derrota", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (seleccion == 1){
						try{
							File log = new File ("src\\Usuarios\\log.txt");
							Desktop.getDesktop().open(log);
						}catch (IOException ex){
							System.out.println(ex);
						}
					}
				}	
			}else{
				this.filaLog.enqueue("Disparo del Enemigo errado en la casilla: "+formatoCasilla(casilla)+" En el tiempo de juego: " + formatoTiempo());
				casilla.setColor(Color.blue);
				this.efectos.setTocar(1);
				this.efectos.tocar();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 1; i<=100;i++){
				this.barcosGrafosEnemigo.getBotonBarco(i).setEnabled(true);
			}
			this.turnoJugador = true;
		}
	}
	
	public void DisparoJugador (BotonBarco casilla){
		if (!casilla.getDisparado()){
			casilla.setDisparado(true);
			this.disparos++;
			System.out.println(casilla.getIndex());
			if (casilla.getTieneBarco()){
				this.filaLog.enqueue("Disparo del jugador acertado en la casilla: "+formatoCasilla(casilla)+" En el tiempo de juego: " + formatoTiempo());
				this.aciertos++;
				casilla.setColor(Color.RED);
				this.vidaEnemigo --;
				this.efectos.setTocar(2);
				this.efectos.tocar();
				switch (casilla.getTipoDeBarco()){
				case 1:
					this.destructorEnemigo --;
					if (this.destructorEnemigo==0){
						this.parent.getView().getPanelDatos().setDestruido(false, 1);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_destruidos++;
						this.filaLog.enqueue("Destructor enemigo destruido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Destructor destruido!");
					}
					break;
				case 2:
					this.cruceroEnemigo --;
					if (this.cruceroEnemigo==0){
						this.parent.getView().getPanelDatos().setDestruido(false, 2);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_destruidos++;
						this.filaLog.enqueue("Crucero enemigo destruido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Crucero destruido!");
					}
					break;
				case 3:
					this.submarinoEnemigo --;
					if (this.submarinoEnemigo==0){
						this.parent.getView().getPanelDatos().setDestruido(false, 3);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_destruidos++;
						this.filaLog.enqueue("Submarino enemigo destruido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Submarino destruido!");
					}
					break;
				case 4:
					this.acorazadoEnemigo --;
					if (this.acorazadoEnemigo==0){
						this.parent.getView().getPanelDatos().setDestruido(false, 4);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_destruidos++;
						this.filaLog.enqueue("Acorazado enemigo destruido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Acorazado destruido!");
					}
					break;
				case 5:
					this.portavionesEnemigo --;
					if (this.portavionesEnemigo==0){
						this.parent.getView().getPanelDatos().setDestruido(false, 5);
						this.efectos.setTocar(3);
						this.efectos.tocar();
						this.barcos_destruidos++;
						this.filaLog.enqueue("Portaviones enemigo destruido  En el tiempo de juego: " + formatoTiempo());
						JOptionPane.showMessageDialog(null, "¡Porta aviones destruido!");
					}
					break;
				}
				if (this.vidaEnemigo==0){
					this.efectos.setTocar(4);
					this.efectos.tocar();
					this.usuario.setVictorias();
					this.filaLog.enqueue("Victoria en el tiempo de juego: " + formatoTiempo());
					terminarJuego();
					String[] options = {"Aceptar", "Log"};
					int seleccion = JOptionPane.showOptionDialog(null, "¡Victoria!", "Victoria", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (seleccion == 1){
						try{
							File log = new File ("src\\Usuarios\\log.txt");
							Desktop.getDesktop().open(log);
						}catch (IOException ex){
							System.out.println(ex);
						}
					}
	
				}
			}else{
				this.fallos++;
				this.filaLog.enqueue("Disparo del jugador errado en la casilla: "+formatoCasilla(casilla)+" En el tiempo de juego: " + formatoTiempo());
				casilla.setColor(Color.blue);
				this.efectos.setTocar(1);
				this.efectos.tocar();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 1; i<=100;i++){
				this.barcosGrafosEnemigo.getBotonBarco(i).setEnabled(false);
			}
			if(!this.fin){
			this.turnoJugador=false;
			}
		}
	}
	
	
	public void terminarJuego(){
		this.fin = true;
		this.usuario.setAciertos(this.aciertos);
		this.usuario.setBarcosDestruidos(this.barcos_destruidos);
		this.usuario.setBarcosPerdidos(this.barcos_perdidos);
		this.usuario.setDisparos(this.disparos);
		this.usuario.setFallos(this.fallos);
		this.usuario.setPartidas();
		this.usuario.setTiempo(this.timer.getTime());
		this.parent.getMenuPrincipal().getParent().saveDatabase();
		this.filaLog.enqueue("¡La batalla ha finalizado!: " + this.fecha.format(this.date));
		this.filaLog.enqueue("Disparos: "+this.disparos+" Aciertos: "+this.aciertos+" Fallos: "+this.fallos+" Barcos Perdidos: "+this.barcos_perdidos+" Barcos Destruidos: "+this.barcos_destruidos);
		saveLog();
		this.parent.getMenuPrincipal().getView().getPanelEstadisticas().ActualizarEstadisticas();
		this.parent.getMenuPrincipal().getModel().mostrar();
		this.parent.getMenuPrincipal().getView().actualizar();
		this.parent.getView().dispose();
	}
	
	public String formatoCasilla(BotonBarco casilla){
		int intNumero = ((casilla.getIndex()-1)%10)+1;
		String letra = "";
		int intletra = (casilla.getIndex()-1)/10;
		switch (intletra){
		case 0:
			letra = "A";
			break;
		case 1:
			letra = "B";
			break;
		case 2:
			letra = "C";
			break;
		case 3:
			letra = "D";
			break;
		case 4:
			letra = "E";
			break;
		case 5:
			letra = "F";
			break;
		case 6:
			letra = "G";
			break;
		case 7:
			letra = "H";
			break;
		case 8:
			letra = "I";
			break;
		case 9:
			letra = "J";
			break;
		}
		return letra+intNumero;
	}
	
	public BarcosGrafos getBarcosGrafosJugador(){
		return this.barcosGrafosJugador;
	}
	
	public BarcosGrafos getBarcosGrafosEnemigo(){
		return this.barcosGrafosEnemigo;
	}
	
	public Min_Heap<BarcosHeaps> getOrdenDisparoEnemigo(){
		return this.heapOrdenDisparoEnemigo;
	}


	public void run() {
		try {
			this.turnoJugador=true;
			while(true){
				while (turnoJugador){	
					Thread.sleep(1000);
				};
				Thread.sleep(2000);
				if (!this.pilaDisparosContiguosEnemigoDestructor.isEmpty()){
					ListaEnlazada<BotonBarco> listatmp = this.pilaDisparosContiguosEnemigoDestructor.top();
					if (listatmp.size() == 1){
						DisparoEnemigo(this.pilaDisparosContiguosEnemigoDestructor.pop().borrarFin());
					}else{
						Random random = new Random (System.nanoTime());
						DisparoEnemigo(listatmp.borrarEn(random.nextInt(listatmp.size())));
					}
				}
				else if (!this.pilaDisparosContiguosEnemigoAcorazado.isEmpty()){
						ListaEnlazada<BotonBarco> listatmp = this.pilaDisparosContiguosEnemigoAcorazado.top();
						if (listatmp.size() == 1){
							DisparoEnemigo(this.pilaDisparosContiguosEnemigoAcorazado.pop().borrarFin());
						}else{
							Random random = new Random (System.nanoTime());
							DisparoEnemigo(listatmp.borrarEn(random.nextInt(listatmp.size())));
						}
				}
				else if (!this.pilaDisparosContiguosEnemigoCrucero.isEmpty()){
					ListaEnlazada<BotonBarco> listatmp = this.pilaDisparosContiguosEnemigoCrucero.top();
					if (listatmp.size() == 1){
						DisparoEnemigo(this.pilaDisparosContiguosEnemigoCrucero.pop().borrarFin());
					}else{
						Random random = new Random (System.nanoTime());
						DisparoEnemigo(listatmp.borrarEn(random.nextInt(listatmp.size())));
					}
				}
				else if (!this.pilaDisparosContiguosEnemigoPortaviones.isEmpty()){
					ListaEnlazada<BotonBarco> listatmp = this.pilaDisparosContiguosEnemigoPortaviones.top();
					if (listatmp.size() == 1){
						DisparoEnemigo(this.pilaDisparosContiguosEnemigoPortaviones.pop().borrarFin());
					}else{
						Random random = new Random (System.nanoTime());
						DisparoEnemigo(listatmp.borrarEn(random.nextInt(listatmp.size())));
					}
				}
				else if (!this.pilaDisparosContiguosEnemigoSubmarino.isEmpty()){
					ListaEnlazada<BotonBarco> listatmp = this.pilaDisparosContiguosEnemigoSubmarino.top();
					if (listatmp.size() == 1){
						DisparoEnemigo(this.pilaDisparosContiguosEnemigoSubmarino.pop().borrarFin());
				}else{
						Random random = new Random (System.nanoTime());
						DisparoEnemigo(listatmp.borrarEn(random.nextInt(listatmp.size())));
					}
			}else{
					DisparoEnemigo (this.heapOrdenDisparoEnemigo.borrar().getBotonBarco());	
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private String formatoTiempo (){
		return this.timer.getTime()/60 + " minutos con " + this.timer.getTime()%60 + " segundos";
	}
	
	public void saveLog(){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("src\\Usuarios\\log.txt"));
            while (!this.filaLog.isEmpty()){
            	pw.println(this.filaLog.dequeue());
            }
            pw.close();
        }catch(IOException ex){
            
        }
    }
	
	public Juego getParent(){
		return this.parent;
	}
	
	public void iniciaCronometro(){
		this.timer.iniciaCronometro();
	}
	
}
