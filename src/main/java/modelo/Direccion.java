package main.java.modelo;

public class Direccion {

    private Ciudad ciudad;
    private String calle;
    private int numeroPuerta;
    private int numeroApartamento;

    public Direccion(Ciudad ciudad, String calle, int numeroPuerta, int numeroApartamento) {
        this.ciudad = ciudad;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.numeroApartamento = numeroApartamento;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(int numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public int getNumeroApartamento() {
        return numeroApartamento;
    }

    public void setNumeroApartamento(int numeroApartamento) {
        this.numeroApartamento = numeroApartamento;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Direccion{" + "ciudad=" + ciudad + ", calle='" + calle + '\'' + ", numeroPuerta=" + numeroPuerta + ", numeroApartamento=" + numeroApartamento + '}';
    }
}