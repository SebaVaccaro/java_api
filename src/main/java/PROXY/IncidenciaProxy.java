package PROXY;

import modelo.Incidencia;
import servicios.IncidenciaServicio;
import utils.ValidarUsuario;

import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaProxy {

    private final IncidenciaServicio incidenciaServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de incidencias y el validador de usuario
    public IncidenciaProxy() throws Exception {
        this.incidenciaServicio = new IncidenciaServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear incidencia (solo propietario)
    public Incidencia crearIncidencia(String titulo,
                                      OffsetDateTime fecHora,
                                      String descripcion,
                                      boolean estActivo,
                                      int idFuncionario,
                                      String lugar) throws Exception {

        if (!validarUsuario.esPropietario(idFuncionario) || validarUsuario.esEstudiante()) {
            throw new SecurityException("Solo el propietario puede crear sus incidencias.");
        }

        return incidenciaServicio.crearIncidencia(titulo, fecHora, descripcion, estActivo, idFuncionario, lugar);
    }

    // Obtener incidencia por ID (solo administradores, psicopedagogos o el propietario)
    public Incidencia obtenerIncidencia(int idIncidencia) throws Exception {
        Incidencia incidencia = incidenciaServicio.obtenerIncidencia(idIncidencia);

        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(incidencia.getIdFuncionario())
                && validarUsuario.esEstudiante()) {
            throw new SecurityException("Solo administradores, psicopedagogos o el propietario pueden ver esta incidencia.");
        }

        return incidencia;
    }

    // Listar todas las incidencias (solo administradores o psicopedagogos)
    public List<Incidencia> listarIncidencias() throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden listar incidencias.");
        }
        return incidenciaServicio.listarIncidencias();
    }

    // Listar incidencias por funcionario (solo administradores, psicopedagogos o el propietario)
    public List<Incidencia> listarPorFuncionario(int idFuncionario) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idFuncionario)) {
            throw new SecurityException("Solo administradores, psicopedagogos o el propietario pueden listar estas incidencias.");
        }
        return incidenciaServicio.listarPorFuncionario(idFuncionario);
    }

    // Actualizar incidencia (solo propietario)
    public boolean actualizarIncidencia(int idIncidencia,
                                        String titulo,
                                        String descripcion,
                                        boolean estActivo,
                                        String lugar) throws Exception {



        return incidenciaServicio.actualizarIncidencia(idIncidencia, titulo, descripcion, estActivo, lugar);
    }

    // Eliminar incidencia (solo administradores)
    public boolean eliminarIncidencia(int idIncidencia) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden eliminar incidencias.");
        }
        return incidenciaServicio.eliminarIncidencia(idIncidencia);
    }
}
