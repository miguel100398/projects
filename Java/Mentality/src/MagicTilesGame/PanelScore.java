package MagicTilesGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelScore extends JPanel{
    
    private MagicTilesModel model;
    private JLabel instruction,
                   score,
                   time,
                   highScore;
    
    
    public PanelScore(MagicTilesView parent){
        super();
        this.model = parent.getModel();
        this.setPreferredSize(new Dimension(450, 75));
        this.setBackground(Color.BLACK);
        ((FlowLayout)this.getLayout()).setHgap(60);
        ((FlowLayout)this.getLayout()).setVgap(10);
        
        this.instruction = new JLabel();
        this.instruction.setFont(new Font("default", Font.BOLD, 19));
        this.instruction.setForeground(new Color(MagicTilesModel.WHITE));
        this.score = new JLabel("Puntuacion: ");
        this.score.setFont(new Font("default", Font.BOLD, 16));
        this.score.setForeground(new Color(MagicTilesModel.WHITE));
        this.highScore = new JLabel("Mejor Puntuacion: ");
        this.highScore.setFont(new Font("default", Font.ITALIC, 16));
        this.highScore.setForeground(new Color(MagicTilesModel.WHITE));
        this.time = new JLabel();
        this.time.setFont(new Font("default", Font.PLAIN, 19));
        this.time.setForeground(new Color(MagicTilesModel.WHITE));
        
        this.update();
        this.add(this.instruction);
        this.add(this.time);
        this.add(this.score);
        this.add(this.highScore);
    }
    
    public void update(){
        this.instruction.setText(this.model.getInstruction());
        this.score.setText(String.format("Puntuacion: %03d", this.model.getScore()));
        this.time.setText((this.model.isTimeHidden())? "  ":this.model.getTime() + "");
        this.highScore.setText(String.format("Mejor Puntuacion: %03d", this.model.getUser().getScore("MagicTiles")));
    }
}
