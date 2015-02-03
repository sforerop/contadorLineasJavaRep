package uniandes.cupi2.hojaCalculo.excepciones;
import javax.swing.JOptionPane;
public class ArchivoException extends Exception {

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

	private String nombreArchivo;
    //-----------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------

	/**
	 * metodo que captura la excepcion para un archivo
	 * @param elArchivo
	 */
	public ArchivoException(String elArchivo){	
		super("error al cargar el archivo :  " + elArchivo);
		nombreArchivo = elArchivo;
		JOptionPane.showMessageDialog(null, "Formato del Archivo Invalido", "Error", JOptionPane.ERROR_MESSAGE );
	}

	/**
	 * metodo que da el nombre del archivo capturado en la excepcion
	 * @return nombreArchivo
	 */
	public String darNombreArchivo(){
		return nombreArchivo;
	}
}
