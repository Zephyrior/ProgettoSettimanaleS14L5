package test_catalogo;

import catalogo_bibliotecario.Libri;
import catalogo_bibliotecario.Periodicità;
import catalogo_bibliotecario.Riviste;

import java.util.*;

public class Archivio {
    public static void main(String[] args) {
        Set<Libri> libri = new HashSet<>();
        Set<Riviste> riviste = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        while(true){
        System.out.println("Aggiungi un elemento all'archivio:");
        System.out.println("Selezione 1 per aggiungere un libro, 2 per aggiungere una rivista, 0 per terminare");
        int scelta = scanner.nextInt();
        scanner.nextLine();
        if (scelta == 0) {
            System.out.println("Programma terminata. Grazie dell'utilizzo.");
            break;
        }
        switch (scelta) {
            case 1 -> {
                System.out.println("Inserisci il codice ISBN del libro:");
                long codiceIsbn = scanner.nextLong();
                scanner.nextLine();
                boolean isPresent = libri.stream()
                        .anyMatch(c -> c.getCodiceIsbn() == codiceIsbn);
                if (isPresent) {
                    System.out.println("Un libro dello stesso codice ISBN è già presente in archivio.");
                } else {
                    System.out.println("Inserisci il titolo del libro:");
                    String titolo = scanner.nextLine();
                    System.out.println("Inserisci l'autore del libro:");
                    String autore = scanner.nextLine();
                    System.out.println("Inserisci il genere del libro:");
                    String genere = scanner.nextLine();
                    System.out.println("Inserisci l'anno di pubblicazione del libro:");
                    int annoPubblicazione = scanner.nextInt();
                    System.out.println("Inserisci il numero di pagine del libro:");
                    int numeroPagine = scanner.nextInt();
                    Libri libro = new Libri(codiceIsbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
                    libri.add(libro);
                    System.out.println("Il libro, " + titolo + ", è stato aggiunto correttamente");
                }
            }
            case 2 -> {
                System.out.println("Inserisci il codice ISBN della rivista:");
                long codiceIsbn = scanner.nextLong();
                scanner.nextLine();
                boolean isPresent = riviste.stream()
                        .anyMatch(c -> c.getCodiceIsbn() == codiceIsbn);
                if (isPresent) {
                    System.out.println("Una rivista dello stesso codice ISBN è già presente in archivio.");
                } else {
                    System.out.println("Inserisci il titolo della rivista:");
                    String titolo = scanner.nextLine();
                    System.out.println("Inserisci l'anno di pubblicazione della rivista:");
                    int annoPubblicazione = scanner.nextInt();
                    System.out.println("Inserisci il numero di pagine della rivista:");
                    int numeroPagine = scanner.nextInt();
                    System.out.println("Inserisci la periodicità della rivista:");
                    System.out.println("1 - Settimanale, 2 - Mensile, 3 - Semestrale");
                    int sceltaPeriodicità = scanner.nextInt();
                    scanner.nextLine();

                    Periodicità periodicità = null;

                    switch (sceltaPeriodicità) {
                        case 1 -> periodicità = Periodicità.SETTIMANALE;
                        case 2 -> periodicità = Periodicità.MENSILE;
                        case 3 -> periodicità = Periodicità.SEMESTRALE;
                        default -> {
                            System.out.println("Periodicità non valida.");
                        }
                    }
                    if (periodicità != null) {
                        Riviste rivista = new Riviste(codiceIsbn, titolo, annoPubblicazione, numeroPagine, periodicità);
                        riviste.add(rivista);
                        System.out.println("La rivista, " + titolo + ", è stata aggiunta correttamente.");
                    } else {
                        System.out.println("Rivista non aggiuntà");
                    }
                }
            } default -> System.out.println("Scelta non valida, riprova");
        }
        }
        scanner.close();
        System.out.println("\nArchivio completato.");
        System.out.println("Hai aggiunto " + libri.size() + " libro(i) e " + riviste.size() + " rivista(e).");
    }
}
