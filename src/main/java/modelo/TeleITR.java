package modelo;

public class TeleITR {

    private int idTelefono;
    private String numero;
    private int idItr;

    // Constructor vacío
    public TeleITR() {
    }

    // Constructor sin ID (para insertar en la BD)
    public TeleITR(String numero, int idItr) {
        this.numero = numero;
        this.idItr = idItr;
    }

    // Constructor completo
    public TeleITR(int idTelefono, String numero, int idItr) {
        this.idTelefono = idTelefono;
        this.numero = numero;
        this.idItr = idItr;
    }

    // Getters y Setters
    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getIdItr() {
        return idItr;
    }

    public void setIdItr(int idItr) {
        this.idItr = idItr;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "TeleITR{" +
                "idTelefono=" + idTelefono +
                ", numero='" + numero + '\'' +
                ", idItr=" + idItr +
                '}';
    }
}
