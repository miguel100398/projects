package MathChallengeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MemoryGame.MemoryView;
import Menuprincipal.MainMenu;
import Users.User;

public class Mate extends JFrame implements ActionListener{
	
    private MathChallenge parent;
	private JButton btInstrucciones,
						btSumas,
						btRestas,
						btMultiplicaciones,
						btDivisiones,
						btRegresar,
						btCombinados;
	private Sumas sumas;
	private Restas restas;
	private Multiplicaciones multiplicaciones;
	private Divisiones divisiones;
	private Combinadas combinadas;
	private Juego gm;
	private JuegoSumas juegoSumas;
	private JuegoRestas juegoRestas;
	private JuegoMultiplicaciones juegoMultiplicaciones;
	private JuegoDivisiones juegoDivisiones;
	private JuegoCombinadas juegoCombinadas;
	private User user;
	
	public Mate(MathChallenge parent, User user){
		super();
		this.parent = parent;
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Mate.this.parent.endGame();
            }

        });
		this.setIconImage(new ImageIcon(this.parent.getImage()).getImage());
		this.setResizable(false);
		
		this.user=user;
		this.gm= new Juego(this.user);
		this.gm.setLocationRelativeTo(null);
		//Sumas
		this.juegoSumas= new JuegoSumas(this.gm);
		//Restas
		this.juegoRestas = new JuegoRestas(this.gm);
		//Multiplicaciones
		this.juegoMultiplicaciones = new JuegoMultiplicaciones(this.gm);
		//Divisiones
		this.juegoDivisiones = new JuegoDivisiones(this.gm);
		//Titulo
		JPanel Titulo= new JPanel();
		Titulo.setPreferredSize(new Dimension(700,100));
		Titulos lbTitulo= new Titulos(Color.BLUE, "Matematicas", "Tahoma", 60);
		Titulo.add(lbTitulo,BorderLayout.CENTER);
		
		//Botones
		JPanel botones= new JPanel();
		botones.setPreferredSize(new Dimension(700,400));
		
		this.btInstrucciones= new JButton();
		this.btInstrucciones.addActionListener(this);
		this.btInstrucciones.setIcon(new ImageIcon("src\\MathChallengeGame\\Images\\interrogacion.PNG"));
		this.btInstrucciones.setPreferredSize(new Dimension(25,25));
		JLabel lbespacio =new JLabel ("");
		lbespacio.setPreferredSize(new Dimension (650,25));
		botones.add(lbespacio);
		botones.add(this.btInstrucciones);
		
		this.btSumas = new JButton();
		this.btSumas.addActionListener(this);
		this.btSumas.setText("Sumas");
		this.btSumas.setBackground(Color.RED);
		this.btSumas.setPreferredSize(new Dimension(650,50));
		botones.add(this.btSumas);
		
		this.btRestas = new JButton();
		this.btRestas.addActionListener(this);
		this.btRestas.setText("Restas");
		this.btRestas.setBackground(Color.BLUE);
		this.btRestas.setPreferredSize(new Dimension(650,50));
		botones.add(this.btRestas);
		
		this.btMultiplicaciones = new JButton();
		this.btMultiplicaciones.addActionListener(this);
		this.btMultiplicaciones.setText("Multiplicaciones");
		this.btMultiplicaciones.setBackground(Color.GREEN);
		this.btMultiplicaciones.setPreferredSize(new Dimension(650,50));
		botones.add(this.btMultiplicaciones);
		
		this.btDivisiones = new JButton();
		this.btDivisiones.addActionListener(this);
		this.btDivisiones.setText("Divisiones");
		this.btDivisiones.setBackground(Color.YELLOW);
		this.btDivisiones.setPreferredSize(new Dimension(650,50));
		botones.add(this.btDivisiones);
		
		this.btCombinados = new JButton();
		this.btCombinados.addActionListener(this);
		this.btCombinados.setText("Combinadas");
		this.btCombinados.setBackground(Color.ORANGE);
		this.btCombinados.setPreferredSize(new Dimension(650,50));
		botones.add(this.btCombinados);
		
		this.btRegresar= new BotonRegresar();
		this.btRegresar.addActionListener(this);
		this.btRegresar.setIcon(new ImageIcon("src\\MathChallengeGame\\Images\\regresar.png"));
		botones.add(this.btRegresar);
		JLabel lbEspacio2 = new JLabel ("");
		lbEspacio2.setPreferredSize(new Dimension(550,45));
		botones.add(lbEspacio2);
		
		//Añadir Componentes
		this.add(Titulo,BorderLayout.NORTH);
		this.add(botones);
		
		this.pack();
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.btSumas){
			if (this.sumas==null){
				this.sumas= new Sumas(this, this.juegoSumas, this.gm);
				this.gm.setJuegoSumas(this.juegoSumas);
				this.gm.setSumas(this.sumas);
				this.sumas.setLocationRelativeTo(null);
				this.setVisible(false);
				this.sumas.setVisible(true);
			}else{
				this.sumas.setVisible(true);
				this.setVisible(false);
			}
		}
		else if (e.getSource()==this.btRestas){
			if (this.restas==null){
				this.restas = new Restas (this, this.juegoRestas, this.gm);
				this.gm.setJuegoRestas(this.juegoRestas);
				this.gm.setRestas(this.restas);
				this.restas.setLocationRelativeTo(null);
				this.setVisible(false);
				this.restas.setVisible(true);
			}
			else{
				this.restas.setVisible(true);
				this.setVisible(false);
			}
		}
		else if (e.getSource()==this.btMultiplicaciones){
			if(this.multiplicaciones==null){
				this.multiplicaciones = new Multiplicaciones(this, this.juegoMultiplicaciones, this.gm);
				this.gm.setJuegoMultiplicaciones(this.juegoMultiplicaciones);
				this.gm.setMultiplicaciones(this.multiplicaciones);
				this.multiplicaciones.setLocationRelativeTo(null);
				this.setVisible(false);
				this.multiplicaciones.setVisible(true);
			}
			else{
				this.multiplicaciones.setVisible(true);
				this.setVisible(false);
			}
		}
		else if (e.getSource()==this.btDivisiones){
			if(this.divisiones==null){
				this.divisiones = new Divisiones(this, this.juegoDivisiones, this.gm);
				this.gm.setJuegoDivisiones(this.juegoDivisiones);
				this.gm.setDivisiones(this.divisiones);
				this.divisiones.setLocationRelativeTo(null);
				this.setVisible(false);
				this.divisiones.setVisible(true);
			}else{
				this.divisiones.setVisible(true);
				this.setVisible(false);
			}
		}
		else if (e.getSource()==this.btCombinados){
			if (this.combinadas==null){
				this.juegoCombinadas = new JuegoCombinadas(this.gm, this.juegoSumas, this.juegoRestas, this.juegoMultiplicaciones, this.juegoDivisiones);
				this.combinadas = new Combinadas(this, this.juegoCombinadas, this.gm);
				this.gm.setJuegoCombinadas(this.juegoCombinadas);
				this.gm.setCombinadas(this.combinadas);
				this.combinadas.setLocationRelativeTo(null);
				this.setVisible(false);
				this.combinadas.setVisible(true);
			}else{
				this.combinadas.setVisible(true);
				this.setVisible(false);
			}
		}
		else if (e.getSource()==this.btInstrucciones){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Matematicas \n"
					+ "Existen 5 modalidades de juego\n"
					+ "En cada una solo encontrarás ese tipo de operaciones\n"
					+ "Responde las operaciones corectamente y acumula puntos", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			// Terminar el juego
		    this.parent.endGame();
		}
			
	}

}
