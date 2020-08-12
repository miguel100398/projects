package MagicTilesGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Menuprincipal.FrameStats;

public class MagicTilesView extends JFrame{
    
    private MagicTiles parent;
    private MagicTilesModel model;
    private MagicTilesController controller;
    private PanelTiles pt;
    private PanelScore ps;
    
    private JButton regresar;       // Regresa al menu principal
    
    public MagicTilesView(MagicTiles parent){
        super();
        
        this.parent = parent;
        this.model = this.parent.getModel();
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MagicTilesView.this.parent.endGame();
            }
        });
        this.setIconImage(new ImageIcon(this.parent.getImage()).getImage());
        this.setResizable(false);
        this.ps = new PanelScore(this);
        this.pt = new PanelTiles(this);
        JPanel panelRegresar = new JPanel();
        panelRegresar.setPreferredSize(new Dimension(450, 50));
        panelRegresar.setBackground(Color.BLACK);
        this.regresar = new JButton("Regresar al menu");
        this.regresar.setName("-1");        // Nombre para reconocer el boton de regresar
        this.regresar.setBackground(Color.WHITE);
        this.regresar.setForeground(Color.BLACK);
        panelRegresar.add(this.regresar);
        
        this.add(this.pt);
        this.add(this.ps, BorderLayout.NORTH);
        this.add(panelRegresar, BorderLayout.SOUTH);
        
        this.pack();
        
        // Las siguientes 2 lineas de codigo fueron recuperadas de http://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        this.setVisible(true);
    }
    
    public void update(){
        this.ps.update();
        this.pt.update();
    }
    
    public MagicTilesModel getModel(){
        return this.model;
    }
    
    public void addController(MagicTilesController controller){
        this.controller = controller;
        // Aniadir evento al boton regresar
        this.regresar.addActionListener(this.controller);
        this.pt.addController(this.controller);
    }
    

}
