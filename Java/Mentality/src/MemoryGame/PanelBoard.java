package MemoryGame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBoard extends JPanel{
    private MemoryModel model;
    private MemoryController controller;
    private JButton[] cards;
    
    public PanelBoard(Memory parent){
        super();
        
        this.setPreferredSize(new Dimension(970, 850));
        this.model = parent.getModel();
        
        this.cards = new JButton[this.model.getM()*this.model.getN()];
        ((FlowLayout) this.getLayout()).setHgap(26);
        ((FlowLayout) this.getLayout()).setVgap(26);
        
        for(int i = 0; i < this.cards.length; i++){
            this.cards[i] = new JButton();
            this.cards[i].setPreferredSize(new Dimension(130, 110));
            this.cards[i].setName(i + "");
            this.add(cards[i]);
        }
        
        this.update();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(new ImageIcon(this.model.getWallpaper()).getImage(), 0, 0, this.getWidth(), this.getHeight(),this);
    }
    
    public void update(){
        for(int i = 0; i < this.model.getTurned().length; i++){
            if(this.model.getTurned()[i]){
                this.cards[i].setIcon(new ImageIcon(this.model.getCurrentImages()[this.model.getDeck()[i]]));
            }else{
                this.cards[i].setIcon(new ImageIcon(this.model.getDefaultCard()));
            }
        }
    }
    
    public void addController(MemoryController controller){
        this.controller = controller;
        this.registerEvents();
    }
    
    private void registerEvents(){
        for(int i = 0; i < this.cards.length; i++){
            this.cards[i].addActionListener(this.controller);
        }
    }
}
