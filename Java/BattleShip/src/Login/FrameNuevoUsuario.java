//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: FrameNUevoUsuario
//Fecha: 6/05/19
//Comentarios

package Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Usuarios.UsuarioRegistrado;

public class FrameNuevoUsuario extends JFrame implements ActionListener{
	
	private Login parent;
	private LoginModel model;
	private LoginView view;
	private String nombre, correo, foto;
	private JPanel panelCorreo, panelUsuario, panelPassword, panelBotonIzquierdo, panelBotonDerecho, panelBotones, panelImagen;
	private JLabel labelCorreo, labelUsuario, labelPassword, labelImagen;
	private JTextField fieldCorreo, fieldUsuario, fieldPassword, fieldImagen;
	private JButton btAceptar, btCancelar, btImagen;
	
	FrameNuevoUsuario (Login parent, LoginModel model, LoginView view, Color color){
		super("Nuevo Usuario");
		this.setResizable(false);
		this.parent = parent;
		this.model = model;
		this.view = view;
		
		this.labelCorreo = new JLabel("Correo electrónico: ");
		this.labelUsuario = new JLabel("Usuario: ");
		this.labelPassword = new JLabel("Contraseña: ");
		this.labelImagen = new JLabel("Imagen");
		
		this.fieldCorreo = new JTextField(20);
		this.fieldUsuario = new JTextField(20);
		this.fieldPassword = new JTextField(20);
		this.fieldImagen = new JTextField(17);
		
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.setBackground(new Color(0x47556b));
		this.btAceptar.setForeground(Color.WHITE);
	    this.btAceptar.setPreferredSize(new Dimension(90,40));
	    this.btAceptar.addActionListener(this);
	    
	    this.btCancelar = new JButton("Cancelar");
		this.btCancelar.setBackground(new Color(0x47556b));
		this.btCancelar.setForeground(Color.WHITE);
	    this.btCancelar.setPreferredSize(new Dimension(90,40));
	    this.btCancelar.addActionListener(this);
	    
	    this.btImagen = new JButton();
	    this.btImagen.setBackground(new Color(0x47556b));
	    this.btImagen.setForeground(Color.WHITE);
	    this.btImagen.setPreferredSize(new Dimension(22,20));
	    this.btImagen.addActionListener(this);
	    this.btImagen.setIcon(new ImageIcon("src\\Imagenes\\AbrirCarpeta.png"));
		 
		this.panelCorreo=new JPanel();
		this.panelCorreo.setPreferredSize(new Dimension(350,50));
		this.panelCorreo.setBackground(color);
		this.panelCorreo.add(this.labelCorreo,BorderLayout.WEST);
		this.panelCorreo.add(this.fieldCorreo,BorderLayout.EAST);
		
		JPanel panelAuxiliarUsuario = new JPanel();
		panelAuxiliarUsuario.setPreferredSize(new Dimension(60,20));
		panelAuxiliarUsuario.setBackground(color);
		this.panelUsuario=new JPanel();
		this.panelUsuario.setPreferredSize(new Dimension(350,50));
		this.panelUsuario.setBackground(color);
		this.panelUsuario.add(this.labelUsuario,BorderLayout.WEST);
		this.panelUsuario.add(panelAuxiliarUsuario, BorderLayout.CENTER);
		this.panelUsuario.add(this.fieldUsuario,BorderLayout.EAST);
		
		JPanel panelAuxiliarPassword = new JPanel();
		panelAuxiliarPassword.setPreferredSize(new Dimension(38,15));
		panelAuxiliarPassword.setBackground(color);
		this.panelPassword=new JPanel();
		this.panelPassword.setPreferredSize(new Dimension(350,40));
		this.panelPassword.setBackground(color);
		this.panelPassword.add(this.labelPassword,BorderLayout.WEST);
		this.panelPassword.add(panelAuxiliarPassword, BorderLayout.CENTER);
		this.panelPassword.add(this.fieldPassword, BorderLayout.EAST);
		
		JPanel panelAuxiliarImagen = new JPanel();
		panelAuxiliarImagen.setPreferredSize(new Dimension(65,50));
		panelAuxiliarImagen.setBackground(color);
		panelAuxiliarImagen.add(this.btImagen, BorderLayout.EAST);
		this.panelImagen=new JPanel();
		this.panelImagen.setPreferredSize(new Dimension(350,50));
		this.panelImagen.setBackground(color);
		this.panelImagen.add(this.labelImagen, BorderLayout.WEST);
		this.panelImagen.add(panelAuxiliarImagen, BorderLayout.CENTER);
		this.panelImagen.add(this.fieldImagen, BorderLayout.EAST);
		this.panelImagen.add(this.btImagen);
		
		
		
		this.panelBotones=new JPanel();
		this.panelBotones.setPreferredSize(new Dimension(350,70));
		this.panelBotones.setBackground(color);
		
		this.panelBotonIzquierdo=new JPanel();
		this.panelBotonIzquierdo.setPreferredSize(new Dimension(215,70));
		this.panelBotonIzquierdo.setBackground(color);
		this.panelBotonIzquierdo.add(this.btCancelar, BorderLayout.EAST);
		
		this.panelBotonDerecho=new JPanel();
		this.panelBotonDerecho.setPreferredSize(new Dimension(115,70));
		this.panelBotonDerecho.setBackground(color);
		this.panelBotonDerecho.add(btAceptar, BorderLayout.EAST);
		
		this.panelBotones.add(panelBotonIzquierdo, BorderLayout.WEST);
		this.panelBotones.add(panelBotonDerecho, BorderLayout.EAST);
		
		JPanel panelNorte = new JPanel();
		panelNorte.setPreferredSize(new Dimension(350,150));
		panelNorte.setBackground(color);
		panelNorte.add(this.panelCorreo, BorderLayout.NORTH);
		panelNorte.add(this.panelUsuario, BorderLayout.CENTER);
		panelNorte.add(this.panelPassword, BorderLayout.SOUTH);
		
		JPanel panelSur = new JPanel();
		panelSur.setPreferredSize(new Dimension(350, 120));
		panelSur.setBackground(color);
		panelSur.add(this.panelImagen, BorderLayout.NORTH);
		panelSur.add(this.panelBotones, BorderLayout.SOUTH);
		
		this.add(panelNorte, BorderLayout.NORTH);
		this.add(panelSur, BorderLayout.SOUTH);
		
		
		// Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.pack();
        this.setVisible(true);
		
	}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton){
			if (e.getSource() == this.btImagen){
				 obtenerImagen();
			} else if (e.getSource() == this.btCancelar){
				this.dispose();
			} else if (e.getSource() == this.btAceptar){
				agregarUsuario();
			}
		}
		
	}
	
	public void obtenerImagen(){
		JFileChooser seleccionarImagen = new JFileChooser();
		seleccionarImagen.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resultado = seleccionarImagen.showOpenDialog(this);
		File archivo = seleccionarImagen.getSelectedFile();
		if (!((archivo == null) || (archivo.getName().equals("")))) {
			this.fieldImagen.setText(archivo.getAbsolutePath());
		}
	}
	
	public void agregarUsuario(){
	    if ((this.fieldCorreo.getText().equals("")) || (this.fieldUsuario.getText().equals("")) || (this.fieldPassword.getText().equals("")) || (this.fieldImagen.getText().equals(""))) {
	    	JOptionPane.showMessageDialog(null, "Favor de rellenar todos los campos", "alerta",1);
	    }
	    else {try{
	    	File copiar = new File (this.fieldImagen.getText());
	    	String tipoArchivo = Files.probeContentType(copiar.toPath());
	    	if (tipoArchivo == null){
	    		JOptionPane.showMessageDialog(null, "No es una imagen", "alerta",1);
	    	}
	    	else if (tipoArchivo.equals("image/png") || tipoArchivo.equals("image/jpeg") || tipoArchivo.equals("image/jpg")){
	    		if (tipoArchivo.equals("image/png")){
	    			this.nombre = this.fieldUsuario.getText()+".png";
	    		}else{
	    			this.nombre = this.fieldUsuario.getText()+".jpg";
	    		}
	    		File destino = new File ("src\\Usuarios\\Images\\", this.nombre);
	    			if (copiar.exists()) {
	    				Files.copy(Paths.get(copiar.getAbsolutePath()), Paths.get(destino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
	        		} else {
	        			JOptionPane.showMessageDialog(null, "No existe la imagen", "alerta",1);
	    			}
	    	} else{
	    		JOptionPane.showMessageDialog(null, "No es una imagen", "alerta",1);
	    	}
	    	UsuarioRegistrado nvoUsuario = new UsuarioRegistrado(this.fieldUsuario.getText(), "src\\Usuarios\\Images\\"+this.nombre,this.fieldPassword.getText(),0,0,0,0,0,0,0,0,0);
	    	this.model.addUser(nvoUsuario);
	    	this.view.dispose();
	    	this.parent.saveDatabase();
	    	this.parent.setView(new LoginView(this.parent));
	    	this.dispose();
	    	}catch (Exception e){
    			e.printStackTrace();
	    	}
	    }
	}
	
	
}
