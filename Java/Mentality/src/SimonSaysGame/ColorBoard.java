package SimonSaysGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

public class ColorBoard extends JPanel implements ActionListener, Runnable {
	
    private SimonSays parent;
	private JButton btRed,
					btBlue,
					btYellow,
					btGreen;
	private Secuencia secuencia;
	private Score sc;
	private int comparar;
	private String[] botones= {"Reiniciar", "volver"};
	private Object[] boton={"Reiniciar", "volver"};
	private int largo3,
				ancho3,
				largo2,
				ancho2,
				largo1,
				ancho1;
	private final static Image uno=new ImageIcon("src\\SimonSaysGame\\Images\\1.PNG").getImage();
	private final static Image dos=new ImageIcon("src\\SimonSaysGame\\Images\\2.JPG").getImage();
	private final static Image tres=new ImageIcon("src\\SimonSaysGame\\Images\\3.PNG").getImage();
	private final static Image start=new ImageIcon("src\\SimonSaysGame\\Images\\start.PNG").getImage();
	private Titulo titulo;
	private JLabel lbInicio;
	private Musica musica;
	private boolean dibujarStart;
	private SimonSaysFrame frame;
	
	
	public ColorBoard(Secuencia secuencia, Score sc, SimonSays parent, SimonSaysFrame frame){
		super();
		this.parent = parent;
		this.frame = frame;
		this.setPreferredSize(new Dimension(700,350));
		this.setBackground(Color.gray);
		
		//Inicializar componentes
		this.btRed=new JButton();
		this.btRed.setBackground(Color.RED);
		this.btRed.setPreferredSize(new Dimension(100,100));
		this.btRed.setEnabled(false);
		this.btRed.addActionListener(this);
		this.btRed.setVisible(false);
		
		
		this.btBlue=new JButton();
		this.btBlue.setBackground(Color.BLUE);
		this.btBlue.setPreferredSize(new Dimension(100,100));
		this.btBlue.setEnabled(false);
		this.btBlue.addActionListener(this);
		this.btBlue.setVisible(false);
		
		this.btYellow=new JButton();
		this.btYellow.setBackground(Color.yellow);
		this.btYellow.setPreferredSize(new Dimension(100,100));
		this.btYellow.setEnabled(false);
		this.btYellow.addActionListener(this);
		this.btYellow.setVisible(false);
		
		this.btGreen=new JButton();
		this.btGreen.setBackground(Color.GREEN);
		this.btGreen.setPreferredSize(new Dimension(100,100));
		this.btGreen.setEnabled(false);
		this.btGreen.addActionListener(this);
		this.btGreen.setVisible(false);
		
		this.lbInicio= new JLabel("          Observa la Secuencia          ");
		this.lbInicio.setFont(new Font("Tahoma",0,36));
		this.lbInicio.setVisible(false);
		
		this.secuencia=secuencia;
		this.sc=sc;
		this.comparar=0;
		
		this.largo3=0;
		this.ancho3=0;
		this.largo2=0;
		this.ancho2=0;
		this.largo1=0;
		this.ancho1=0;
		this.dibujarStart=false;
		
		
		this.titulo= new Titulo();
		this.musica= new Musica();
		this.secuencia.setMusica(this.musica);
		
		//Agregar Componentes
		this.add(this.titulo,BorderLayout.NORTH);
		this.add(this.btRed);
		this.add(this.btBlue);
		this.add(this.btYellow);
		this.add(this.btGreen);
		this.add(this.lbInicio);
	}
	
	public void Start(){
			this.reset();
			this.secuencia.CrearSecuencia();
			this. hilo();
	}
	
