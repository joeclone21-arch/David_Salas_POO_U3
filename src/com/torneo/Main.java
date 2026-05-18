package com.torneo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---SISTEMA DE SORTEO PARA LIGA PROFESIONAL--");

        try {
            // Ingreso y Validacion de la Etapa
            System.out.print("Ingrese la etapa del torneo (octavos/cuartos/semifinales): ");
            String etapa = scanner.nextLine().trim().toLowerCase();

            int cantEsperada = 0;
            if (etapa.equals("octavos")) {
                cantEsperada = 16;
            } else if (etapa.equals("cuartos")) {
                cantEsperada = 8;
            } else if (etapa.equals("semifinales")) {
                cantEsperada = 4;
            } else {
                // Lanza excepcion propia
                throw new EtapaInvalidaException(etapa);
            }
            // Ingreso de Equipos (con validacion y control de duplicados)
            System.out.println("\nIngrese los nombres de los " + cantEsperada + " equipos para " + etapa + ":");
            ArrayList<String> equipos = new ArrayList<>();

            for (int i = 0; i < cantEsperada; i++) {
                System.out.print("Equipo " + (i + 1) + ": ");
                String nombre = scanner.nextLine().trim();

                // validacion si esta vacio o no tiene caracteres validos?
                if (nombre.isEmpty() || !nombre.matches(".*[a-zA-Z0-9].*")) {
                    throw new NombreEquipoInvalidoException(i + 1);
                }
                // validacion: si el equipo ya fue ingresado (Ignorando mayusculas/minusculas)
                // Convierte a minusculas temporalmente para comparar y evitar "Ldu" y "LDU"
                boolean yaExiste = false;
                for (String eq : equipos) {
                    if (eq.equalsIgnoreCase(nombre)) {
                        yaExiste = true;
                        break;
                    }
                }
                
                if (yaExiste) {
                    // Lanzamos la excepción de duplicados si encuentra coincidencia
                    throw new EquiposDuplicadosException(nombre);
                }
                
                equipos.add(nombre);
            }

            if (equipos.size() != cantEsperada) {
                throw new CantidadEquiposException(etapa, cantEsperada, equipos.size());
            }

            // Mezcla la lista inicialmente para garantizar aleatoriedad 
            Collections.shuffle(equipos);

            // Sorteo Aleatorio mediante Recursividad
            System.out.println("\nProcesando sorteo de forma aleatoria mediante recursividad...");
            ArrayList<Partido> partidosResultantes = new ArrayList<>();
            realizarSorteoRecursivo(equipos, partidosResultantes);

            // Salida del Programa en Consola
            System.out.println("\n---------------------------------");
            System.out.println("  RESULTADOS DEL SORTEO: " + etapa.toUpperCase());
            System.out.println("-----------------------------------");
            for (int i = 0; i < partidosResultantes.size(); i++) {
                System.out.println(" Partido " + (i + 1) + ": " + partidosResultantes.get(i));
            }
            System.out.println("-----------------------------------\n");

            // Guardado en Archivos (Persistencia)
            guardarResultadosTexto(etapa, partidosResultantes);
            guardarResultadosBinarios(etapa, partidosResultantes);

        } catch (SorteoException e) {
            // Captura las excepciones personalizadas
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error imprevisto
            System.err.println("Ocurrio un error inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

     //RECURSIVIDAD DEL SORTEO
     //Divide el problema removiendo 2 equipos en cada ciclo recursivo.
     
    public static void realizarSorteoRecursivo(ArrayList<String> equipos, ArrayList<Partido> acumulador) {
        // Caso Base Si quedan menos de 2 equipos, detiene la recursion
        if (equipos.size() < 2) {
            return;
        }
        // Caso recursivo Toma los dos primeros de la lista (ya mezclada aleatoriamente)
        String competidor = equipos.remove(0);
        String oponente = equipos.remove(0);

        // Creamos el objeto Partido y lo añadimos al acumulador
        acumulador.add(new Partido(competidor, oponente));

        // Llamada recursiva con el resto de la lista modificada
        realizarSorteoRecursivo(equipos, acumulador);
    }

     // ARCHIVOS DE TEXTO
     // Escribe los datos en un formato TXT legible usando BufferedWriter.
     
    public static void guardarResultadosTexto(String etapa, ArrayList<Partido> partidos) {
        String nombreArchivo = "sorteo_" + etapa + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            bw.write("--- SORTEO DE LA ETAPA: " + etapa.toUpperCase() + " ---\n");
            for (int i = 0; i < partidos.size(); i++) {
                bw.write("Partido " + (i + 1) + ": " + partidos.get(i).toString() + "\n");
            }
            System.out.println("[OK] Sorteo guardado en formato de texto: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo de texto: " + e.getMessage());
        }
    }

     // ARCHIVOS BINARIOS Y SERIALIZACION
     // Convierte la lista de objetos 'Partido' en bytes y los almacena en un archivo .dat (.bin)
     
    public static void guardarResultadosBinarios(String etapa, ArrayList<Partido> partidos) {
        String nombreArchivo = "sorteo_" + etapa + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            // Serializamos la lista completa de objetos Partido
            oos.writeObject(partidos);
            System.out.println("[OK] Sorteo serializado y guardado en binario: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al serializar y guardar el archivo binario: " + e.getMessage());
        }
    }
}