//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: MenuPrincipal
//Fecha: 6/05/19
//Comentarios

package MenuPrincipal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Login.Login;
import MenuPrincipal.MenuPrincipalModel;
import MenuPrincipal.MenuPrincipalView;
import Usuarios.UsuarioRegistrado;

public class MenuPrincipal {
	
	private Login parent;
    private MenuPrincipalModel model;
    private MenuPrincipalView view;
    
    public MenuPrincipal(Login parent){
        this.parent = parent;
        this.model = new MenuPrincipalModel(this, this.parent.getModel().getUsuarios(), this.parent.getModel().getUsuarioActual());
        this.view = new MenuPrincipalView(this);
    }
    
    public MenuPrincipalModel getModel(){
        return this.model;
    }
    
    public MenuPrincipalView getView(){
        return this.view;
    }
    
    public Login getParent(){
    	return this.parent;
    }
    
    public void logout(){
        this.view.dispose();
        this.parent.getModel().mostrar();
        this.parent.getView().actualizar();
    }
    
    public void guardarDatos(){
    	this.parent.saveDatabase();
    }

}
