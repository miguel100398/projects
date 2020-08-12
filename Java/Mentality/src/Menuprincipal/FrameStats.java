package Menuprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameStats extends JFrame implements ActionListener{
	
	private MainMenuView parent;
	private JButton btVolver;
	private MainMenuModel model;
	private JPanel panelStats, panelButton, panelNombre;
	private JLabel nombre;
	private JLabel[][] estadisticas;
	
	public FrameStats(MainMenuView parent, MainMenuModel model){
		super("Estadisticas");
		this.setResizable(false);
		this.parent=parent;
		this.model=model;
		this.estadisticas = new JLabel[4][4];
		this.setIconImage(new ImageIcon("src\\brain.jpg").getImage());
		//Panel
		this.panelStats = new JPanel();
		this.panelStats.setPreferredSize(new Dimension(700,400));
		this.panelStats.setBackground(new Color(0xb9c2d3));
        this.panelStats.setForeground(Color.WHITE);
        this.panelStats.setLayout(new BoxLayout(this.panelStats, BoxLayout.PAGE_AXIS));
        this.panelButton = new JPanel();
        this.panelButton.setPreferredSize(new Dimension(700,100));
        this.panelButton.setBackground(new Color(0xb9c2d3));
        this.panelButton.setForeground(Color.WHITE);
        this.panelNombre = new JPanel(){
        	 public void paintComponent(Graphics g){
                 super.paintComponent(g);
                 g.drawImage(new ImageIcon(FrameStats.this.model.getUsers()[FrameStats.this.model.getCurrentUser()].getImage()).getImage(), 325, 35, 55, 55, this);
             }
        };
        this.panelNombre.setPreferredSize(new Dimension(700,100));
        this.panelNombre.setBackground(new Color(0xa1adc4));
        this.panelNombre.setForeground(Color.WHITE);
        this.panelNombre.setLayout(new BoxLayout(this.panelNombre, BoxLayout.PAGE_AXIS));
		
       
		//Etiquetas
		this.nombre= new JLabel(this.model.getUsers()[this.model.getCurrentUser()].getName());
		this.nombre.setFont(new Font("default", Font.BOLD, 16));
		this.nombre.setAlignmentX(CENTER_ALIGNMENT);
		this.panelNombre.add(this.nombre);
		for (int i = 0; i < 4; i++) {
			this.estadisticas[0][i]= new JLabel();
			this.estadisticas[0][i].setFont(new Font("default", Font.BOLD, 30));
			this.estadisticas[0][i].setForeground(new Color(0x47556b));
			this.estadisticas[1][i] = new JLabel();
			this.estadisticas[1][i].setFont(new Font("default", Font.BOLD, 18));
			this.estadisticas[2][i] = new JLabel();
			this.estadisticas[2][i].setFont(new Font("default", Font.BOLD, 18));
			this.estadisticas[3][i] = new JLabel();
			this.estadisticas[3][i].setFont(new Font("default", Font.BOLD, 18));
		}
		//nombre del juego
		this.estadisticas[0][0].setText("Magic Tiles");
		this.estadisticas[0][1].setText("Memory");
		this.estadisticas[0][2].setText("Simon Says");
		this.estadisticas[0][3].setText("Math Game");
		//estadisticas del usuario
		this.estadisticas[1][0].setText("          High Score: " + this.model.getUsers()[this.model.getCurrentUser()].getScore("MagicTiles"));
		this.estadisticas[2][0].setText("          Total de tiempo jugado: " + this.model.getUsers()[this.model.getCurrentUser()].getTime("MagicTiles")/60 + " minutos con " + this.model.getUsers()[this.model.getCurrentUser()].getTime("MagicTiles")%60 + " segundos");
		this.estadisticas[1][1].setText("          High Score: " + this.model.getUsers()[this.model.getCurrentUser()].getScore("Memory"));
		this.estadisticas[2][1].setText("          Mejor Tiempo: " + this.model.getUsers()[this.model.getCurrentUser()].getTime("Memory")/60 + " minutos con " + this.model.getUsers()[this.model.getCurrentUser()].getTime("Memory")%60 + " segundos");
		this.estadisticas[1][2].setText("          High Score: " + this.model.getUsers()[this.model.getCurrentUser()].getScore("SimonSays"));
		this.estadisticas[2][2].setText("          Total de tiempo jugado: " + this.model.getUsers()[this.model.getCurrentUser()].getTime("SimonSays")/60 + " minutos con " + this.model.getUsers()[this.model.getCurrentUser()].getTime("SimonSays")%60 + " segundos");
		this.estadisticas[3][2].setText("          Nivel máximo: " + this.model.getUsers()[this.model.getCurrentUser()].getLevel());
		this.estadisticas[1][3].setText("          High Score: " + this.model.getUsers()[this.model.getCurrentUser()].getScore("MathGame"));
		this.estadisticas[2][3].setText("          Total de tiempo jugado: " + this.model.getUsers()[this.model.getCurrentUser()].getTime("MathGame")/60 + " minutos con " + this.model.getUsers()[this.model.getCurrentUser()].getTime("MathGame")%60 + " segundos");	
		//Agregar etiquetas al panel
		this.panelStats.add(this.estadisticas[0][0]);
		this.panelStats.add(this.estadisticas[1][0]);
		this.panelStats.add(this.estadisticas[2][0]);
		this.panelStats.add(this.estadisticas[0][1]);
		this.panelStats.add(this.estadisticas[1][1]);
		this.panelStats.add(this.estadisticas[2][1]);
		this.panelStats.add(this.estadisticas[0][2]);
		this.panelStats.add(this.estadisticas[1][2]);
		this.panelStats.add(this.estadisticas[2][2]);
		this.panelStats.add(this.estadisticas[3][2]);
		this.panelStats.add(this.estadisticas[0][3]);
		this.panelStats.add(this.estadisticas[1][3]);
		this.panelStats.add(this.estadisticas[2][3]);
		 //Boton
		this.btVolver = new JButton("Regresar");
		this.btVolver.setBackground(new Color(0x47556b));
        this.btVolver.setForeground(Color.WHITE);
        this.btVolver.setPreferredSize(new Dimension(200,50));
        this.btVolver.addActionListener(this);
        this.panelButton.add(this.btVolver);
        
        //Agregar componentes
        this.add(this.panelNombre,BorderLayout.NORTH);
        this.add(this.panelStats);
        this.add(this.panelButton,BorderLayout.SOUTH);
        this.pack();
        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                FrameStats.this.closeWindow();
            }

        });
        
		this.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		this.closeWindow();
	}
	
	private void closeWindow(){
	    this.dispose();
        this.model.show();
        this.parent.update();
	}

}
