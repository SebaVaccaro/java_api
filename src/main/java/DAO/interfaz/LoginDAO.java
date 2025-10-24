package DAO.interfaz;

import modelo.Usuario;
import java.sql.SQLException;

public interface LoginDAO {

    // Obtener usuario por username
    Usuario obtenerUsuarioPorUsername(String username) throws SQLException;
}
