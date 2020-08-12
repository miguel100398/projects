//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: ListaEnlazada
//Fecha: 6/05/19
//Comentarios

package ListaEnlazada;

import java.util.NoSuchElementException;

//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Quicksort
//Fecha: 11/02/19
//Comentarios

public class ListaEnlazada <E> {

	private NodoLE<E> inicio,
					  fin;
	private int size;
	
	
	public ListaEnlazada(){
		this.inicio = this.fin = null;
		this.size = 0;
	}
	
	public ListaEnlazada(E[] datos){
		this.size=0;
		for (int i=0; i<datos.length; i++){
			this.insertarFin(datos[i]);
		}
	}
	
	public void insertarInicio(E dato){
		NodoLE<E> nvo = new NodoLE<>(dato, this.inicio);
		this.inicio = nvo;
		if(this.size == 0){
			this.fin = nvo;
		}
		this.size ++;
	}
	
	public void insertarFin(E dato){
		if (this.estaVacia()){
			this.insertarInicio(dato);
		}else{
		NodoLE<E> nvo = new NodoLE<E>(dato);
		this.fin.setNext(nvo);
		this.fin = nvo;
		this.size++;
		}
	}
	
	public void insertarEn(E dato, int pos) throws IndexOutOfBoundsException{
		NodoLE<E> nodotmp;
		NodoLE<E> nvo;
		if (pos < 0 || pos>size){
			throw new IndexOutOfBoundsException ("Posición fuera de los limites del arreglo");
		}else{
			if (pos == 0){
				this.insertarInicio(dato);
			}else if (pos == size){
				this.insertarFin(dato);
			}else{
				nodotmp = this.inicio;
				for (int i=0; i<pos-1; i++){
					nodotmp = nodotmp.getNext();
				}
				nvo = new NodoLE<E>(dato, nodotmp.getNext());
				nodotmp.setNext(nvo);
				size++;
			}
		}
			
	}
	
	public E borrarInicio(){
		try{
		E res = this.inicio();
		this.inicio = this.inicio.getNext();
		this.size--;
		if (this.size==0){
			this.fin = null;
		}
		return res; 
		} catch(NoSuchElementException ex){
			throw new IndexOutOfBoundsException ("No se puede borrar el primer elemento de una lista vacia");
		}
	}
	
	public E borrarFin(){
		if (this.size>1){
			E res=this.fin();
			NodoLE<E> current = this.inicio;
			for (int i=0; i<this.size-2;i++){
				current=current.getNext();
			}
			current.setNext(null);
			this.fin=current;
			this.size--;
			return res;
		}else{
			try {
			return this.borrarInicio();
			} catch (IndexOutOfBoundsException ex){
				throw new IndexOutOfBoundsException("No se puede elimiar el elemento de la lista");
			}
		}
			
	}
	
	public E borrarEn( int pos) throws IndexOutOfBoundsException{
		NodoLE<E> nodotmp;
		NodoLE<E> regresar;
		if (pos<0||pos>size){
			throw new IndexOutOfBoundsException ("posicion fuera de los limites del arreglo");
		}else{
			if (pos == 0){
				return this.borrarInicio();
			} else if (pos == size){
				return this.borrarFin();
			}else{
				nodotmp = this.inicio;
				for (int i=0; i<pos-1; i++){
					nodotmp = nodotmp.getNext();
				}
				regresar = nodotmp.getNext();
				nodotmp.setNext(nodotmp.getNext().getNext());
				size --;
				return regresar.getDato();
			}
		}
	}
	
	public E getEn(int pos){
		if(pos >=0 && pos<this.size){
			NodoLE <E> current = this.inicio;
			for (int i =0; i<pos; i++){
				current=current.getNext();
			}
			return current.getDato();
		}else{
			throw new IndexOutOfBoundsException("No se puede regresar el elemento en la posición " +pos+" en una lista de tamaño "+this.size);
		}
	}
	
	public void setEn(E dato, int pos){
		if(pos >=0 && pos<this.size){
			NodoLE <E> current = this.inicio;
			for (int i =0; i<pos; i++){
				current=current.getNext();
			}
			current.setDato(dato);
		}else{
			throw new IndexOutOfBoundsException("No se puede regresar el elemento en la posición " +pos+" en una lista de tamaño "+this.size);
		}
	}
	public void flush(){
		this.inicio=this.fin=null;
		this.size=0;
		System.gc();
	}
	
	public int size(){
		return this.size;
	}
	
	public E inicio(){
		try{
			return this.inicio.getDato();
		}catch (NullPointerException ex){
			throw new NoSuchElementException ("No se puede regresar el primer elemento de una lista vacia");
		}
	}
	
	public E fin(){
		try{
			return this.fin.getDato();
		} catch (NullPointerException ex){
			throw new NoSuchElementException ("No se puede regresar el ultimo elemento de una lista vacia");
		}
	}
	
	public boolean estaVacia(){
		return this.size==0;
	}
	
	public String toString(){
		String res ="";
		NodoLE<E> current = this.inicio;
		for(int i=0; i<this.size; i++){
			res += current.getDato()+",";
			current=current.getNext();
		}
		return res;
	}
}

class NodoLE<E>{
	private E dato;
	private NodoLE<E> next;
	
	public NodoLE(E dato){
		this(dato,null);
	}
	
	public NodoLE(E dato, NodoLE<E> next) {
		super();
		this.dato = dato;
		this.next = next;
	}

	public E getDato() {
		return dato;
	}

	public void setDato(E dato) {
		this.dato = dato;
	}

	public NodoLE<E> getNext() {
		return next;
	}

	public void setNext(NodoLE<E> next) {
		this.next = next;
	}
	
	public String toString(){
		return this.dato+":"+this.next;
	}
}
