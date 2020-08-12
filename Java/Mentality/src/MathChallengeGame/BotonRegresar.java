package MathChallengeGame;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonRegresar extends JButton{

	public BotonRegresar(){
		super();
		this.setPreferredSize(new Dimension(100,45));
		this.setIcon(new ImageIcon("regresar.PNG"));
	}
}
