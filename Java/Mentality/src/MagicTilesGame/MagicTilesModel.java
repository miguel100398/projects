package MagicTilesGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Songs.Music;
import Users.User;

public class MagicTilesModel {
    
    private int state;
    private int sizeSequence;           // El numero de cuadros que aparecen cada vez
    private int m,
                n;                      // Numero de filas y columnas
    private int[] colors;              // Arreglo de colores hexadecimales formato 0xValorHex
    private int[] sequence;            // La secuencia que se genera
    private int currentSequenceTile;    // El tile actual de la secuencia
    private String instruction;         // Instrucción del juego
    private final String[] instructions = {"La siguiente ronda comienza en... ","Memoriza la secuencia...","Repite la secuencia en orden...", "", "Ronda Ganada!", "Se acabo el juego"};
    private int score;                  // Se guarda el score del juego
    private int time;                   // Segundos para que el jugador memorize la secuencia
    static final int[] segundosSecuencia = {1000, 750, 500, 250, 100};          // Milesimas de segundo en las que aparece cada tile de la secuencia dependiendo del nivel
    static final int[] segundosEspera = {5000, 4000, 3000, 2000, 1000};          // Milesimas de segundo para que el jugador memorice la secuencia
    private int nivelActual;            // Da a conocer el nivel para aumentar de velocidad Rango(0-4)
    private int esperaActual;           // Tiempo para memorizar
    private ArrayList<Integer> inactiveTiles;       // Tiles individuales desactivadas
    private boolean tilesActive,
                    isTimeHidden,
                    running;                // Indica que el Thread esta activo
    static final int WHITE = 0xffffff,
                      BLACK = 0x000000,
                      GREEN = 0x36cc2e,
                      RED = 0xba2323,
                      YELLOW = 0xe8d05c;
    
    private User user;      // Usuario del juego
    
    // Musica del juego
    private Music wrongAnswer;
    private Music cuenta;
    private Music[] tilesMusic;         // Se almacena cada una de las notas de cada tile
    
    public MagicTilesModel(){
        this.state = 1;
        this.tilesActive = false;
        this.inactiveTiles = new ArrayList<>();
        this.sizeSequence = 2;
        this.m = 7;
        this.n = 5;
        this.colors = new int[this.m*this.n];
        this.score = 0;
        this.time = 3;
        this.running = true;
        this.isTimeHidden = false;
        this.instruction = instructions[0];     // El juego comienza con la primera instruccion
        this.currentSequenceTile = 0;
        this.nivelActual = 0;
        this.esperaActual = 0;
        // Inicialmente todas las filas son de negro
        this.paintAllBlack();
        this.wrongAnswer = new Music("src\\MagicTilesGame\\Music\\wrong.wav");
        this.cuenta = new Music("src\\MagicTilesGame\\Music\\inicio.wav");
        this.tilesMusic = new Music[this.m*this.n];
        for(int i = 0; i < this.tilesMusic.length; i++){
            // Llenar el arreglo de musica
            this.tilesMusic[i] = new Music("src\\MagicTilesGame\\Music\\note"+i+".wav");
        }
    }
    
    public void generateSequence(){
        // El siguiente bloque de instrucciones fue tomado y modificado de http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
        this.sequence = new int[this.sizeSequence];
        Integer[] arr = new Integer[this.m*this.n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        for(int i = 0; i < sequence.length; i++){
            sequence[i] = arr[i];
        }
        if(this.sizeSequence < this.m*this.n){
            this.sizeSequence++;
        }
    }
    
    public void paintAllWhite(){
        for(int i = 0; i < this.colors.length; i++){
            this.colors[i] = WHITE;
        }
    }
    
    public void paintAllBlack(){
        for(int i = 0; i < this.colors.length; i++){
            this.colors[i] = BLACK;
        }
    }
    
    public int[] getSequence(){
        return this.sequence;
    }
    
    public int getCurrentSequenceTile(){
        return this.currentSequenceTile;
    }
    
    public int[] getColors(){
        return this.colors;
    }
    
    public int getTime(){
        return this.time;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public String getInstruction(){
        return this.instruction;
    }
    
    public int getM(){
        return this.m;
    }
    
    public int getN(){
        return this.n;
    }
    
    public boolean areTilesActive(){
        return this.tilesActive;
    }
    
    public ArrayList<Integer> getInactiveTiles(){
        return this.inactiveTiles;
    }
    
    public void addInactiveTile(int e){
        this.inactiveTiles.add(e);
    }
    
    public void emptyInactiveTiles(){
        this.inactiveTiles.clear();
    }
    
    public boolean isTimeHidden(){
        return this.isTimeHidden;
    }
    
    public void hideTime(){
        this.isTimeHidden = true;
    }
    
    public void showTime(){
        this.isTimeHidden = false;
    }
    
    
    public int getState(){
        // Regresa el estado del juego
        return this.state;
    }
    
    public void setState(int state){
        this.state = state;
    }
    
    public void resetSequenceTile(){
        this.currentSequenceTile = 0;
    }
    
    public void nextSequenceTile(){
        this.currentSequenceTile++;
    }
    
    public void setInstruction(int numInstruction){
        this.instruction = instructions[numInstruction];
    }
    
    public void paintTile(int numTile, int color){
        this.colors[numTile] = color;
    }
    
    public void addScore(int score){
        this.score += score;
    }
    
    public void subtractTime(){
        this.time--;
    }
    
    public void resetTime(int time){
        this.time = time;
    }
    
    public void activateTiles(){
        this.tilesActive = true;
    }
    
    public void deactivateTiles(){
        this.tilesActive = false;
    }
    
    public boolean isRunning(){
        return this.running;
    }
    
    public void stopRunning(){
        this.running = false;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public int getNivelActual(){
        return this.nivelActual;
    }
    
    public void nextLevel(){
        this.nivelActual++;
        this.esperaActual++;
    }
    
    public int getEsperaActual(){
        return this.esperaActual;
    }
    
    public Music getWrongAnswer(){
        return this.wrongAnswer;
    }
    public Music getCuenta(){
        return this.cuenta;
    }
    public Music[] getTilesMusic(){
        return this.tilesMusic;
    }
    
    
    public void initState(){
        // Reiniciar el estado del juego
        this.state = 1;
        this.tilesActive = false;
        this.inactiveTiles.clear();
        this.score = 0;
        this.time = 3;
        this.isTimeHidden = false;
        this.instruction = instructions[0];
        this.sizeSequence = 2;
        this.m = 7;
        this.n = 5;
        this.colors = new int[m*n];
        this.currentSequenceTile = 0;
        this.nivelActual = 0;
        this.esperaActual = 0;
        this.paintAllBlack();
    }

}
