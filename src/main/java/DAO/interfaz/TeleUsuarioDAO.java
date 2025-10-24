package DAO.interfaz;

import modelo.TeleUsuario;
import java.sql.SQLException;
import java.util.List;

public interface TeleUsuarioDAO {

    // Crear un TeleUsuario
    TeleUsuario crearTeleUsuario(TeleUsuario teleUsuario) throws SQLException;

    // Obtener TeleUsuario por ID
    TeleUsuario obtenerTeleUsuario(int idTelefono) throws SQLException;

    // Listar todos los TeleUsuarios
    List<TeleUsuario> listarTodos() throws SQLException;

    // Actualizar TeleUsuario
    boolean actualizarTeleUsuario(TeleUsuario teleUsuario) throws SQLException;

    // Eliminar TeleUsuario por ID
    boolean eliminarTeleUsuario(int idTelefono) throws SQLException;
}
