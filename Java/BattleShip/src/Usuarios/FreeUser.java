//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: FreeUser
//Fecha: 6/05/19
//Comentarios

package Usuarios;

public class FreeUser extends Usuario{

		public FreeUser (int tiempo, int partidas, int victorias, int derrotas, int disparos, int aciertos, int fallos, int barcosDestruidos, int barcosPerdidos){
			super ("Guest", "src\\Usuarios\\Images\\guest.jpg", tiempo, partidas, victorias, derrotas, disparos, aciertos, fallos, barcosDestruidos, barcosPerdidos);
		}
}
