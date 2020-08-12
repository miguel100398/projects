//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: LoginModel
//Fecha: 6/05/19
//Comentarios

package Login;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import HASH.MyHashTable;
import Usuarios.FreeUser;
import Usuarios.Usuario;

public class LoginModel {
	
	private Login parent;
	private boolean visible;
	private static final int MAXIMUM_USERS = 5;
	private int numUsuarios;
	private MyHashTable<String, Usuario> usuarios;
	private String usuarioActual;
	
	private boolean [] usuariosVisibles;
	
	public LoginModel(Login parent){
		this.parent=parent;
		this.visible=true;
		this.usuarios= new MyHashTable<String, Usuario> (10);
		this.numUsuarios=0;
		//crear usuario default
		this.usuarios.put("FreeUser", new FreeUser(0,0,0,0,0,0,0,0,0)); 
		this.usuariosVisibles = new boolean[MAXIMUM_USERS];  
		//Esconder Usuarios
		this.esconderUsuarios();
	}
	
	 public void addUser(Usuario user){
		 if (!this.usuarios.containsKey(user.getName())){
			 this.parent.addUser(user.getName());
			 this.usuarios.put(user.getName(), user);
		 }else{
			 JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre", "alerta",1);
		 }
	       
	  }
	 
	    
	 public boolean getVisible(){
	        return this.visible;
	 }
	 
	 public void esconder(){
	        this.visible = false;
	 }
	 
	 public void mostrar(){
	        this.visible = true;
	 }
	 
	 public MyHashTable<String, Usuario> getUsuarios(){
	        return this.usuarios;
	    }
	    
	 public int getNumUsuarios(){
	       return this.numUsuarios;
     }
	    
     public void setUsuarioActual(String usuario){
          this.usuarioActual = usuario;
     }
	    
    public String getUsuarioActual(){
        return this.usuarioActual;
    }
	    
    public boolean getUsuarioVisible(int i){
        return this.usuariosVisibles[i];
    }
	    
    public void esconderUsuarios(){
        for(int i = 0; i < MAXIMUM_USERS; i++){
            this.usuariosVisibles[i] = false;
        }
    }
	    
    public void mostrarUsuario(int i){
        this.usuariosVisibles[i] = true;
    }

	
}
