//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: MyQueue
//Fecha: 6/05/19
//Comentarios

package Queue;
import java.util.NoSuchElementException;

import ListaEnlazada.ListaEnlazada;

public class MyQueue<E> {
	
	private ListaEnlazada <E> lista;
	
	public static void main(String[] args) {
		MyQueue<String> cola = new MyQueue<>();
		cola.enqueue("Hola");
		cola.enqueue("que");
		cola.enqueue("tal");
		cola.enqueue("veamos");
		cola.enqueue("si");
		cola.enqueue("funciona");
		cola.enqueue("esto");

		System.out.println(cola.next());
		while (!cola.isEmpty()){
			System.out.println(cola.dequeue());
		}
		
	}

	public MyQueue (){
		this.lista=new ListaEnlazada<>();
	}
	
	public void enqueue (E dato){
		this.lista.insertarFin(dato);
	}
	
	public E dequeue (){
		try{
		return this.lista.borrarInicio();
		} catch(IndexOutOfBoundsException ex){
			throw new NoSuchElementException("No se puede hacer un deque en una cola vacia");
		}
	}
	
	public E next(){
		try{
			return this.lista.inicio();
			} catch(IndexOutOfBoundsException ex){
				throw new NoSuchElementException("No se puede regresar un elemento en una cola vacia");
			}
	}
	
	public int size (){
		return this.lista.size();
	}
	
	public boolean isEmpty(){
		return this.lista.estaVacia();
	}
	
	public void flush(){
		this.lista.flush();
	}
	
	public String toString(){
		return this.lista.toString();
	}
	
}
