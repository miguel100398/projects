//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: MenuPrincipalModel
//Fecha: 6/05/19
//Comentarios

package MenuPrincipal;


import HASH.MyHashTable;
import Usuarios.Usuario;
//import Juego.Juego;

public class MenuPrincipalModel {
	//private Juego juego;
	static final int GAME_BACKGROUND = 0xa5adb7; //Color de fondo del juego
	
	private boolean visible;
	
	private MyHashTable<String, Usuario> usuarios;
    private String usuarioActual;
    
    public MenuPrincipalModel(MenuPrincipal parent, MyHashTable<String, Usuario> usuarios, String usuarioActual){
       // this.juego = new Juego();
        this.visible = true;
        this.usuarios = usuarios;
        this.usuarioActual = usuarioActual;
    }
    
    /*
    public Juego getJuego(){
        return this.juego;
    }
    */
    
    
    public void esconder(){
        this.visible = false;
    }
    
    public void mostrar(){
        this.visible = true;
    }
    
    public boolean getVisible(){
        return this.visible;
    }
    
    public MyHashTable<String, Usuario> getUsuarios(){
        return this.usuarios;
    }
    
    public String getUsuarioActual(){
        return this.usuarioActual;
    }
	
}
