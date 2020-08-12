//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: EfectosSonido
//Fecha: 6/05/19
//Comentarios


package Juego;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jfugue.player.Player;

//Los archivos wav fueron descargados de la pagina Sound Jay y son de uso libre
//La libreria utilizada para reproducir los sonidos es jfugue, es de uso libre
//recopilada de http://www.jfugue.org/


public class EfectosSonido implements Runnable{

private int tocar;
	
	private Clip sonido;
	
	private Player player;
	
	private File acierto,
				 agua,
				 destruido,
				 victoria,
				 derrota;
	
	public EfectosSonido(){
		try{
			this.tocar=-1;
			this.sonido= AudioSystem.getClip();
			this.player=new Player();
			
			this.agua=new File("src\\Songs\\agua_final.wav");
			this.acierto=new File("src\\Songs\\explosion_final.wav");
			this.destruido=new File("src\\Songs\\hundimiento_barco.wav");
			this.victoria=new File("src\\Songs\\victory_final.wav");
			this.derrota=new File("src\\Songs\\derrota.wav");
			
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
	}
	
	public void setTocar(int tocar){
		this.tocar=tocar;
	}
	
	public void tocar(){
		Thread hilo=new Thread(this);
		hilo.start();
	}
	
	public void reset(){
		this.tocar=-1;
		this.sonido.close();
	}

	@Override
	public void run() {
		try{
			 if(this.tocar==1){
				sonido.open(AudioSystem.getAudioInputStream(this.agua));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				sonido.start();
				Thread.sleep(500);
				sonido.close();
			}
			else if (this.tocar==2){
				sonido.open(AudioSystem.getAudioInputStream(this.acierto));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				sonido.start();
				Thread.sleep(500);
				sonido.close();
			}
			else if (this.tocar==3){
				sonido.open(AudioSystem.getAudioInputStream(this.destruido));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				
				
				sonido.start();
				Thread.sleep(3500);
				sonido.close();
			}
			else if (this.tocar==4){
				sonido.open(AudioSystem.getAudioInputStream(this.victoria));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				
				
				sonido.start();
				Thread.sleep(3500);
				sonido.close();
			}
			else if (this.tocar==5){
				sonido.open(AudioSystem.getAudioInputStream(this.derrota));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				
				
				sonido.start();
				Thread.sleep(3500);
				sonido.close();
			}
			this.tocar=-1;
		}
		 catch (LineUnavailableException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}



