/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Materia implements Serializable{
//Atributos:
    private String codigo;
    private String nombre;
    private int cantidadNiveles;
    private ArrayList<Paralelo> paralelos;
    private ArrayList<Preguntas> preguntas;

//Constructor:
    public Materia(String codigo, String nombre, int cantidadNiveles){
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadNiveles = cantidadNiveles;
        paralelos = new ArrayList<Paralelo>();
        preguntas = new ArrayList<Preguntas>();
    }
//getters
    public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public int getCantidadNiveles(){
        return cantidadNiveles;
    }
    public ArrayList<Paralelo> getParalelos() {
        return paralelos;
    }
    public ArrayList<Preguntas> getPreguntas(){
        return preguntas;
    }
//Métodos para editar datos de los objetos Materia que funcionan como setters:
    public void editarMateria(String nombre,String codigo){
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public void editarMateria(String nombre,int cantidadNiveles){
        this.cantidadNiveles = cantidadNiveles;
        this.nombre = nombre;
    }
    public void setListaParalelo(ArrayList<Paralelo> p){
        paralelos = p;
    }
    public void editarMateria(int cantidadNiveles){
        this.cantidadNiveles = cantidadNiveles;
    }
    public void aggParalelo(Paralelo p){
        paralelos.add(p);
    }
//Método para agregar preguntas en la lista de preguntas:
    public void agregarPregunta(Preguntas pregunta){
        preguntas.add(pregunta);
    }
//Método para eliminar materias en la lista de preguntas:
    public void eliminarPregunta(Preguntas pregunta){
        int x = preguntas.indexOf(pregunta);
        preguntas.remove(x);
    }
    
//Metodo equals
    /*public boolean equals(Object o){
        if (o == this) return true;
        if (o != null && getClass() == o.getClass()){
            Materia mat = (Materia) o;
            return (this.codigo.equals(mat.codigo)) || (this.codigo.equals(mat.codigo) && this.nombre.equals(mat.nombre));
        } else {
            return false;
        }
    }*/
//Metodo toString
    public String toString(){
        return nombre + ", paralelos: " + paralelos;
    }
}