/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 * $Id$ 
 * Universidad de los Andes (Bogot� - Colombia) 
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n6_hojaCalculo
 * Autor: Pablo Barvo - Nov 22, 2005
 * Modificaci�n: Katalina Marcos - 07-Dic-2005
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package uniandes.cupi2.hojaCalculo.mundo;


/**
 * Representa una Celda en la Hoja de C�lculo<br>
 * <b>inv:</b><br>
 * fila != 0 <br>
 * columna != 0 <br>
 * contenido != null <br>
 * !contenido.equals("")
 */
public class Celda
{
    //-----------------------------------------------------------------
    // Constantes
    //-----------------------------------------------------------------
    
    /**
     * Constante para la serializaci�n
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Cadena que representa un contenido vac�o
     */
    private static final String CONTENIDO_VACIO = "";

    /**
     * Cadena que representa un inicio de f�rmula
     */
    private static final String INICIO_FORMULA = "=";

    /**
     * Car�cter que representa la operaci�n suma
     */
    private static final char SUMA = '+';

    /**
     * Car�cter que representa la operaci�n resta
     */
    private static final char RESTA = '-';

    /**
     * Car�cter que representa la operaci�n multiplicaci�n
     */
    private static final char MULTIPLICACION = '*';

    /**
     * Car�cter que representa la operaci�n divisi�n
     */
    private static final char DIVISION = '/';

    /**
     * Car�cter que representa que no hay una operaci�n
     */
    private static final char SIN_OPERACION = '0';

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Posici�n horizontal de la celda
     */
    private int fila;

    /**
     * Posici�n vertical de la celda
     */
    private int columna;

    /**
     * Contenido de la celda
     */
    private String contenido;

    /**
     * Hoja de c�lculo a la que pertenece la celda
     */
    private HojaCalculo hojaCalculo;

    //-----------------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------------

    /**
     * Constructor de una celda con el contenido vac�o.
     * @param laFila N�mero de fila de la celda.
     * @param laColumna N�mero de columna de la celda.
     * @param laHojaCalculo Hoja de c�lculo padre. laFila y laColumna deben ser dimensiones v�lidas de laHojaCalulo.
     */
    public Celda( int laFila, int laColumna, HojaCalculo laHojaCalculo )
    {
        contenido = CONTENIDO_VACIO;
        fila = laFila;
        columna = laColumna;
        hojaCalculo = laHojaCalculo;
        
        verificarInvariante();
    }

    //-----------------------------------------------------------------
    // M�todos
    //-----------------------------------------------------------------

    /**
     * Devuelve el contenido de la celda sin evaluar
     * @return Contenido sin evaluar
     */
    public String darContenido( )
    {
        return contenido;
    }

    /**
     * Establece el contenido sin evaluar. <br>
     * <b>post: </b> si elContenido es null, la celda queda con CONTENIDO_VACIO.
     * @param elContenido Nuevo contenido.
     */
    public void asignarContenido( String elContenido )
    {
        if( elContenido == null )
            contenido = CONTENIDO_VACIO;
        else
            contenido = elContenido;
    }

    /**
     * Devuelve el resultado de la celda. Si es una f�rmula, la eval�a.
     * @return Contenido evaluado
     */
    public String darResultado( )
    {
        String resultado;
        if( esFormula( ) )
        {
            resultado = evaluarFormula( );
        }
        else
        {
            resultado = contenido;
        }
        return resultado;
    }

    /**
     * Indica si la celda contiene una f�rmula. La celda es f�rmula cuando comienza con INICIO_FORMULA.
     * @return true si la celda es una f�rmula, false en caso contrario.
     */
    public boolean esFormula( )
    {
        return contenido.trim( ).startsWith( INICIO_FORMULA );
    }

    /**
     * Indica si el contenido de la celda es un entero
     * @return true si el contenido es un entero, false en caso contrario.
     */
    public boolean esEntero( )
    {
        boolean resultado;
        try
        {
            darContenidoEntero( );
            resultado = true;
        }
        catch( NumberFormatException e )
        {
            resultado = false;
        }
        return resultado;
    }

    /**
     * Devuelve el contenido de la celda en valor entero. <br>
     * <b>pre: </b> El contenido de la celda es entero.
     * @return Valor entero del contenido de la celda.
     * @throws NumberFormatException Se genera esta excepci�n si el contenido de la celda no es entero.
     */
    private int darContenidoEntero( )
    {
        return Integer.parseInt( contenido );
    }

    /**
     * Devuelve el n�mero de fila de la celda
     * @return fila
     */
    public int darFila( )
    {
        return fila;
    }

    /**
     * Devuelve el n�mero de columna de la celda
     * @return columna
     */
    public int darColumna( )
    {
        return columna;
    }

    //-----------------------------------------------------------------
    // M�todos de Evaluaci�n de F�rmulas
    //-----------------------------------------------------------------

