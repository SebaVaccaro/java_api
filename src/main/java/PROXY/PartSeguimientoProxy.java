package PROXY;

import modelo.PartSeguimiento;
import servicios.PartSeguimientoServicio;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoProxy {

    private final PartSeguimientoServicio partSeguimientoServicio;

    // Constructor: inicializa el servicio de participante-seguimiento
    public PartSeguimientoProxy() throws SQLException {
        this.partSeguimientoServicio = new PartSeguimientoServicio();
    }

    // Agregar participante a un seguimiento (sin restricción de permisos)
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.agregarParticipante(idParticipante, idSeguimiento);
    }

    // Eliminar participante de un seguimiento (sin restricción de permisos)
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.eliminarParticipante(idParticipante, idSeguimiento);
    }

    // Listar todas las relaciones participante-seguimiento (sin restricción de permisos)
    public List<PartSeguimiento> listarTodos() throws SQLException {
        return partSeguimientoServicio.listarTodos();
    }

    // Listar seguimientos de un participante (sin restricción de permisos)
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        return partSeguimientoServicio.listarSeguimientosPorParticipante(idParticipante);
    }

    // Listar participantes de un seguimiento (sin restricción de permisos)
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        return partSeguimientoServicio.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}

