package MathChallengeGame;

import javax.swing.JOptionPane;

import Games.Game;
import Menuprincipal.MainMenu;
import Users.User;

public class MathChallenge extends Game implements Runnable{

    private MainMenu parent;
	private Mate m;
	private int tm;
	private boolean count;
    
	public MathChallenge(MainMenu parent) {
        super("MathGame", "src\\Games\\MathChallengeThumbnail.png");
        this.parent = parent;
        this.tm=0;
        this.count=false;
    }
	
    public void endGame() {
        this.count=false;
    	this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()].setTime(this.tm, "MathGame");
    	this.parent.saveDatabase();
        this.m.dispose();
        this.parent.getModel().show();
        this.parent.getView().update();
    }

    public void playGame() {
    	this.tm=0;
    	String[] botones= {"Iniciar", "volver"};
        Object[] boton={"Iniciar", "volver"};
        User user=(this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()]);
        this.m= new Mate(this, user);
        this.count=true;
        Thread hilo = new Thread(this);
        hilo.start();
        this.m.setLocationRelativeTo(null);
        int choice = JOptionPane.showOptionDialog(m, "Bienvenido al juego de Matematicas \n"
                + "Existen 5 modalidades de juego\n"
                + "En cada una solo encontraras ese tipo de operaciones\n"
                + "Responde las operaciones corectamente y acumula puntos", "Intrucciones",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,botones, boton[0]);
        if (choice==JOptionPane.NO_OPTION || choice==JOptionPane.CLOSED_OPTION){
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
