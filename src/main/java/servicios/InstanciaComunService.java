package servicios;

import DAO.InstanciaComunDAOImpl;
import modelo.InstanciaComun;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunService {

    private final InstanciaComunDAOImpl dao;

    public InstanciaComunService() throws SQLException {
        this.dao = new InstanciaComunDAOImpl();
    }

    // 🔹 Crear nueva InstanciaComun completa
    public InstanciaComun crearInstanciaComun(String titulo,
                                              OffsetDateTime fecHora,
                                              String descripcion,
                                              boolean estActivo,
                                              int idFuncionario,
                                              int idSeguimiento) throws SQLException {
        InstanciaComun ic = new InstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
        return dao.crearInstanciaComun(ic);
    }

    // 🔹 Obtener InstanciaComun por idInstancia
    public InstanciaComun obtenerPorInstancia(int idInstancia) throws SQLException {
        return dao.obtenerPorInstancia(idInstancia);
    }

    // 🔹 Listar todas las InstanciaComun
    public List<InstanciaComun> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    // 🔹 Listar por idSeguimiento
    public List<InstanciaComun> listarPorSeguimiento(int idSeguimiento) throws SQLException {
        return dao.listarPorSeguimiento(idSeguimiento);
    }

    // 🔹 Actualizar InstanciaComun completa
    public boolean actualizarInstanciaComun(int idInstancia,
                                            String titulo,
                                            OffsetDateTime fecHora,
                                            String descripcion,
                                            boolean estActivo,
                                            int idFuncionario,
                                            int idSeguimiento) throws SQLException {
        InstanciaComun ic = new InstanciaComun(idInstancia, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
        return dao.actualizarInstanciaComun(ic);
    }

    // 🔹 Eliminar InstanciaComun por idInstancia
    public boolean eliminarPorInstancia(int idInstancia) throws SQLException {
        return dao.eliminarPorInstancia(idInstancia);
    }
}
