package SimonSaysGame;

// Los archivos wav fueron descargados de la pagina Sound Jay y son de uso libre

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jfugue.player.Player;


public class Musica implements Runnable {

	private int tocar;
	
	private Clip sonido;
	
	private Player player;
	
	private File cuenta,
				 inicio,
				 derrota;
				
	
	public Musica(){
		try{
		this.tocar=-1;
		this.sonido= AudioSystem.getClip();
		this.player=new Player();
		
		this.cuenta=new File("src\\SimonSaysGame\\Songs\\cuenta.wav");
		this.inicio=new File("src\\SimonSaysGame\\Songs\\inicio.wav");
		this.derrota=new File("src\\SimonSaysGame\\Songs\\derrota.wav");
		
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
			if (this.tocar==0){
				this.player.play("Cw");
			}
			else if(this.tocar==1){
				this.player.play("Dw");
			}
			else if(this.tocar==2){
				this.player.play("Ew");
			}
			else if (this.tocar==3){
				this.player.play("Aw");
			}
			else if(this.tocar==4){
				sonido.open(AudioSystem.getAudioInputStream(this.cuenta));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				sonido.start();
				Thread.sleep(500);
				sonido.close();
			}
			else if (this.tocar==5){
				sonido.open(AudioSystem.getAudioInputStream(this.inicio));
				//Las siguientes dos lineas fueron tomadas de Stack Overflow para disminuir el volumen del wav
				FloatControl gainControl =(FloatControl) sonido.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f);
				sonido.start();
				Thread.sleep(500);
				sonido.close();
			}
			else if (this.tocar==6){
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
