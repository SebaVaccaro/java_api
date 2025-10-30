package PROXY;

import modelo.Incidencia;
import servicios.IncidenciaServicio;
import utils.ValidarUsuario;

import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaProxy {

    private final IncidenciaServicio incidenciaServicio;
    private final ValidarUsuario validarUsuario;

    public IncidenciaProxy() throws Exception {
        this.incidenciaServicio = new IncidenciaServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // ============================================================
    // CREAR INCIDENCIA - solo propietario
    // ============================================================
    public Incidencia crearIncidencia(String titulo,
                                      OffsetDateTime fecHora,
                                      String descripcion,
                                      boolean estActivo,
                                      int idFuncionario,
                                      String lugar) throws Exception {
        if (!validarUsuario.esPropietario(idFuncionario)) {
            throw new SecurityException("No tiene permiso para crear esta incidencia");
        }
        return incidenciaServicio.crearIncidencia(titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
    }

    // ============================================================
    // OBTENER INCIDENCIA - adm, psico o propietario
    // ============================================================
    public Incidencia obtenerIncidencia(int idIncidencia) throws Exception {
        //if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuarioPropietario)) {
        //    throw new SecurityException("No tiene permiso para ver esta incidencia");
        //}
        return incidenciaServicio.obtenerIncidencia(idIncidencia);
    }

    // ============================================================
    // LISTAR
    // ============================================================
    public List<Incidencia> listarIncidencias() throws Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tiene permiso para listar incidencias de funcionarios");
        }
        return incidenciaServicio.listarIncidencias();
    }

    // Listar por funcionario - solo adm o psico
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tiene permiso para listar incidencias de funcionarios");
        }
        return incidenciaServicio.listarPorFuncionario(idFuncionario);
    }

    // ============================================================
    // ACTUALIZAR INCIDENCIA - solo propietario
    // ============================================================
    public boolean actualizarIncidencia(int idIncidencia,
                                        String titulo,
                                        OffsetDateTime fecHora,
                                        String descripcion,
                                        boolean estActivo,
                                        int idFuncionario,
                                        String lugar) throws Exception {
        if (!validarUsuario.esPropietario(idFuncionario)) {
            throw new SecurityException("No tiene permiso para actualizar esta incidencia");
        }
        return incidenciaServicio.actualizarIncidencia(idIncidencia, titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
    }

    // ============================================================
    // ELIMINAR INCIDENCIA - solo administrador
    // ============================================================
    public boolean eliminarIncidencia(int idIncidencia) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("No tiene permiso para eliminar esta incidencia");
        }
        return incidenciaServicio.eliminarIncidencia(idIncidencia);
    }
}
