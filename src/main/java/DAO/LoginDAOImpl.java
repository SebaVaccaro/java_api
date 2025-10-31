package DAO;

import DAO.interfaz.LoginDAO;
import modelo.Usuario;
import SINGLETON.ConexionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementación del DAO para la funcionalidad de Login.
 * Gestiona el acceso y autenticación de usuarios en la base de datos.
 */
public class LoginDAOImpl implements LoginDAO {

    // Conexión única a la base de datos mediante el Singleton
    private final Connection connection;

    // Constructor: obtiene la conexión desde el Singleton
    public LoginDAOImpl() throws SQLException {
        this.connection = ConexionSingleton.getInstance().getConexion();
    }

    // Obtener un usuario por su nombre de usuario (username)
    // Retorna un objeto Usuario si existe, o null si no se encuentra
    @Override
    public Usuario obtenerUsuarioPorUsername(String username) throws SQLException {
        String sql = "SELECT id_usuario, cedula, nombre, apellido, username, password, correo " +
                "FROM usuarios WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("correo")
                ) {};
            }
        }
        return null;
    }
}
