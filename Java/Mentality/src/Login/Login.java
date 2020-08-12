package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import Menuprincipal.MainMenu;
import Users.RegisteredUser;

public class Login {
    
    private LoginView view;
    private LoginModel model;
    
    public Login(){
        this.model = new LoginModel(this);
        this.openDatabase();
        this.view = new LoginView(this);
    }
    
    public LoginModel getModel(){
        return this.model;
    }
    
    public LoginView getView(){
        return this.view;
    }
    
    public void beginLogin(){
        new MainMenu(this);
    }
    
    private void openDatabase(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\Users\\database.txt"));
            String line = br.readLine();
            while(line != null){
                StringTokenizer tk = new StringTokenizer(line, ",");
                this.model.addUser(new RegisteredUser(tk.nextToken(), 
                                    tk.nextToken(),
                                    tk.nextToken(),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken()),
                                    Integer.parseInt(tk.nextToken())));
                line = br.readLine();
            }
            br.close();
        }catch(IOException ex){
            
        }
    }

}
