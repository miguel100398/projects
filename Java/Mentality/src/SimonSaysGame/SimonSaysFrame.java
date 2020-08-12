package SimonSaysGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Users.User;

public class SimonSaysFrame extends JFrame{
	
    private SimonSays parent;
	private ColorBoard cB;
	private Score sc;
	private Secuencia secuencia;
	private User user;
	
	public SimonSaysFrame(SimonSays parent, User user){
		super();
		this.parent = parent;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.user=user;
		this.setIconImage(new ImageIcon(this.parent.getImage()).getImage());
		//Inicializar Componentes
		
		this.secuencia= new Secuencia();
		this.sc=new Score(this, this.user);
		this.cB= new ColorBoard(this.secuencia, this.sc, this.parent, this);
		this.sc.setColorBoard(this.cB);
		JPanel jpS= new JPanel();
		jpS.setPreferredSize(new Dimension(700,75));
		jpS.setBackground(Color.gray);
		
		//Añadir Componentes a los paneles
		jpS.add(this.secuencia);
		
		//Añadir Paneles al frame
		this.add(this.sc,BorderLayout.NORTH);
		this.add(this.cB);
		this.add(jpS,BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
		
	}
	
	public void start(){
		this.cB.Start();
	}
	
	public void setUser(User user){
		this.user=user;
	}
	
	public User getUser(){
		return this.user;
	}

}

