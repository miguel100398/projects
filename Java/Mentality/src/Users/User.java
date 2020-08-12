package Users;

public class User {
    protected String name;
    protected String image;
    
    protected int[] MagicTilesStats;
    protected int[] MemoryGameStats;
    protected int[] SimonSaysStats;
    protected int[] MathGameStats;
    /*
     * ** MagicTilesStats **
     *     0 - high score
     *     1 - time played
     * 
     * ** MemoryGameStats **
     *     0 - high score
     *     1 - best time
     *     
     * ** SimonSaysStats **
     *     0 - high score
     *     1 - time played
     *     2 - max level
     *     
     * ** MathGameStats **
     *     0 - high score
     *     1 - time played
     * 
     */
    public User(String nombre, String image, int hs1, int time1, int hs2, int time2, int hs3, int time3, int mxLevel,int hs4, int time4){
        this.MagicTilesStats = new int[2];
        this.MemoryGameStats = new int[2];
        this.SimonSaysStats = new int[3];
        this.MathGameStats = new int[2];
        
        this.name = nombre;
        this.image = image;
        
        this.MagicTilesStats[0] = hs1;
        this.MagicTilesStats[1] = time1;
        this.MemoryGameStats[0] = hs2;
        this.MemoryGameStats[1] = time2;
        this.SimonSaysStats[0] = hs3;
        this.SimonSaysStats[1] = time3;
        this.SimonSaysStats[2] = mxLevel;
        this.MathGameStats[0] = hs4;
        this.MathGameStats[1] = time4;
    }
    
    public void setScore(int score, String game){
        // Checa si el score del juego es mayor al high score, en caso de ser verdadero, lo cambia
        switch(game){
        case "MagicTiles":
            if(score > this.MagicTilesStats[0]){
                this.MagicTilesStats[0] = score;
            }
            break;
        case "Memory":
            if(score > this.MemoryGameStats[0]){
                this.MemoryGameStats[0] = score;
            }
            break;
        case "SimonSays":
            if(score > this.SimonSaysStats[0]){
                this.SimonSaysStats[0] = score;
            }
            break;
        case "MathGame":
            if(score > this.MathGameStats[0]){
                this.MathGameStats[0] = score;
            }
            break;
        }
    }
    
    public void setTime(int seconds, String game){
        switch(game){
        case "MagicTiles":
            // Total tiempo jugado
            this.MagicTilesStats[1] += seconds;
            break;
        case "Memory":
            // Mejor tiempo
            if(seconds < this.MemoryGameStats[1] && this.MemoryGameStats[1] != 0){
                this.MemoryGameStats[1] = seconds;
            }else{
                this.MemoryGameStats[1] = seconds;
            }
            break;
        case "SimonSays":
            // Total tiempo jugado
            this.SimonSaysStats[1] += seconds;
            break;
        case "MathGame":
            // Total tiempo jugado
            this.MathGameStats[1] += seconds;
            break;
        }
    }
    
    public String getName(){
        return this.name;
    }
     
    public void setLevel(int nivel){
    	if (nivel>this.SimonSaysStats[2]){
    		this.SimonSaysStats[2]=nivel;
    	}
    }
    public String getImage(){
        return this.image;
    }
    
    public int getScore(String game){
        switch(game){
        case "MagicTiles":
            return this.MagicTilesStats[0];
        case "Memory":
            return this.MemoryGameStats[0];
        case "SimonSays":
            return this.SimonSaysStats[0];
        case "MathGame":
            return this.MathGameStats[0];
        }
        return -1;      // El juego no se encuentra en la lista
    }
    
    public int getTime(String game){
        // Returns in seconds
        switch(game){
        case "MagicTiles":
            return this.MagicTilesStats[1];
        case "Memory":
            return this.MemoryGameStats[1];
        case "SimonSays":
            return this.SimonSaysStats[1];
        case "MathGame":
            return this.MathGameStats[1];
        }
        return -1;      // El juego no se encuentra en la lista
    }
    public int getLevel(){
    	return this.SimonSaysStats[2];
    }
    
    
}
