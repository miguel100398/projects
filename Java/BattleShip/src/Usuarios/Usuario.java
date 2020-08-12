//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Usuario
//Fecha: 6/05/19
//Comentarios

package Usuarios;

public class Usuario {
	protected String name;
    protected String image;
    
    protected int[] stats;
    
    public Usuario (String nombre, String image, int tiempo, int partidas, int victorias, int derrotas, int disparos, int aciertos, int fallos, int barcosDestruidos, int barcosPerdidos){
    	this.stats = new int[9];
    	
    	this.name=nombre;
    	this.image=image;
    	
    	this.stats[0] = tiempo;
    	this.stats[1] = partidas;
    	this.stats[2] = victorias;
    	this.stats[3] = derrotas;
    	this.stats[4] = disparos;
    	this.stats[5] = aciertos;
    	this.stats[6] = fallos;
    	this.stats[7] = barcosDestruidos;
    	this.stats[8] = barcosPerdidos;
    }
    
    public void setTiempo(int time){
    	this.stats[0] += time;
    }
    
    public void setPartidas (){
    	this.stats[1] ++;
    }
    
    public void setVictorias (){
    	this.stats[2] ++;
    }
    
    public void setDerrotas (){
    	this.stats[3] ++;
    }
    
    public void setDisparos(int disparos){
    	this.stats[4] += disparos;
    }
    
    public void setAciertos(int aciertos){
    	this.stats[5] += aciertos;
    }
    
    public void setFallos (int fallos){
    	this.stats[6] += fallos;
    }
    
    public void setBarcosDestruidos (int barcos){
    	this.stats[7] += barcos;
    }
    
    public void setBarcosPerdidos (int barcos){
    	this.stats[8] += barcos;
    }
    
    public int getTiempo(){
    	return this.stats[0];
    }
    
    public int getPartidas (){
    	return this.stats[1];
    }
    
    public int getVictorias (){
    	return this.stats[2];
    }
    
    public int getDerrotas (){
    	return this.stats[3];
    }
    
    public int getDisparos(){
    	return this.stats[4];
    }
    
    public int getAciertos(){
    	return this.stats[5];
    }
    
    public int getFallos (){
    	return this.stats[6];
    }
    
    public int getBarcosDestruidos (){
    	return this.stats[7];
    }
    
    public int getBarcosPerdidos (){
    	return this.stats[8];
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getImage(){
        return this.image;
    }
    
}
