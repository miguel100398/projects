package Menuprincipal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import Login.Login;
import Users.RegisteredUser;
import Users.User;

public class MainMenu {
    
    private Login parent;
    private MainMenuModel model;
    private MainMenuView view;
    
    public MainMenu(Login parent){
        this.parent = parent;
        this.model = new MainMenuModel(this, this.parent.getModel().getUsers(), this.parent.getModel().getCurrentUser());
        this.view = new MainMenuView(this);
    }
    
    public MainMenuModel getModel(){
        return this.model;
    }
    
    public MainMenuView getView(){
        return this.view;
    }
    
    public void logout(){
        this.view.dispose();
        this.parent.getModel().show();
        this.parent.getView().update();
    }
    
    public void saveDatabase(){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("src\\Users\\database.txt"));
            for(int i = 1; i < this.parent.getModel().getUsersSize(); i++){                 // Omitir el usuario guest
                pw.println((RegisteredUser)this.model.getUsers()[i]);
            }
            pw.close();
        }catch(IOException ex){
            
        }
    }
}
