package modelo;

public class TeleUsuario {

    private int idTelefono;
    private String numero;
    private int idUsuario;

    // ============================================================
    // CONSTRUCTORES
    // ============================================================

    // Constructor vacío
    public TeleUsuario() {
    }

    // Constructor para crear un nuevo registro (sin ID)
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

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

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
}
