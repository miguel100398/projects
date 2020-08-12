//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: MenuPrincipalView
//Fecha: 6/05/19
//Comentarios

package MenuPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import MenuPrincipal.MenuPrincipal;
import MenuPrincipal.MenuPrincipalModel;


public class MenuPrincipalView extends JFrame{ //implements ActionListener{
	
	    private MenuPrincipal parent;
	    private MenuPrincipalModel model;
	    private JPanel tituloPanel;
	    
	    private JButton juego, instrucciones;
	    private JButton  logout;
	    
	    private Estadisticas panelEstadisticas;
	    private PanelUsuario panelUsuario;
	    
	    private JLabel userInfo, titulo;
	    
	    public MenuPrincipalView(MenuPrincipal parent){
	        super("Menu Principal");
	        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        this.parent = parent;
	        this.setIconImage(new ImageIcon("src\\brain.jpg").getImage()); //Cambiar imagen
	        this.model = this.parent.getModel();
	        
	        this.tituloPanel = new JPanel();
	        this.tituloPanel.setPreferredSize(new Dimension(850, 80));
	        this.tituloPanel.setBackground(new Color(0x34c1ba));
	        this.titulo = new JLabel("Battle Ship");
	        this.titulo.setFont(new Font("default", Font.BOLD, 40));
	        this.titulo.setForeground(new Color(0x20726e));
	        this.tituloPanel.add(this.titulo);
	        
	        this.panelEstadisticas = new Estadisticas(this, this.model, 700, 540, new Color(0x20726e));                                                  
	        
	        this.panelUsuario = new PanelUsuario(this.parent, this.model, 150, 540, new Color(0x20726e));
	        
	        JPanel panelcentral = new JPanel ();
	        panelcentral.setPreferredSize(new Dimension(1,540));
	        panelcentral.setBackground(Color.BLACK);
	        
	        this.add(this.tituloPanel, BorderLayout.NORTH);
	        this.add(panelcentral, BorderLayout.CENTER);
	        this.add(this.panelEstadisticas, BorderLayout.EAST);
	        this.add(this.panelUsuario, BorderLayout.WEST);
	        
	        this.pack();
	        
	        
	        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	        
	        
	        this.actualizar();
	        this.setResizable(false);
	    }
	    
	    public void actualizar(){
	        this.setVisible(this.model.getVisible());
	    }
	    
	    public Estadisticas getPanelEstadisticas(){
	    	return this.panelEstadisticas;
	    }

}
