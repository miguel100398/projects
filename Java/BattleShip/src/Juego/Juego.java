//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Juego
//Fecha: 6/05/19
//Comentarios


package Juego;

import MenuPrincipal.MenuPrincipal;

public class Juego {

	private ModelJuego model;
	private ViewJuego view;
	private MenuPrincipal menuPrincipal;
	private ColocarBarcos colocarBarcos;
	
	public Juego (MenuPrincipal menuPrincipal){
		this.menuPrincipal = menuPrincipal;
		this.model = new ModelJuego(this);
		this.view = new ViewJuego(this, model);
		this.view.esconder();
		for (int i = 1; i<=100; i++){	
			this.model.getBarcosGrafosJugador().getBotonBarco(i).setEnabled(true);
		}
		this.colocarBarcos = new ColocarBarcos(this, this.view.getPanelJugador());
	}
	
	public void jugar(){
		for (int i = 1; i<=100; i++){
			this.model.getBarcosGrafosEnemigo().getBotonBarco(i).setJugar(true);	
			this.model.getBarcosGrafosJugador().getBotonBarco(i).setJugar(true);
			this.model.getBarcosGrafosJugador().getBotonBarco(i).setEnabled(false);
		}
		this.view.setPanelJugador(this.colocarBarcos.getPanelJugador());
		this.colocarBarcos.dispose();
		this.view.mostrar();
		this.model.jugar();
	}
	
	public MenuPrincipal getMenuPrincipal(){
		return this.menuPrincipal;
	}
	
	public ModelJuego getModel(){
		return this.model;
	}
	
	public ViewJuego getView(){
		return this.view;
	}
	
	public ColocarBarcos getColocarBarcos(){
		return this.colocarBarcos;
	}
	
}
