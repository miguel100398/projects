package MemoryGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PanelOptions extends JPanel{
    
    private Memory parent;
    private MemoryModel model;
    private MemoryController controller;
    private JLabel score, timer, pairsCollected, highScore, bestTime;
    private JButton newGame, goBack;
    private JRadioButton[] imageSets;
    /*
     * imageSets:
     * 0 - coches
     * 1 - animales
     * 2 - postres
     * 3 - random
     * 
     */
    private JRadioButton[] numPlayers;      // Permite al jugador elegir un juego para dos jugadores
    private JPanel panelTwoPlayers;
    private JLabel p1Pairs, p2Pairs, turn;
    
    public PanelOptions(Memory parent){
        super();
        this.parent = parent;
        this.model = this.parent.getModel();
        this.setPreferredSize(new Dimension(300, 850));
        this.score = new JLabel("Puntuacion: ");
        this.score.setFont(new Font("default", Font.BOLD, 26));
        this.score.setForeground(Color.WHITE);
        this.timer = new JLabel("Tiempo transcurrido \t0:00");
        this.timer.setFont(new Font("default", Font.BOLD, 22));
        this.timer.setForeground(Color.WHITE);
        this.pairsCollected = new JLabel("Pares de cartas: ");
        this.pairsCollected.setFont(new Font("default", Font.BOLD, 22));
        this.pairsCollected.setForeground(Color.WHITE);
        this.highScore = new JLabel("Mejor Puntuacion: ");
        this.highScore.setFont(new Font("default", Font.ITALIC, 19));
        this.highScore.setForeground(Color.WHITE);
        this.bestTime = new JLabel("Mejor tiempo: ");
        this.bestTime.setFont(new Font("default", Font.ITALIC, 19));
        this.bestTime.setForeground(Color.WHITE);
        this.newGame = new JButton("Comenzar nuevo juego");
        this.newGame.setBackground(new Color(0x3d201c));
        this.newGame.setForeground(Color.WHITE);
        this.newGame.setName("-1");
        this.goBack = new JButton("Regresar al Menu");
        this.goBack.setBackground(new Color(0x3d201c));
        this.goBack.setForeground(Color.WHITE);
        this.goBack.setName("-2");
        this.imageSets = new JRadioButton[4];
        this.imageSets[0] = new JRadioButton("Memoria de animales");
        this.imageSets[1] = new JRadioButton("Memoria de coches");
        this.imageSets[2] = new JRadioButton("Memoria de postres");
        this.imageSets[3] = new JRadioButton("Memoria random", true);
        
        this.numPlayers = new JRadioButton[2];
        this.numPlayers[0] = new JRadioButton("1 Jugador", true);
        this.numPlayers[0].setName("One");
        this.numPlayers[1] = new JRadioButton("2 Jugadores");
        this.numPlayers[1].setName("Two");
        
        this.panelTwoPlayers = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(new ImageIcon("src\\MemoryGame\\Images\\wood2.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        this.panelTwoPlayers.setPreferredSize(new Dimension(250, 150));
        ((FlowLayout) this.panelTwoPlayers.getLayout()).setHgap(60);
        ((FlowLayout) this.panelTwoPlayers.getLayout()).setVgap(8);
        JLabel P2Title = new JLabel("Players");
        P2Title.setFont(new Font("default", Font.BOLD, 18));
        this.p1Pairs = new JLabel("Pares Jugador 1: ");
        this.p1Pairs.setFont(new Font("default", Font.BOLD, 16));
        this.p2Pairs = new JLabel("Pares Jugador 2: ");
        this.p2Pairs.setFont(new Font("default", Font.BOLD, 16));
        this.turn = new JLabel("Turno: ");
        this.turn.setFont(new Font("default", Font.BOLD, 18));
        this.turn.setForeground(new Color(0x961a07));
        this.panelTwoPlayers.add(P2Title);
        this.panelTwoPlayers.add(this.p1Pairs);
        this.panelTwoPlayers.add(this.p2Pairs);
        this.panelTwoPlayers.add(this.turn);
        
        ((FlowLayout) this.getLayout()).setVgap(20);
        ((FlowLayout) this.getLayout()).setHgap(20);
        
        JPanel panelNuevo = new JPanel(){
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(new ImageIcon("src\\MemoryGame\\Images\\wood3.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        panelNuevo.setPreferredSize(new Dimension(250, 350));
        ((FlowLayout) panelNuevo.getLayout()).setVgap(15);
        
        this.add(this.score);
        this.add(this.highScore);
        this.add(this.timer);
        this.add(this.bestTime);
        this.add(this.pairsCollected);
        this.add(this.panelTwoPlayers);
        JLabel title = new JLabel("Customizar nuevo juego:");
        title.setFont(new Font("default", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panelNuevo.add(title);
        panelNuevo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // Aniadir los radio buttons al panel
        ButtonGroup group = new ButtonGroup();
        for(int i = 0; i < this.imageSets.length; i++){
            this.imageSets[i].setName(i + "");
            this.imageSets[i].setOpaque(false);
            this.imageSets[i].setFont(new Font("default", Font.BOLD, 15));
            this.imageSets[i].setForeground(Color.WHITE);
            group.add(this.imageSets[i]);
            panelNuevo.add(this.imageSets[i]);
        }
        ButtonGroup players = new ButtonGroup();
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(250, 50));
        jp.setOpaque(false);
        JLabel jug = new JLabel("Numero de Jugadores");
        jug.setFont(new Font("default", Font.BOLD, 16));
        jug.setForeground(Color.WHITE);
        jp.add(jug);
        for(int i = 0; i < this.numPlayers.length; i++){
            this.numPlayers[i].setOpaque(false);
            this.numPlayers[i].setFont(new Font("default", Font.BOLD, 13));
            this.numPlayers[i].setForeground(Color.WHITE);
            players.add(this.numPlayers[i]);
            jp.add(this.numPlayers[i]);
        }
        panelNuevo.add(jp);
        panelNuevo.add(this.newGame);
        this.add(panelNuevo);
        this.add(this.goBack);
        this.update();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(new ImageIcon(this.model.getOptionsWp()).getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
    
    public void update(){
        this.score.setText(String.format("Puntuacion: %04d", this.model.getScore()));
        this.highScore.setText(String.format("Mejor Puntuacion: %04d", this.model.getUser().getScore(this.parent.getName())));
        int minutes = this.model.getUser().getTime(this.parent.getName())/60;
        int seconds = this.model.getUser().getTime(this.parent.getName())%60;
        this.bestTime.setText(String.format("Mejor tiempo: %d:%02d", minutes, seconds));
        
        this.timer.setText(String.format("Tiempo transcurrido \t%d:%02d", this.model.getMinutes(), this.model.getSeconds()));
        this.pairsCollected.setText("Pares de cartas: " + this.model.getPairsCollected());
        
        // Updatear componentes para dos jugadores
        this.panelTwoPlayers.setVisible(this.model.isTwoPlayers());
        if(this.model.isTwoPlayers()){
            this.p1Pairs.setText("Pares Jugador 1: " + this.model.getP1PairsCollected());
            this.p2Pairs.setText("Pares Jugador 2: " + this.model.getP2PairsCollected());
            String pTurn = (this.model.isP1Turn())?"Jugador 1":"Jugador 2";
            this.turn.setText("Turno: " + pTurn);
        }
    }
    
    public void addController(MemoryController controller){
        this.controller = controller;
        this.registerEvents();
    }
    
    private void registerEvents(){
        this.newGame.addActionListener(this.controller);
        this.goBack.addActionListener(this.controller);
        for(int i = 0; i < this.imageSets.length; i++){
            this.imageSets[i].addActionListener(this.controller);
        }
        for(int i = 0; i < this.numPlayers.length; i++){
            this.numPlayers[i].addActionListener(this.controller);
        }
    }
}
