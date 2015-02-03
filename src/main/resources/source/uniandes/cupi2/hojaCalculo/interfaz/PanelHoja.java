/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 * $Id$ 
 * Universidad de los Andes (Bogotá - Colombia) 
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co) 
 * Ejercicio: n6_hojaCalculo
 * Autor: Pablo Barvo - Nov 25, 2005
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */

package uniandes.cupi2.hojaCalculo.interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.hojaCalculo.mundo.Celda;
import uniandes.cupi2.hojaCalculo.mundo.HojaCalculo;

/**
 * Panel con la hoja de cálculo
 */
public class PanelHoja extends JPanel
{

    //-----------------------------------------------------------------
    // Constantes
    //-----------------------------------------------------------------

    /**
     * Modo Edición
     */
    public static final int EDICION = 0;

    /**
     * Modo Visualización
     */
    public static final int VISUALIZACION = 1;

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Ancho de la visualización actual
     */
    private int ancho;

    /**
     * Alto de la visualización actual
     */
    private int alto;
    
    /**
     * Modo actual de la visualización
     */
    private int modo;

    //-----------------------------------------------------------------
    // Atributos de Interfaz
    //-----------------------------------------------------------------

    /**
     * Campos de texto de las celdas
     */
    private JTextField[][] txtCelda;

    //-----------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------

    /**
     * Crea un nuevo panel
     * @param hojaCalculo Hoja de cálculo para dibujar inicialmente. hojaCalculo != null.
     */
    public PanelHoja( HojaCalculo hojaCalculo )
    {
        alto = 0;
        ancho = 0;
        
        setBorder( new TitledBorder( "Hoja de cálculo" ) );
        //
        //Actualiza el panel
        actualizar( hojaCalculo, EDICION );
    }

    //-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------

    /**
     * Actualiza la visualización de la hoja de cálculo
     * @param hojaCalculo a mostrar. hojaCalculo != null.
     * @param modoVisualizacion Modo de visualización. modo pertenece a {EDICION, VISUALIZACION}
     */
    public void actualizar( HojaCalculo hojaCalculo, int modoVisualizacion )
    {
        modo = modoVisualizacion;
        //
        //Actualiza los elementos visuales si es necesario
        actualizarElementosVisuales( hojaCalculo );
        //
        //Repinta los valores
        for( int i = 0; i < ancho; i++ )
        {
            for( int j = 0; j < alto; j++ )
            {
                Celda celda = hojaCalculo.darCelda( i, j );
                if( modoVisualizacion == EDICION )
                {
                    txtCelda[ i + 1 ][ j + 1 ].setEditable( true );
                    txtCelda[ i + 1 ][ j + 1 ].setText( celda.darContenido( ) );
                }
                else
                {
                    txtCelda[ i + 1 ][ j + 1 ].setEditable( false );
                    txtCelda[ i + 1 ][ j + 1 ].setText( celda.darResultado( ) );
                }

            }
        }
    }

    /**
     * Devuelve el modo actual de la visualización
     * @return El modo actual de la visualización
     */
    public int darModo( )
    {
        return modo;
    }
    
    /**
     * Guarda los valores de las celdas en la Hoja de cálculo
     * @param hojaCalculo Hoja de cálculo a guardar. hojaCalculo != null.
     * @throws Exception La hoja no tiene las mismas dimensiones
     */
    public void guardarEnHoja( HojaCalculo hojaCalculo ) throws Exception
    {
        if( ancho != hojaCalculo.darAncho( ) || alto != hojaCalculo.darAlto( ) )
        {
            throw new Exception( "La hoja no tiene las mismas dimensiones" );
        }
        for( int i = 0; i < ancho; i++ )
        {
            for( int j = 0; j < alto; j++ )
            {
                String contenido = txtCelda[ i + 1 ][ j + 1 ].getText( );
                hojaCalculo.darCelda( i, j ).asignarContenido( contenido );
            }
        }
    }

    /**
     * Actualiza los elementos visuales de la hoja de cálculo
     * @param hojaCalculo a mostrar. hojaCalculo != null.
     */
    private void actualizarElementosVisuales( HojaCalculo hojaCalculo )
    {
        //
        //Solo actualiza cuando las medidas de la hoja cambiaron
        if( ancho != hojaCalculo.darAncho( ) || alto != hojaCalculo.darAlto( ) )
        {
            ancho = hojaCalculo.darAncho( );
            alto = hojaCalculo.darAlto( );
            //
            //Limpia el Panel
            removeAll( );
            //
            //Crea los espacios
            setLayout( new GridLayout( alto + 1, ancho + 1 ) );
            //
            //Crea con + 1 para dejar el espacio de
            txtCelda = new JTextField[ancho + 1][alto + 1];
            //
            //Inicializa con objetos nuevos y agrega al panel
            txtCelda[ 0 ][ 0 ] = crearCampoTitulo( );
            add( txtCelda[ 0 ][ 0 ] );
            //
            //Crea los títulos de Columnas
            for( int i = 1; i < ancho + 1; i++ )
            {
                txtCelda[ i ][ 0 ] = crearCampoTitulo( );
                txtCelda[ i ][ 0 ].setText( hojaCalculo.darNombreColumna( i - 1 ) );
                add( txtCelda[ i ][ 0 ] );
            }

            for( int j = 1; j < alto + 1; j++ )
            {
                //
                //Agrega el titulo de la fila
                txtCelda[ 0 ][ j ] = crearCampoTitulo( );
                txtCelda[ 0 ][ j ].setText( Integer.toString( j ) );
                add( txtCelda[ 0 ][ j ] );

                for( int i = 1; i < ancho + 1; i++ )
                {

                    txtCelda[ i ][ j ] = crearCampoCelda( );
                    txtCelda[ i ][ j ].setText( hojaCalculo.darNombreColumna( i - 1 ) + ( j ) );
                    add( txtCelda[ i ][ j ] );
                }
            }
            revalidate( );
            repaint( );
        }
    }

    /**
     * Crea el campo de texto de una celda
     * @return Crea un nuevo campo de texto para una celda
     */
    private JTextField crearCampoCelda( )
    {
        JTextField resultado = new JTextField( );
        resultado.setColumns( 5 );
        return resultado;
    }

    /**
     * Crea el campo de texto de un título
     * @return Crea el campo de texto de un título
     */
    private JTextField crearCampoTitulo( )
    {
        JTextField resultado = new JTextField( );
        //
        //No es editable ni puede tener foco
        resultado.setEditable( false );
        resultado.setFocusable( false );
        //
        //Color Gris
        resultado.setBackground( Color.LIGHT_GRAY );
        //
        //Tamaño 5
        resultado.setColumns( 5 );
        //
        //Texto centrado
        resultado.setHorizontalAlignment( JTextField.CENTER );
        //
        //Texto en Negrilla
        resultado.setFont( resultado.getFont( ).deriveFont( Font.BOLD ) );
        return resultado;
    }

}
