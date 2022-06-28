/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.vistas;
import ufps.util.colecciones_seed.*;

/**
 *
 * @author MADARME
 */

public class Test_Algunas_Estructuras {

    public static void main(String args[]) {
        
        ArbolHuffMan a = new ArbolHuffMan("SISTEMAS");
        
        System.out.println(a.crear());
        System.out.println(a.getRamas());
        System.out.println(a.getCodificación());
        
        
        //System.out.println(a.obtenerLetraCodificada('S'));
        /*Test_Algunas_Estructuras.print(a.raiz);*/
        //    crear_ListaSimple();
        //    crear_ListaDoble();
        //    crear_Pila();
        //    crear_ArbolBinarioBusqueda();
    }
}
