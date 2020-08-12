//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: LoginView
//Fecha: 6/05/19
//Comentarios

package Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Usuarios.UsuarioRegistrado;
import Usuarios.FreeUser;
import Usuarios.UsuarioRegistrado;

public class LoginView extends JFrame implements ActionListener{

	private Login parent;
	private LoginModel loginModel;
	
	private JPanel loginPanel,
				   tituloLogin,
				   usuarioPanel,
				   tituloPanel;
	
	private JLabel tituloLabel,
				   titulo;
	
	private JButton btInvitado, login;
	private JButton btNuevoUsuario;
	private JTextField nombreUsuario;
	private JPasswordField password;
	 
	public LoginView(Login parent){ 
		super("Battle Ship");
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.parent = parent;
	    this.loginModel = this.parent.getModel();
	    this.setIconImage(new ImageIcon("src\\brain.jpg").getImage()); //Cambiar imagen
	    //Panel de login
	    this.loginPanel = new JPanel();
	    this.loginPanel.setPreferredSize(new Dimension(820, 300));
        this.loginPanel.setBackground(new Color(0x37ccc4));
        ((FlowLayout) this.loginPanel.getLayout()).setHgap(20);
        this.nombreUsuario = new JTextField(10);
        this.password = new JPasswordField();
        this.login = new JButton();
        
        this.tituloLogin = new JPanel();
        this.tituloLogin.setPreferredSize(new Dimension(820, 50));
        this.tituloLogin.setOpaque(false);
        this.tituloLabel = new JLabel("Iniciar Sesion");
        this.tituloLabel.setFont(new Font("default", Font.BOLD, 24));
        this.tituloLabel.setForeground(new Color(0x063d3a));
        this.tituloLogin.add(this.tituloLabel);
        this.loginPanel.add(this.tituloLogin);
        
        this.btNuevoUsuario = new JButton("Nuevo Usuario");
        this.btNuevoUsuario.setBackground(new Color(0x47556b));
        this.btNuevoUsuario.setForeground(Color.WHITE);
        this.btNuevoUsuario.addActionListener(this);
        
        this.btInvitado = new JButton("Invitado");
        this.btInvitado.setBackground(new Color(0x47556b));
        this.btInvitado.setForeground(Color.WHITE);
        this.btInvitado.addActionListener(this);
        
        this.usuarioPanel = new JPanel();
        this.usuarioPanel.setPreferredSize(new Dimension(120, 210));
        this.usuarioPanel.setOpaque(false);
            
        this.usuarioPanel.add(this.nombreUsuario);
        this.password = new JPasswordField(10);
        this.login = new JButton ("Iniciar Sesion");
        this.login.addActionListener(this);
        this.usuarioPanel.add(this.password);
        this.usuarioPanel.add(this.login);
        this.usuarioPanel.add(this.btNuevoUsuario);
        this.usuarioPanel.add(this.btInvitado);
        this.usuarioPanel.setBackground(Color.RED);
        this.usuarioPanel.setVisible(true);
        this.loginPanel.add(usuarioPanel, BorderLayout.SOUTH);         // Aniadimos visualmente el usuario al panel
        
        
        this.tituloPanel = new JPanel();
        this.tituloPanel.setPreferredSize(new Dimension(820, 80));
        this.tituloPanel.setBackground(new Color(0x34c1ba));
        this.titulo = new JLabel("Battle Ship");
        this.titulo.setFont(new Font("default", Font.BOLD, 40));
        this.titulo.setForeground(new Color(0x20726e));
        this.tituloPanel.add(this.titulo);

        
        this.add(this.tituloPanel, BorderLayout.NORTH);
        this.add(this.loginPanel);
        this.pack();
        
        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.actualizar();
        this.setResizable(false);      
	}    
	
	public void actualizar(){
        this.setVisible(this.loginModel.getVisible());
               // this.password.setVisible(this.loginModel.getUsuarioVisible(i));
                //this.login.setVisible(this.loginModel.getUsuarioVisible(i));
    }
	
	
	
	//Acciones Botones
	public void actionPerformed(ActionEvent e) {
        	if (e.getSource()==this.btNuevoUsuario){      		
        			FrameNuevoUsuario nvo = new FrameNuevoUsuario (this.parent, this.loginModel, this, new Color(0x20726e));
        	}else if (e.getSource()==this.btInvitado){
        		this.loginModel.esconder();
        		this.loginModel.setUsuarioActual("FreeUser");
        		this.actualizar();
        		this.parent.iniciarLogin();
        	}
        	else if (e.getSource()==this.login){
        		if (this.loginModel.getUsuarios().containsKey(this.nombreUsuario.getText())){
        			if (((UsuarioRegistrado)this.loginModel.getUsuarios().get(this.nombreUsuario.getText())).ContraseñaCorrecta(this.password.getText())){
        				this.loginModel.esconder();
        				this.loginModel.setUsuarioActual(this.nombreUsuario.getText());
        				this.actualizar();
        				this.parent.iniciarLogin();
        			}else{
        				JOptionPane.showMessageDialog(this, "Nombre de Usuario o password incorrecto");
        			}
        		}else{
        			JOptionPane.showMessageDialog(this, "Nombre de Usuario o password incorrecto");
        		}
        			
        	}
	}
        
    
}