    /**
     * Eval�a la f�rmula en contenido. Si la formula tiene errores, retorna #ERROR.
     * @return resultado de la evaluaci�n. resultado = evaluaci�n de la f�rmula o resultado="#ERROR"
     */
    private String evaluarFormula( )
    {
        String resultado;

        try
        { //
            //Saca los operandos y operaciones
            char operacion = darOperacion( );
            //
            //Si la operaci�n no existe
            if( operacion == SIN_OPERACION )
            {
                resultado = "#ERROR";
            }
            else
            {
                //
                //Saca los operandos
                int operando1 = darOperando1( );
                int operando2 = darOperando2( );
                resultado = Integer.toString( realizarOperacion( operando1, operando2, operacion ) );
            }
        }
        //
        //Si hay error entonces el resultado es inv�lido
        catch( Exception e )
        {
            resultado = "#ERROR";
        }
        return resultado;
    }

    /**
     * Realiza la operaci�n y devuelve el resultado. <br>
     * @param operando1 Operando 1 de la operaci�n
     * @param operando2 Operando 2 de la operaci�n
     * @param operacion Operaci�n a realizar. operacion pertenece a {SUMA, RESTA, MULTIPLICACION, DIVISION}
     * @return resultado de la operaci�n. resultado = (operando1 (operacion) operando2)
     */
    private int realizarOperacion( int operando1, int operando2, char operacion )
    {
        if( DIVISION == operacion )
        {
            return ( operando1 / operando2 );
        }
        else if( RESTA == operacion )
        {
            return ( operando1 - operando2 );
        }
        else if( MULTIPLICACION == operacion )
        {
            return ( operando1 * operando2 );
        }
        else
        {
            return ( operando1 + operando2 );
        }
    }

    /**
     * Devuelve la operaci�n de la f�rmula. Si no encuentra una operaci�n retorna SIN_OPERACION
     * @return Operaci�n 1 de la f�rmula. Si no hay una retorna SIN_OPERACION
     */
    private char darOperacion( )
    {
        char resultado = SIN_OPERACION;
        int pos = contenido.indexOf( SUMA );
        if( pos < 0 )
        {
            pos = contenido.indexOf( RESTA );
        }
        if( pos < 0 )
        {
            pos = contenido.indexOf( MULTIPLICACION );
        }
        if( pos < 0 )
        {
            pos = contenido.indexOf( DIVISION );
        }
        if( pos >= 0 )
        {
            resultado = contenido.charAt( pos );
        }
        return resultado;
    }
    /**
     * Devuelve el Operando 1 evaluado. <br>
     * <b>pre: </b>contenido es una f�rmula correcta.
     * @return Operando 1 de la f�rmula evaluado.
     * @throws Exception Si el operando es inv�lido.
     */
    private int darOperando1( ) throws Exception
    {
        char operacion = darOperacion( );
        String operando1 = contenido.trim( ).substring( 1, contenido.indexOf( operacion ) ).trim( );
        return evaluarOperando( operando1 );
    }

    /**
     * Devuelve el Operando 2 evaluado. <br>
     * <b>pre: </b>contenido es una f�rmula correcta.
     * @return Operando 2 de la f�rmula evaluado.
     * @throws Exception Si el operando es inv�lido.
     */
    private int darOperando2( ) throws Exception
    {
        char operacion = darOperacion( );
        String operando2 = contenido.substring( contenido.indexOf( operacion ) + 1 ).trim( );
        return evaluarOperando( operando2 );
    }

    /**
     * Eval�a el operando especificado. <br>
     * <b>pre: </b> operando es un operando v�lido. <br>
     * @param operando Operando a evaluar. operando != null.
     * @return resultado de la evaluaci�n del operando.
     * @throws Exception Si el operando es inv�lido.
     */
    private int evaluarOperando( String operando ) throws Exception
    {
        int resultado;
        //
        //Si no es una celda
        if( !operando.trim( ).startsWith( "[" ) )
        {

            resultado = Integer.parseInt( operando );
        }
        else
        {
            //
            //Saca la celda del operando
            Celda celda = darCeldaReferenciada( operando );
            if( celda == null )
            {
                throw new Exception( "Referencia inv�lida" );
            }
            else
            {
                if( !celda.esEntero( ) )
                {
                    throw new Exception( "La celda no tiene un valor entero" );
                }
                resultado = celda.darContenidoEntero( );
            }
        }
        return resultado;
    }

    /**
     * Busca la celda de la referencia dada. Devuelve null si hay un error en la referencia.
     * @param referencia Referencia con formato [COL!FIL] (ejemplo [A!12])
     * @return celda correspondiente a la referencia. Si la referencia es incorrecta retorna null.
     */
    private Celda darCeldaReferenciada( String referencia )
    {
        Celda resultado = null;
        //
        //Quita los par�ntesis cuadrados
        referencia = referencia.substring( 1, referencia.length( ) - 1 );
        //
        //Saca los valores
        String[] valores = referencia.split( "!" );
        if( valores.length == 2 )
        {
            int col = hojaCalculo.darValorColumna( valores[ 0 ] );
            int fil = Integer.parseInt( valores[ 1 ] ) - 1;
            resultado = hojaCalculo.darCelda( col, fil );
        }
        return resultado;
    }
    
    //-----------------------------------------------------------------
    // Invariante
    //-----------------------------------------------------------------
    
   private void verificarInvariante(){
	   	assert fila != 0 : "fila es cero";
	   	assert columna != 0 : "columna es cero"; 
	   	assert contenido != null : "contenido es null";
	   	assert !contenido.equals("") : "contenido es vacio";
   }
}
