//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: ColocarBarcos
//Fecha: 6/05/19
//Comentarios


package Juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColocarBarcos extends JFrame implements ActionListener{

	private PanelJugador panelJugador;
	private JPanel panelBotones;
	
	private JButton btBarcoAnterior, btSiguienteBarco;
	
	private BotonBarco [] destructor, crucero, submarino, acorazado, portaAviones;
	
	private Juego parent;
	
	private int numBarco;
	private int casillasOcupadasBarcoActual;
	
	private boolean vertical, horizontal;
	
	public ColocarBarcos(Juego parent, PanelJugador panelJugador){
		super("BattleShip");
		
		this.parent = parent;
		this.setBackground(new Color (0x20726e));
		this.setForeground(new Color (0x20726e));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.vertical=this.horizontal=false;
		this.panelJugador = panelJugador;
		
		this.destructor = new BotonBarco[2];
		this.crucero = new BotonBarco[3];
		this.submarino = new BotonBarco[3];
		this.acorazado = new BotonBarco[4];
		this.portaAviones = new BotonBarco[5];
		
		this.btBarcoAnterior = new JButton ();
		this.btBarcoAnterior.setForeground(Color.white);
		this.btBarcoAnterior.setText("BarcoAnterior");
		this.btBarcoAnterior.setBackground(new Color (0x47556b));
		this.btBarcoAnterior.setPreferredSize(new Dimension (200,30));
		this.btBarcoAnterior.addActionListener(this);
		this.btBarcoAnterior.setVisible(false);
		
		this.btSiguienteBarco = new JButton();
		this.btSiguienteBarco.setForeground(Color.WHITE);
		this.btSiguienteBarco.setText("SiguienteBarco(Crucero)");
		this.btSiguienteBarco.setBackground(new Color (0x47556b));
		this.btSiguienteBarco.setPreferredSize(new Dimension(200,30));
		this.btSiguienteBarco.addActionListener(this);
		
		this.panelBotones = new JPanel();
		this.panelBotones.setBackground(new Color (0x20726e));
		this.panelBotones.setPreferredSize(new Dimension(100,50));
		this.panelBotones.add(this.btBarcoAnterior);
		this.panelBotones.add(this.btSiguienteBarco);
		
		this.numBarco=0;
		this.casillasOcupadasBarcoActual = 0;
		
		
		this.add(panelJugador, BorderLayout.NORTH);
		this.add(panelBotones);
		
		this.pack();
		
		// Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    
	    this.setVisible(true);
	    this.setResizable(false);
	    
	    JOptionPane.showMessageDialog(null, "Colocar Destructor 2 Casillas");
	    
	}
	
	public PanelJugador getPanelJugador(){
		return this.panelJugador;
	}

	public void colocarBarco (BotonBarco casilla){
		if (!casilla.getTieneBarco()){
			int index = casilla.getIndex();
			switch(this.numBarco){
			case 0: //destructor
				if (destructor[1]!=null){
					limpiarCasillas(this.destructor);
					this.casillasOcupadasBarcoActual = 0;
				}
				if(vertical){
					boolean correcto = false;
					for (int i = 0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.destructor[i].getIndex() - index;
						if (resta ==10 || resta==-10){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.destructor);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if(horizontal){
					boolean correcto = false;
					for (int i=0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.destructor[i].getIndex() - index;
						if (resta == 1 || resta == -1){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.destructor);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if (destructor[0]!=null){
					int resta = destructor[0].getIndex() - index;
					if (resta ==1 || resta==-1){
						this.horizontal = true;
					}else if (resta ==10 || resta ==-10){
						this.vertical = true;
					}else{
						limpiarCasillas(this.destructor);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}
				this.destructor[this.casillasOcupadasBarcoActual++] = casilla;
				casilla.setTieneBarco(true);
				casilla.setTipoDeBarco(1);
				casilla.setBackground(Color.BLACK);
				break;
			case 1: //Crucero
				if (crucero[2]!=null){
					limpiarCasillas(this.crucero);
					this.casillasOcupadasBarcoActual = 0;
				}
				if(vertical){
					boolean correcto = false;
					for (int i = 0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.crucero[i].getIndex() - index;
						if (resta ==10 || resta==-10){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.crucero);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if(horizontal){
					boolean correcto = false;
					for (int i=0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.crucero[i].getIndex() - index;
						if (resta == 1 || resta == -1){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.crucero);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if (crucero[0]!=null){
					int resta = crucero[0].getIndex() - index;
					if (resta ==1 || resta==-1){
						this.horizontal = true;
					}else if (resta ==10 || resta ==-10){
						this.vertical = true;
					}else{
						limpiarCasillas(this.crucero);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}
				this.crucero[this.casillasOcupadasBarcoActual++] = casilla;
				casilla.setTieneBarco(true);
				casilla.setTipoDeBarco(2);
				casilla.setBackground(Color.BLACK);
				break;
			case 2: //Submarino
				if (this.submarino[2]!=null){
					limpiarCasillas(this.submarino);
					this.casillasOcupadasBarcoActual = 0;
				}
				if(vertical){
					boolean correcto = false;
					for (int i = 0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.submarino[i].getIndex() - index;
						if (resta ==10 || resta==-10){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.submarino);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if(horizontal){
					boolean correcto = false;
					for (int i=0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.submarino[i].getIndex() - index;
						if (resta == 1 || resta == -1){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.submarino);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if (submarino[0]!=null){
					int resta = submarino[0].getIndex() - index;
					if (resta ==1 || resta==-1){
						this.horizontal = true;
					}else if (resta ==10 || resta ==-10){
						this.vertical = true;
					}else{
						limpiarCasillas(this.submarino);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}
				this.submarino[this.casillasOcupadasBarcoActual++] = casilla;
				casilla.setTieneBarco(true);
				casilla.setTipoDeBarco(3);
				casilla.setBackground(Color.BLACK);
				break;
			case 3: //Acorazado
				if (this.acorazado[3]!=null){
					limpiarCasillas(this.acorazado);
					this.casillasOcupadasBarcoActual = 0;
				}
				if(vertical){
					boolean correcto = false;
					for (int i = 0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.acorazado[i].getIndex() - index;
						if (resta ==10 || resta==-10){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.acorazado);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if(horizontal){
					boolean correcto = false;
					for (int i=0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.acorazado[i].getIndex() - index;
						if (resta == 1 || resta == -1){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.acorazado);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if (acorazado[0]!=null){
					int resta = acorazado[0].getIndex() - index;
					if (resta ==1 || resta==-1){
						this.horizontal = true;
					}else if (resta ==10 || resta ==-10){
						this.vertical = true;
					}else{
						limpiarCasillas(this.acorazado);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}
				this.acorazado[this.casillasOcupadasBarcoActual++] = casilla;
				casilla.setTieneBarco(true);
				casilla.setTipoDeBarco(4);
				casilla.setBackground(Color.BLACK);
				break;
			case 4: //Porta Aviones
				if (this.portaAviones[4]!=null){
					limpiarCasillas(this.portaAviones);
					this.casillasOcupadasBarcoActual = 0;
				}
				if(vertical){
					boolean correcto = false;
					for (int i = 0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.portaAviones[i].getIndex() - index;
						if (resta ==10 || resta==-10){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.portaAviones);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if(horizontal){
					boolean correcto = false;
					for (int i=0; i<this.casillasOcupadasBarcoActual;i++){
						int resta = this.portaAviones[i].getIndex() - index;
						if (resta == 1 || resta == -1){
							correcto = true;
							break;
						}
					}
					if (!correcto){
						limpiarCasillas(this.portaAviones);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}else if (portaAviones[0]!=null){
					int resta = portaAviones[0].getIndex() - index;
					if (resta ==1 || resta==-1){
						this.horizontal = true;
					}else if (resta ==10 || resta ==-10){
						this.vertical = true;
					}else{
						limpiarCasillas(this.portaAviones);
						this.horizontal=this.vertical = false;
						this.casillasOcupadasBarcoActual = 0;
					}
				}
				this.portaAviones[this.casillasOcupadasBarcoActual++] = casilla;
				casilla.setTieneBarco(true);
				casilla.setTipoDeBarco(5);
				casilla.setBackground(Color.BLACK);
				break;
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == this.btSiguienteBarco){
			switch(this.numBarco){
			case 0: //destructor
				if (this.destructor[1]!=null){
					this.numBarco++;
					this.casillasOcupadasBarcoActual = 0;
					this.vertical=this.horizontal=false;
					this.btBarcoAnterior.setText("Barco Anterior(Destructor)");
					this.btBarcoAnterior.setVisible(true);
					this.btSiguienteBarco.setText("Siguiente Barco (Submarino)");
					JOptionPane.showMessageDialog(null, "Colocar Crucero 3 casillas");
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Colocar todas las casillas (2)");
					break;
				}
			case 1: //Crucero
				if (this.crucero[2]!=null){
					this.numBarco++;
					this.casillasOcupadasBarcoActual = 0;
					this.vertical=this.horizontal=false;
					this.btBarcoAnterior.setText("Barco Anterior(Crucero)");
					this.btSiguienteBarco.setText("Siguiente Barco (Acorazado)");
					JOptionPane.showMessageDialog(null, "Colocar Submarino 3 casillas");
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Colocar todas las casillas (3)");
					break;
				}
			case 2: //Submarino
				if (this.submarino[2]!=null){
					this.numBarco++;
					this.casillasOcupadasBarcoActual = 0;
					this.vertical=this.horizontal=false;
					this.btBarcoAnterior.setText("Barco Anterior(Submarino)");
					this.btSiguienteBarco.setText("Siguiente Barco (PortaAviones)");
					JOptionPane.showMessageDialog(null, "Colocar Acorazado 4 casillas");
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Colocar todas las casillas (3)");
					break;
				}
			case 3: //Acorazado
				if (this.acorazado[3]!=null){
					this.numBarco++;
					this.casillasOcupadasBarcoActual = 0;
					this.vertical=this.horizontal=false;
					this.btBarcoAnterior.setText("Barco Anterior(Acorazado)");
					this.btSiguienteBarco.setText("Terminar");
					JOptionPane.showMessageDialog(null, "Colocar Porta Aviones 5 casillas");
					break;
				}else{
					JOptionPane.showMessageDialog(null, "Colocar todas las casillas (4)");
					break;
				}
			case 4: //PortaAviones
				if (this.portaAviones!=null){
					this.parent.jugar();
					break;	
				}else {
					JOptionPane.showMessageDialog(null, "Colocar todas las casillas (5)");
					break;
				}
			}
		}else if (ev.getSource() == this.btBarcoAnterior){
			switch(this.numBarco){
			case 0: //destructor
				break;
			case 1: //Crucero
				this.numBarco--;
				this.casillasOcupadasBarcoActual = 0;
				this.vertical=this.horizontal=false;
				limpiarCasillas(this.crucero);
				limpiarCasillas(this.destructor);
				this.btBarcoAnterior.setVisible(false);
				this.btSiguienteBarco.setText("Siguien Barco (Crucero)");
				JOptionPane.showMessageDialog(null, "Colocar Destructor 2 casillas");
				break;
			case 2: //Submarino
				this.numBarco--;
				this.casillasOcupadasBarcoActual = 0;
				this.vertical=this.horizontal=false;
				limpiarCasillas(this.submarino);
				limpiarCasillas(this.crucero);
				this.btBarcoAnterior.setText("Barco Anterior(Destructor)");
				this.btSiguienteBarco.setText("Siguien Barco (Submarino)");
				JOptionPane.showMessageDialog(null, "Colocar Crucero 3 casillas");
				break;
			case 3: //Acorazado
				this.numBarco--;
				this.casillasOcupadasBarcoActual = 0;
				this.vertical=this.horizontal=false;
				limpiarCasillas(this.acorazado);
				limpiarCasillas(this.submarino);
				this.btBarcoAnterior.setText("Barco Anterior(Crucero)");
				this.btSiguienteBarco.setText("Siguien Barco (Acorazado)");
				JOptionPane.showMessageDialog(null, "Colocar Submarino 3 casillas");
				break;
			case 4: //PortaAviones
				this.numBarco--;
				this.casillasOcupadasBarcoActual = 0;
				this.vertical=this.horizontal=false;
				limpiarCasillas(this.acorazado);
				limpiarCasillas(this.portaAviones);
				this.btBarcoAnterior.setText("Barco Anterior(Submarino)");
				this.btSiguienteBarco.setText("Siguien Barco (PortaAviones)");
				JOptionPane.showMessageDialog(null, "Colocar Acorazado 4 casillas");
				break;	
			}
		}
		
	}
	
	private void limpiarCasillas(BotonBarco[] botones){
		for (int i = 0; i<botones.length;i++){
			if (botones[i]!=null){
				botones[i].setTieneBarco(false);
				botones[i].setTipoDeBarco(0);
				botones[i].setBackground(Color.GRAY);
				botones[i] = null;
			}
		}
	}
	
}