	public void cuentaRegresiva(){
		try{
		
			
			this.musica.setTocar(4);
			this.musica.tocar();
			this.largo3=200;
			this.ancho3=200;
			while (this.largo3>0){
				Thread.sleep(50);
				this.largo3-=10;
				this.ancho3-=10;
				this.repaint();
			}
			this.musica.setTocar(4);
			this.musica.tocar();
			this.largo2=200;
			this.ancho2=200;
			while (this.largo2>0){
				Thread.sleep(50);
				this.largo2-=10;
				this.ancho2-=10;
				this.repaint();
			}
			this.musica.setTocar(4);
			this.musica.tocar();
			this.largo1=200;
			this.ancho1=200;
			while (this.largo1>0){
				Thread.sleep(50);
				this.largo1-=10;
				this.ancho1-=10;
				this.repaint();
			}
			this.musica.setTocar(5);
			this.musica.tocar();
			this.dibujarStart=true;
			repaint();
			Thread.sleep(1500);
			this.dibujarStart=false;
			this.repaint();
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		g.drawImage(ColorBoard.tres, (this.getWidth()/2)-(this.largo3/2), (this.getHeight()/2)-this.ancho3+140, this.largo3, this.ancho3, this);
		g.drawImage(ColorBoard.dos, (this.getWidth()/2)-(this.largo2/2), (this.getHeight()/2)-this.ancho2+140, this.largo2, this.ancho2, this);
		g.drawImage(ColorBoard.uno, (this.getWidth()/2)-(this.largo1/2), (this.getHeight()/2)-this.ancho1+140, this.largo1, this.ancho1, this);
		if(this.dibujarStart){
			g.drawImage(ColorBoard.start, (this.getWidth()/2)-100, (this.getHeight()/2)-50, 200,200, this);
		}
	}
	
	public void hilo(){
		Thread hilo= new Thread(this);
		hilo.start();
	}
	public void botones(){
		this.btBlue.setEnabled(true);
		this.btGreen.setEnabled(true);
		this.btRed.setEnabled(true);
		this.btYellow.setEnabled(true);
		this.lbInicio.setText("Introduce la Secuencia");
		this.sc.iniciaCronometro();
	}
	
	public void Victoria(){
		this.sc.detenerCronometro();
		this.comparar=0;
		this.btBlue.setEnabled(false);
		this.btGreen.setEnabled(false);
		this.btRed.setEnabled(false);
		this.btYellow.setEnabled(false);
		this.sc.agregarPunto(this.secuencia.getNumColores()*3);
		this.sc.agregarTiempo(this.secuencia.getNumColores());
		this.sc.agregarNivel();
		this.secuencia.SiguienteSecuencia();
		this.hilo();
		this.lbInicio.setText("Observa la secuencia");
	}
	
	public void Derrota(){
		this.frame.getUser().setScore(this.sc.getScore(), "SimonSays");
		this.frame.getUser().setLevel(this.sc.getNivel());
		this.sc.detenerCronometro();
		this.musica.setTocar(6);
		this.musica.tocar();
		this.btBlue.setEnabled(false);
		this.btGreen.setEnabled(false);
		this.btRed.setEnabled(false);
		this.btYellow.setEnabled(false);
		int choice=JOptionPane.showOptionDialog(this, this.frame.getUser().getName()+ " tu Puntuacion fue de: " +this.sc.getScore() +"\n"
				+ "¿Quieres intentarlo de nuevo?", "Has perdido",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,this.botones, this.boton[0]);
		if (choice==JOptionPane.YES_OPTION){
			try{	
				this.musica.reset();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.Start();
		}
		else{
			this.parent.endGame();
		}
	}
	
	public void Compara(int comparar){
		if (comparar==this.secuencia.getSecuencia()[this.comparar]){
			this.comparar++;
			if (this.comparar==this.secuencia.getSecuencia().length){
				this.sc.agregarPunto(1);
				this.Victoria();
			}
			else{
				this.sc.agregarPunto(1);
			}
		}
		else{
			this.Derrota();
		}
	}
	

	public void actionPerformed(ActionEvent e) {

		if (e.getSource()==this.btBlue){
			this.Compara(1);
			this.musica.setTocar(1);
			this.musica.tocar();
		}
		else if (e.getSource()==this.btGreen){
			this.Compara(3);
			this.musica.setTocar(3);
			this.musica.tocar();
		}
		else if (e.getSource()==this.btRed){
			this.Compara(0);
			this.musica.setTocar(0);
			this.musica.tocar();
		}
		else{
			this.Compara(2);
			this.musica.setTocar(2);
			this.musica.tocar();
		}
	}
	
	public void run() {
		try {
			if (this.secuencia.getNumColores()==3){
				this.cuentaRegresiva();
				this.btBlue.setVisible(true);
				this.btRed.setVisible(true);
				this.btGreen.setVisible(true);
				this.btYellow.setVisible(true);
				this.lbInicio.setVisible(true);
				Thread.sleep(300);
			}
			Thread.sleep(1000);
			for (int i = 0; i < this.secuencia.getNumColores(); i++) {
				this.secuencia.PonerSecuencia(i);
				Thread.sleep(1000);
				this.musica.reset();
				this.secuencia.setBackground(Color.BLACK);
				Thread.sleep(10);
			}
			this.secuencia.setBackground(Color.gray);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.botones();
	}
	
	public void reset(){
		this.largo3=200;
		this.ancho3=200;
		this.comparar=0;
		this.sc.Reset();
		this.secuencia.reset();
		this.btBlue.setVisible(false);
		this.btGreen.setVisible(false);
		this.btRed.setVisible(false);
		this.btYellow.setVisible(false);
		this.lbInicio.setText("Observa la secuencia");
		this.lbInicio.setVisible(false);
	}
	
	
}
