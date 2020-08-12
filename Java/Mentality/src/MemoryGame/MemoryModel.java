package MemoryGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;

import Songs.Music;
import Users.User;

public class MemoryModel {
    
    private int state;                          // Estado del juego
    private int m,
                n;
    private int score;
    private int minutes,
                seconds;
    private String[] animalImages;                    // Arreglo con path para imagenes de animales
    private String[] carsImages;                      // Arreglo con path para imagenes de coches
    private String[] desertsImages;                      // Arreglo con path para imagenes de postres
    private String[][] totalImages;                   // Banco total de imagenes
    
    private boolean[] memoryDeckOptions;                   // Almacena las opciones customizables para la siguiente ronda elegidas por el usuario
    
    private String[] defaultCards;                    // Banco de cartas default
    
    private String[] currentImages;             // Almacena las imagenes de la ronda actual
    private String defaultCard;               // Almacena la carta default de las cartas al inicio
    
    private int[] deck;                         // Contiene el deck de cartas con paths para las imagenes
    private boolean[] turned;                 // Imagenes que se han volteado

    private int imgCompare;               // Primera imagen a comparar

    private String wallpaper, optionsWp;
    
    private Random rnd;
    
    private int pairsCollected;             // Numero de parejas volteadas
    private boolean isTimeTicking;          // Controla el reloj del juego
    
    private User user;
    
    private Music shuffleSound, cardTurn, cardTurnOver;
    
    // Las siguientes variables son para cuando el juego se vuelve de dos jugadores
    private int numPlayersSelected;     // Cambia cada vez que se modifica el radio Button de jugadores para tener una referencia
    private boolean twoPlayers;         // Indica de cuantos jugadores es el juego
    private int p1PairsCollected;       // Parejas volteadas por j1
    private int p2PairsCollected;       // Parejas volteadas por j2
    private boolean p1Turn;             // True si es turno de jugador 1, false si es del 2
    
    
    public MemoryModel(){
        this.m = 6;
        this.n = 6;
        this.state = 1;                      // Comenzar en el primer estado del juego
        // Iniciar score
        this.score = 0;
        // Iniciar minutos y segundos
        this.minutes = 0;
        this.seconds = 0;
        this.pairsCollected = 0;
        this.shuffleSound = new Music("src\\MemoryGame\\Music\\shuffle.wav");
        this.cardTurn = new Music("src\\MemoryGame\\Music\\cardTurn.wav");
        this.cardTurnOver = new Music("src\\MemoryGame\\Music\\cardReturn.wav");
        this.animalImages = new String[(this.m*this.n)/2];
        this.carsImages = new String[(this.m*this.n)/2];
        this.desertsImages = new String[(this.m*this.n)/2];
        this.totalImages = new String[3][];         // Originalmente, almacena 3 bancos de imagenes
        this.defaultCards = new String[4];         // ******Modificar******
        this.turned = new boolean[this.m*this.n];
        this.deck = new int[this.m*this.n];
        this.rnd = new Random();
        this.memoryDeckOptions = new boolean[4];
        this.isTimeTicking = true;
        
        // Variables para dos jugadores
        this.twoPlayers = false;        // Inicia con un jugador
        this.numPlayersSelected = 1;    // Originalmente el radio button viene por default seleccionado un jugador
        
        for(int i = 0; i < this.memoryDeckOptions.length; i++){
            this.memoryDeckOptions[i] = false;
        }
        this.memoryDeckOptions[this.memoryDeckOptions.length-1] = true;    // La opcion por default
        
        // Inicializar banco de imagenes (Todos los bancos deben tener minimo 18 imagenes)
        for(int i = 0; i < (this.m*this.n)/2; i++){
             this.animalImages[i] = "src\\MemoryGame\\Images\\Animals\\animal" + (i+1) + ".jpg";
             this.carsImages[i] = "src\\MemoryGame\\Images\\Cars\\car" + (i+1) + ".jpg";
             this.desertsImages[i] = "src\\MemoryGame\\Images\\Deserts\\desert" + (i+1) + ".jpg";
        }
        this.totalImages[0] = this.animalImages;
        this.totalImages[1] = this.carsImages;
        this.totalImages[2] = this.desertsImages;
        
        this.currentImages = totalImages[rnd.nextInt(totalImages.length)];
        
        // Inicializar imagenes default
        for(int i = 0; i < this.defaultCards.length; i++){
            this.defaultCards[i] = "src\\MemoryGame\\Images\\back" + (i+1) + ".png";
        }
        
        this.defaultCard = this.defaultCards[rnd.nextInt(this.defaultCards.length)];
        
        this.wallpaper = "src\\MemoryGame\\Images\\grass.jpg";
        this.optionsWp = "src\\MemoryGame\\Images\\wood.jpg";
        
        for(int i = 0; i < this.m*this.n; i++){
            this.turned[i] = false;           // Todas las cartas empiezan boca abajo
        }
        
        // Llenar el deck de cartas
        int card = 0;
        for(int i = 0; i < this.m*this.n; i+=2){
            this.deck[i] = card;
            this.deck[i+1] = card++;
        }
        this.shuffleDeck();
    }
    
