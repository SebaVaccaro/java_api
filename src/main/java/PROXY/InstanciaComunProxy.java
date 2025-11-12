package PROXY;

import modelo.InstanciaComun;
import modelo.Seguimiento;
import servicios.InstanciaComunServicio;
import servicios.SeguimientoServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunProxy {

    private final InstanciaComunServicio instanciaServicio;
    private final ValidarUsuario validarUsuario;
    private final SeguimientoServicio seguimientoServicio;

    // Constructor: inicializa los servicios y validador
    public InstanciaComunProxy() throws SQLException {
        this.instanciaServicio = new InstanciaComunServicio();
        this.validarUsuario = new ValidarUsuario();
        this.seguimientoServicio = new SeguimientoServicio();
    }

    // Crear instancia común (solo funcionarios)
    public InstanciaComun crearInstanciaComun(String titulo,
                                              OffsetDateTime fecHora,
                                              String descripcion,
                                              boolean estActivo,
                                              int idFuncionario,
                                              int idSeguimiento) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idFuncionario)) {
            throw new SecurityException("Solo funcionarios pueden crear una instancia común.");
        }
        return instanciaServicio.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Obtener instancia común por ID (admin, psico o propietario)
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws Exception {
        InstanciaComun instancia = instanciaServicio.obtenerInstanciaComun(idInstancia);

        Seguimiento seguimiento = seguimientoServicio.buscarPorId(instancia.getIdSeguimiento());
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(seguimiento.getIdEstudiante())) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden ver esta instancia.");
        }

        return instancia;
    }

    // Listar todas las instancias comunes (admin o psico)
    public List<InstanciaComun> listarInstanciasComunes() throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administrador o psicopedagogo pueden listar instancias comunes.");
        }
        return instanciaServicio.listarInstanciasComunes();
    }

    // Listar instancias comunes por estudiante (admin, psico o propietario)
    public List<InstanciaComun> listarPorEstudiante(int idEstudiante) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idEstudiante)) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden listar las instancias de este estudiante.");
        }
        return instanciaServicio.listarPorEstudiante(idEstudiante);
    }

    // Listar instancias comunes por seguimiento (admin, psico o propietario)
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws Exception {
        Seguimiento seguimiento = seguimientoServicio.buscarPorId(idSeguimiento);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(seguimiento.getIdEstudiante())) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden listar estas instancias.");
        }
        return instanciaServicio.listarPorSeguimiento(idSeguimiento);
    }

    // Actualizar instancia común (admin, psico o propietario)
    public boolean actualizarInstanciaComun(int idInstancia,
                                            String titulo,
                                            OffsetDateTime fecHora,
                                            String descripcion,
                                            boolean estActivo,
                                            int idFuncionario,
                                            int idSeguimiento) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idFuncionario)) {
            throw new SecurityException("Solo un administrador, psicopedagogo o propietario puede actualizar una instancia común.");
        }
        return instanciaServicio.actualizarInstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Eliminar instancia común (solo admin)
    public boolean eliminarInstanciaComun(int idInstancia) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo un administrador puede eliminar una instancia común.");
        }
        return instanciaServicio.eliminarInstanciaComun(idInstancia);
    }
}
