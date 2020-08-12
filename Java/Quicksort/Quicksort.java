//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: Quicksort
//Fecha: 10/02/19
//Comentarios

public class Quicksort {

	public static void main(String[] args) {
		Integer arreglo [] = {3,8,2,5,1,4,7,6};
		Quicksort.imprimeArreglo(arreglo);
		Quicksort.quicksort(arreglo);
		Quicksort.imprimeArreglo(arreglo);
		System.out.println();
		Integer[] arreglo2 = {90,60,50,70,80,4};
		Quicksort.imprimeArreglo(arreglo2);
		Quicksort.quicksort(arreglo2);
		Quicksort.imprimeArreglo(arreglo2);
		System.out.println();
		String [] palabras = {"Pablo", "Miguel", "David", "Luis", "Raul"};
		Quicksort.imprimeArreglo(palabras);
		Quicksort.quicksort(palabras);
		Quicksort.imprimeArreglo(palabras);

	}
	
	public static <E extends Comparable<E>> void quicksort (E[] datos){
		quicksort(datos, 0, datos.length-1);
	}
	
	private static <E extends Comparable<E>> void quicksort(E[] datos, int min, int max){
		int p;
		if (min<max){
			p = particionar(datos,min,max);
			quicksort (datos, min, p-1);
			quicksort (datos, p+1, max);
		}
	}
	
	private static <E extends Comparable <E>> int particionar(E[] datos, int min, int max){
		int i = min;
		E tmp;
		E p = datos [i++];
		for (int j=i; j<=max; j++){
			if (datos[j].compareTo(p)<0){
				tmp = datos[i];
				datos[i++] = datos[j];
				datos[j] = tmp;
			}
		}
		datos[min] = datos[i-1];
		datos[i-1] = p;
		return (i-1);
	}
	
	private static <E extends Comparable <E>>void imprimeArreglo(E [] arreglo){
		for (int i=0; i<arreglo.length;i++){
			System.out.print(arreglo[i]+",");
		}
		System.out.println();
	}

}
