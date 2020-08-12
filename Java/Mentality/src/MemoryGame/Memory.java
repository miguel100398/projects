package MemoryGame;

import Games.Game;
import Menuprincipal.MainMenu;

public class Memory extends Game{
    
    private MainMenu parent;
    private MemoryModel model;
    private MemoryView view;
    private MemoryController controller;

    public Memory(MainMenu parent){
        super("Memory", "src\\Games\\MemoryThumbnail.png");
        this.parent = parent;
    }
    
    public MemoryModel getModel(){
        return this.model;
    }
    
    public MemoryView getView(){
        return this.view;
    }

    public void endGame(){
        this.parent.saveDatabase();
        this.view.dispose();
        this.model.stopTime();
        this.parent.getModel().show();
        this.parent.getView().update();
    }

    public void playGame() {
        this.model = new MemoryModel();
        this.model.setUser(this.parent.getModel().getUsers()[this.parent.getModel().getCurrentUser()]);         // Pasarle el usuario que esta jugando actualmente
        this.view = new MemoryView(this);
        this.controller = new MemoryController(this);
        this.view.addController(this.controller);
    }

}
