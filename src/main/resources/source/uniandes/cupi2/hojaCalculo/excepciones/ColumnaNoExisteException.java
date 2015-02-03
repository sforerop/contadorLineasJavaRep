package uniandes.cupi2.hojaCalculo.excepciones;

public class ColumnaNoExisteException extends Exception{

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

	private int numColumna;

    //-----------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------

	/**
	 *  metodo que captura la excepcion
	 * @param noColumna
	 */
	public ColumnaNoExisteException(int noColumna){
		super("La columna " + noColumna +" no existe");
		numColumna = noColumna;
	}
	/**
	 * da el nuero de la columna capturada en la excepcion
	 * @return numColumna
	 */
	public int darNumColumna(){
		return numColumna;
	}
}
