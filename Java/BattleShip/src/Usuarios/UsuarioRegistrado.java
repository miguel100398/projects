//Autor: A01633021 Miguel Ángel Bucio Macías
//Clase: UsuarioRegistrado
//Fecha: 6/05/19
//Comentarios

package Usuarios;

public class UsuarioRegistrado extends Usuario{

	private String password;
	
	public UsuarioRegistrado (String nombre, String image, String password, int tiempo, int partidas, int victorias, int derrotas, int disparos, int aciertos, int fallos, int barcosDestruidos, int barcosPerdidos){
		super (nombre, image, tiempo, partidas, victorias, derrotas, disparos, aciertos, fallos, barcosDestruidos, barcosPerdidos);
		this.password = password;
	}
	
	public boolean ContraseñaCorrecta(String password){
        return this.password.equals(password);
    }
	
	public String toString(){
        return String.format("%s,%s,%s,%d,%d,%d,%d,%d,%d,%d,%d,%d", this.name, this.image, this.password,
                this.getTiempo(),this.getPartidas(),this.getVictorias(),this.getDerrotas(),this.getDisparos(),this.getAciertos(),
                this.getFallos(),this.getBarcosDestruidos(),this.getBarcosPerdidos());
    }
	
}
