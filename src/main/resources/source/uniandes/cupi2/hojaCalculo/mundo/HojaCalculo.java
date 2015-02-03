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
 * Modificación: Katalina Marcos - 07-Dic-2005
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.cupi2.hojaCalculo.mundo;

import java.io.File;
import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import uniandes.cupi2.hojaCalculo.excepciones.ArchivoException;
import uniandes.cupi2.hojaCalculo.excepciones.ColumnaNoExisteException;
import uniandes.cupi2.hojaCalculo.excepciones.ColumnaNoOrdenadaException;

/**
 * Representa una Hoja de cálculo<br>
 * ancho !=0 <br>
 * alto !=0 
 */
public class HojaCalculo implements Serializable
{
    //-----------------------------------------------------------------
    // Constantes
    //-----------------------------------------------------------------
    
    /**
     * Constante para la serialización
     */
    private static final long serialVersionUID = 1L;
    
    //-----------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------

    /**
     * Celdas de la hoja de cálculo
     */
    private Celda[][] celdas;

    /**
     * Ancho de la hoja (horizontal)
     */
    private int ancho;

    /**
     * Alto de la hoja (vertical)
     */
    private int alto;

    //-----------------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------------

    /**
     * Crea una nueva hoja de cálculo con las celdas vacías.
     * @param elAncho Número de columnas. elAncho >= 1.
     * @param elAlto Número de filas. elAlto >= 1.
     */
    public HojaCalculo( int elAncho, int elAlto )
    {
        inicializarHoja( elAncho, elAlto );
    }

    //-----------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------

    /**
     * Inicializa la hoja de las dimensiones dadas con celdas vacías
     * @param elAncho Número de columnas. elAncho >= 1.
     * @param elAlto Número de filas. elAlto >= 1.
     */
    private void inicializarHoja( int elAncho, int elAlto )
    {
        //
        //Guarda los tamaños
        ancho = elAncho;
        alto = elAlto;
        verificarInvariante();
        
        //Inicializa las celdas
        celdas = new Celda[alto][ancho];
        for( int i = 0; i < alto; i++ )
        {
            for( int j = 0; j < ancho; j++ )
            {
                celdas[ i ][ j ] = new Celda( i, j, this );
            }
        }
    }

    /**
     * Devuelve el número de columnas (ancho) de la hoja actual.
     * @return Numero de columnas
     */
    public int darAncho( )
    {
        return ancho;
    }

    /**
     * Devuelve el numero de filas (alto) de la hoja actual.
     * @return Numero de filas
     */
    public int darAlto( )
    {
        return alto;
    }
    
    public void setMatriz(Celda[][] matriz){
    	celdas = matriz;
    }

    /**
     * Devuelve la celda en la posición especificada. Devuelve null si la posición es inválida.
     * @param columna Columna de la celda.
     * @param fila Fila de la celda.
     * @return Celda en la posición especificada (fila,columna).
     */
    public Celda darCelda( int columna, int fila )
    {
        Celda resultado = null;
        if( ancho > columna && alto > fila )
        {
            resultado = celdas[ fila ][ columna ];
        }
        return resultado;
    }

    /**
     * Devuelve la posición numérica de la columna, dada su representación en caracteres. <br>
     * @param columna Columna a evaluar. columna sólo tiene caracteres 'A' - 'Z'.
     * @return valor numérico de la columna. valor >= 0
     */
    public int darValorColumna( String columna )
    {
        String columnaMay = columna.toUpperCase( );
        int result = 0;
        for( int i = 0; i < columnaMay.length( ); i++ )
        {
            //
            //Multiplica por el factor especifico (Z - A)
            result *= ( 'Z' - 'A' );
            //
            //Saca el siguiente carácter
            char caracter = columnaMay.charAt( i );
            //
            //Suma el valor del carácter
            result += ( caracter - 'A' );
        }
        return result;
    }

