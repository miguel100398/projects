package Games;

public abstract class Game {
    private String game;
    private String image;
    
    public Game(String game, String image){
        this.game = game;
        this.image = image;
    }
    public abstract void endGame();
    
    public abstract void playGame();
    
    public String getName(){
        return this.game;
    }
    
    public String getImage(){
        return this.image;
    }
    
    
}
