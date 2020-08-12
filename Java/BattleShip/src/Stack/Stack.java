//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Stack
//Fecha: 6/05/19
//Comentarios

package Stack;

import java.util.NoSuchElementException;

import ListaEnlazada.ListaEnlazada;

public class Stack <E>{

	private ListaEnlazada <E> lista;
	
	
	
	public Stack(){
		this.lista=new ListaEnlazada<>();
	}
	
	public void push(E dato){
		this.lista.insertarInicio(dato);
	}

	public E pop() throws NoSuchElementException{
		try{
		return this.lista.borrarInicio();
		} catch(IndexOutOfBoundsException ex){
			throw new NoSuchElementException("No se puede hacer un pop en una pila vacia");
		}
	}
	
	public E top() throws NoSuchElementException{
		try{
		return this.lista.inicio();
		}catch(IndexOutOfBoundsException ex){
			throw new NoSuchElementException("No se puede hacer un top en una pila vacia");
		}
	}
	
	public int size(){
		return this.lista.size();
	}
	
	public boolean isEmpty(){
		return this.lista.estaVacia();
	}
	
	public void flush(){
		this.lista.flush();
	}
}
