//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Estadisticas
//Fecha: 6/05/19
//Comentarios

package MenuPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MenuPrincipal.MenuPrincipalModel;
import MenuPrincipal.MenuPrincipalView;

public class Estadisticas extends JPanel {
	
	private MenuPrincipalView parent;
	private MenuPrincipalModel model;
	private JPanel panelStats, tituloPanel;
	private JLabel  titulo;
	private JLabel[] estadisticas;
	
	public Estadisticas(MenuPrincipalView parent, MenuPrincipalModel model, int x, int y, Color color){
		this.parent=parent;
		this.model=model;
		this.setPreferredSize(new Dimension(x,y));
		this.setBackground(color);
		this.tituloPanel = new JPanel();
	    this.tituloPanel.setPreferredSize(new Dimension(x, 80));
	    this.tituloPanel.setBackground(new Color(0x34c1ba));
	    this.titulo = new JLabel("Estadisticas");
	    this.titulo.setFont(new Font("default", Font.BOLD, 40));
        this.titulo.setForeground(Color.BLACK);
        this.tituloPanel.add(this.titulo);
        
        this.add(tituloPanel, BorderLayout.NORTH);
		//Panel
		this.panelStats = new JPanel();
		this.panelStats.setPreferredSize(new Dimension(x,y-80));
		this.panelStats.setBackground(new Color(0xb9c2d3));
        this.panelStats.setForeground(Color.WHITE);
        this.panelStats.setLayout(new BoxLayout(this.panelStats, BoxLayout.PAGE_AXIS));
		
       
		//Etiquetas
        this.estadisticas = new JLabel[9];
		for (int i = 0; i < 9; i++) {
			this.estadisticas[i]= new JLabel();
			this.estadisticas[i].setFont(new Font("default", Font.BOLD, 30));
			this.estadisticas[i].setForeground(new Color(0x47556b));
		}

		//estadisticas del usuario
		this.estadisticas[0].setText("          Tiempo jugado: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getTiempo()/60 + " minutos con " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getTiempo()%60 + " segundos");
		this.estadisticas[1].setText("          Total de partidas jugadas: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getPartidas());
		this.estadisticas[2].setText("          Total de victorias: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getVictorias());
		this.estadisticas[3].setText("          Total de derrotas: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getDerrotas());
		this.estadisticas[4].setText("          Total de disparos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getDisparos());
		this.estadisticas[5].setText("          Total de aciertos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getAciertos());
		this.estadisticas[6].setText("          Total de fallos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getFallos());
		this.estadisticas[7].setText("          Total de barcos destruidos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getBarcosDestruidos());
		this.estadisticas[8].setText("          Total de barcos perdidos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getBarcosPerdidos());	
		//Agregar etiquetas al panel
		this.panelStats.add(this.estadisticas[0]);
		this.panelStats.add(this.estadisticas[1]);
		this.panelStats.add(this.estadisticas[2]);
		this.panelStats.add(this.estadisticas[3]);
		this.panelStats.add(this.estadisticas[4]);
		this.panelStats.add(this.estadisticas[5]);
		this.panelStats.add(this.estadisticas[6]);
		this.panelStats.add(this.estadisticas[7]);
		this.panelStats.add(this.estadisticas[8]);
        
        //Agregar componentes
        this.add(this.panelStats);

        
		this.setVisible(true);
		
	}

	public void ActualizarEstadisticas(){
		//estadisticas del usuario
		this.estadisticas[0].setText("          Tiempo jugado: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getTiempo()/60 + " minutos con " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getTiempo()%60 + " segundos");
		this.estadisticas[1].setText("          Total de partidas jugadas: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getPartidas());
		this.estadisticas[2].setText("          Total de victorias: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getVictorias());
		this.estadisticas[3].setText("          Total de derrotas: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getDerrotas());
		this.estadisticas[4].setText("          Total de disparos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getDisparos());
		this.estadisticas[5].setText("          Total de aciertos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getAciertos());
		this.estadisticas[6].setText("          Total de fallos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getFallos());
		this.estadisticas[7].setText("          Total de barcos destruidos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getBarcosDestruidos());
		this.estadisticas[8].setText("          Total de barcos perdidos: " + this.model.getUsuarios().get(this.model.getUsuarioActual()).getBarcosPerdidos());	
		
	}

}
