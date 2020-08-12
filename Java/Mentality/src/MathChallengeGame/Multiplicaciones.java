package MathChallengeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Multiplicaciones extends JFrame implements ActionListener {

	private JButton btInstrucciones,
	btNivelUno,
	btNivelDos,
	btNivelTres,
	btNivelCuatro,
	btRegresar,
	btNivelCinco;
	private Mate mate;
	private Juego gm;
	private JuegoMultiplicaciones juegoMultiplicaciones;

	public Multiplicaciones(Mate mate, JuegoMultiplicaciones juegoMultiplicaciones, Juego gm){
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		this.mate=mate;
		this.gm=gm;
		this.juegoMultiplicaciones=juegoMultiplicaciones;
		//Titulo
		JPanel Titulo= new JPanel();
		Titulo.setPreferredSize(new Dimension(700,100));
		Titulos lbTitulo= new Titulos(Color.BLUE, "Multiplicaciones", "Tahoma", 60);
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

		this.btNivelUno = new JButton();
		this.btNivelUno.addActionListener(this);
		this.btNivelUno.setText("Nivel 1");
		this.btNivelUno.setBackground(Color.RED);
		this.btNivelUno.setPreferredSize(new Dimension(650,50));
		botones.add(this.btNivelUno);

		this.btNivelDos = new JButton();
		this.btNivelDos.addActionListener(this);
		this.btNivelDos.setText("Nivel 2");
		this.btNivelDos.setBackground(Color.BLUE);
		this.btNivelDos.setPreferredSize(new Dimension(650,50));
		botones.add(this.btNivelDos);

		this.btNivelTres = new JButton();
		this.btNivelTres.addActionListener(this);
		this.btNivelTres.setText("Nivel 3");
		this.btNivelTres.setBackground(Color.GREEN);
		this.btNivelTres.setPreferredSize(new Dimension(650,50));
		botones.add(this.btNivelTres);

		this.btNivelCuatro = new JButton();
		this.btNivelCuatro.addActionListener(this);
		this.btNivelCuatro.setText("Nivel 4");
		this.btNivelCuatro.setBackground(Color.YELLOW);
		this.btNivelCuatro.setPreferredSize(new Dimension(650,50));
		botones.add(this.btNivelCuatro);

		this.btNivelCinco = new JButton();
		this.btNivelCinco.addActionListener(this);
		this.btNivelCinco.setText("Nivel 5");
		this.btNivelCinco.setBackground(Color.ORANGE);
		this.btNivelCinco.setPreferredSize(new Dimension(650,50));
		botones.add(this.btNivelCinco);

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
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.btNivelUno){
			this.gm.setNivel(1);
			this.gm.setJuego("Multiplicaciones");
			this.juegoMultiplicaciones.setNivel(1);
			this.gm.setOperador("*");
			this.gm.reset();
			this.gm.activarBotones();
			this.gm.setVisible(true);
			this.setVisible(false);
			this.gm.start();
		}
		else if(e.getSource()==this.btNivelDos){
			this.gm.setNivel(2);
			this.gm.setJuego("Multiplicaciones");
			this.juegoMultiplicaciones.setNivel(2);
			this.gm.setOperador("*");
			this.gm.reset();
			this.gm.activarBotones();
			this.gm.setVisible(true);
			this.setVisible(false);
			this.gm.start();
		}
		else if(e.getSource()==this.btNivelTres){
			this.gm.setNivel(3);
			this.gm.setJuego("Multiplicaciones");
			this.juegoMultiplicaciones.setNivel(3);
			this.gm.setOperador("*");
			this.gm.reset();
			this.gm.activarBotones();
			this.gm.setVisible(true);
			this.setVisible(false);
			this.gm.start();
		}
		else if(e.getSource()==this.btNivelCuatro){
			this.gm.setNivel(4);
			this.gm.setJuego("Multiplicaciones");
			this.juegoMultiplicaciones.setNivel(4);
			this.gm.setOperador("*");
			this.gm.reset();
			this.gm.activarBotones();
			this.gm.setVisible(true);
			this.setVisible(false);
			this.gm.start();
		}
		else if(e.getSource()==this.btNivelCinco){
			this.gm.setNivel(5);
			this.gm.setJuego("Multiplicaciones");
			this.juegoMultiplicaciones.setNivel(5);
			this.gm.setOperador("*");
			this.gm.reset();
			this.gm.activarBotones();
			this.gm.setVisible(true);
			this.setVisible(false);
			this.gm.start();
		}
		else if (e.getSource()==this.btInstrucciones){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Multiplicaciones \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "Nivel 1: 1 digito * 1 digito\n"
					+ "Nivel 2: 2 digitos * 1 digito\n"
					+ "Nivel 3: 3 digitos * 1 digitos\n"
					+ "Nivel 4: 4 digitos  * 1 digitos\n"
					+ "Nivel 5: 2 digitos * 2", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			this.mate.setVisible(true);
			this.setVisible(false);
		}

}
}
