/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: DialogoOrdenarColumna.java,v 1.2 2008/03/25 23:22:26 carl-veg Exp $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n6_hojaCalculo
 * Autor: Camilo Jiménez - 15-nov-2009
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.hojaCalculo.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.hojaCalculo.excepciones.ColumnaNoOrdenadaException;

/**
 * Dialogo para escoger una columna para ordenar
 */
public class DialogoBuscarEnColumna extends JDialog implements ActionListener
{
    // -----------------------------------------------
    // Constantes
    // -----------------------------------------------

    /**
     * Constante para la serialización
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante aceptar
     */
    private final static String ACEPTAR = "Aceptar";

    /**
     * Constante cancelar
     */
    private final static String CANCELAR = "Cancelar";

    // -----------------------------------------------
    // Atributos
    // -----------------------------------------------

    /**
     * Etiqueta nombre
     */
    private JLabel labelNombreColumna;

    /**
     * Etiqueta para el valor que se quiere buscar
     */
    private JLabel labelBuscar;

    /**
     * Cuadro de texto con el valor que se quiere buscar
     */
    private JTextField textBuscar;

    /**
     * Combo Box donde están las columnas.
     */
    private JComboBox comboColumnas;

    /**
     * Botón para aceptar y escoger una columna
     */
    private JButton btnAceptar;

    /**
     * Botón para cancelar el ordenamiento
     */
    private JButton btnCancelar;

    /**
     * Ventana principal de la aplicación
     */
    private InterfazHojaCalculo principal;

    // -----------------------------------------------
    // Métodos
    // -----------------------------------------------

    /**
     * Crea el dialogo para ingresar una categoría
     * @param ventana Ventana principal de la aplicación
     */
    public DialogoBuscarEnColumna( InterfazHojaCalculo ventana )
    {
        super( ventana, true );

        principal = ventana;
        setTitle( "Escoger columna y valor a buscar" );
        setLayout( new java.awt.BorderLayout( ) );

        JPanel panelInfo = new JPanel( );
        panelInfo.setPreferredSize( new Dimension( 400, 100 ) );

        panelInfo.setBorder( new TitledBorder( "Columna" ) );
        GridLayout layout = new GridLayout( 2, 2, -175, 30 );
        layout.setVgap( 10 );
        panelInfo.setLayout( layout );

        labelNombreColumna = new JLabel( "Columna: " );
        panelInfo.add( labelNombreColumna );

        comboColumnas = new JComboBox( );
        actualizarComboColumnas( );

        panelInfo.add( comboColumnas );

        labelBuscar = new JLabel( "Valor: " );
        panelInfo.add( labelBuscar );

        textBuscar = new JTextField( );
        panelInfo.add( textBuscar );

        add( panelInfo, BorderLayout.NORTH );

        JPanel panelBotones = new JPanel( new GridLayout( 1, 2 ) );

        btnAceptar = new JButton( ACEPTAR );
        btnAceptar.setActionCommand( ACEPTAR );
        btnAceptar.addActionListener( this );
        panelBotones.add( btnAceptar );

        btnCancelar = new JButton( CANCELAR );
        btnCancelar.setActionCommand( CANCELAR );
        btnCancelar.addActionListener( this );
        panelBotones.add( btnCancelar );

        add( panelBotones, BorderLayout.SOUTH );

        pack( );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    /**
     * Muestra la lista de columnas según el tamaño de la hoja de cálculo
     */
    public void actualizarComboColumnas( )
    {
        comboColumnas.removeAllItems( );

        int numColumnas = principal.darHojaCalculo( ).darAncho( );

        for( int i = 0; i < numColumnas; i++ )
        {
            String valorColumna = principal.darHojaCalculo( ).darNombreColumna( i );
            comboColumnas.addItem( valorColumna );
        }

        if( numColumnas > 0 )
            comboColumnas.setSelectedIndex( 0 );
    }

    /**
     * Método que recoge las acciones sobre los objetos que está escuchando.
     * @param e Evento que se realizó.
     */
    public void actionPerformed( ActionEvent e )
    {
        if( e.getActionCommand( ).equals( ACEPTAR ) )
        {
            String valor = textBuscar.getText( );

            if( valor.equals( "" ) )
            {
                JOptionPane.showMessageDialog( this, "Datos Incompletos", "Buscar", JOptionPane.ERROR_MESSAGE );
                return;
            }

            String valorColumna = ( String )comboColumnas.getSelectedItem( );
            int indice= comboColumnas.getSelectedIndex();
            try {
				if( principal.buscarEnColumna( valorColumna, valor,  indice) )
				{
				    JOptionPane.showMessageDialog( this, "El texto " + valor + " SI existe en la columna " + valorColumna, "Buscar", JOptionPane.INFORMATION_MESSAGE );
				}
				else
				{
				    JOptionPane.showMessageDialog( this, "El texto " + valor + " NO existe en la columna " + valorColumna, "Buscar", JOptionPane.INFORMATION_MESSAGE );
				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (ColumnaNoOrdenadaException e1) {
				e1.printStackTrace();
			}
            dispose( );
        }
        else
        {
            dispose( );
        }
    }
}
