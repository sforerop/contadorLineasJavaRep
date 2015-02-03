package uniandes.cupi2.hojaCalculo.excepciones;

public class ColumnaNoOrdenadaException extends Exception{

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

	private int numColumna;

    //-----------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------
	/**
	 * captura la excepcion
	 * @param laColumna
	 */
	public ColumnaNoOrdenadaException(int laColumna){		
		super("La columna "+laColumna+" no se encuentra ordenada");
		numColumna = laColumna;

	}
	/**
	 * metodo devuelve el numero de columa capturada en la excepcion
	 * @return numero de la columna
	 */
	public int darNumColumna(){
		return numColumna;
	}
}
