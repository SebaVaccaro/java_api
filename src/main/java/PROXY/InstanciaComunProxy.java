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

    // Constructor: inicializa el servicio de instancias comunes
    public InstanciaComunProxy() throws SQLException {
        this.instanciaServicio = new InstanciaComunServicio();
        this.validarUsuario = new ValidarUsuario();
        this.seguimientoServicio = new SeguimientoServicio();
    }

    // Crear instancia común (funcionarios)
    public InstanciaComun crearInstanciaComun(String titulo,
                                              OffsetDateTime fecHora,
                                              String descripcion,
                                              boolean estActivo,
                                              int idFuncionario,
                                              int idSeguimiento) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idFuncionario)) {
            throw new SecurityException("Solo funcionarios pueden crear una instancia comun");
        }
        return instanciaServicio.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Obtener instancia común por ID (admin, psico o propietar)
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        InstanciaComun instancia = instanciaServicio.obtenerInstanciaComun(idInstancia);
        Seguimiento seguimiento = seguimientoServicio.buscarPorId(instancia.getIdSeguimiento());
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(seguimiento.getIdEstudiante())) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario pueden obtener esta instancia.");
        }
        return instancia;
    }

    // Listar todas las instancias comunes (admin o psico)
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administrador o psicopedagogo pueden obtener esta lista.");
        }
        return instanciaServicio.listarInstanciasComunes();
    }

    // Listar instancias comunes por seguimiento (admin, psico o propietar)
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        Seguimiento seguimiento = seguimientoServicio.buscarPorId(idSeguimiento);
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(seguimiento.getIdEstudiante())) {
            throw new SecurityException("Solo administrador, psicopedagogo o el propietario del seguimiento pueden listar estas instancias.");
        }
        return instanciaServicio.listarPorSeguimiento(idSeguimiento);
    }

    // Actualizar instancia común (admin o psico)
    public boolean actualizarInstanciaComun(int idInstancia,
                                            String titulo,
                                            OffsetDateTime fecHora,
                                            String descripcion,
                                            boolean estActivo,
                                            int idFuncionario,
                                            int idSeguimiento) throws SQLException {

        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede actualizar una instancia comun.");
        }
        return instanciaServicio.actualizarInstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Eliminar instancia común (admin)
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        if (!validarUsuario.esAdministrador()){
            throw new SecurityException("Solo un administrador puede eliminar una instancia comun.");
        }
        return instanciaServicio.eliminarInstanciaComun(idInstancia);
    }
}
