package test_catalogo;

import catalogo_bibliotecario.Libri;
import catalogo_bibliotecario.Periodicità;
import catalogo_bibliotecario.Riviste;
import exceptions.LibroORivistaNonEsistenteException;

import java.util.*;
import java.util.stream.Collectors;

public class Archivio {
    public static void main(String[] args) throws LibroORivistaNonEsistenteException {
        Set<Libri> libri = new HashSet<>();
        Set<Riviste> riviste = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenuto/a all'archivio");
        while(true) {
            System.out.println("Inserisci l'operazione desiderata.");
            System.out.println("1 - Aggiungere un libro o una rivista.");
            System.out.println("2 - Ricercare un libro o una rivista con codice ISBN.");
            System.out.println("3 - Cancellare un libro o una rivista.");
            System.out.println("4 - Ricercare libri o riviste per l'anno di pubblicazione.");
            System.out.println("5 - Ricercare un libro per autore.");
            System.out.println("6 - Aggiornare un libro o rivista già esistente tramite codice ISBN.");
            System.out.println("7 - Scoprire la statistica del catalogo.");
            System.out.println("0 - Terminare il programma.");

            int operazione = scanner.nextInt();
            scanner.nextLine();

            if (operazione == 0) {
                System.out.println("Programma terminata. Grazie dell'utilizzo.");
                break;
            }
            switch (operazione) {
                case 1 -> {
                    System.out.println("Aggiungi un elemento all'archivio:");
                    System.out.println("Selezione 1 per aggiungere un libro, 2 per aggiungere una rivista, 0 per terminare");
                    int scelta = scanner.nextInt();
                    scanner.nextLine();
                    if (scelta == 0) {
                        System.out.println("Programma di aggiunta terminato. Grazie dell'utilizzo.");
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
                                String titolo = scanner.nextLine().toLowerCase();
                                System.out.println("Inserisci l'autore del libro:");
                                String autore = scanner.nextLine().toLowerCase();
                                System.out.println("Inserisci il genere del libro:");
                                String genere = scanner.nextLine().toLowerCase();
                                System.out.println("Inserisci l'anno di pubblicazione del libro:");
                                int annoPubblicazione = scanner.nextInt();
                                System.out.println("Inserisci il numero di pagine del libro:");
                                int numeroPagine = scanner.nextInt();
                                scanner.nextLine();
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
                                scanner.nextLine();
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
                        }
                        default -> System.out.println("Scelta non valida, riprova");
                    }
                    System.out.println("\nArchivio completato.");
                    System.out.println("Hai aggiunto " + libri.size() + " libro(i) e " + riviste.size() + " rivista(e).");
                }
                case 2 -> {
                    if (!libri.isEmpty() || !riviste.isEmpty()) {
                        System.out.println("Selezione l'elemento da ricercare.");
                        System.out.println("1 - Libro");
                        System.out.println("2 - Rivista");
                        int libroORivista = scanner.nextInt();
                        scanner.nextLine();
                        switch(libroORivista){
                            case 1 -> {
                                if(libri.isEmpty()){
                                    System.out.println("Non ci sono libri presenti nell'archivio");
                                } else {
                                    boolean validISBN = false;
                                    while(!validISBN) {
                                        try {
                                            System.out.println("Inserisci il Codice ISBN del libro desiderato:");
                                            long libroDesiderato = scanner.nextLong();
                                            Libri libro = libri.stream()
                                                    .filter(l -> l.getCodiceIsbn() == libroDesiderato)
                                                    .findAny()
                                                    .orElseThrow(() -> new LibroORivistaNonEsistenteException("Il libro con ISBN " + libroDesiderato + " non trovato."));

                                            System.out.println("Il libro selezionato è " + libro.getTitolo() + " con codice ISBN: " + libro.getCodiceIsbn());
                                            validISBN = true;
                                        } catch (LibroORivistaNonEsistenteException e) {
                                            System.out.println("Riprova ad inserire un ISBN valido");
                                        }
                                    }
                                }
                            }
                            case 2 -> {
                                if(riviste.isEmpty()){
                                    System.out.println("Non ci sono riviste presenti nell'archivio");
                                } else {
                                    boolean validISBN = false;
                                    while (!validISBN) {
                                        try {
                                            System.out.println("Inserisci il Codice ISBN della rivista desiderata:");
                                            long rivistaDesiderato = scanner.nextLong();
                                            Riviste rivista = riviste.stream()
                                                    .filter(l -> l.getCodiceIsbn() == rivistaDesiderato)
                                                    .findAny()
                                                    .orElseThrow(() -> new LibroORivistaNonEsistenteException("La rivista con ISBN " + rivistaDesiderato + " non trovata."));
                                            System.out.println("La rivista selezionata è " + rivista.getTitolo() + " con codice ISBN:  " + rivista.getCodiceIsbn());
                                            validISBN = true;
                                        } catch (LibroORivistaNonEsistenteException e){
                                            System.out.println("Riprova ad inserire un ISBN valido");
                                        }
                                    }
                                }
                            } default -> {
                                System.out.println("Selezione non valida. Riprova");
                            }
                        }
                    } else {
                        System.out.println("Non ci sono né libri o né riviste presenti nell'archvio.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro o una rivista.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                } case 3 -> {
                    if(!libri.isEmpty() || !riviste.isEmpty()){
                        System.out.println("Selezione l'elemento da rimuovere.");
                        System.out.println("1 - Libro");
                        System.out.println("2 - Rivista");
                        int libroORivista = scanner.nextInt();
                        scanner.nextLine();
                        switch (libroORivista) {
                            case 1 -> {
                                if (libri.isEmpty()) {
                                    System.out.println("Non ci sono libri presenti nell'archivio");
                                } else {
                                    boolean validISBN = false;
                                    while (!validISBN) {
                                        try {
                                            System.out.println("Inserisci il Codice ISBN del libro desiderato:");
                                            long libroDesiderato = scanner.nextLong();
                                            Libri libro = libri.stream()
                                                    .filter(l -> l.getCodiceIsbn() == libroDesiderato)
                                                    .findAny()
                                                    .orElseThrow(() -> new LibroORivistaNonEsistenteException("Il libro con ISBN " + libroDesiderato + " non trovato."));
                                            System.out.println("la rivista da rimuovere è " + libro.getTitolo() + " con codice ISBN: " + libro.getCodiceIsbn() + " è stata rimossa con successo!");
                                            validISBN = true;
                                            libri.remove(libro);
                                        } catch (LibroORivistaNonEsistenteException e) {
                                            System.out.println("Riprova ad inserire un ISBN valido");
                                        }
                                    }
                                }
                            }
                            case 2 -> {
                                {
                                    if (riviste.isEmpty()) {
                                        System.out.println("Non ci sono riviste presenti nell'archivio");
                                    } else {
                                        boolean validISBN = false;
                                        while (!validISBN) {
                                            try {
                                                System.out.println("Inserisci il Codice ISBN della rivista desiderata:");
                                                long rivistaDesiderato = scanner.nextLong();
                                                Riviste rivista = riviste.stream()
                                                        .filter(l -> l.getCodiceIsbn() == rivistaDesiderato)
                                                        .findAny()
                                                        .orElseThrow(() -> new LibroORivistaNonEsistenteException("La rivista con ISBN " + rivistaDesiderato + " non trovata."));
                                                System.out.println("la rivista da rimuovere è " + rivista.getTitolo() + " con codice ISBN: " + rivista.getCodiceIsbn() + " è stata rimossa con successo!");
                                                validISBN = true;
                                                riviste.remove(rivista);
                                            } catch (LibroORivistaNonEsistenteException e) {
                                                System.out.println("Riprova ad inserire un ISBN valido");
                                            }
                                        }
                                    }
                                }
                            }default -> {
                                System.out.println("Selezione non valida. Riprova");
                            }
                        }
                    } else {
                        System.out.println("Non ci sono né libri o né riviste presenti nell'archvio.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro o una rivista.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                } case 4 -> {
                    if(!libri.isEmpty() || !riviste.isEmpty()){
                        System.out.println("Selezione l'elemento da ricercare per anno di pubblicazione.");
                        System.out.println("1 - Libro");
                        System.out.println("2 - Rivista");
                        int libroORivista = scanner.nextInt();
                        scanner.nextLine();
                        switch (libroORivista) {
                            case 1 -> {
                                if (libri.isEmpty()) {
                                    System.out.println("Non ci sono libri presenti nell'archivio");
                                } else {
                                    try {
                                        System.out.println("Inserisci il l'anno di pubblicazione desiderato:");
                                        int annoInteressato = scanner.nextInt();
                                        Set<Libri> libro = libri.stream()
                                                .filter(l -> l.getAnnoPubblicazione() == annoInteressato)
                                                .collect(Collectors.toSet());

                                        libro.forEach(l -> System.out.println(
                                                "Titolo: " + l.getTitolo() +
                                                        ", Autore: " + l.getAutore() +
                                                        ", Genere: " + l.getGenere() +
                                                        ", Anno di pubblicazione: " + l.getAnnoPubblicazione()
                                        ));

                                        if (libro.isEmpty()) {
                                            System.out.println("Prova a mettere un altro anno.");
                                            throw new LibroORivistaNonEsistenteException("Non ci sono libri all'anno inserito.");
                                        }

                                    } catch (LibroORivistaNonEsistenteException e) {
                                        System.out.println("Riprova ad inserire un altro anno.");
                                    }
                                }
                            }
                            case 2 -> {
                                if(riviste.isEmpty()){
                                    System.out.println("Non ci sono riviste presenti nell'archivio");
                                } else{
                                    try {
                                        System.out.println("Inserisci il l'anno di pubblicazione desiderato:");
                                        int annoInteressato = scanner.nextInt();
                                        Set<Riviste> Rivista = riviste.stream()
                                                .filter(l -> l.getAnnoPubblicazione() == annoInteressato)
                                                .collect(Collectors.toSet());

                                        Rivista.forEach(r -> System.out.println(
                                                "Titolo: " + r.getTitolo() +
                                                        ", Periodicità: " + r.getPeriodicità() +
                                                        ", Anno di pubblicazione: " + r.getAnnoPubblicazione()
                                        ));

                                        if(Rivista.isEmpty()){
                                            System.out.println("Prova a mettere un altro anno.");
                                            throw new LibroORivistaNonEsistenteException("Non ci sono riviste all'anno inserito.");
                                        }

                                    } catch (LibroORivistaNonEsistenteException e) {
                                        System.out.println("Riprova ad inserire un altro anno.");
                                    }
                                }
                            } default -> {
                                System.out.println("Selezione non valida.");
                            }
                        }
                    } else {
                        System.out.println("Non ci sono né libri o né riviste presenti nell'archvio.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro o una rivista.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                } case 5 -> {
                    if(!libri.isEmpty()){
                            if(libri.isEmpty()){
                                System.out.println("Non ci sono libri presenti nell'archivio");
                            } else {
                                try {
                                    System.out.println("Inserisci il l'autore da ricercare:");
                                    String autoreDesiderato = scanner.nextLine().toLowerCase();
                                    Set<Libri> libro = libri.stream()
                                            .filter(l -> l.getAutore().equals(autoreDesiderato))
                                            .collect(Collectors.toSet());

                                    libro.forEach(l -> System.out.println(
                                            "Titolo: " + l.getTitolo() +
                                                    ", Autore: " + l.getAutore() +
                                                    ", Genere: " + l.getGenere() +
                                                    ", Anno di pubblicazione: " + l.getAnnoPubblicazione()
                                    ));

                                    if(libro.isEmpty()){
                                        System.out.println("Prova a mettere un altro autore.");
                                        throw new LibroORivistaNonEsistenteException("Non ci sono libri con l'autore fornito.");
                                    }

                                } catch (LibroORivistaNonEsistenteException e) {
                                    System.out.println("Riprova ad inserire un altro anno.");
                                }
                            }
                    } else {
                        System.out.println("Non ci sono libri presenti nell'archvio.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                } case 6 -> {
                    if(!libri.isEmpty() || !riviste.isEmpty()){
                        System.out.println("Selezione l'elemento da aggiornare.");
                        System.out.println("1 - Libro");
                        System.out.println("2 - Rivista");
                        int libroORivista = scanner.nextInt();
                        scanner.nextLine();
                        switch (libroORivista) {
                            case 1 -> {
                                if (libri.isEmpty()) {
                                    System.out.println("Non ci sono libri presenti nell'archivio");
                                } else {
                                    try {
                                        System.out.println("Inserisci il codice ISBN del libro da aggiornare.");
                                        long codIsbn = scanner.nextLong();
                                        scanner.nextLine();
                                        Set<Libri> libro = libri.stream()
                                                .filter(l -> l.getCodiceIsbn() == codIsbn)
                                                .collect(Collectors.toSet());

                                        System.out.println("I dettagli del libro trovato è il seguente:");

                                        libro.forEach(l -> System.out.println(
                                                "Titolo: " + l.getTitolo() +
                                                        ", Autore: " + l.getAutore() +
                                                        ", Genere: " + l.getGenere() +
                                                        ", Anno di pubblicazione: " + l.getAnnoPubblicazione()
                                        ));

                                        if (libro != null) {
                                            libri.removeAll(libro);
                                            long codiceIsbn = codIsbn;
                                            System.out.println("Inserisci il titolo del nuovo libro:");
                                            String titolo = scanner.nextLine().toLowerCase();
                                            System.out.println("Inserisci l'autore del nuovo libro:");
                                            String autore = scanner.nextLine().toLowerCase();
                                            System.out.println("Inserisci il genere del nuovo libro:");
                                            String genere = scanner.nextLine().toLowerCase();
                                            System.out.println("Inserisci l'anno di pubblicazione del nuovo libro:");
                                            int annoPubblicazione = scanner.nextInt();
                                            System.out.println("Inserisci il numero di pagine del nuovo libro:");
                                            int numeroPagine = scanner.nextInt();
                                            scanner.nextLine();
                                            Libri nuovoLibro = new Libri(codiceIsbn, titolo, annoPubblicazione, numeroPagine, autore, genere);
                                            libri.add(nuovoLibro);
                                            System.out.println("Il libro con codice ISBN, " + codiceIsbn + ", è stato aggiornato correttamente");

                                            System.out.println("I dettagli del nuovo libro è il seguente:");

                                            System.out.println(
                                                    "Titolo: " + nuovoLibro.getTitolo() +
                                                            ", Autore: " + nuovoLibro.getAutore() +
                                                            ", Genere: " + nuovoLibro.getGenere() +
                                                            ", Anno di pubblicazione: " + nuovoLibro.getAnnoPubblicazione());

                                        } else {
                                            System.out.println("Codice ISBN inesistente.");
                                            throw new LibroORivistaNonEsistenteException("Fare un nuovo inserimento.");
                                        }

                                    } catch (LibroORivistaNonEsistenteException e) {
                                        System.out.println("Riprova ad inserire un altro codice ISBN");
                                    }
                                }
                            }
                            case 2 -> {
                                if (riviste.isEmpty()) {
                                    System.out.println("Non ci sono riviste presenti nell'archivio");
                                } else {
                                    try {
                                        System.out.println("Inserisci il codice ISBN del libro da aggiornare.");
                                        long codIsbn = scanner.nextLong();
                                        scanner.nextLine();
                                        Set<Riviste> rivista = riviste.stream()
                                                .filter(l -> l.getCodiceIsbn() == codIsbn)
                                                .collect(Collectors.toSet());

                                        System.out.println("I dettagli della rivista trovata è il seguente:");

                                        rivista.forEach(r -> System.out.println(
                                                "Titolo: " + r.getTitolo() +
                                                        ", Anno di pubblicazione: " + r.getAnnoPubblicazione() +
                                                        ", Periodicità: " + r.getPeriodicità()
                                        ));

                                        if (riviste != null) {
                                            riviste.removeAll(riviste);
                                            long codiceIsbn = codIsbn;
                                            System.out.println("Inserisci il titolo della rivista:");
                                            String titolo = scanner.nextLine();
                                            System.out.println("Inserisci l'anno di pubblicazione della rivista:");
                                            int annoPubblicazione = scanner.nextInt();
                                            System.out.println("Inserisci il numero di pagine della rivista:");
                                            int numeroPagine = scanner.nextInt();
                                            scanner.nextLine();
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
                                                Riviste nuovaRivista = new Riviste(codiceIsbn, titolo, annoPubblicazione, numeroPagine, periodicità);
                                                riviste.add(nuovaRivista);
                                                System.out.println("La rivista con codice ISBN, " + codiceIsbn + ", è stata aggiornata correttamente");

                                                System.out.println("I dettagli della nuova rivista è il seguente:");

                                                System.out.println(
                                                        "Titolo: " + nuovaRivista.getTitolo() +
                                                                ", Anno di pubblicazione: " + nuovaRivista.getAnnoPubblicazione() +
                                                                ", Periodicità: " + nuovaRivista.getPeriodicità());

                                            } else {
                                                System.out.println("Rivista non aggiuntà");
                                            }

                                        } else {
                                            System.out.println("Codice ISBN inesistente.");
                                            throw new LibroORivistaNonEsistenteException("Fare un nuovo inserimento.");
                                        }

                                    } catch (LibroORivistaNonEsistenteException e) {
                                        System.out.println("Riprova ad inserire un altro codice ISBN");
                                    }
                                }
                            } default -> {
                                System.out.println("Selezione non valida.");
                            }
                        }
                    } else {
                        System.out.println("Non ci sono né libri o né riviste presenti nell'archvio.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro o una rivista.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                } case 7 -> {
                    if(!libri.isEmpty() && !riviste.isEmpty()){
                        System.out.println("Statistica del catalogo:");
                        System.out.println("Numero di libri presenti: " + libri.size());
                        System.out.println("Numeri di riviste presenti: " + riviste.size());

                        IntSummaryStatistics statLibro = libri.stream()
                                .mapToInt(Libri::getNumeroPagine)
                                .summaryStatistics();
                        int numPagineMaxLibri = statLibro.getMax();

                        Set<Libri> libro = libri.stream()
                                .filter(l -> l.getNumeroPagine() == numPagineMaxLibri)
                                .collect(Collectors.toSet());

                        IntSummaryStatistics statRivista = riviste.stream()
                                .mapToInt(Riviste::getNumeroPagine)
                                .summaryStatistics();
                        int numPagineMaxRiviste = statRivista.getMax();
                        Set<Riviste> rivista = riviste.stream()
                                .filter(r -> r.getNumeroPagine() == numPagineMaxRiviste)
                                .collect(Collectors.toSet());

                        if (numPagineMaxLibri > numPagineMaxRiviste) {
                            System.out.println("Il libro con il numero massimo di pagine è:");
                            libro.forEach(l -> System.out.println(
                                    "Titolo: " + l.getTitolo() +
                                            ", Autore: " + l.getAutore() +
                                            ", Genere: " + l.getGenere() +
                                            ", Numero pagine: " + l.getNumeroPagine() +
                                            ", Anno di pubblicazione: " + l.getAnnoPubblicazione()));
                        } else if (numPagineMaxRiviste > numPagineMaxLibri) {
                            System.out.println("La rivista con il numero massimo di pagine è:");
                            rivista.forEach(r -> System.out.println(
                                    "Titolo: " + r.getTitolo() +
                                            ", Periodicità: " + r.getPeriodicità() +
                                            ", Numero pagine: " + r.getNumeroPagine() +
                                            ", Anno di pubblicazione: " + r.getAnnoPubblicazione()));
                        } else {
                            System.out.println("i libri e le riviste hanno lo stesso numero massimo di pagine: " + numPagineMaxLibri + " pagine.");
                        }
                        double mediaPagineLibri = statLibro.getAverage();
                        double mediaPagineRiviste = statRivista.getAverage();
                        double mediaPagineElementi = (mediaPagineLibri + mediaPagineRiviste) / 2;
                        System.out.println("Media delle pagine di tutti gli elementi: " + mediaPagineElementi);
                    } else {
                        System.out.println("Potrebbe non esserci né un libro né una rivista.");
                        System.out.println("Puoi scegliere 1 per aggiungere un libro o una rivista.");
                        System.out.println("O inserisci 0 per terminare");
                    }
                }
            }
        }
    }
}
