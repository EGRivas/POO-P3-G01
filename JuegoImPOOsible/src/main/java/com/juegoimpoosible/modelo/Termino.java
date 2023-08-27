/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.modelo;
import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author Administrador
 */
public class Termino implements Serializable{
    //variables de instancia
    private String anio;
    private String periodo;
    private ArrayList<Materia> materias;

    //constructor
    public Termino(String anio, String periodo){
        this.anio = anio;
        this.periodo = periodo;
        materias = new ArrayList<Materia>();
    }
    //getters
    public String getAnio(){
        return anio;
    }
    public String getPeriodo(){
        return periodo;
    }
    public ArrayList<Materia> getMaterias(){
        return materias;
    }
    //setters
    public void setAnio(String anio){
        this.anio = anio;
    }
    public void setPeriodo(String periodo){
        this.periodo = periodo;
    }
    public void actuMaterias(ArrayList<Materia> m){
        materias = m;
    }
    public void actuMaterias(Materia m){
        materias.add(m);
    }
    //metodo equals
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o != null && getClass() == o.getClass()){
            Termino term = (Termino) o;
            return (anio.equals(term.anio)) && (periodo.equals(term.periodo));
        } else {
            return false;
        }
    }

    public void setMaterias(ArrayList<Materia> m){
        materias = m;
    }
    //metodo toString
    public String toString(){
        return anio+"-"+periodo;
    }
}
