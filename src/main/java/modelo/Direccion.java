package main.java.modelo;

public class Direccion {
    private int id;
    private Ciudad ciudad;
    private String calle;
    private String numPuerta;
    private String numApto;
    private Usuario usuario;

    public Direccion(int id, Ciudad ciudad, String calle, String numPuerta, String numApto, Usuario usuario) {
        this.id = id;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numPuerta = numPuerta;
        this.numApto = numApto;
        this.usuario = usuario;
    }

    public int getId() { return id; }
    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumPuerta() { return numPuerta; }
    public void setNumPuerta(String numPuerta) { this.numPuerta = numPuerta; }
    public String getNumApto() { return numApto; }
    public void setNumApto(String numApto) { this.numApto = numApto; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return "Direccion{" + "id=" + id + ", ciudad=" + ciudad + ", calle='" + calle + '\'' + ", numPuerta='" + numPuerta + '\'' + ", numApto='" + numApto + '\'' + '}';
    }
}