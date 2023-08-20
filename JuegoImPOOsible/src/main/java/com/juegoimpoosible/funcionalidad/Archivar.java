/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.funcionalidad;

import com.juegoimpoosible.modelo.*;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Archivar {
    public static void writeTerms(ArrayList<Termino> terms){
        try {
            FileOutputStream fileOut = new FileOutputStream("JuegoImPOOsible/target/generated-sources/terms.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(terms);

            out.close();
            fileOut.close();
            System.out.println("guardado en terms.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeGames(ArrayList<Juego> juegos){
        try {
            FileOutputStream fileOut = new FileOutputStream("files/games.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(juegos);

            out.close();
            fileOut.close();
            System.out.println("guardado en games.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStudents(ArrayList<Estudiante> estudiantes){
        try {
            FileOutputStream fileOut = new FileOutputStream("files/students.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(estudiantes);

            out.close();
            fileOut.close();
            System.out.println("guardado en students.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeParalelos(ArrayList<Paralelo> paralelos){
        try {
            FileOutputStream fileOut = new FileOutputStream("files/paralelos.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(paralelos);

            out.close();
            fileOut.close();
            System.out.println("guardado en paralelos.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeMaterias(ArrayList<Materia> materias){
        try {
            FileOutputStream fileOut = new FileOutputStream("files/subject.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(materias);

            out.close();
            fileOut.close();
            System.out.println("guardado en subject.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Termino> readTerms(){
        ArrayList<Termino> deserializedList = new ArrayList<Termino>();
        try {
            FileInputStream fileIn = new FileInputStream("JuegoImPOOsible/target/generated-sources/terms.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            deserializedList = (ArrayList<Termino>) in.readObject();

            in.close();
            fileIn.close();

            // Use the deserializedList
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[-]Serial no ha sido creado");
        }
        return deserializedList;

    }

    public static ArrayList<Juego> readGames(){
        ArrayList<Juego> deserializedList = new ArrayList<Juego>();
        try {
            FileInputStream fileIn = new FileInputStream("files/games.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            deserializedList = (ArrayList<Juego>) in.readObject();

            in.close();
            fileIn.close();

            // Use the deserializedList
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[-]Serial no ha sido creado");
        }
        return deserializedList;

    }

    public static ArrayList<Estudiante> readStudents(){
        ArrayList<Estudiante> deserializedList = new ArrayList<Estudiante>();
        try {
            FileInputStream fileIn = new FileInputStream("files/students.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            deserializedList = (ArrayList<Estudiante>) in.readObject();

            in.close();
            fileIn.close();

            // Use the deserializedList
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[-]Serial no ha sido creado");
        }
        return deserializedList;

    }

    public static ArrayList<Paralelo> readParalelos(){
        ArrayList<Paralelo> deserializedList = new ArrayList<Paralelo>();
        try {
            FileInputStream fileIn = new FileInputStream("files/paralelos.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            deserializedList = (ArrayList<Paralelo>) in.readObject();

            in.close();
            fileIn.close();

            // Use the deserializedList
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[-]Serial no ha sido creado");
        }
        return deserializedList;
    }

    public static ArrayList<Materia> readSubjects(){
        ArrayList<Materia> deserializedList = new ArrayList<Materia>();
        try {
            FileInputStream fileIn = new FileInputStream("files/subject.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            deserializedList = (ArrayList<Materia>) in.readObject();

            in.close();
            fileIn.close();

            // Use the deserializedList
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[-]Serial no ha sido creado");
        }
        return deserializedList;

    }
}