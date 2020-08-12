//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: PanelDatos
//Fecha: 6/05/19
//Comentarios


package Juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelDatos extends JPanel implements ActionListener{

	private ViewJuego parent;
	private JLabel titulo,
				   tiempo,
				   jugador,
				   enemigo;
	private JPanel panelTitulo,
				   panelJugadores,
				   		panelEnemigo,
				   		panelJugador;
	private ImageIcon destructorJugador, acorazadoJugador, submarinoJugador, portaAvionesJugador, cruceroJugador,
					  destructorEnemigo, acorazadoEnemigo, submarinoEnemigo, portaAvionesEnemigo, cruceroEnemigo;
	private JButton btRegresar;
	
	public PanelDatos (ViewJuego view, Color color){
		super();
		this.parent = view;
		this.setPreferredSize(new Dimension (200,620));
		this.setBackground(new Color(0x20726e));
		this.titulo = new JLabel (" Datos    ");
		this.titulo.setFont(new Font(this.titulo.getFont().getFontName(), this.titulo.getFont().getStyle(),20));
		this.tiempo = new JLabel ("Tiempo:00:00");
		this.tiempo.setFont(new Font(this.tiempo.getFont().getFontName(), this.tiempo.getFont().getStyle(),20));
		this.jugador = new JLabel (this.parent.getJuego().getMenuPrincipal().getModel().getUsuarios().get(this.parent.getJuego().getMenuPrincipal().getModel().getUsuarioActual()).getName());
		this.enemigo = new JLabel ("Enemigo");
		this.panelTitulo = new JPanel();
		this.panelTitulo.setPreferredSize(new Dimension(200,60));
		this.panelTitulo.add(this.titulo, BorderLayout.NORTH);
		this.panelTitulo.add(this.tiempo, BorderLayout.SOUTH);
		this.panelTitulo.setBackground(new Color (0x34c1ba));
		
		this.btRegresar = new JButton();
        this.btRegresar.setPreferredSize(new Dimension(100,30));
        this.btRegresar.setForeground(Color.white);
        this.btRegresar.setBackground(new Color(0x47556b));
        this.btRegresar.setText("Regresar");
        this.btRegresar.addActionListener(this);
		
		this.panelJugadores = new JPanel();
		this.panelJugadores.setPreferredSize(new Dimension(200,520));
		
		this.destructorJugador = new ImageIcon("Imagenes\\Destructor.png");
		this.acorazadoJugador = new ImageIcon("Imagenes\\Acorazado.png");
		this.submarinoJugador = new ImageIcon("Imagenes\\Submarino.png");
		this.portaAvionesJugador = new ImageIcon("Imagenes\\PortaAviones.png");
		this.cruceroJugador = new ImageIcon("Imagenes\\Crucero.png");
		
		this.destructorEnemigo = new ImageIcon("Imagenes\\Destructor.png");
		this.acorazadoEnemigo = new ImageIcon("Imagenes\\Acorazado.png");
		this.submarinoEnemigo = new ImageIcon("Imagenes\\Submarino.png");
		this.portaAvionesEnemigo = new ImageIcon("Imagenes\\PortaAviones.png");
		this.cruceroEnemigo = new ImageIcon("Imagenes\\Crucero.png");
		
		this.panelJugador= new JPanel(){
			 public void paintComponent(Graphics g){
	             super.paintComponent(g);
	             g.drawImage(portaAvionesJugador.getImage(), 70, 30, 70, 35, this);
	             g.drawImage(acorazadoJugador.getImage(), 70, 60, 75, 35, this);
	             g.drawImage(submarinoJugador.getImage(), 70, 90, 60, 35, this);
	             g.drawImage(cruceroJugador.getImage(), 70, 120, 60, 35, this);
	             g.drawImage(destructorJugador.getImage(), 70, 150, 55, 35, this);
	         }
		};
		this.panelJugador.setPreferredSize(new Dimension(200,280));
		this.panelJugador.setBackground(new Color (0x34c1ba));
		this.panelJugador.add(this.jugador);

		this.panelEnemigo = new JPanel(){
			 public void paintComponent(Graphics g){
	             super.paintComponent(g);
	             g.drawImage(portaAvionesEnemigo.getImage(), 70, 30, 70, 35, this);
	             g.drawImage(acorazadoEnemigo.getImage(), 70, 60, 75, 35, this);
	             g.drawImage(submarinoEnemigo.getImage(), 70, 90, 60, 35, this);
	             g.drawImage(cruceroEnemigo.getImage(), 70, 120, 60, 35, this);
	             g.drawImage(destructorEnemigo.getImage(), 70, 150, 55, 35, this);
	         }
		};
		this.panelEnemigo.setPreferredSize(new Dimension(200,280));
		this.panelEnemigo.add(this.enemigo);
		this.panelEnemigo.setBackground(new Color (0x34c1ba));
		
		
		this.panelJugadores.setBackground(new Color (0x34c1ba));
		this.panelJugadores.add(this.panelEnemigo,BorderLayout.NORTH);
		this.panelJugadores.add(this.panelJugador,BorderLayout.SOUTH);
		this.add(this.panelTitulo,BorderLayout.NORTH);
		this.add(this.panelJugadores,BorderLayout.SOUTH);
		this.add(this.btRegresar);
	}
	
	public void setTime(int time){
		int minutos = time/60;
		int segundos = time%60;
		String seg = "";
		if (segundos<10){
			seg = "0"+segundos;
		}else{
			seg = ""+segundos;
		}
		this.tiempo.setText("Tiempo:"+minutos+":"+seg);
	}
	
	public void setDestruido (boolean jugador, int barco){
		if (jugador){
			switch (barco){
			case 1:
				this.destructorJugador = new ImageIcon("Imagenes\\DestructorDestruido.png");
				break;
			case 2:
				this.cruceroJugador = new ImageIcon("Imagenes\\CruceroDestruido.png");
				break;
			case 3:
				this.submarinoJugador = new ImageIcon("Imagenes\\SubmarinoDestruido.png");
				break;
			case 4:
				this.acorazadoJugador = new ImageIcon ("Imagenes\\AcorazadoDestruido.png");
				break;
			case 5:
				this.portaAvionesJugador = new ImageIcon("Imagenes\\PortaAvionesDestruido.png");
				break;
			}
		}else{
			switch (barco){
			case 1:
				this.destructorEnemigo = new ImageIcon("Imagenes\\DestructorDestruido.png");
				break;
			case 2:
				this.cruceroEnemigo = new ImageIcon("Imagenes\\CruceroDestruido.png");
				break;
			case 3:
				this.submarinoEnemigo = new ImageIcon("Imagenes\\SubmarinoDestruido.png");
				break;
			case 4:
				this.acorazadoEnemigo = new ImageIcon ("Imagenes\\AcorazadoDestruido.png");
				break;
			case 5:
				this.portaAvionesEnemigo = new ImageIcon("Imagenes\\PortaAvionesDestruido.png");
				break;
			}
		}
		this.repaint();
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.btRegresar){
			this.parent.getModel().terminarJuego();
		}
		
	}
	
}
