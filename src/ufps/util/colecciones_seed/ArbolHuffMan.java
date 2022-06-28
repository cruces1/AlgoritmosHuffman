/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.util.colecciones_seed;

/**
 *
 * @author madarme
 */
public class ArbolHuffMan {

    public NodoHuffman raiz;
    private String cadena;

    public ArbolHuffMan() {
    }

    public ArbolHuffMan(String cadena) {
        this.cadena = cadena;
    }
    
     private ListaS<NodoHuffman> mapListaCharacterToListaNodoHuffman (ListaS<Character> caracteresSinRepetir) {
        ListaS<NodoHuffman> lista = new ListaS<>();  //
        
        for (char caracter : caracteresSinRepetir) {
            int contador = 0;
            for (char caracterAux: cadena.toCharArray()) {
                if (caracter == caracterAux) contador++;
            }
            NodoHuffman nodo = new NodoHuffman(caracter, contador, null, null);
            lista.insertarAlFinal(nodo);
        }
        
        return lista;
    }

    private ListaS<Character> obtenerCaracteresSinRepetir() {
        ListaS<Character> listaAuxiliar = new ListaS();    //3
        for (char caracter: cadena.toCharArray()) {        //  0 - n.length
            if (!listaAuxiliar.esta(caracter)){ 
                listaAuxiliar.insertarAlFinal(caracter);    // 14
            }
        }
        return listaAuxiliar;                            // 1
    }
    
    private ListaS<NodoHuffman> ordenarPorFrecuencia(ListaS<NodoHuffman> frecuencia) {
        
        NodoHuffman[] arreglo = new NodoHuffman[frecuencia.getTamanio()];
        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = frecuencia.get(i);
        }
        
        for (int y = 0; y < arreglo.length - 1; y++) {
            NodoHuffman elementoActual = arreglo[y], elementoSiguiente = arreglo[y + 1];
            if (elementoActual.getRepeticion() > elementoSiguiente.getRepeticion()) {
                arreglo[y] = elementoSiguiente;
                arreglo[y + 1] = elementoActual;
            }
        }
        
        frecuencia = new ListaS<>();
        for (NodoHuffman arreglo1 : arreglo) {
            frecuencia.insertarAlFinal(arreglo1);
        }
        
