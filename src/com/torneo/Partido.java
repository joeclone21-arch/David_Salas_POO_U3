package com.torneo;


import java.io.Serializable;

public class Partido implements Serializable {
    // Buenas prácticas de serialización en Java
    private static final long serialVersionUID = 1L; 
    
    private String competidor;
    private String oponente;

    public Partido(String competidor, String oponente) {
        this.competidor = competidor;
        this.oponente = oponente;
    }

    public String getCompetidor() { return competidor; }
    public String getOponente() { return oponente; }

    @Override
    public String toString() {
        return "[ " + competidor + " ] VS [ " + oponente + " ]";
    }
}