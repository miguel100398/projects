package Songs;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    private File musicFile;
    private Clip soundClip;
    private boolean hasStopped,
                    looping;
    
    public Music(String url){
        this.musicFile = new File(url);
        this.hasStopped = true;
        this.looping = false;
        
    }
    
    public void playSound(){
        if(this.hasStopped && !looping){
            try {
                this.soundClip = AudioSystem.getClip();
                this.soundClip.open(AudioSystem.getAudioInputStream(this.musicFile));
            } catch (LineUnavailableException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            this.soundClip.start();
            
            Thread hilo = new Thread(new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep(Music.this.soundClip.getMicrosecondLength()/1000);
                        if(!Music.this.hasStopped){
                            Music.this.hasStopped = true;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            this.hasStopped = false;
        }
    }
    
    public void loopSound(){
        // Para dejar de escuchar, es necesario parar el sonido
        this.looping = true;
        if(this.hasStopped){
            try {
                this.soundClip = AudioSystem.getClip();
                this.soundClip.open(AudioSystem.getAudioInputStream(this.musicFile));
            } catch (LineUnavailableException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (UnsupportedAudioFileException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            this.soundClip.start();
            
            Thread hilo = new Thread(new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep(Music.this.soundClip.getMicrosecondLength()/1000);
                        if(!Music.this.hasStopped){
                            Music.this.hasStopped = true;
                            Music.this.loopSound();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
            this.hasStopped = false;
        }
        
    }
    
    public void stopSound(){
        if(!this.hasStopped){
            this.soundClip.stop();
            this.hasStopped = true;
            if(this.looping){
                this.looping = false;
            }
        }
    }
    
    
}