        return frecuencia;
    }
    
    private String generarArbol(ListaS<NodoHuffman> listaNodos, String pasos, int numPaso) {
        ListaS<NodoHuffman> listaNodosAux = new ListaS();
        if (listaNodos.getTamanio()< 2) {
            this.raiz = listaNodos.get(0);
            return pasos;
        }
        
        NodoHuffman nodoIzq = listaNodos.get(0);
        NodoHuffman nodoDer = listaNodos.get(1);
        
        int sumaFrecuencia = nodoIzq.getRepeticion() + nodoDer.getRepeticion();
        
        NodoHuffman nodoPadre = new NodoHuffman((char) 12, sumaFrecuencia, nodoIzq, nodoDer);
        
        for (int i = 2; i < listaNodos.getTamanio(); i++) {
            listaNodosAux.insertarAlFinal(listaNodos.get(i));
        }
        
        listaNodosAux.insertarAlFinal(nodoPadre);
        
        listaNodosAux = this.ordenarPorFrecuencia(listaNodosAux);
        pasos += "Paso "+ numPaso +": " + listaNodosAux + "\n";
        return generarArbol(listaNodosAux, pasos, numPaso+1);
    }

    /**
     * Dada una cadena que se paso como atributo de la clase, se crea el árbol y retorna la secuencia de pasos. 
     * Ejemplo: cadena= SISTEMAS 
     * Retornaría: 
     * Paso Frecuencia: (S,3)->(I,1)->(T,1)->(E,1)->(M,1)->(A,1)->null 
     * Ordenar Frecuencia: (I,1)->(T,1)->(E,1)->(M,1)->(A,1)->(S,3)->null 
     * Paso 1: (E,1)->(M,1)->(A,1)->((null,2),((I,1)->(T,1)))->(S,3)->null 
     * Paso 2: (A,1)->(( null,2)->((I,1)->(T,1))->((2, null), (E,1)->(M,1))->(S,3)-> null 
     * Paso 3: ((null, 2), (E,1)->(M,1))->(S,3)->((null,3),(A,1)->((null,2)->((I,1)->(T,1))-> null 
     * Paso 4: ((null,3),(A,1)->(( null,2)->((I,1)->(T,1))-> ((null,5),((null, 2), ((E,1)->(M,1))->(S,3))-->null
     * Paso 5: (null, 8),((null,3),(A,1)->(( null,2)->((I,1)->(T,1))-> ((null,5),((null, 2), ((E,1)->(M,1))->(S,3))-->null
     *
     * @return un String con la secuencia de pasos.
     */
    public String crear() {
        
        ListaS<Character> caracteresSinRepetir = this.obtenerCaracteresSinRepetir();   // 5 + 15n
        
        ListaS<NodoHuffman> listaNodos = this.mapListaCharacterToListaNodoHuffman(caracteresSinRepetir);  // 5 + 20n + 2n^2
        
        ListaS<NodoHuffman> ordenarPorFrecuencia = this.ordenarPorFrecuencia(listaNodos); //34n + 2
        
        String respuesta = "Paso Frecuencia:";  // 2
        respuesta += listaNodos;                 // 2
        respuesta += "\nOrdenar Frecuencia: ";   // 2
        respuesta += ordenarPorFrecuencia + "\n";  // 34n + 2  

        respuesta += this.generarArbol(ordenarPorFrecuencia, "", 1); // 
        
        return respuesta;   // 1
    }
    

    /**
     * Obtiene en una cadena todas las ramas del árbol. Ejemplo: Si cadena= "SISTEMAS". El String generado sería: 
     * Rama 1: (null, 8)- (null,3) -(A,1) 
     * Rama 2: (null, 8)-(null,3)-(null,2)-(I,1) 
     * Rama 3: (null, 8)-(null,3)-(null,2)-(T,1)
     * Rama 4: (null, 8)-(null,5)-(null,2)-(E,1) 
     * Rama 5: (null, 8)-(null,5)-(null,2)-(M,1) 
     * Rama 6: (null, 8)-(null,5)-(S,3)
     *
     * @return un String con las ramas del árbol generado
     */
    public String getRamas() {
        ListaS<NodoHuffman> rama = new ListaS();  // 3
        rama.insertarAlFinal(raiz);    // 14
        StringBuilder respuesta = new StringBuilder("Ramas\n"); // 3
        this.mostrarRamas(raiz, rama, respuesta);  // n
        return respuesta.toString(); // n
    }
    
    private void mostrarRamas(NodoHuffman nodo, ListaS<NodoHuffman> rama, StringBuilder respuesta) {

        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
            respuesta.append(" Rama ").append(": ");
            for (NodoHuffman n: rama) {
                respuesta.append("(").append(n.getLetra()).append(",").append(n.getRepeticion()).append(")");
            }
            respuesta.append("\n");
            return;
        }
        
        rama.insertarAlFinal(nodo.getIzquierdo());
        mostrarRamas(nodo.getIzquierdo(), rama, respuesta);
        rama.eliminar(rama.getTamanio()- 1);

        rama.insertarAlFinal(nodo.getDerecho());
        mostrarRamas(nodo.getDerecho(), rama, respuesta);
        rama.eliminar(rama.getTamanio() - 1);
    }

    /**
     * Obtiene la codificaciòn representada en el árbol con la cadena que se pasa como argumento Ejemplo: Si
     * cadena="SISTEMAS" El String generado sería: S : 11 I : 010 T : 011 E : 100 M : 101 A : 00 SISTEMAS:
     * 11010110111010011
     *
     * @return un String con la codificación de la palabra
     */
    public String getCodificación() {
        String aux = "";  // 2
        for(char caracter : obtenerCaracteresSinRepetir()){     // 
            aux += caracter + ":" + obtenerLetraCodificada(caracter) + "   ";  //  7n
        }
        
        aux += "\ncadena " + cadena + " : ";   // 2
                
        for(char caracter : cadena.toCharArray()){   // n
            aux += obtenerLetraCodificada(caracter);   // 7n
        }
        return aux;  // 1
    }
    
    
    public String obtenerLetraCodificada (char letra) {
        ListaS binario = this.obtenerBinario(raiz, letra);   // n
        String aux = "";   // 2
        for (int i = 0; i < binario.getTamanio() - 1; i++) {
            aux += binario.get(i);   // 3
        }
        return aux;  //1
    }
    
    public ListaS obtenerBinario(NodoHuffman nodo, char caracter) {
        if (nodo == null) {
            return new ListaS();   // 2
        }
// else
        if (nodo.getLetra() == caracter) {
            ListaS lista = new ListaS();   //  3
            lista.insertarAlFinal(nodo.getLetra()); // 14
            return lista;  // 1
        }

        ListaS listaIzq = obtenerBinario(nodo.izquierdo, caracter);
        
        if (listaIzq.getTamanio() > 0) {
            listaIzq.insertarAlInicio(0);   //7
            return listaIzq;  // 1
        }

        ListaS listaDer = obtenerBinario(nodo.derecho, caracter);
        if (listaDer.getTamanio() > 0) {
            listaDer.insertarAlInicio(1);  // 7
            return listaDer;    // 1
        }
        
        return new ListaS();  //1
    }
    
    
}
