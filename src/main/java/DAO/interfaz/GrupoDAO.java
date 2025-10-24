package DAO.interfaz;

import modelo.Grupo;
import java.sql.SQLException;
import java.util.List;

public interface GrupoDAO {

    // Crear nuevo grupo
    Grupo crearGrupo(Grupo grupo) throws SQLException;

    // Obtener grupo por ID
    Grupo obtenerGrupo(int idGrupo) throws SQLException;

    // Listar todos los grupos
    List<Grupo> listarGrupos() throws SQLException;

    // Listar grupos por carrera
    List<Grupo> listarPorCarrera(int idCarrera) throws SQLException;

    // Actualizar grupo
    boolean actualizarGrupo(Grupo grupo) throws SQLException;

    // Eliminar grupo
    boolean eliminarGrupo(int idGrupo) throws SQLException;
}
