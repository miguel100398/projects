//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Bubblesort
//Fecha: 30/01/19
//Comentarios

public class Bubblesort {

	
	public static void main(String[] args) {
		Integer[] arreglo = {90,60,50,70,80,4};
		imprimeArreglo(arreglo);
		Bubblesort.bubbleSort(arreglo);
		imprimeArreglo(arreglo);
		String[] arregloString = {"Diego","Cesar","Aristoteles","Luis","Jose","Hector"};
		imprimeArreglo(arregloString);
		Bubblesort.bubbleSort(arregloString);
		imprimeArreglo(arregloString);
	}
	
	public static <E extends Comparable<E>> void bubbleSort(E[] datos){
		for(int j=0; j<datos.length-1;j++){
			for (int i = 0; i<datos.length-1-j;i++){
				if (datos[i].compareTo(datos[i+1])>0){
					swap(datos, i, i+1);
				}
			}
		}
	}
	
	private static <E> void swap (E[] datos, int i, int j){
		E tmp;
		tmp = datos[i];
		datos [i] = datos [j];
		datos [j] = tmp;
	}
	
	public static <E> void imprimeArreglo (E[] arreglo){
		for (int i=0; i<arreglo.length;i++){
			System.out.print(arreglo[i]+",");
		}
		System.out.println();
	}
	
	public static void bubbleSort(int [] datos){
		for(int j=0;j<datos.length-1;j++) {
			for (int i=0; i<datos.length-1-j;i++){
				if (datos[i]>datos[i+1]){
					swap(datos, i, i+1);
				}
			}
		}
	}
	
	private static void swap (int[] datos, int i, int j){
		int tmp;
		tmp = datos[i];
		datos [i] = datos[j];
		datos[j] = tmp;
	}

	public static void imprimeArreglo(int [] arreglo){
		for (int i=0; i<arreglo.length;i++){
			System.out.print(arreglo[i]+",");
		}
		System.out.println();
	}
	
}
