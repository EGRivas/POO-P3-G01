/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.modelo;
import java.io.Serializable;
import java.util.ArrayList;

public class Paralelo implements Serializable{
//Atribbutos:
    private String numero;
    private ArrayList<Estudiante> estudiantes;
// Costructor:
    public Paralelo(String numero){
        this.numero = numero;
        estudiantes = new ArrayList<Estudiante>();
    }
//Sobrecarga de constructor:
    public Paralelo(String numero, ArrayList<Estudiante> estudiantes){
        this.numero = numero;
        this.estudiantes = estudiantes;
    }
//Getters:
    public String getNumero(){
        return numero;
    }

    public ArrayList<Estudiante> getEstudiantes(){
        return estudiantes;
    }
//Setters:
    public void setNumero(String numero){
        this.numero = numero;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes){
        this.estudiantes = estudiantes;
    }
//Método para agrefar estudiantes a la lista de estudiantes:
    public void addEstudiante(Estudiante estudiante){
        estudiantes.add(estudiante);
    }
    public void addEstudiantes(ArrayList<Estudiante> estudiantes){
        if(!estudiantes.isEmpty()){
            this.estudiantes.addAll(estudiantes);
        }
    }
    public String toString(){
        //return numero;
        return "paralelo: "+numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paralelo paralelo = (Paralelo) o;

        return numero != null ? numero.equals(paralelo.numero) : paralelo.numero == null;
    }

}