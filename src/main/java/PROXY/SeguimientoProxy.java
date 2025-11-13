package PROXY;

import modelo.Seguimiento;
import servicios.SeguimientoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoProxy {

    private final SeguimientoServicio seguimientoServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de seguimientos
    public SeguimientoProxy() throws SQLException {
        this.seguimientoServicio = new SeguimientoServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear seguimiento (administrador o psicopedagogo)
    public boolean agregarSeguimiento(Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administrador o psicopedagogo pueden crear un seguimiento.");
        }
        return seguimientoServicio.agregarSeguimiento(idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // Obtener seguimiento por ID (administrador, psicopedagogo o propietario)
    public Seguimiento buscarPorId(int idSeguimiento) throws SQLException {
        Seguimiento seguimiento = seguimientoServicio.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("No se encontro seguimiento.");
        }
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(seguimiento.getIdEstudiante())) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden consultar este seguimiento.");
        }
        return seguimiento;
    }

    // Listar todos los seguimientos (administrador o psicopedagogo)
    public List<Seguimiento> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administrador o psicopedagogo pueden listar todos los seguimientos.");
        }
        return seguimientoServicio.listarTodos();
    }

    // Listar todos los seguimientos de un estudiante (administrador, psicopedagogo o propietario)
    public List<Seguimiento> listarPorEstudiante(int idEstudiante) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idEstudiante)){
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden consultar este seguimiento.");
        }
        return seguimientoServicio.listarPorEstudiante(idEstudiante);
    }

    // Actualizar seguimiento (administrador o psicopedagogo)
    public boolean actualizarSeguimiento(int idSeguimiento, Integer idInforme, int idEstudiante, LocalDate fecInicio, LocalDate fecCierre, boolean estActivo) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administrador o psicopedagogo pueden actualizar un seguimiento.");
        }
        return seguimientoServicio.actualizarSeguimiento(idSeguimiento, idInforme, idEstudiante, fecInicio, fecCierre, estActivo);
    }

    // Eliminar seguimiento (administrador)
    public boolean eliminarSeguimiento(int idSeguimiento) throws SQLException {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede eliminar un seguimiento.");
        }
        return seguimientoServicio.eliminarSeguimiento(idSeguimiento);
    }
}

