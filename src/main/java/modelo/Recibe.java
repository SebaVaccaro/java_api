package modelo;

public class Recibe {
    private int idNotificacion;  // FK hacia Notificacion
    private int idUsuario;       // FK hacia Usuario

    // Constructor vacío
    public Recibe() {
    }

    // Constructor completo
    public Recibe(int idNotificacion, int idUsuario) {
        this.idNotificacion = idNotificacion;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Recibe{" +
                "idNotificacion=" + idNotificacion +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
