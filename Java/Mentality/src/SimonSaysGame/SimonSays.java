package SimonSaysGame;

import java.awt.Color;

import javax.swing.JOptionPane;

import Games.Game;
import Menuprincipal.MainMenu;
import Users.User;

public class SimonSays extends Game implements Runnable{
    
    private MainMenu parent;
    private SimonSaysFrame s;
    private int tm;
    private boolean count;
    
	public SimonSays(MainMenu parent) {
        super("SimonSays", "src\\Games\\SimonSaysThumbnail.png");
        this.parent = parent;
        this.tm=0;
        this.count=false;
    }

    public void endGame() {
    	this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()].setTime(this.tm,"SimonSays");
    	this.count=false;
    	this.parent.saveDatabase();
        this.s.dispose();
        this.parent.getModel().show();
        this.parent.getView().update();
    }

    public void playGame() {
    	this.tm=0;
    	String[] botones= {"Iniciar", "volver"};
        Object[] boton={"Iniciar", "volver"};
        User user=(this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()]);
        s=new SimonSaysFrame(this, user);
        this.count=true;
        Thread hilo = new Thread(this);
        hilo.start();
        s.setLocationRelativeTo(null);
        int choice = JOptionPane.showOptionDialog(s, "Bienvenido al juego de simon Dice \n"
                + "1.-Observe la secuencia de colores que \n"
                + "aparecera en la parte inferior de la pantalla\n"
                + "2.-Memorice la secuencia de Colors\n"
                + "3.-Repita la secuencia presionando los\n"
                + "Botones de la parte superior\n"
                + "4.-Perdera si presiona el boton incorrecto\n"
                + "o si se agota el tiempo\n","Instrucciones,",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,botones, boton[0]);
        if (choice==JOptionPane.YES_OPTION){
            s.start();
        }
        else{
            this.endGame();
        }
    }

	@Override
	public void run() {
		while (this.count){
			try {
				Thread.sleep(1000);
				this.tm++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
