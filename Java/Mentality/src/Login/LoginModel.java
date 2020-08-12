package Login;

import Users.FreeUser;
import Users.User;

public class LoginModel {
    
    private Login parent;
    private boolean isVisible;
    private static final int MAXIMUM_USERS = 6;
    private int usersStored;
    private User[] users;
    private int currentUser;
    
    private boolean[] componentsVisible;
    
    public LoginModel(Login parent){
        this.parent = parent;
        this.isVisible = true;
        this.users = new User[MAXIMUM_USERS];
        this.usersStored = 0;
        this.users[this.usersStored++] = new FreeUser(0,0,0,0,0,0,0,0,0);         // Aniadir al usuario default
        this.componentsVisible = new boolean[MAXIMUM_USERS];       // Componentes escondidos al inicio
        this.hideComponents();
    }
    
    public void addUser(User user){
        this.users[this.usersStored++] = user;
    }
    
    public boolean getIsVisible(){
        return this.isVisible;
    }
    
    public void hide(){
        this.isVisible = false;
    }
    
    public void show(){
        this.isVisible = true;
    }
    
    public User[] getUsers(){
        return this.users;
    }
    
    public int getUsersSize(){
        return this.usersStored;
    }
    
    public void setCurrentUser(int user){
        this.currentUser = user;
    }
    
    public int getCurrentUser(){
        return this.currentUser;
    }
    
    public boolean isVisible(int i){
        return this.componentsVisible[i];
    }
    
    public void hideComponents(){
        for(int i = 0; i < MAXIMUM_USERS; i++){
            this.componentsVisible[i] = false;
        }
    }
    
    public void unhideComponent(int i){
        this.componentsVisible[i] = true;
    }
}
