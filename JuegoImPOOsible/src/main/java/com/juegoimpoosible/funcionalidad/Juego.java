/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible.funcionalidad;
import com.juegoimpoosible.modelo.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Juego implements Serializable{
//Atributos:
    private static Termino termino;
    private Materia materia;
    private Paralelo paralelo;
    private ArrayList<Paralelo> paralelos;
    private int numPreNivel;
    private Estudiante participante;
    private Estudiante comApoyo;
    private ArrayList<Preguntas> preguntas;
    private String fecha;
    private int nivelAlcanzado;
    private int nivelMax;
    private int cantidadPreguntasContestadas = 0;
    private int comodinesUtilizados;
    private int premio;
    private String premioGanado;

    //agrego disponibilidad de comodines como variable de instancia

    private int[] discomodines = {1, 1, 1};

    private TipoComodin[] comodines = TipoComodin.values();
    private ArrayList<TipoComodin> comodinesUsados;



//Constructor:
    public Juego(Materia m, Paralelo p, Estudiante par){
        materia = m;
        paralelo = p;
        participante = par;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese la matricula del estudiante de apoyo");
        comApoyo = ayudante(sc.nextLine());
        System.out.println("Ingrese la fecha en formato DD-MM-AA:");
        fecha = sc.nextLine();
        preguntas = m.getPreguntas(); //se obtienen las preguntas segun la materia ingresada
        //para validar el numero de preguntas por nivel
        
        boolean valido = false;
        while(valido == false){
            int c = 0; //contador externo
            System.out.println("Ingrese el número de preguntas por nivel:");
            int num = sc.nextInt(); //numero de preguntas por nivel
            sc.nextLine();
            //se hace una lista de enteros con el numero de niveles que hay en cada pregunta
            ArrayList<Integer> niveles = new ArrayList<>(); 
            for (Preguntas preg: preguntas){
                niveles.add(preg.getNivel()); //agregando el numero de nivel de la pregunta a la lista "niveles"
            }
            int maxNivel = Collections.max(niveles); //el nivel maximo encontrado en las preguntas
            for (int x = 1;x<=maxNivel;x++){ //primer lazo for que itera del 1 al nivelMaximo con un contador interno
                int c1 = 0;
                for(int n : niveles){ //se recorre la lista de niveles
                    if(n==x){ //se compara que el nivel sea igual a la iteracion del lazo for (el nivel analizado)
                       c1++; //se ira aumentando este contador interno hasta que se lea toda la lista
                    }//En la primera iteracion (x=1), si hay 3 preguntas de nivel 1, este contador da como resultado 3
                }
                if(c1>=num){//se aumenta el contador externo si el interno supera o es igual a num
                    c++;//es decir, siendo num = 3
                    //si hay 4 preguntas en el nivel 1, 4>=3 es true, por lo que queda validado el nivel 1
                }//si hay 2 preguntas en el nivel 2, 2>=3 es false, por lo que el nivel 2 no es validado y c no aumenta
            }
            if(c==maxNivel){//se valida que todos los niveles hayan aumentado, por lo que el maximo debe ser igual a c
                valido = true;
                numPreNivel = num;
                nivelMax = maxNivel; //nivel maximo de preguntas
            }
        }
    }
    //constructor para prueba
    public Juego(Materia m){
        materia = m;
    }
//Método:
    public static void setTermino(Termino t){
        termino = t;
    }

    public Estudiante ayudante(String matriculaEstudiante){
        for(Estudiante e: paralelo.getEstudiantes()) {
            if (e.getMatricula().equals(matriculaEstudiante)) {
                return e;
            }
        } return null;
    }


    public Estudiante participante(String matriculaEstudiante){
        for(Estudiante e: paralelo.getEstudiantes()) {
            if (e.getMatricula().equals(matriculaEstudiante)) {
                return e;
            }
        } return null;
    }


    public void empezarJuego() {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("A");
        answer.add("B");
        answer.add("C");
        answer.add("D");
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> niveles = new ArrayList<>();
        niveles.add(0);

        ArrayList<Preguntas> preguntasDisponibles = new ArrayList<>(preguntas);

        for (int x = 1; x <= nivelMax; x++) {
            int cont = 0;
            Collections.shuffle(preguntasDisponibles); // Barajar las preguntas disponibles
            for (Preguntas p : preguntasDisponibles) {
                if (cont < numPreNivel && p.getNivel() == x) {
                    ArrayList<String> lresp = p.listaRespuestas();
                    System.out.println(p.getEnunciado());

                    // Mostrar las opciones de respuesta con etiquetas A), B), C), D)
                    for (int i = 0; i < lresp.size(); i++) {
                        System.out.println(answer.get(i) + ") " + lresp.get(i));
                    }

                    System.out.print("Ingrese la letra de la respuesta (A, B, C o D), o * para usar un comodín: ");
                    String resp = sc.nextLine();

                    if (resp.equals("*")) {
                        mostrarComodinesDisponibles(lresp, p, niveles);

                        System.out.println(p.getEnunciado());

                        // Mostrar las opciones de respuesta con etiquetas A), B), C), D) después de usar un comodín
                        for (int i = 0; i < lresp.size(); i++) {
                            System.out.println(answer.get(i) + ") " + lresp.get(i));
                        }

                        System.out.print("Ingrese la letra de la respuesta (A, B, C o D), o * para usar un comodín: ");
                        resp = sc.nextLine();
                    }

                    int numResp = answer.indexOf(resp);
                    String respuestaIngresada = lresp.get(numResp);
                    boolean respuestaCorrecta = p.indicarRespuestaCorrecta(respuestaIngresada);
                    if (!respuestaCorrecta) {
                        System.out.println("Respuesta incorrecta. Fin del juego.");
                        nivelAlcanzado = Collections.max(niveles);
                        return;
                    }
                    niveles.add(p.getNivel());
                    cont++;
                    cantidadPreguntasContestadas++;
                }
            }
        }

        nivelAlcanzado = Collections.max(niveles);
        System.out.println("¡Felicitaciones! Has respondido correctamente a todas las preguntas.");

        System.out.print("Ingrese el premio que ha ganado el estudiante: ");
        premio = sc.nextInt();
        sc.nextLine();

        System.out.println("¡Felicidades! Ha ganado: " + premio);
    }

    //revisar implementacion
    public void mostrarComodinesDisponibles(ArrayList<String> lresp, Preguntas p, ArrayList<Integer> niveles){
        Scanner sc = new Scanner(System.in);
        for(int j=0; j < 3; j++){
            if(discomodines[j] == 1){
                System.out.println(comodines[j] + "");
            }
        }

        if(discomodines[0] == 0 && discomodines[1] == 0 && discomodines[2] == 0){
            System.out.println("Te has quedado sin comodines");
        }else{
            System.out.println("Ingrese 1, 2, o 3, para usar un comodin: ");
            int opcion = sc.nextInt();
            sc.nextLine();
            if(opcion == 1){
                if(discomodines[0] == 1){
                    comodinesUtilizados += 1;
                    discomodines[0] = 0;
                    comodinCompanero(lresp, p, niveles);
                    System.out.println("Has usado tu comodin 1!");
                } else {
                    System.out.println("Comodin 1 ya fue utilizado anteriormente.");
                }
            } else if (opcion == 2) {
                if(discomodines[1] == 1){
                    comodinesUtilizados += 1;
                    discomodines[1] = 0;
                    comodin5050(lresp, p, niveles);
                    System.out.println("Has usado tu comodin 2!");
                } else {
                    System.out.println("Comodin 2 ya fue utilizado anteriormente.");
                }
            } else if (opcion == 3) {
                if(discomodines[2] == 1){
                    comodinesUtilizados += 1;
                    discomodines[2] = 0;
                    System.out.println("Has usado tu comodin 3!");
                } else {
                    System.out.println("Comodin 3 ya fue utilizado anteriormente.");
                }
            } else {
                System.out.println("Opción inválida. Debes ingresar 1, 2 o 3 para usar un comodín.");
            }
        }

    }
    public void comodinCompanero(ArrayList<String> lresp, Preguntas p, ArrayList<Integer> niveles) {
        Scanner sc = new Scanner(System.in);
        System.out.println(comApoyo.getNombre() + ", ¿cuál es tu respuesta?");

        // Mostrar las opciones de respuesta sin la posibilidad de usar un comodín
        for (int i = 0; i < lresp.size(); i++) {
            System.out.println((char)('A' + i) + ") " + lresp.get(i));
        }

        String resp = sc.nextLine();
        int numResp = resp.charAt(0) - 'A';
        String respuestaIngresada = lresp.get(numResp);

        boolean respuestaCorrecta = p.indicarRespuestaCorrecta(respuestaIngresada);
        if (!respuestaCorrecta) {
            nivelAlcanzado = Collections.max(niveles);
            return;
        }

        niveles.add(p.getNivel());
        cantidadPreguntasContestadas++;
    }
    public void comodin5050(ArrayList<String> lresp, Preguntas p, ArrayList<Integer> niveles) {
        ArrayList<String> respuestas = p.listaRespuestas();

        // Filtrar las respuestas incorrectas
        ArrayList<String> respuestasIncorrectas = new ArrayList<>();
        for (String respuesta : respuestas) {
            if (!p.indicarRespuestaCorrecta(respuesta)) {
                respuestasIncorrectas.add(respuesta);
            }
        }

        // Mantener una respuesta incorrecta y una correcta
        ArrayList<String> respuestasRestantes = new ArrayList<>();
        respuestasRestantes.add(respuestasIncorrectas.get(0));
        respuestasRestantes.add(p.getRespuestaCorrecta());

        // Mostrar las respuestas restantes
        System.out.println("Respuesta correcta y una incorrecta:");
        System.out.println("A) " + respuestasRestantes.get(0));
        System.out.println("B) " + respuestasRestantes.get(1));

        // Solicitar la nueva respuesta del usuario
        Scanner sc = new Scanner(System.in);
        //System.out.print("Ingrese la letra de la respuesta (A o B): ");
        String resp = sc.nextLine();

        int numResp = resp.equals("A") ? 0 : 1;
        String respuestaIngresada = respuestasRestantes.get(numResp);
        boolean respuestaCorrecta = p.indicarRespuestaCorrecta(respuestaIngresada);
        if (!respuestaCorrecta) {
            System.out.println("Respuesta incorrecta. Fin del juego.");
            nivelAlcanzado = Collections.max(niveles);
            return;
        }
        niveles.add(p.getNivel());
        cantidadPreguntasContestadas++;
    }

    //getters:
    public int getNumPreNivel(){
        return numPreNivel;
    }
    public int getNivelMax(){
        return nivelMax;
    }
    public String getCodigoMateria(){
        return materia.getCodigo();
    }
    public String getNumParalelo(){
        return paralelo.getNumero();
    }
    public static Termino getTermino(){
        return termino;
    }
    public String getFecha(){
        return fecha;
    }
    public Estudiante getParticipante(){
        return participante;
    }
    public int getNivelAlcanzado(){
        return nivelAlcanzado;
    }
    public int getCantidadPreguntasContestadas(){
        return cantidadPreguntasContestadas;
    }
    public int getComodinesUtilizados(){
        return comodinesUtilizados;
    }
    public int getPremio(){
        return premio;
    }
    public int[] getDiscomodines(){
        return discomodines;
    }
    public TipoComodin[] getComodines(){
        return comodines;
    }
    

    //setters:
    public void setNumPreNivel(int n){
        numPreNivel = n;
    }
    public void setNivelMax(int n){
        nivelMax = n;
    }
    public void setMateria(Materia m){
        materia = m;
    }
    public void setParalelo(Paralelo p){
        paralelo = p;
    }
    public void setParticipante(Estudiante e){
        participante = e;
    }
    public void setComApoyo(Estudiante e){
        comApoyo = e;
    }
    public void setNivelAlcanzado(int n){
        nivelAlcanzado = n;
    }
    public void setCantidadPreguntasContestadas(int n){
        cantidadPreguntasContestadas = n;
    }
    public void setComodinesUtilizados(int n){
        comodinesUtilizados = n;
    }
    public void setPremioGanado(String prem){
        premioGanado = prem;
    }
    public void setComodinesUsados(ArrayList<TipoComodin> c){
        comodinesUsados = c;
    }
}