    private void shuffleDeck(){
        // Revuelve las cartas en el arreglo de imagenes
        // El siguiente bloque de instrucciones fue tomado y modificado de http://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
        Integer[] tmp = new Integer[this.deck.length];
        for(int i = 0; i < this.deck.length; i++){
            tmp[i] = this.deck[i];
        }
        Collections.shuffle(Arrays.asList(tmp));
        for(int i = 0; i < this.deck.length; i++){
            this.deck[i] = tmp[i];
        }
    }
    
    public int getM(){
        return this.m;
    }
    public int getN(){
        return this.n;
    }
    public int getState(){
        return this.state;
    }
    public void setState(int state){
        this.state = state;
    }
    public String getDefaultCard(){
        return this.defaultCard;
    }
    public boolean[] getTurned(){
        return this.turned;
    }
    public void turnOver(int cardSelected){
        this.turned[cardSelected] = (this.turned[cardSelected])?false:true;
    }
    public int[] getDeck(){
        return this.deck;
    }
    public String[] getCurrentImages(){
        return this.currentImages;
    }
    public String getWallpaper(){
        return this.wallpaper;
    }
    public String getOptionsWp(){
        return this.optionsWp;
    }
    public void setImgCompare(int compare){
        // Guarda la imagen a comparar
        this.imgCompare = compare;
    }
    public int getImgCompare(){
        return this.imgCompare;
    }
    
    public int getScore(){
        return this.score;
    }
    public void addScore(int score){
        this.score += score;
    }
    public void subtractScore(int score){
        if(this.score-score >= 0){          // Solo restar si hay puntos
            this.score -= score;
        }
    }
    public void incrementSeconds(){
        if(this.seconds + 1 == 60){
            this.incrementMinutes();
        }
        this.seconds = (this.seconds+1)%60;
    }
    private void incrementMinutes(){
        this.minutes++;
    }
    public int getMinutes(){
        return this.minutes;
    }
    public int getSeconds(){
        return this.seconds;
    }
    public void changeDeckSelection(int pos){
        for(int i = 0; i < this.memoryDeckOptions.length; i++){
            if(pos == i){
                this.memoryDeckOptions[i] = true;
            }else{
                this.memoryDeckOptions[i] = false;
            }
        }
    }
    public int getDeckSelected(){
        // Regresa la posicion del deck seleccionado
        for(int i = 0; i < this.memoryDeckOptions.length; i++){
            if(this.memoryDeckOptions[i]){
                return i;
            }
        }
        return -1;      // No existe una posicion seleccionada ERROR
    }
    public int getPairsCollected(){
        return this.pairsCollected;
    }
    public void addPairCollected(){
        // Checa si el juego es de dos jugadores para aniadir al jugador su par
        if(this.twoPlayers){
            if(this.p1Turn){
                this.p1PairsCollected++;
            }else{
                this.p2PairsCollected++;
            }
        }
        this.pairsCollected++;
    }
    public boolean getTimeTicking(){
        return this.isTimeTicking;
    }
    public void stopTime(){
        this.isTimeTicking = false;
    }
    public void startTime(){
        this.isTimeTicking = true;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }
    public Music getShuffleSound(){
        return this.shuffleSound;
    }
    public Music getCardSound(){
        return this.cardTurn;
    }
    public Music getCardSound2(){
        return this.cardTurnOver;
    }
    
    // Metodos para dos jugadores
    public void setNumPlayersSelected(int num){
        // num debe ser valor 1 o 2
        this.numPlayersSelected = num;
    }
    public boolean isTwoPlayers(){
        return this.twoPlayers;
    }
    public boolean isP1Turn(){
        return this.p1Turn;
    }
    public int getP1PairsCollected(){
        return this.p1PairsCollected;
    }
    public int getP2PairsCollected(){
        return this.p2PairsCollected;
    }
    public void changeTurn(){
        this.p1Turn = !this.p1Turn;
    }
    
    public void initState(int memoryDeck){
        // memoryDeck es el deck de cartas seleccionado por el usuario para la siguiente ronda
        
        this.m = 6;
        this.n = 6;
        this.state = 1;                      // Comenzar en el primer estado del juego
        // Iniciar score
        this.score = 0;
        // Iniciar minutos y segundos
        this.minutes = 0;
        this.seconds = 0;
        this.pairsCollected = 0;
        this.isTimeTicking = true;
        this.currentImages = totalImages[(memoryDeck == 3)? rnd.nextInt(totalImages.length):memoryDeck];        // Si memoryDeck es 3, el deck es random

        this.defaultCard = this.defaultCards[rnd.nextInt(this.defaultCards.length)];

        for(int i = 0; i < this.m*this.n; i++){
            this.turned[i] = false;           // Todas las cartas empiezan boca abajo
        }
        
        // Checar si es de dos jugadores el juego
        if(this.numPlayersSelected == 2){
            this.twoPlayers = true;
            this.p1Turn = true;
            this.p1PairsCollected = 0;
            this.p2PairsCollected = 0;
        }else{
            this.twoPlayers = false;
        }

        this.shuffleDeck();
    }
}
