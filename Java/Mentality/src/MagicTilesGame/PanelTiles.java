package MagicTilesGame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelTiles extends JPanel{
    
    private MagicTilesView parent;
    private MagicTilesController controller;
    private MagicTilesModel model;
    private JButton[] tiles;
    
    public PanelTiles(MagicTilesView parent){
        super();
        this.parent = parent;
        this.model = parent.getModel();
        this.setPreferredSize(new Dimension(450, 750));
        this.setBackground(Color.BLACK);
        
        this.tiles = new JButton[this.model.getM()*this.model.getN()];      // Crear grid 5x7
        
        //LLenar los tiles
        for(int i = 0; i < this.tiles.length; i++){
                this.tiles[i] = new JButton();
                this.tiles[i].setBorder(null);        // Quitar el borde
                this.tiles[i].setPreferredSize(new Dimension(80, 100));
                this.tiles[i].setName(i+"");          // Poner id
                this.add(this.tiles[i]);
        }
        this.update();          // Actualización inicial
        
    }
    
    public void update(){
        // Cambiar colores del grid y activar o desactivar
        for(int i = 0; i < this.tiles.length; i++){
            this.tiles[i].setBackground(new Color(this.model.getColors()[i]));
            this.tiles[i].setEnabled(this.model.areTilesActive());
        }
        for(int tile: this.model.getInactiveTiles()){
            this.tiles[tile].setEnabled(false);         // Desabilitar tiles individuales
        }
    }
    
    public void addController(MagicTilesController controller){
        this.controller = controller;
        this.registerEvents();
    }
    
    private void registerEvents(){
        for(int i = 0; i < this.tiles.length; i++){
            this.tiles[i].addMouseListener(this.controller);
            this.tiles[i].addActionListener(this.controller);
        }
    }
}
