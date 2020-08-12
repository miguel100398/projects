//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Min_Heap
//Fecha: 6/05/19
//Comentarios


package HEAPS;
public class Min_Heap <E extends Comparable<E>>{

	public int size;
	private NodoHeap root;
	private E [] array;
	private int central;
	

	
	public Min_Heap (E [] entrada, int size){
		int indice = 0;
		this.central = 0;
		this.size=0;
		array = (E[]) new Comparable [size+1];
		for (int i=0; i<array.length; i++){
			if (i<entrada.length){
				array[i] = entrada[i];
				this.size++;
			}else{
				array[i] = null;
			}
		}
		central = (size/2)-1;
		indice = central;
		while (indice >= 0){
			hacer_heap(indice);
			indice --;
		}
	}
	
	private void hacer_heap(int indice){
		E tmp=null;
		int indice_izquierdo = (2*indice)+1;
		E izquierdo = array[indice_izquierdo];
		int indice_derecho = (2*indice)+2;
		E derecho = array[indice_derecho];
		E comparar = array[indice];
		if (izquierdo != null){
			if (derecho!=null){
				if ((izquierdo.compareTo(derecho))<0){
					if ((izquierdo.compareTo(comparar)<0)){
						tmp = izquierdo;
						array[indice_izquierdo]=comparar;
						array[indice]=tmp;
						if (indice_izquierdo < central){
						hacer_heap(indice_izquierdo);
						}
					}
				} else if ((derecho.compareTo(comparar))<0){
					tmp = derecho;
					array[indice_derecho]=comparar;
					array[indice]=tmp;
					if (indice_derecho<central){
					hacer_heap(indice_derecho);
					}
				}
			}else{
				if ((izquierdo.compareTo(comparar))<0){
					tmp = izquierdo;
					array[indice_izquierdo]=comparar;
					array [indice]=tmp;
					if (indice_izquierdo<central){
					hacer_heap(indice_izquierdo);
					}
				}
			}
		}
			
	}
	
	public void insertar (E dato){
		array[size] = dato;
		size++;
		central = (size/2)-1;
		int indice;
		indice = central;
		while (indice >= 0){
			hacer_heap(indice);
			indice --;
		}
	}
	
	public E borrar (){
		E regresar = array[0];
		size--;
		array[0] = array[size];
		array[size] = null;
		
		central = (size/2)-1;
		int indice = central;
		while (indice >= 0){
			hacer_heap(indice);
			indice --;
		}
		return regresar;
	}
	
	
	public String toString(){
		String regresar="";
		int i = 0;
		while (this.array[i]!=null){
			regresar += array[i] + ", ";
			i++;
		}
		return regresar;
	}
	
}

class NodoHeap <E extends Comparable<E>>{
	
	private E dato;
	private NodoHeap<E> right;
	private NodoHeap<E> left;
	
	NodoHeap(E dato){
		this.dato = dato;
		this.right = null;
		this.left = null;
	}

	NodoHeap(E dato, NodoHeap<E> right, NodoHeap<E> left){
		this.dato = dato;
		this.right=right;
		this.left=left;
	}
	
	
	public E getDato() {
		return dato;
	}

	public void setDato(E dato) {
		this.dato = dato;
	}

	public NodoHeap getRight() {
		return right;
	}

	public void setRight(NodoHeap right) {
		this.right = right;
	}

	public NodoHeap getLeft() {
		return left;
	}

	public void setLeft(NodoHeap left) {
		this.left = left;
	}
	
	
	
}