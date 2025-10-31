package modelo;

import java.time.LocalDate;

public class Seguimiento {

    private int idSeguimiento;
    private Integer idInforme;
    private int idEstudiante;
    private LocalDate fecInicio;
    private LocalDate fecCierre;
    private boolean estActivo;

    // Constructor vacío
    public Seguimiento() {
    }

    // Constructor sin ID (para insertar en la BD)
    public Seguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) {
        this.idInforme = idInforme;
        this.idEstudiante = idEstudiante;
        this.fecInicio = fecInicio;
        this.fecCierre = fecCierre;
        this.estActivo = estActivo;
    }

    // Constructor completo
    public Seguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) {
        this.idSeguimiento = idSeguimiento;
        this.idInforme = idInforme;
        this.idEstudiante = idEstudiante;
        this.fecInicio = fecInicio;
        this.fecCierre = fecCierre;
        this.estActivo = estActivo;
    }

    // Getters y Setters
    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public LocalDate getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(LocalDate fecInicio) {
        this.fecInicio = fecInicio;
    }

    public LocalDate getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(LocalDate fecCierre) {
        this.fecCierre = fecCierre;
    }

    public boolean isEstActivo() {
        return estActivo;
    }

    public void setEstActivo(boolean estActivo) {
        this.estActivo = estActivo;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "Seguimiento{" +
                "idSeguimiento=" + idSeguimiento +
                ", idInforme=" + idInforme +
                ", idEstudiante=" + idEstudiante +
                ", fecInicio=" + fecInicio +
                ", fecCierre=" + fecCierre +
                ", estActivo=" + estActivo +
                '}';
    }
}