    /**
     * Dado un valor numérico, devuelve el nombre de la columna. <br>
     * @param valor numérico. valor >= 0.
     * @return nombre de la columna.
     */
    public String darNombreColumna( int valor )
    {
        String resultado = "";
        //
        //El factor de división
        int factor = 'Z' - 'A';
        //
        //Convierte el valor
        while( valor > 0 )
        {
            int valorNumerico = valor % factor;
            valor = valor / factor;
            char valorChar = ( char ) ( valorNumerico + 'A' );
            resultado = valorChar + resultado;
        }
        //
        //Si el valor es cero
        if( resultado.length( ) == 0 )
        {
            resultado = "A";
        }
        return resultado;
    }

    /**
     * Ordena la columna correspondiente
     * @param columna Valor de la columna a ordenar
     * @throws ColumnaNoExisteException En caso en el que la columna no exista
     */
    public void ordenarColumna( int columna ) throws ColumnaNoExisteException
    {
    	System.out.println("Entro a Ordenar Columna" + columna);
    	try {
    		for (int i = 0; i < alto - 1; i++)
            {
                    int min = i;
                    for (int j = i + 1; j < alto; j++)
                    {
                            if (Integer.parseInt(celdas[j][columna].darContenido()) < Integer.parseInt(celdas[min][columna].darContenido()))
                            {
                                    min = j;
                            }
                    }
                    if (i != min) 
                    {
                            int aux= Integer.parseInt(celdas[i][columna].darContenido());
                            celdas[i][columna].asignarContenido(celdas[min][columna].darContenido());
                            celdas[min][columna].asignarContenido(Integer.toString(aux));
                    }
            }
		} catch (Exception e) {
			System.out.println("HAY UN VALOR STRING EN UNA COLUMNA");
			JOptionPane.showMessageDialog( null, "se ha encontrado en la columna un valor no númerico" , "Error", JOptionPane.ERROR_MESSAGE );
		}
    	
    }
    
