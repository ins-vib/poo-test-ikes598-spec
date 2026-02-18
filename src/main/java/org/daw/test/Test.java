/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.daw.test;

import java.util.ArrayList;

/**
 * Clase que representa el Test (contenedor de preguntas y respuestas del usuario).
 */
public class Test {

    private ArrayList<Pregunta> preguntes;
    private int[] respostes; // -1 = no contestada, otherwise index of chosen answer
    private int numPregunta; // current question index

    public Test(ArrayList<Pregunta> preguntes) {
        this.preguntes = new ArrayList<>(preguntes);
        this.respostes = new int[preguntes.size()];
        for (int i = 0; i < respostes.length; i++) {
            respostes[i] = -1; // unanswered
        }
        this.numPregunta = 0;
    }

    public String getEnunciatPreguntaActual() {
        return preguntes.get(numPregunta).getEnunciat();
    }

    public String[] getRespostesPreguntaActual() {
        // return the array of possible answers for the current question
        return preguntes.get(numPregunta).getRespostes();
    }

    public int getNumeroPregunta() {
        return numPregunta;
    }

    public void respondre(int respostaIndex) {
        if (respostaIndex < 0 || respostaIndex >= preguntes.get(numPregunta).getRespostes().length) {
            throw new IllegalArgumentException("Resposta fora de rang");
        }
        respostes[numPregunta] = respostaIndex;
    }

    public boolean anarEndavant() {
        if (numPregunta < preguntes.size() - 1) {
            numPregunta++;
            return true;
        }
        return false;
    }

    public boolean anarEndarrera() {
        if (numPregunta > 0) {
            numPregunta--;
            return true;
        }
        return false;
    }

    public double solucionarTest() {
        int n = preguntes.size();
        if (n == 0) return 0.0;
        double puntsPerCorrecta = 10.0 / n;
        double puntuacio = 0.0;

        for (int i = 0; i < n; i++) {
            int resposta = respostes[i];
            if (resposta == -1) continue; // no contestada
            Pregunta p = preguntes.get(i);
            if (resposta == p.getCorrecta()) {
                puntuacio += puntsPerCorrecta;
            } else {
                // penalització: puntsPerCorrecta / numeroRespostesPossibles
                double penalitzacio = puntsPerCorrecta / p.getRespostes().length;
                puntuacio -= penalitzacio;
            }
        }

        // Optional: do not allow negative scores below 0
        if (puntuacio < 0) puntuacio = 0.0;
        // Round to 2 decimals
        puntuacio = Math.round(puntuacio * 100.0) / 100.0;
        return puntuacio;
    }

    // Additional helper methods
    public int getTotalPreguntes() {
        return preguntes.size();
    }

    public int getRespostaActual() {
        return respostes[numPregunta];
    }
}
