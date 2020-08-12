package MathChallengeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelJuegos <t> extends JPanel implements ActionListener, KeyListener{

	private JButton btInstrucciones;
	private String juego,
				   operador;
	private Titulos titulo;
	private JLabel lbdigito1,
				   lbdigito2,
				   lboperador,
				   lbresiduo;
	private JTextField resultado,
					 residuo;
	private int nivel;
	private Juego gm;
	private t digito;
	
	public PanelJuegos() {
		super();
		this.setPreferredSize(new Dimension(700,300));
		this.setLayout(null);
		
		Font fuente = new Font("Tahoe", 0, 30);
		this.lbdigito1 = new JLabel("");
		this.lbdigito1.setFont(fuente);
		this.lboperador = new JLabel (this.operador);
		this.lboperador.setFont(fuente);
		this.lbdigito2 = new JLabel ("");
		this.lbdigito2.setFont(fuente);
		this.resultado = new JTextField();
		this.resultado.setPreferredSize(new Dimension (150,30));
		this.resultado.setFont(fuente);
		this.resultado.addKeyListener(this);
		this.lbresiduo = new JLabel("residuo");
		this.lbresiduo.setFont(fuente);
		this.lbresiduo.setVisible(false);
		this.residuo = new JTextField();
		this.residuo.setPreferredSize(new Dimension (150,30));
		this.residuo.setFont(fuente);
		this.residuo.setVisible(false);
		this.residuo.addKeyListener(this);
		
		this.btInstrucciones= new JButton();
		this.btInstrucciones.addActionListener(this);
		this.btInstrucciones.setIcon(new ImageIcon("src\\MathChallengeGame\\Images\\interrogacion.PNG"));
		this.btInstrucciones.setPreferredSize(new Dimension(25,25));
		this.btInstrucciones.setBounds(660, 80, 25, 25);
		this.add(this.btInstrucciones);
		
		this.titulo = new Titulos(Color.BLUE,"","Tahoe",30);
		this.titulo.setBounds(50, 0, 700, 100);
		this.add(this.titulo,BorderLayout.NORTH);
		this.lbdigito1.setBounds(290, 120, 100, 45);
		this.add(lbdigito1);
		this.lbdigito2.setBounds(290, 170, 100, 45);
		this.add(this.lbdigito2);
		this.lboperador.setBounds(260, 150, 25, 25);
		this.add(this.lboperador);
		JLabel igual = new JLabel();
		igual.setText("=");
		igual.setBounds(240,210, 25, 25);
		igual.setFont(fuente);
		this.add(igual);
		this.resultado.setBounds(270, 210, 100, 30);
		this.add(this.resultado);
		this.lbresiduo.setBounds(380, 210, 125, 30);
		this.add(lbresiduo);
		this.residuo.setBounds(480, 210, 100, 30);
		this.add(residuo);
		this.residuo.setHorizontalAlignment(SwingConstants.RIGHT); 
		this.resultado.setHorizontalAlignment(SwingConstants.RIGHT); 
	}

	public void setNivel (int nivel){
		this.nivel=nivel;
		this.titulo.setText("Juego de " + this.juego + "  Nivel: " + this.nivel);
	}
	
	public void setJuego(String juego){
		this.juego=juego;
	}
	
	public void ponerTitulo(){
		this.titulo.setText("Juego de " + this.juego + "  Nivel: " + this.nivel);
		if (this.juego.equals("Operaciones combinadas")){
			this.titulo.setFont(new Font("Tahoe", 0, 30));
		}
		else if (this.juego.equals("Multiplicaciones")){
			this.titulo.setFont(new Font("Tahoe", 0, 35));
		}
		else{
			this.titulo.setFont(new Font("Tahoe", 0, 50));
		}
	}
	
	public void setDigito1(t digito){
		this.lbdigito1.setText(""+digito);
	}
	
	public void setDigito2(t digito){
		this.lbdigito2.setText(""+digito);
	}
	
	public void setOperador(String operador){
		this.operador=operador;
		this.lboperador.setText(operador);
	}
	
	public String getResultado(){
		return this.resultado.getText();
	}
	
	public String getResiduo(){
		return this.residuo.getText();
	}
	
	
	public void cleanResultado(){
		this.resultado.setText("");
		this.residuo.setText(".0");
	}
	
	public void setJuego (Juego gm){
		this.gm=gm;
	}
	
	public void residuo(boolean activar){
		if (activar){
			this.lbresiduo.setVisible(true);
			this.residuo.setVisible(true);
			this.residuo.setText(".0");
		}
		else{
			this.lbresiduo.setVisible(false);
			this.residuo.setVisible(false);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (this.juego.equals("Sumas")){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Sumas \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "Nivel 1: 1 digito + 1 digito\n"
					+ "Nivel 2: 2 digitos + 2 digito\n"
					+ "Nivel 3: 3 digitos + 3 digitos\n"
					+ "Nivel 4: 4 digitos + 4 digitos\n"
					+ "Nivel 5: 5 digitos + 3 digitos", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (this.juego.equals("Restas")){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Restas \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "Nivel 1: 1 digito - 1 digito\n"
					+ "Nivel 2: 2 digitos - 1 digito\n"
					+ "Nivel 3: 2 digitos - 2 digitos\n"
					+ "Nivel 4: 2 digitos  - 2 digitos con resultados negativos\n"
					+ "Nivel 5: 2 digitos - 3 digitos con negativos", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (this.juego.equals("Multiplicaciones")){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Multiplicaciones \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "Nivel 1: 1 digito * 1 digito\n"
					+ "Nivel 2: 2 digitos * 1 digito\n"
					+ "Nivel 3: 3 digitos * 1 digitos\n"
					+ "Nivel 4: 4 digitos * 1 digitos\n"
					+ "Nivel 5: 2 digitos * 2 ", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (this.juego.equals("Divisiones")){
			JOptionPane.showMessageDialog(this,"Bienvenido al juego de Divisiones \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "En los niveles 1, 2 y 3 unicamente escriba el cociente\n"
					+ "EN los niveles 4 y 5 escriba el residuo"
					+ "Nivel 1: 1 digito / 1 digito\n"
					+ "Nivel 2: 2 digitos / 1 digito\n"
					+ "Nivel 3: 2 digitos / 2 digitos\n"
					+ "Nivel 4: 2 digitos / 2 digitos + residuo\n"
					+ "Nivel 5: 3 digitos / 2 digitos + residuo", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(this,"Bienvenido al juego con operaciones combinadas \n"
					+ "Excribe el resultado correcto dentro de la caja de texto antes de que se agote el tiempo\n"
					+ "Apareceran operaciones de los diferentes juegos\n"
					+ "Las operaciones serán de acuerdo al nivel elegido", "Intrucciones", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			this.gm.calcular();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

	
}
