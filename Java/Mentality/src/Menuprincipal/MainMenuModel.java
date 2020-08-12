package Menuprincipal;

import Games.Game;
import MagicTilesGame.MagicTiles;
import MathChallengeGame.MathChallenge;
import MemoryGame.Memory;
import SimonSaysGame.SimonSays;
import Users.User;

public class MainMenuModel {
    
    private Game[] games;       // Arreglo de juegos
    private int numGames;       // Numero de juegos disponibles
    static final int GAME_BACKGROUND = 0xa5adb7;   // Color background de los juegos
    
    private boolean isVisible;
    
    private User[] users;
    private int currentUser;
    
    
    public MainMenuModel(MainMenu parent, User[] users, int currentUser){
        this.numGames = 4;      // Cambiar conforme vaya aumentando el numero de juegos
        this.games = new Game[this.numGames];
        this.games[0] = new MagicTiles(parent);
        this.games[1] = new Memory(parent);
        this.games[2] = new SimonSays(parent);
        this.games[3] = new MathChallenge(parent);
        this.isVisible = true;
        this.users = users;
        this.currentUser = currentUser;
    }
    
    public Game[] getGames(){
        return this.games;
    }
    
    public int getNumGames(){
        return this.numGames;
    }
    
    public void hide(){
        this.isVisible = false;
    }
    
    public void show(){
        this.isVisible = true;
    }
    
    public boolean getIsVisible(){
        return this.isVisible;
    }
    
    public User[] getUsers(){
        return this.users;
    }
    
    public int getCurrentUser(){
        return this.currentUser;
    }
}
