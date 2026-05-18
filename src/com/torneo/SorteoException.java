package com.torneo;

//Clase base para nuestras excepciones del torneo
public class SorteoException extends Exception {
 	private static final long serialVersionUID = 1L;
 	public SorteoException(String mensaje) {
     super(mensaje);
 }
}
//Subclase para cuando la etapa no es válida
class EtapaInvalidaException extends SorteoException {
 private static final long serialVersionUID = 1L;
 public EtapaInvalidaException(String etapa) {
     super("Error: '" + etapa + "' no es una etapa valida. Elija: octavos, cuartos o semifinales");
 }
}
//Subclase para cuando faltan o sobran equipos
class CantidadEquiposException extends SorteoException {
 private static final long serialVersionUID = 1L;
 public CantidadEquiposException(String etapa, int esperados, int recibidos) {
     super("Error para " + etapa + ": Se esperaban " + esperados + " equipos, pero se registraron " + recibidos + ".");
 }
}
// excepcion para controlar nombres vacíos o inválidos
class NombreEquipoInvalidoException extends SorteoException {
 	private static final long serialVersionUID = 1L;
 public NombreEquipoInvalidoException(int numeroEquipo) {
     super("Error en Equipo " + numeroEquipo + ": El nombre del equipo no puede estar vacío ni ser un carácter invalido");
 }
}
//excepcion para evitar que existan equipos repetidos en la lista
class EquiposDuplicadosException extends SorteoException {
private static final long serialVersionUID = 1L;
 public EquiposDuplicadosException(String nombreEquipo) {
     super("Error: El equipo '" + nombreEquipo + "' ya ha sido registrado. No se permiten nombres duplicados en el torneo");
 }
}