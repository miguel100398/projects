package MathChallengeGame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Titulos extends JLabel{

	
	public Titulos(Color color, String titulo, String fuente, int tamaño){
		super();
		this.setText(titulo);
		this.setForeground(color);
		this.setFont(new Font(fuente, 0, tamaño));
	}
}
