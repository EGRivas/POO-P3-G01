/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Preguntas implements Serializable{

//Atributos:
    private String enunciado;
    private int nivel;
    private ArrayList<String> listaRespuestas;
    private String respuestaCorrecta;
    private TipoComodin comodin;

//Constructor:
    public Preguntas(String e, int n, ArrayList<String> lRespuestas, String rCorrecta){
        enunciado = e;
        nivel = n;
        listaRespuestas = lRespuestas;
        respuestaCorrecta = rCorrecta;
    }
    public Preguntas(String e){
        enunciado = e; //para verificar que una pregunta sea igual a otra en el metodo de remover preguntas
        nivel = 0;
        listaRespuestas = new ArrayList<>();
        respuestaCorrecta = null;
    }

//Métodos:
    public boolean indicarRespuestaCorrecta(String r){
        return r.equals(respuestaCorrecta);
    }
    public TipoComodin getComodin(){
        return comodin;
    }
    public String getEnunciado(){
        return enunciado;
    }
    public int getNivel(){
        return nivel;
    }
    public String getRespuestaCorrecta(){
        return respuestaCorrecta;
    }
    public ArrayList<String> listaRespuestas(){
        ArrayList<String> lresp = listaRespuestas;
        if(listaRespuestas.size()==3){
            lresp.add(respuestaCorrecta);
        }
        return lresp;
    }
//Metodo toString
    public String toString(){
        return "enunciado: "+enunciado+"\nnivel: "+nivel;
    }
    
    //Metodo equals
    public boolean equals(Object o){
        if (o == this) return true;
        if (o != null && getClass() == o.getClass()){
            Preguntas p = (Preguntas) o;
            return enunciado.equals(p.enunciado);
        } else {
            return false;
        }
    }

}