/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.modelo;
import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class Estudiante implements Serializable{
    //variables de instancia
    private String nombre;
    private String matricula;
    private String correoInst;
    //constructor
    public Estudiante(String nombre, String matricula, String correoInst){
        this.nombre = nombre;
        this.matricula = matricula;
        this.correoInst = correoInst;
    }
    //constructor auxiliar
    public Estudiante(String matricula){
        this.matricula = matricula;
    }
    //getters
    public String getNombre(){
        return nombre;
    }
    public String getMatricula(){
        return matricula;
    }
    public String getCorreoInst(){
        return correoInst;
    }
    public String toString(){
        return nombre+", "+matricula+", "+correoInst;
    }
    @Override
    public boolean equals(Object o){
        if(o==this) return true;
        if(o != null && getClass() == o.getClass()){
            Estudiante other = (Estudiante) o;
            return this.getMatricula().equals(other.getMatricula());
        } else{
            return false;
        }
    }
}