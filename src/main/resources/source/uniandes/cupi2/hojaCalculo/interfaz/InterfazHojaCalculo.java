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

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import uniandes.cupi2.hojaCalculo.excepciones.ArchivoException;
import uniandes.cupi2.hojaCalculo.excepciones.ColumnaNoExisteException;
import uniandes.cupi2.hojaCalculo.excepciones.ColumnaNoOrdenadaException;
import uniandes.cupi2.hojaCalculo.mundo.HojaCalculo;

/**
 * Ventana principal de la aplicación.
 */
public class InterfazHojaCalculo extends JFrame
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para la serialización
     */
    private static final long serialVersionUID = 1L;

    /**
     * Ancho por defecto
     */
    private static final int ANCHO = 10;

    /**
     * Alto por defecto
     */
    private static final int ALTO = 12;

    /**
     * Ruta al archivo donde se guarda la información que se serializa automáticamente
     */
    private final static String DATA = "./data/ultimaHojaCalculo.data";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo
     */
    private HojaCalculo hojaCalculo;

    // -----------------------------------------------------------------
    // Atributos de la interfaz
    // -----------------------------------------------------------------

    /**
     * Panel Imagen encabezado
     */
    private PanelImagen panelImagen;

    /**
     * panel con la hoja de cálculo
     */
    private PanelHoja panelHoja;

    /**
     * Panel con los botones
     */
    private PanelBotones panelBotones;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea la ventana principal de la hoja de cálculo
     */
    public InterfazHojaCalculo( )
    {
        // Crea la clase principal
        File archivo = new File( DATA );
        if( !archivo.exists( ) )
        {
            //
            // Es la primera vez que se carga la aplicación. Se cargan los valores por defecto
            hojaCalculo = new HojaCalculo( ANCHO, ALTO );
        }
        else
        {
        	try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
				 hojaCalculo = (HojaCalculo) ois.readObject();
				 ois.close();
			} catch (Exception e) {
				System.out.println("error al cargar el archivo");
				 hojaCalculo = new HojaCalculo( ANCHO, ALTO );
			}
        	
            // Hay información de la última vez que se cerró la hoja de cálculo

            //
            // TODO: Inicializar la hoja de cálculo teniendo en cuenta que puede haber serializado la información de la última hoja de cálculo que se guardó
        }

        // organizar el panel principal
        getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 530, 530 );
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setTitle( "Hoja de Cálculo" );

        // Panel con la imagen
        panelImagen = new PanelImagen( );
        getContentPane( ).add( panelImagen, BorderLayout.NORTH );

        // Panel Hoja de cálculo
        panelHoja = new PanelHoja( this.hojaCalculo );
        getContentPane( ).add( panelHoja, BorderLayout.CENTER );

        // Panel con los botones
        panelBotones = new PanelBotones( this );
        getContentPane( ).add( panelBotones, BorderLayout.SOUTH );

        pack( );

        modoEdicion( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------s

    /**
     * Crea una nueva hoja de cálculo solicitando las dimensiones y dejando las celdas vacías.
     */
    public void nueva( )
    {
        //
        // Crea la hoja de cálculo
        hojaCalculo = new HojaCalculo( ANCHO, ALTO );
        //
        // Va a modo de edición
        modoEdicion( );
    }

    /**
     * Permite al usuario seleccionar el archivo a cargar y carga una nueva hoja de cálculo. Por último, pasa a modo edición.
     */
    public void cargar( )
    {
    	
    	System.out.println("ENTRO A CARGAR");
    	File archivo = null;
    	JFileChooser fc = new JFileChooser("C:/Users/Usuario/workspace/n6_hojaCalculo/data");
    	fc.setDialogTitle("Abrir XML Hoja de calclo");;
    	int resultado  = fc.showOpenDialog(this);
    	if(resultado == JFileChooser.APPROVE_OPTION){
    		archivo = fc.getSelectedFile();
    		try {
				hojaCalculo.cargarArchivoXML(archivo);
			} catch (ArchivoException e) {
				JOptionPane.showMessageDialog(this, "Error al cargar el archivo", "Error", JOptionPane.ERROR_MESSAGE );
			}
    	}
    	modoEdicion( );
       
    }

    /**
     * Guarda una hoja de cálculo en un archivo XML
     */
    public void guardar( )
    {    	
    	try {
    		panelHoja.guardarEnHoja(hojaCalculo);
        	File archivo = new File("./data/");
			hojaCalculo.guardarArchivoXML(archivo);
			JOptionPane.showMessageDialog( this, "Se guardo la Hoja en la ruta " +archivo.getAbsolutePath(),"Info",JOptionPane.INFORMATION_MESSAGE );
		} catch (Exception e ) {
			JOptionPane.showMessageDialog(this, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE );
		}
    }

    /**
     * Muestra el dialogo para escoger la columna y el texto que se quiere buscar
     */
    public void mostrarBuscarEnColumna( )
    {
        DialogoBuscarEnColumna dialogo = new DialogoBuscarEnColumna( this );
        dialogo.setVisible( true );
    }

    /**
     * Muestra el dialogo para escoger la columna que se quiere ordenar.
     */
    public void mostrarOrdenarColumna( )
    {
    	DialogoOrdenarColumna dialog = new DialogoOrdenarColumna(this);
    	dialog.setVisible(true);

    }

    /**
     * Ordena la columna que entra como parámetro
     * @param valorColumna Valor de la columna que va a ordenar
     */
    public void ordenarColumna( int valorColumna ) throws ColumnaNoExisteException
    {
    	
        hojaCalculo.ordenarColumna( valorColumna );
        modoEdicion( );
        JOptionPane.showMessageDialog( this, "Se ordeno la columna de la Hoja","Info",JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Busca un valor en la columna que entra como parámetro
     * @param valorColumna Valor de la columna
     * @param valorColumna Valor que se quiere buscar
     * @return True si el valor existe en la columna correspondiente, false de lo contrario.
     * @throws ColumnaNoOrdenadaException En caso en el que la columna no se encuentre ordenada
     */
    public boolean buscarEnColumna( String valorColumna, String valorBuscar, int indice ) throws ColumnaNoOrdenadaException
    {
        return hojaCalculo.buscarEnColumna( indice, valorBuscar );
    }

    /**
     * Devuelve la hoja de cálculo
     * @return La hoja de cálculo que se está editando
     */
    public HojaCalculo darHojaCalculo( )
    {
        return hojaCalculo;
    }

    /**
     * Método que se llama al cerrar el JFrame
     */
    public void dispose( )
    {
        try
        {
            serializar( );
        }
        catch( ArchivoException e )
        {
            JOptionPane.showMessageDialog( this, "Archivo: " + e.darNombreArchivo() + " || Mensaje: " + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
        super.dispose( );
    }

    /**
     * Serializa la hoja de cálculo en el archivo cuyo nombre se encuentra en la constante DATA
     */
    private void serializar( ) throws ArchivoException
    {    	
    	try {
    		
    		ObjectOutputStream oos = new  ObjectOutputStream(new FileOutputStream(DATA));
    		oos.writeObject(hojaCalculo);
    		oos.close();
			
		} catch (IOException e) {
			throw new ArchivoException(e.getMessage());
		}
    }

    /**
     * Pone la hoja de cálculo en modo edición
     */
    public void modoEdicion( )
    {
        //
        // Repinta la hoja en modo edición
        panelHoja.actualizar( hojaCalculo, PanelHoja.EDICION );
        panelBotones.modoEdicion( );
        validate( );
    }

    /**
     * Pone la hoja de cálculo en modo visualización
     */
    public void modoVisualizacion( )
    {
        try
        {
            //
            // Guarda los valores en la hoja
            panelHoja.guardarEnHoja( hojaCalculo );
            //
            // Repinta la hoja en modo edición
            panelHoja.actualizar( hojaCalculo, PanelHoja.VISUALIZACION );
            panelBotones.modoVisualizacion( );
            validate( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    // -----------------------------------------------------------------
    // Puntos de Extensión
    // -----------------------------------------------------------------

    /**
     * Método para la extensión 1
     */
    public void reqFuncOpcion1( )
    {
        String resultado = hojaCalculo.metodo1( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Método para la extensión 2
     */
    public void reqFuncOpcion2( )
    {
        String resultado = hojaCalculo.metodo2( );
        JOptionPane.showMessageDialog( this, resultado, "Respuesta", JOptionPane.INFORMATION_MESSAGE );
    }

    // -----------------------------------------------------------------
    // Ejecución
    // -----------------------------------------------------------------

    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args
     */
    public static void main( String[] args )
    {
        InterfazHojaCalculo interfaz = new InterfazHojaCalculo( );
        interfaz.setVisible( true );
    }
}