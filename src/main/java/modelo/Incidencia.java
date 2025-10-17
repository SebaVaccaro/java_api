package modelo;

public class Incidencia {
    private int idInstancia;    // PK y FK hacia Instancia
    private int idFuncionario;  // FK hacia Funcionario
    private String lugar;

    // Constructor vacío
    public Incidencia() {
    }

    // Constructor completo
    public Incidencia(int idInstancia, int idFuncionario, String lugar) {
        this.idInstancia = idInstancia;
        this.idFuncionario = idFuncionario;
        this.lugar = lugar;
    }

    // Getters y Setters
    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "idInstancia=" + idInstancia +
                ", idFuncionario=" + idFuncionario +
                ", lugar='" + lugar + '\'' +
                '}';
    }
}
