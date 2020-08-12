package HASH;

//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: MyHashTable
//Fecha: 6/05/19
//Comentarios: 

import java.util.NoSuchElementException;

import ListaEnlazada.ListaEnlazada;

public class MyHashTable <K, V>{
	
	private ListaEnlazada <MyNodoHash<K,V>>[] tabla;
	private int size;
	private static final double LOAD_FACTOR=0.75;
	

	public MyHashTable(){
		this.tabla=(ListaEnlazada<MyNodoHash<K,V>>[])new ListaEnlazada[11];
		for (int i =0; i<this.tabla.length; i++){
			this.tabla[i]=new ListaEnlazada<>();
		}
		this.size = 0;
	}
	
	public MyHashTable(int size){
		this.tabla=(ListaEnlazada<MyNodoHash<K,V>>[])new ListaEnlazada[size];
		for (int i =0; i<this.tabla.length; i++){
			this.tabla[i]=new ListaEnlazada<>();
		}
		this.size = 0;
	}
	
	public int size(){
		return this.size;
	}
	
	public void put(K llave, V valor){
		//Si la llave ya existe se sobreescribe el valor
		int index=Math.abs(llave.hashCode())%this.tabla.length;
		if (this.containsKey(llave)){
			for (int i=0; i<this.tabla[index].size();i++){
				if (this.tabla[index].getEn(i).llave.equals(llave)){
					this.tabla[index].getEn(i).setValor(valor);
				}
			}
		}else{
		this.tabla[index].insertarFin(new MyNodoHash<K,V>(llave, valor));
		this.size++;
		if (((double)this.size/this.tabla.length)> MyHashTable.LOAD_FACTOR){
			rehashing();
		}
		}
	}
	
	public void rehashing(){
		//Genera una nueva tabla de tamaño 2n+1 y guarda los elementos de la tabla anterior en la nueva tabla
		//La tabla grande será la nueva tabla
		//MUY IMPORTANTE reacomodar los elementos
		 MyHashTable<K, V> tmptable = new MyHashTable<K, V>((2*this.tabla.length)+1);
		 for (int i=0; i<this.tabla.length;i++){
			 for (int j=0; j<this.tabla[i].size();j++){
				tmptable.put(this.tabla[i].getEn(j).llave, this.tabla[i].getEn(j).valor);
			 }
		 }
		 //System.out.println("Rehash table");
		 this.tabla = tmptable.tabla;
		 this.size = tmptable.size;
	}
	

	public V get(K llave){
		int index=Math.abs(llave.hashCode())%this.tabla.length;
		for (int i = 0; i<this.tabla[index].size(); i++){
			if (this.tabla[index].getEn(i).llave.equals(llave)){
				return this.tabla[index].getEn(i).valor;
			}
		}
		throw new NoSuchElementException("No se encuentra el valor en la tabla"); 
	}
	
	
	public V delete(K llave){
		int index=Math.abs(llave.hashCode())%this.tabla.length;
		for (int i = 0; i<this.tabla[index].size(); i++){
			if(this.tabla[index].getEn(i).llave.equals(llave)){
				this.size--;
				return this.tabla[index].borrarEn(i).getValor();
			}
		}
		throw new NoSuchElementException("No se encuentra el valor en la tabla");
	}
	
	public boolean containsKey(K llave){
		//Regresa true si la tabla tiene la llave que se pasó como parametro
		int index=Math.abs(llave.hashCode())%this.tabla.length;
		for (int i=0; i<this.tabla[index].size();i++){
			if (this.tabla[index].getEn(i).llave.equals(llave)){
				return true;
			}
		}
		return false;
	}
	
	public void clear(){
		for (int i =0; i<this.tabla.length; i++){
			this.tabla[i].flush();
		}
		this.size=0;
		System.gc();
	}
	
	public String toString(){
		String imprimir="***************************************\n";
		for (int i =0; i<this.tabla.length; i++){
			imprimir += tabla[i].toString() + "("+i+")\n";
		}
	    imprimir+="***************************************\n";
		return imprimir;
	}
	
	
}

class MyNodoHash<K,V>{
	K llave;
	V valor;
	public MyNodoHash(K llave, V valor) {
		super();
		this.llave = llave;
		this.valor = valor;
	}
	public V getValor() {
		return valor;
	}
	public void setValor(V valor) {
		this.valor = valor;
	}
	public K getLlave() {
		return llave;
	}
	
	public String toString(){
		return (this.valor + "["+this.llave+"]");

	}
	
}