    /**
     * Verifica si la columna correspondiente está ordenada
     * @param columna Valor de la columna a verificar
     */
    public boolean estaOrdenada( String columna )
    {
        int i = darValorColumna( columna );
        for( int j = 0; j < alto - 1; j++ )
        {
            if( celdas[i][j].darResultado( ).compareToIgnoreCase( celdas[i][j+1].darResultado( ) ) > 0 )
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Busca un texto en las casillas de determinada columna
     * @param indice Valor de la columna en la que se buscará
     * @param valorBuscar Texto que se quiere buscar
     * @return True si el valor a buscar existe en la columna correspodiente. False de lo contrario.
     * @throws ColumnaNoOrdenadaException En caso en el que la columna no se encuentre ordenada
     */
    public boolean buscarEnColumna( int indice, String valorBuscar ) throws ColumnaNoOrdenadaException
    {
    	boolean existe = false;
    	try {    		
            System.out.println("Entro a buscar en columna");
            int inicio =0;
            int fin  = alto;
            while(inicio <= fin && !existe){
            	int medio = (inicio + fin) /2;
            	if(celdas[medio][indice].darContenido().equals(valorBuscar)){
            		existe = true;
            	}else if(Integer.parseInt(celdas[medio][indice].darContenido())> Integer.parseInt(valorBuscar)){
            		fin = medio -1;
            	}else{
            		inicio = 	medio +1;
            	}
            }
		} catch (Exception e) {
			System.out.println("HAY VALORES STRING");
			JOptionPane.showMessageDialog( null, "el valor " + valorBuscar + " NO es númerico" , "Error", JOptionPane.ERROR_MESSAGE );
		}        
        
        return existe;
    }
    
    /**
     * Carga el archivo XML en la hoja de cálculo
     * @param archivo Archivo a cargar. archivo != null.
     * @throws ArchivoException Error al cargar el archivo
     */
    public void cargarArchivoXML( File archivo ) throws ArchivoException
    {    	
    	try {
    		DocumentBuilderFactory fábricaCreadorDocumento = DocumentBuilderFactory.newInstance();
        	DocumentBuilder creadorDocumento = fábricaCreadorDocumento.newDocumentBuilder();
			Document documento = creadorDocumento.parse(archivo);			
			if(documento != null){
				NodeList listaHoja = documento.getElementsByTagName("hoja");
				for(int i=0; i< listaHoja.getLength(); i++){
					Node nodo = listaHoja.item(i);					
					System.out.println(nodo.getAttributes().getNamedItem("filas").getNodeValue());
					System.out.println(nodo.getAttributes().getNamedItem("columnas"));
					int elAlto = Integer.parseInt(nodo.getAttributes().getNamedItem("filas").getNodeValue());
					int elAncho = Integer.parseInt(nodo.getAttributes().getNamedItem("columnas").getNodeValue());
					this.inicializarHoja(elAncho, elAlto);
				}
				NodeList listaCelda = documento.getElementsByTagName("celda");
				for(int i=0; i< listaCelda.getLength(); i++){
					Node nodo = listaCelda.item(i);
					System.out.println(nodo.getAttributes().getNamedItem("fila"));
					System.out.println(nodo.getAttributes().getNamedItem("columna"));
					int elAlto = Integer.parseInt(nodo.getAttributes().getNamedItem("fila").getNodeValue());
					int elAncho = Integer.parseInt(nodo.getAttributes().getNamedItem("columna").getNodeValue());
					 for( int k = 0; k < alto; k++ )
				        {
				            for( int l = 0; l < ancho; l++ )
				            {
				            	if(elAlto == k && elAncho == l){
				            		if(nodo.getFirstChild()!= null){
				            			celdas[ k ][ l ].asignarContenido(nodo.getFirstChild().getNodeValue());
				            		}else{
				            			celdas[ k ][ l ].asignarContenido("");
				            		}
				            	}
				            }
				        }
				}				
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
    }

    /**
     * Guarda la hoja de cálculo actual en el archivo especificado siguiendo el formato XML.
     * @param archivo Archivo a guardar, archivo != null.
     * @throws Exception Error al guardar el archivo
     */
    public void guardarArchivoXML( File archivo ) throws ArchivoException
    {
    	 
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	 DocumentBuilder db = dbf.newDocumentBuilder();
	    	 Document doc = db.newDocument();
	    	 Element hoja = doc.createElement("hoja");
	    	 hoja.setAttribute("filas", Integer.toString(this.darAlto()));
	    	 hoja.setAttribute("columnas", Integer.toString(this.darAncho()));
	    	 
	    	 for( int k = 0; k < alto; k++ ){
		            for( int l = 0; l < ancho; l++ )
		            {		       
			            Element celda = doc.createElement("celda");
			            celda.setAttribute("fila", Integer.toString(k));
			            celda.setAttribute("columna", Integer.toString(l));
			   	    	Text valorCel = doc.createTextNode(celdas[ k ][ l ].darContenido());   
			   	    	celda.appendChild(valorCel);
			   	    	hoja.appendChild(celda);
		            }
		        }
	    	 doc.appendChild(hoja);
	    	  
	    	 // añadimos sangrado y la cabecera de XML
	    	 Source source = new DOMSource(doc);
	    	 //El archivo se genera en la carpeta Raiz del proyecto
	    	 Result result = new StreamResult(new java.io.File(archivo + "/hojaCalculo.xml"));
	    	 Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    	 transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    //-----------------------------------------------------------------
    // Invariante
    //-----------------------------------------------------------------
    private void verificarInvariante(){
    	assert alto !=0: "alto es cero";
    	assert ancho !=0: "ancho es cero";
    }
    
    //-----------------------------------------------------------------
    // Puntos de Extensión
    //-----------------------------------------------------------------

    /**
     * Método para la extensión 1
     * @return respuesta1
     */
    public String metodo1( )
    {
        return "Respuesta 1";
    }

    /**
     * Método para la extensión2
     * @return respuesta2
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }
}