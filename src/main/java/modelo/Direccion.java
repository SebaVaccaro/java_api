package modelo;

public class Direccion {

    private int idDireccion;
    private String calle;
    private String numPuerta;
    private String numApto;
    private int idCiudad;
    private int idUsuario;

    // Constructor vacío
    public Direccion() {
    }

    // Constructor sin ID (para insertar en la BD)
    public Direccion(String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) {
        this.calle = calle;
        this.numPuerta = numPuerta;
        this.numApto = numApto;
        this.idCiudad = idCiudad;
        this.idUsuario = idUsuario;
    }

    // Constructor completo
    public Direccion(int idDireccion, String calle, String numPuerta, String numApto, int idCiudad, int idUsuario) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numPuerta = numPuerta;
        this.numApto = numApto;
        this.idCiudad = idCiudad;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumPuerta() {
        return numPuerta;
    }

    public void setNumPuerta(String numPuerta) {
        this.numPuerta = numPuerta;
    }

    public String getNumApto() {
        return numApto;
    }

    public void setNumApto(String numApto) {
        this.numApto = numApto;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
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
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", calle='" + calle + '\'' +
                ", numPuerta='" + numPuerta + '\'' +
                ", numApto='" + numApto + '\'' +
                ", idCiudad=" + idCiudad +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
