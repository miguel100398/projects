//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Login
//Fecha: 6/05/19
//Comentarios

package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import MenuPrincipal.MenuPrincipal;
import Queue.MyQueue;
import Usuarios.UsuarioRegistrado;


public class Login {

	private LoginView view;
	private LoginModel model;
	private MyQueue<String> usuarios;
	
	public Login(){
		this.usuarios = new MyQueue<String>();
		this.model = new LoginModel(this);
		this.abrirDatos();
		this.view = new LoginView (this);
		
	}
	
	public LoginModel getModel(){
        return this.model;
   }
	    
    public LoginView getView(){
        return this.view;
  }
    
    public void setView(LoginView view){
    	this.view = view;
    }
    
    
  public void iniciarLogin(){
     new MenuPrincipal(this);
 }
    
    private void abrirDatos(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\Usuarios\\datos.txt"));
            String line = br.readLine();
            while(line != null){
                StringTokenizer tk = new StringTokenizer(line, ",");
                this.model.addUser(new UsuarioRegistrado(tk.nextToken(), 
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
                StringTokenizer nm = new StringTokenizer(line,",");
//                this.usuarios.enqueue(nm.nextToken());
                line = br.readLine();
            }
            br.close();
        }catch(IOException ex){
            
        }
    }
    
    public void addUser(String usuario){
    	this.usuarios.enqueue(usuario);
    }
    
    public void saveDatabase(){
        try{
        	MyQueue<String> tmp = new MyQueue<String>();
            PrintWriter pw = new PrintWriter(new FileWriter("src\\Usuarios\\datos.txt"));
            while(!this.usuarios.isEmpty()){                 // Omitir el usuario guest
            	String imprimir = ""+(UsuarioRegistrado)this.model.getUsuarios().get(this.usuarios.dequeue());
            	pw.println(imprimir);
            	StringTokenizer nm = new StringTokenizer(imprimir,",");
                tmp.enqueue(nm.nextToken());
            }
            this.usuarios=tmp;
            pw.close();
        }catch(IOException ex){
            
        }
    }
	
	
}
