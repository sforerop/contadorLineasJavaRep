/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class LineasCodigo {

    public static String leerArchivos(ArrayList<File> clases) {
        String detalle = "";
        System.out.println("El proyecto leido tiene "+clases.size()+ " Clases .java");
        System.out.println("-//////////Detallado/////////////");
        for (File clase : clases) {
            int lineas = 0;
            lineas = LineasContadasen(clase);
            System.out.println(clase.getName() + " tiene  " + lineas +" lineas.");
            detalle += clase.getName() + " tiene  " + lineas +" lineas. \n"
                    + "------------------------------\n";
            System.out.println("------------------------------");
        }
        System.out.println("/////////////////////////////////");
        return detalle;
    }

    public static int LineasContadasen(File file) {
        int conta = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            String linea = null;
            do {
                linea = reader.readLine();
                if (linea != null && !linea.contains("*") && !linea.contains("//") && linea.length() > 0 && !linea.isEmpty()) {
//                    System.out.println(linea);
                    conta++;
                }
            } while (linea != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conta;
    }
}
