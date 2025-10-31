package modelo;

public class TeleUsuario {

    private int idTelefono;
    private String numero;
    private int idUsuario;

    // Constructor vacío
    public TeleUsuario() {
    }

    // Constructor sin ID (para insertar en la BD)
    public TeleUsuario(String numero, int idUsuario) {
        this.numero = numero;
        this.idUsuario = idUsuario;
    }

    // Constructor completo
    public TeleUsuario(int idTelefono, String numero, int idUsuario) {
        this.idTelefono = idTelefono;
        this.numero = numero;
        this.idUsuario = idUsuario;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "TeleUsuario{" +
                "idTelefono=" + idTelefono +
                ", numero='" + numero + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
