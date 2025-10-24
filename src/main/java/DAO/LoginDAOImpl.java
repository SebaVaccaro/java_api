package DAO;

import DAO.interfaz.LoginDAO;
import modelo.Usuario;
import SINGLETON.ConexionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl implements LoginDAO {

    private final Connection connection;

    public LoginDAOImpl() throws SQLException {
        this.connection = ConexionSingleton.getInstance().getConexion();
    }

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
                ){};
            }
        }
        return null;
    }
}
