/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n6_hojaCalculo
 * Autor: Pablo Barvo - 22-Nov-2005
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.hojaCalculo.interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Panel de manejo de botones
 */
public class PanelBotones extends JPanel implements ActionListener
{

    //-----------------------------------------------------------------
    // Constantes
    //-----------------------------------------------------------------

    /**
     * Constante para la serialización
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Comando Opción 1
     */
    private static final String OPCION_1 = "OPCION_1";

    /**
     * Comando Opción 2
     */
    private static final String OPCION_2 = "OPCION_2";

    /**
     * Comando Guardar
     */
    private static final String GUARDAR = "GUARDAR";
    
    /**
     * Comando Buscar
     */
    private static final String BUSCAR = "BUSCAR";
    
    /**
     * Comando Ordenar
     */
    private static final String ORDENAR = "ORDENAR";

    /**
     * Comando Cargar
     */
    private static final String CARGAR = "CARGAR";

    /**
     * Comando Nueva Hoja
     */
    private static final String NUEVA = "NUEVA";

    /**
     * Comando Cambiar Modo a edición
     */
    private static final String MODO_EDICION = "MODO_EDICION";

    /**
     * Comando Cambiar Modo visualización
     */
    private static final String MODO_VISUALIZACION = "MODO_VISUALIZACION";

    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Ventana principal de la aplicación
     */
    private InterfazHojaCalculo ventanaPrincipal;

    //-----------------------------------------------------------------
    // Atributos de interfaz
    //-----------------------------------------------------------------

    /**
     * Botón Opción 1
     */
    private JButton btnOpcion1;

    /**
     * Botón Opción 2
     */
    private JButton btnOpcion2;

    /**
     * Botón Cargar
     */
    private JButton btnOrdenar;
    
    /**
     * Botón Cargar
     */
    private JButton btnBuscar;
    
    /**
     * Botón Cargar
     */
    private JButton btnCargar;

    /**
     * Botón Guardar
     */
    private JButton btnGuardar;

    /**
     * Botón Nueva
     */
    private JButton btnNueva;

    /**
     * Botón modo edición
     */
    private JButton btnEdicion;

    /**
     * Botón modo visualización
     */
    private JButton btnVisualizacion;

    //-----------------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------------

    /**
     * Constructor del panel
     * @param ventana Ventana principal. ventana != null.
     */
    public PanelBotones( InterfazHojaCalculo ventana )
    {
        ventanaPrincipal = ventana;

        setLayout( new GridLayout( 2, 1 ) );

        JPanel panelArriba = new JPanel( );
        panelArriba.setLayout( new GridLayout( 1, 2 ) );
        panelArriba.setBorder( new TitledBorder( "Modos" ) );

        //Botón Edición
        btnEdicion = new JButton( "Ir a Edición" );
        btnEdicion.setActionCommand( MODO_EDICION );
        btnEdicion.addActionListener( this );
        btnEdicion.setEnabled( false );
        panelArriba.add( btnEdicion );

        //Botón Visualización
        btnVisualizacion = new JButton( "Ir a Visualización" );
        btnVisualizacion.setActionCommand( MODO_VISUALIZACION );
        btnVisualizacion.addActionListener( this );
        panelArriba.add( btnVisualizacion );

        add( panelArriba );

        JPanel panelAbajo = new JPanel( );
        panelAbajo.setLayout( new GridLayout( 1, 5 ) );
        panelAbajo.setBorder( new TitledBorder( "Opciones" ) );

        //Botón Nueva Hoja
        btnNueva = new JButton( "Nueva" );
        btnNueva.setActionCommand( NUEVA );
        btnNueva.addActionListener( this );
        panelAbajo.add( btnNueva );

        //Botón cargar
        btnCargar = new JButton( "Cargar XML" );
        btnCargar.setActionCommand( CARGAR );
        btnCargar.addActionListener( this );
        panelAbajo.add( btnCargar );

        //Botón guardar
        btnGuardar = new JButton( "Guardar XML" );
        btnGuardar.setActionCommand( GUARDAR );
        btnGuardar.addActionListener( this );
        panelAbajo.add( btnGuardar );
        
        //Botón Buscar
        btnBuscar = new JButton( "Buscar" );
        btnBuscar.setActionCommand( BUSCAR );
        btnBuscar.addActionListener( this );
        panelAbajo.add( btnBuscar );

        //Botón Buscar
        btnOrdenar = new JButton( "Ordenar" );
        btnOrdenar.setActionCommand( ORDENAR );
        btnOrdenar.addActionListener( this );
        panelAbajo.add( btnOrdenar );
        
        //Botón opción 1
        btnOpcion1 = new JButton( "Opción 1" );
        btnOpcion1.setActionCommand( OPCION_1 );
        btnOpcion1.addActionListener( this );
        panelAbajo.add( btnOpcion1 );

        //Botón opción 2
        btnOpcion2 = new JButton( "Opción 2" );
        btnOpcion2.setActionCommand( OPCION_2 );
        btnOpcion2.addActionListener( this );
        panelAbajo.add( btnOpcion2 );

        add( panelAbajo );
    }

    //-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------

    /**
     * Manejo de los eventos de los botones
     * @param e Acción que generó el evento.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( MODO_EDICION.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.modoEdicion( );
        }
        else if( MODO_VISUALIZACION.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.modoVisualizacion( );
        }
        else if( NUEVA.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.nueva( );
        }
        else if( CARGAR.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.cargar( );
        }
        else if( GUARDAR.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.guardar( );
        }
        else if( BUSCAR.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.mostrarBuscarEnColumna( );
        }
        else if( ORDENAR.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.mostrarOrdenarColumna( );
        }
        else if( OPCION_1.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.reqFuncOpcion1( );
        }
        else if( OPCION_2.equals( e.getActionCommand( ) ) )
        {
            ventanaPrincipal.reqFuncOpcion2( );
        }
    }

    /**
     * Cambia los botones a modo edición
     */
    public void modoEdicion( )
    {
        btnEdicion.setEnabled( false );
        btnBuscar.setEnabled( false );
        btnOrdenar.setEnabled( false );
        btnVisualizacion.setEnabled( true );
    }

    /**
     * Cambia los botones a modo visualización
     */
    public void modoVisualizacion( )
    {
        btnEdicion.setEnabled( true );
        btnBuscar.setEnabled( true );
        btnOrdenar.setEnabled( true );
        btnVisualizacion.setEnabled( false );
    }
}
