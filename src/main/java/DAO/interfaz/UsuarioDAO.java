package DAO.interfaz;

import modelo.Usuario;
import java.sql.SQLException;

public interface UsuarioDAO {

    // Insertar un nuevo usuario en la base de datos y devolver su ID generado
    int insertarUsuario(Usuario usuario) throws SQLException;

    // Actualizar la información de un usuario existente
    boolean actualizarUsuario(Usuario usuario) throws SQLException;
}
