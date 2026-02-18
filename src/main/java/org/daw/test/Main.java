package org.daw.test;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Pregunta> preguntes = inicialitzarPreguntes();
        Test test = new Test(preguntes);

        Scanner sc = new Scanner(System.in);
        boolean sortir = false;

        while (!sortir) {
            mostrarPreguntaActual(test);

            System.out.println("Tria una opció: (1)Endarrera - (2) Respondre - (3) Endavant - (4) Finalitzar");
            String opt = sc.nextLine().trim();
            if (opt.isEmpty()) continue;
            int op;
            try {
                op = Integer.parseInt(opt);
            } catch (NumberFormatException ex) {
                System.out.println("Entrada no vàlida");
                continue;
            }

            switch (op) {
                case 1:
                    if (!test.anarEndarrera()) {
                        System.out.println("Ja estàs a la primera pregunta.");
                    }
                    break;
                case 2:
                    // Respondre
                    String[] res = test.getRespostesPreguntaActual();
                    System.out.println("Tria la resposta (número):");
                    for (int i = 0; i < res.length; i++) {
                        System.out.printf("%d) %s\n", i + 1, res[i]);
                    }
                    String rline = sc.nextLine().trim();
                    int elegido;
                    try {
                        elegido = Integer.parseInt(rline);
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrada no vàlida");
                        break;
                    }
                    if (elegido < 1 || elegido > res.length) {
                        System.out.println("Opció fora de rang.");
                        break;
                    }
                    try {
                        test.respondre(elegido - 1);
                        System.out.println("La teva resposta és: " + elegido);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Resposta no vàlida: " + ex.getMessage());
                    }
                    // Avançar automàticament si es pot
                    test.anarEndavant();
                    break;
                case 3:
                    if (!test.anarEndavant()) {
                        System.out.println("Ja estàs a l'última pregunta.");
                    }
                    break;
                case 4:
                    sortir = true;
                    break;
                default:
                    System.out.println("Opció desconeguda.");
            }
        }

        double nota = test.solucionarTest();
        System.out.println("Has aconseguit un " + nota);
        sc.close();
    }

    public static ArrayList<Pregunta> inicialitzarPreguntes() {
        ArrayList<Pregunta> preguntes = new ArrayList<>();

        preguntes.add(new Pregunta("Qui va pintar el Guernica",
                new String[]{"Velàzquez", "Goya", "Picasso"}, 2));
        preguntes.add(new Pregunta("Qui va escriure Alicia al país de les meravelles",
                new String[]{"Grimm", "Stevenson", "Carrol"}, 2));
        preguntes.add(new Pregunta("Quina és la magnitud que relaciona espai i temps",
                new String[]{"velocitat", "temperatura", "pes"}, 0));
        preguntes.add(new Pregunta("Si el radi d'una circumferència és 4, el seu diàmetre és",
                new String[]{"4", "8", "12", "14"}, 1));
        preguntes.add(new Pregunta("Quants segons té 1 hora",
                new String[]{"420", "760", "3600"}, 2));
        preguntes.add(new Pregunta("Quin no és un llenguatge de programació",
                new String[]{"java", "php", "samsung"}, 2));
        preguntes.add(new Pregunta("Dins un termòmetre hi trobem",
                new String[]{"aigua", "aigua amb gas", "mercuri"}, 2));
        preguntes.add(new Pregunta("L'Acropolis es troba a",
                new String[]{"Atenes", "Roma", "París"}, 0));
        preguntes.add(new Pregunta("L'element químic amb símbol Fe és ",
                new String[]{"Estronci", "Ferro", "Feril·li"}, 1));
        preguntes.add(new Pregunta("La capital d'Estats Units és",
                new String[]{"Georgetown", "New York", "Washington"}, 2));

        return preguntes;
    }

    private static void mostrarPreguntaActual(Test test) {
        int idx = test.getNumeroPregunta();
        String enunciat = test.getEnunciatPreguntaActual();
        String[] res = test.getRespostesPreguntaActual();
        System.out.printf("pregunta %d.%s\n", idx + 1, enunciat);
        for (int i = 0; i < res.length; i++) {
            System.out.printf("%d.%s ", i + 1, res[i]);
        }
        System.out.println();
    }
}
