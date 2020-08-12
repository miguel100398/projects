package Menuprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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

public class MainMenuView extends JFrame implements ActionListener{
    
    private MainMenu parent;
    private MainMenuModel model;
    private JPanel panelGames, panelUser;
    
    private JButton[] games, instructions;
    private JButton userStats, logout;
    
    private JLabel userInfo;
    
    public MainMenuView(MainMenu parent){
        super("Menu Principal");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.parent = parent;
        this.setIconImage(new ImageIcon("src\\brain.jpg").getImage());
        this.model = this.parent.getModel();
        this.games = new JButton[this.model.getNumGames()];
        this.instructions = new JButton[this.model.getNumGames()];
        this.panelGames = new JPanel();                                                   // Panel encargado de guardar la lista de juegos
        this.panelGames.setPreferredSize(new Dimension(750, this.model.getNumGames()*160));      // Espacio suficiente para el numero de juegos
        this.panelGames.setBackground(new Color(0xb9c2d3));
        
        this.panelUser = new JPanel();                               // Panel encargado de guardar informacion del usuario y darle acceso a sus estadisticas
        this.panelUser.setPreferredSize(new Dimension(750, 75));
        this.panelUser.setBackground(new Color(0xa1adc4));
        
        // Aniadir al panel user datos del usuario y boton para ir a estadisticas
        JPanel pLeft = new JPanel(){
            public void paintComponent(Graphics g){
                g.drawImage(new ImageIcon(MainMenuView.this.model.getUsers()[MainMenuView.this.model.getCurrentUser()].getImage()).getImage(), 35, 22, 45, 45, this);
            }
        };
        JPanel pRight = new JPanel();
        pLeft.setPreferredSize(new Dimension(550, 75));
        pRight.setPreferredSize(new Dimension(200, 75));
        pLeft.setLayout(new BoxLayout(pLeft, BoxLayout.PAGE_AXIS));
        pRight.setBackground(this.panelUser.getBackground());
        pLeft.setBackground(this.panelUser.getBackground());
        this.userInfo = new JLabel("Bienvenido " + this.model.getUsers()[this.model.getCurrentUser()].getName());      // Falta agregar el nombre de usuario
        this.userInfo.setFont(new Font("default", Font.BOLD, 16));
        this.userInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.userStats = new JButton("Estadisticas del usuario");
        this.userStats.setBackground(new Color(0x47556b));
        this.userStats.setForeground(Color.WHITE);
        this.userStats.setFont(new Font("default", Font.BOLD, 14));
        this.userStats.addActionListener(this);
        this.logout = new JButton("Cerrar Sesion");
        this.logout.setBackground(new Color(0x47556b));
        this.logout.setForeground(Color.WHITE);
        this.logout.setFont(new Font("default", Font.BOLD, 14));
        this.logout.addActionListener(this);
        pLeft.add(this.userInfo);
        pRight.add(this.userStats);
        pRight.add(this.logout);
        this.panelUser.add(pLeft);
        this.panelUser.add(pRight);
        
        ((FlowLayout) this.panelGames.getLayout()).setVgap(20);
        ((FlowLayout) this.panelGames.getLayout()).setHgap(40);
        
        
        JLabel header = new JLabel("Lista de juegos");
        header.setFont(new Font("default", Font.BOLD, 26));
        header.setForeground(new Color(0x47556b));
        
        this.panelGames.add(header);
        
        for(int i = 0; i < this.model.getNumGames(); i++){
            final int pos = i;
            JPanel pg = new JPanel(){
                public void paintComponent(Graphics g){
                    super.paintComponent(g);
                    g.drawImage(new ImageIcon(MainMenuView.this.model.getGames()[pos].getImage()).getImage(), 10, 10, 90, 90, this);
                }
            };
            pg.setLayout(new BoxLayout(pg, BoxLayout.PAGE_AXIS));
            pg.setPreferredSize(new Dimension(500, 110));
            pg.setBackground(new Color(MainMenuModel.GAME_BACKGROUND));
            
            this.games[i] = new JButton("Jugar");                       // Boton para jugar
            this.games[i].setName(i+"");                                // Agregar id al boton para manejar eventos
            this.games[i].addActionListener(this);
            this.games[i].setAlignmentX(Component.CENTER_ALIGNMENT);    // Alinear al centro el boton
            this.games[i].setBackground(new Color(0x47556b));
            this.games[i].setForeground(Color.WHITE);
            
            this.instructions[i] = new JButton("Instrucciones");
            this.instructions[i].setName(i+"");
            this.instructions[i].addActionListener(this);
            this.instructions[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            this.instructions[i].setBackground(new Color(0x47556b));
            this.instructions[i].setForeground(Color.WHITE);
            
            JLabel title = new JLabel(this.model.getGames()[i].getName()); // Poner el nombre al juego
            title.setFont(new Font("default", Font.BOLD, 22));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);            // Alinear el titulo al centro
            title.setForeground(new Color(0x47556b));
            pg.add(title);
            pg.add(Box.createRigidArea(new Dimension(0, 15)));          // Espacio en blanco entre los componentes
            pg.add(this.games[i]);
            pg.add(Box.createRigidArea(new Dimension(0, 5)));          // Espacio en blanco entre los componentes
            pg.add(this.instructions[i]);
            this.panelGames.add(pg);                          // Aniadit al panel
        }
        this.add(this.panelUser, BorderLayout.NORTH);
        this.add(this.panelGames);
        this.pack();
        
        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.update();
        this.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            // Checar cual boton fue presionado
            if(e.getSource() == this.userStats){
                // Fue presionado el boton de estadisticas del usuario
                this.model.hide();
                this.update();
                new FrameStats(this, this.model);
            }else if(e.getSource() == this.logout){
                // Fue presionado el boton de logout
                this.parent.logout();
            }else if(e.getSource() == this.games[Integer.parseInt(((JButton) e.getSource()).getName())]){
                // Fue presionado el boton de jugar
                this.model.hide();          // Ocultar la ventana
                this.update();
                this.model.getGames()[Integer.parseInt(((JButton) e.getSource()).getName())].playGame();
            }else if(e.getSource() == this.instructions[Integer.parseInt(((JButton) e.getSource()).getName())]){
                // Fue presionado el boton de instrucciones
                switch(Integer.parseInt(((JButton) e.getSource()).getName())){
                case 0:
                    JOptionPane.showMessageDialog(this, "Bienvenido al juego de Magic Tiles \n"
                            + "Al inicio del juego se te daran 3 segundos para prepararte para la siguiente ronda\n"
                            + "Al inicio de cada ronda se te presentara una secuencia que deberas memorizar\n"
                            + "Cada ronda, la secuencia aumentara uno y se hara mas rapido\n"
                            + "El juego acaba cuando te equivoques");
                    break;
                case 1:
                    JOptionPane.showMessageDialog(this, "Bienvenido al juego de Memorama \n"
                            + "Existen las modalidades de jugar solo y con otro jugador\n"
                            + "Puedes elegir entre tres bancos de imagenes distintas\n");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(this, "Bienvenido al juego de simon Dice \n"
                        + "1.-Observe la secuencia de colores que \n"
                        + "aparecera en la parte inferior de la pantalla\n"
                        + "2.-Memorice la secuencia de Colors\n"
                        + "3.-Repita la secuencia presionando los\n"
                        + "Botones de la parte superior\n"
                        + "4.-Perdera si presiona el boton incorrecto\n"
                        + "o si se agota el tiempo\n");
                    break;
                case 3:
                    JOptionPane.showMessageDialog(this, "Bienvenido al juego de Matematicas \n"
                        + "Existen 5 modalidades de juego\n"
                        + "En cada una solo encontraras ese tipo de operaciones\n"
                        + "Responde las operaciones corectamente y acumula puntos");
                    break;
                }
            }
        }
    }
    
    public void update(){
        this.setVisible(this.model.getIsVisible());
    }
    
}
