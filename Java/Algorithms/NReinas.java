
public class NReinas {

	private int[] reinas;
	
	public static void main(String[] args) {
		NReinas tablero=new NReinas(8);
		tablero.busca();
	}
	
	public NReinas(int n){
		this.reinas=new int [n];
		for (int i=0; i<this.reinas.length; i++){
			this.reinas[i] = -1;
		}
	}

	public boolean valida (int fila, int columna){
		//Validar columnas
		for (int i =0; i<fila;i++){
			if (this.reinas[i] == columna){
				return false;
			}
		//Validar diagonales
			if (Math.abs(this.reinas[i]-columna)==Math.abs(fila-i)){
				return false;
			}
		}
		return true;
	}
	
	public void imprimeSolucion(){
		for(int pos:this.reinas	){
			System.out.print(pos+",");
		}
		System.out.println();
	}
	
	public void busca(int fila){
		for (int i = 0; i<this.reinas.length; i++){
			if (valida(fila,i)){
				this.reinas[fila]=i;
				if(fila<this.reinas.length-1){
					busca(fila+1);
				}
				else {
					imprimeSolucion();
				}
			}
		}
	}
	
	public void busca(){
		busca(0);
	}
}
