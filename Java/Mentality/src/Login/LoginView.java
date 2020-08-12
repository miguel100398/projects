package Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JTextField;

import Users.FreeUser;
import Users.RegisteredUser;

public class LoginView extends JFrame implements ActionListener{
    
    private Login parent;
    private LoginModel model;
    
    private JPanel loginPanel;
    
    private JButton[] users, userLogin;
    private JLabel[] userNames;
    private JTextField[] userPasswords;
    
    public LoginView(Login parent){ 
        super("Mentality");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.parent = parent;
        this.model = this.parent.getModel();
        this.setIconImage(new ImageIcon("src\\brain.jpg").getImage());
        // Crear el panel para login
        this.loginPanel = new JPanel();
        this.loginPanel.setPreferredSize(new Dimension(820, 300));
        this.loginPanel.setBackground(new Color(0x37ccc4));
        ((FlowLayout) this.loginPanel.getLayout()).setHgap(20);
        this.users = new JButton[this.model.getUsersSize()];
        this.userNames = new JLabel[this.model.getUsersSize()];
        this.userPasswords = new JTextField[this.model.getUsersSize()];
        this.userLogin = new JButton[this.model.getUsersSize()];
        
        JPanel loginTitle = new JPanel();
        loginTitle.setPreferredSize(new Dimension(820, 50));
        loginTitle.setOpaque(false);
        JLabel lt = new JLabel("Iniciar Sesion");
        lt.setFont(new Font("default", Font.BOLD, 24));
        lt.setForeground(new Color(0x063d3a));
        loginTitle.add(lt);
        this.loginPanel.add(loginTitle);
        for(int i = 0; i < this.model.getUsersSize(); i++){
            JPanel userSpace = new JPanel();
            userSpace.setPreferredSize(new Dimension(120, 210));
            userSpace.setOpaque(false);
            this.users[i] = new JButton();
            this.users[i].setPreferredSize(new Dimension(120, 120));
            this.users[i].setIcon(new ImageIcon(this.model.getUsers()[i].getImage()));
            this.users[i].setName(i+"");
            this.users[i].addActionListener(this);
            this.userNames[i] = new JLabel(this.model.getUsers()[i].getName());
            userSpace.add(this.users[i]);
            userSpace.add(this.userNames[i]);
            if(this.model.getUsers()[i] instanceof RegisteredUser){
                // Solo aniadir para contrasenia y boton a los usuarios registrados
                this.userPasswords[i] = new JTextField(10);
                this.userLogin[i] = new JButton("Iniciar Sesion");
                this.userLogin[i].setName(i+"");
                this.userLogin[i].addActionListener(this);
                userSpace.add(this.userPasswords[i]);
                userSpace.add(this.userLogin[i]);
            }
            this.loginPanel.add(userSpace);         // Aniadimos visualmente el usuario al panel
        }
        
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(820, 80));
        titlePanel.setBackground(new Color(0x34c1ba));
        JLabel title = new JLabel("Mentality");
        title.setFont(new Font("default", Font.BOLD, 40));
        title.setForeground(new Color(0x20726e));
        titlePanel.add(title);

        
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(this.loginPanel);
        this.pack();
        
        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        this.update();
        this.setResizable(false);
    }
    
    public void update(){
        this.setVisible(this.model.getIsVisible());
        for(int i = 0; i < this.model.getUsersSize(); i++){
            if(this.model.getUsers()[i] instanceof RegisteredUser){
                this.userPasswords[i].setVisible(this.model.isVisible(i));
                this.userLogin[i].setVisible(this.model.isVisible(i));
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            int pos = Integer.parseInt(((JButton) e.getSource()).getName());
            if(this.model.getUsers()[pos] instanceof FreeUser){
                // Usuario invitado
                this.model.hide();          // Ocultar la ventana
                this.model.setCurrentUser(pos);
                this.update();
                this.parent.beginLogin();
                
            }else if(this.model.getUsers()[pos] instanceof RegisteredUser){
                // Usuarios registrados
                if(e.getSource() == this.users[pos]){
                    // Seleccionar usuario registradp
                    this.model.hideComponents();
                    this.model.unhideComponent(pos);
                    this.update();
                    
                }else{
                    // Boton de log in
                    if(((RegisteredUser)this.model.getUsers()[pos]).comparePasswords(this.userPasswords[pos].getText())){   // Si el password introducido coincide
                        this.model.hide();          // Ocultar la ventana
                        this.model.setCurrentUser(pos);
                        this.update();
                        this.parent.beginLogin();
                    }else{
                        JOptionPane.showMessageDialog(this, "El password introducido es incorrecto");
                    }
                    
                }
                
            }
        }
    }
}
