/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 * $Id$ 
 * Universidad de los Andes (Bogotá - Colombia) 
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n6_hojaCalculo
 * Autor: Pablo Barvo - Nov 22, 2005
 * Modificación: Katalina Marcos - 07-Dic-2005
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package uniandes.cupi2.hojaCalculo.mundo;


/**
 * Representa una Celda en la Hoja de Cálculo<br>
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
     * Constante para la serialización
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Cadena que representa un contenido vacío
     */
    private static final String CONTENIDO_VACIO = "";

    /**
     * Cadena que representa un inicio de fórmula
     */
    private static final String INICIO_FORMULA = "=";

    /**
     * Carácter que representa la operación suma
     */
    private static final char SUMA = '+';

    /**
     * Carácter que representa la operación resta
     */
    private static final char RESTA = '-';

    /**
     * Carácter que representa la operación multiplicación
     */
    private static final char MULTIPLICACION = '*';

    /**
     * Carácter que representa la operación división
     */
    private static final char DIVISION = '/';

    /**
     * Carácter que representa que no hay una operación
     */
    private static final char SIN_OPERACION = '0';

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Posición horizontal de la celda
     */
    private int fila;

    /**
     * Posición vertical de la celda
     */
    private int columna;

    /**
     * Contenido de la celda
     */
    private String contenido;

    /**
     * Hoja de cálculo a la que pertenece la celda
     */
    private HojaCalculo hojaCalculo;

    //-----------------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------------

    /**
     * Constructor de una celda con el contenido vacío.
     * @param laFila Número de fila de la celda.
     * @param laColumna Número de columna de la celda.
     * @param laHojaCalculo Hoja de cálculo padre. laFila y laColumna deben ser dimensiones válidas de laHojaCalulo.
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
    // Métodos
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
     * Devuelve el resultado de la celda. Si es una fórmula, la evalúa.
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
     * Indica si la celda contiene una fórmula. La celda es fórmula cuando comienza con INICIO_FORMULA.
     * @return true si la celda es una fórmula, false en caso contrario.
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
     * @throws NumberFormatException Se genera esta excepción si el contenido de la celda no es entero.
     */
    private int darContenidoEntero( )
    {
        return Integer.parseInt( contenido );
    }

    /**
     * Devuelve el número de fila de la celda
     * @return fila
     */
    public int darFila( )
    {
        return fila;
    }

    /**
     * Devuelve el número de columna de la celda
     * @return columna
     */
    public int darColumna( )
    {
        return columna;
    }

    //-----------------------------------------------------------------
    // Métodos de Evaluación de Fórmulas
    //-----------------------------------------------------------------

    /**
     * Evalúa la fórmula en contenido. Si la formula tiene errores, retorna #ERROR.
     * @return resultado de la evaluación. resultado = evaluación de la fórmula o resultado="#ERROR"
     */
    private String evaluarFormula( )
    {
        String resultado;

        try
        { //
            //Saca los operandos y operaciones
            char operacion = darOperacion( );
            //
            //Si la operación no existe
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
        //Si hay error entonces el resultado es inválido
        catch( Exception e )
        {
            resultado = "#ERROR";
        }
        return resultado;
    }

    /**
     * Realiza la operación y devuelve el resultado. <br>
     * @param operando1 Operando 1 de la operación
     * @param operando2 Operando 2 de la operación
     * @param operacion Operación a realizar. operacion pertenece a {SUMA, RESTA, MULTIPLICACION, DIVISION}
     * @return resultado de la operación. resultado = (operando1 (operacion) operando2)
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
     * Devuelve la operación de la fórmula. Si no encuentra una operación retorna SIN_OPERACION
     * @return Operación 1 de la fórmula. Si no hay una retorna SIN_OPERACION
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
     * <b>pre: </b>contenido es una fórmula correcta.
     * @return Operando 1 de la fórmula evaluado.
     * @throws Exception Si el operando es inválido.
     */
    private int darOperando1( ) throws Exception
    {
        char operacion = darOperacion( );
        String operando1 = contenido.trim( ).substring( 1, contenido.indexOf( operacion ) ).trim( );
        return evaluarOperando( operando1 );
    }

    /**
     * Devuelve el Operando 2 evaluado. <br>
     * <b>pre: </b>contenido es una fórmula correcta.
     * @return Operando 2 de la fórmula evaluado.
     * @throws Exception Si el operando es inválido.
     */
    private int darOperando2( ) throws Exception
    {
        char operacion = darOperacion( );
        String operando2 = contenido.substring( contenido.indexOf( operacion ) + 1 ).trim( );
        return evaluarOperando( operando2 );
    }

    /**
     * Evalúa el operando especificado. <br>
     * <b>pre: </b> operando es un operando válido. <br>
     * @param operando Operando a evaluar. operando != null.
     * @return resultado de la evaluación del operando.
     * @throws Exception Si el operando es inválido.
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
                throw new Exception( "Referencia inválida" );
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
        //Quita los paréntesis cuadrados
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
