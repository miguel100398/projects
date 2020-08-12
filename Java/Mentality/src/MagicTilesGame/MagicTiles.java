package MagicTilesGame;

import Games.Game;
import Menuprincipal.MainMenu;
import Songs.Music;

public class MagicTiles extends Game{
    
    private MainMenu parent;
    private MagicTilesModel model;
    private MagicTilesView view;
    private MagicTilesController controller;

    public MagicTiles(MainMenu parent){
        super("MagicTiles", "src\\Games\\MagicTilesThumbnail.png");
        this.parent = parent;
    }
    
    public void playGame(){
        // Se inicializa todo
        this.model = new MagicTilesModel();
        this.model.setUser(this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()]);
        this.view = new MagicTilesView(this);
        this.controller = new MagicTilesController(this);
        this.view.addController(this.controller);
    }
    
    public MagicTilesModel getModel(){
        return this.model;
    }
    
    public MagicTilesController getController(){
        return this.controller;
    }
    
    public MagicTilesView getView(){
        return this.view;
    }
    
    
    public void endGame(){
        this.parent.saveDatabase();
        this.view.dispose();
        this.model.stopRunning();
        this.parent.getModel().show();
        this.parent.getView().update();
    }
}
