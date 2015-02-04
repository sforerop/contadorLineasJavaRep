/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos.modelo;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ContadorClases {

    private static ArrayList<File> clases = new ArrayList<File>();

    public static String buscarArchivo() {
        File raiz = new File("..\\contadorLineasJava\\target\\classes\\source");
        recorrerCarpeta(raiz);
        String detalle = LineasCodigo.leerArchivos(clases);
        int tamanio = clases.size();
        String resultado = "El proyecto leido tiene "+tamanio+ " Clases .java \n"
                + "///////////Detallado///////////// \n"
                + detalle+"\n"
                + "/////////////////////////////////";
        return resultado;
    }

    /**
     * Este metodo recorre todas las carpetas en busqueda de archivos .java
     *
     * @param raiz
     */
    public static void recorrerCarpeta(File raiz) {
        raiz = raiz.getAbsoluteFile();
        File[] archivos = raiz.listFiles(new FilenameFilter() {
            public boolean accept(File file, String string) {
                if (string.endsWith(".java")) {
                    clases.add(new File(file + "/" + string));
                } else {
                    if (file.isDirectory()) {
                        recorrerCarpeta(new File(file + "/" + string));
                    }
                }
                return false;
            }
        });
    }
}
