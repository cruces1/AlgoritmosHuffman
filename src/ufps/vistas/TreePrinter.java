/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.vistas;

/**
 *
 * @author jbecerra
 */
public class TreePrinter {

    public interface PrintableNode {

        PrintableNode getIzquierdo();

        PrintableNode getDerecho();
    }
}        
