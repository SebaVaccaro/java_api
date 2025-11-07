package PROXY;

import modelo.PartSeguimiento;
import servicios.PartSeguimientoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoProxy {

    private final PartSeguimientoServicio partSeguimientoServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de participante-seguimiento
    public PartSeguimientoProxy() throws SQLException {
        this.partSeguimientoServicio = new PartSeguimientoServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Agregar participante a un seguimiento
    public boolean agregarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede agregar participantes a un seguimiento.");
        }
        return partSeguimientoServicio.agregarParticipante(idParticipante, idSeguimiento);
    }

    // Eliminar participante de un seguimiento
    public boolean eliminarParticipante(int idParticipante, int idSeguimiento) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede eliminar participantes de un seguimiento.");
        }
        return partSeguimientoServicio.eliminarParticipante(idParticipante, idSeguimiento);
    }

    // Listar todas las relaciones participante-seguimiento
    public List<PartSeguimiento> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede listar todas las relaciones participante-seguimiento.");
        }
        return partSeguimientoServicio.listarTodos();
    }

    // Listar seguimientos de un participante
    public List<Integer> listarSeguimientosPorParticipante(int idParticipante) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idParticipante)) {
            throw new SecurityException("Permisos insuficientes: se requiere ser administrador, psicopedagogo o el propietario del participante.");
        }
        return partSeguimientoServicio.listarSeguimientosPorParticipante(idParticipante);
    }

    // Listar participantes de un seguimiento
    public List<Integer> listarParticipantesPorSeguimiento(int idSeguimiento) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede listar los participantes de un seguimiento.");
        }
        return partSeguimientoServicio.listarParticipantesPorSeguimiento(idSeguimiento);
    }
}


