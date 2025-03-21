package catalogo_bibliotecario;

public class Riviste extends Catalogo {
    private Periodicità periodicità;

    public Periodicità getPeriodicità() {
        return periodicità;
    }

    public void setPeriodicità(Periodicità periodicità) {
        this.periodicità = periodicità;
    }

    public Riviste(long codiceIsbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicità periodicità) {
        super(codiceIsbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicità = periodicità;
    }
}
