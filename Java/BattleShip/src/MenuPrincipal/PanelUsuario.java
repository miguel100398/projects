//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: PanelUsuario
//Fecha: 6/05/19
//Comentarios

package MenuPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Juego.Juego;


public class PanelUsuario extends JPanel implements ActionListener {
	
	private MenuPrincipal parent;
	private MenuPrincipalModel model;
	private JPanel panelSuperior, panelJugador, panelBoton, panelNombre;
	private JButton btJugar, btLogout;
	private ImageIcon foto;
	private JLabel nombre;
	
	public PanelUsuario(MenuPrincipal parent, MenuPrincipalModel model, int x, int y, Color color){
		this.parent=parent;
		this.model=model;
		this.setPreferredSize(new Dimension(x,y));
		this.setBackground(color);
		
		this.foto = new ImageIcon(this.model.getUsuarios().get(this.model.getUsuarioActual()).getImage());
		this.nombre = new JLabel();
		this.nombre.setFont(new Font("default", Font.BOLD, 20));
		this.nombre.setForeground(Color.BLACK);
		this.nombre.setText(this.model.getUsuarios().get(this.model.getUsuarioActual()).getName());
		
		this.btLogout = new JButton("Log Out");
		this.btLogout.setBackground(new Color(0x47556b));
        this.btLogout.setForeground(Color.WHITE);
        this.btLogout.setPreferredSize(new Dimension(200,50));
        this.btLogout.addActionListener(this);
		
		this.panelSuperior = new JPanel();
		this.panelSuperior.setPreferredSize(new Dimension(x,100));
		this.panelSuperior.setBackground(color);
		this.panelSuperior.add(this.btLogout);
		
		this.panelJugador = new JPanel(){
       	 public void paintComponent(Graphics g){
             super.paintComponent(g);
             g.drawImage(foto.getImage(), 30, 20, 100, 100, this);
         }
    };
		this.panelJugador.setPreferredSize(new Dimension(x,125));
		this.panelJugador.setBackground(color);
		
		this.panelNombre = new JPanel();
		this.panelNombre.setPreferredSize(new Dimension(x,155));
		this.panelNombre.setBackground(color);
		this.panelNombre.add(this.nombre);
		
		this.panelBoton = new JPanel();
		this.panelBoton.setPreferredSize(new Dimension(x,(y/3)));
		this.panelBoton.setBackground(color);
		
		//Boton
		this.btJugar = new JButton("Jugar");
		this.btJugar.setBackground(new Color(0x47556b));
        this.btJugar.setForeground(Color.WHITE);
        this.btJugar.setPreferredSize(new Dimension(90,90));
        this.btJugar.addActionListener(this);
        this.panelBoton.add(this.btJugar);
        
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(panelJugador, BorderLayout.CENTER);
        this.add(panelNombre);
        this.add(panelBoton, BorderLayout.SOUTH);

}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			if(e.getSource() == this.btLogout){	             
	             this.parent.logout();
		 }
			else if (e.getSource() == this.btJugar){
				Juego juego = new Juego (this.parent);
				this.parent.getModel().esconder();
				this.parent.getView().actualizar();
			}
		}
		
	}
}
