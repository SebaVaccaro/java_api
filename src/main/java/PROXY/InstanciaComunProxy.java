package PROXY;

import modelo.InstanciaComun;
import servicios.InstanciaComunServicio;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunProxy {

    private final InstanciaComunServicio instanciaServicio;

    // Constructor: inicializa el servicio de instancias comunes
    public InstanciaComunProxy() throws SQLException {
        this.instanciaServicio = new InstanciaComunServicio();
    }

    // Crear instancia común (sin restricción de permisos)
    public InstanciaComun crearInstanciaComun(String titulo,
                                              OffsetDateTime fecHora,
                                              String descripcion,
                                              boolean estActivo,
                                              int idFuncionario,
                                              int idSeguimiento) throws SQLException {
        return instanciaServicio.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Obtener instancia común por ID (sin restricción de permisos)
    public InstanciaComun obtenerInstanciaComun(int idInstancia) throws SQLException {
        return instanciaServicio.obtenerInstanciaComun(idInstancia);
    }

    // Listar todas las instancias comunes (sin restricción de permisos)
    public List<InstanciaComun> listarInstanciasComunes() throws SQLException {
        return instanciaServicio.listarInstanciasComunes();
    }

    // Listar instancias comunes por seguimiento (sin restricción de permisos)
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return instanciaServicio.listarPorSeguimiento(idSeguimiento);
    }

    // Actualizar instancia común (sin restricción de permisos)
    public boolean actualizarInstanciaComun(int idInstancia,
                                            String titulo,
                                            OffsetDateTime fecHora,
                                            String descripcion,
                                            boolean estActivo,
                                            int idFuncionario,
                                            int idSeguimiento) throws SQLException {
        return instanciaServicio.actualizarInstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
    }

    // Eliminar instancia común (sin restricción de permisos)
    public boolean eliminarInstanciaComun(int idInstancia) throws SQLException {
        return instanciaServicio.eliminarInstanciaComun(idInstancia);
    }
}
