package DAO;

import DAO.interfaz.UsuarioDAO;
import SINGLETON.ConexionSingleton;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAOImpl implements UsuarioDAO {

    // Conexión a la base de datos
    final Connection conn;

    // Constructor: obtiene la conexión desde el Singleton
    public UsuarioDAOImpl() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Insertar un nuevo usuario en la base de datos y devolver su ID generado
    @Override
    public int insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (cedula, nombre, apellido, username, password, correo) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getCedula());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getUsername());
            ps.setString(5, usuario.getPassword());
            ps.setString(6, usuario.getCorreo());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_usuario");
        }
        throw new SQLException("No se pudo obtener el id generado de usuario.");
    }

    // Actualizar los datos de un usuario existente
    @Override
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET cedula=?, nombre=?, apellido=?, username=?, password=?, correo=? WHERE id_usuario=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getCedula());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getUsername());
            ps.setString(5, usuario.getPassword());
            ps.setString(6, usuario.getCorreo());
            ps.setInt(7, usuario.getIdUsuario());
            return ps.executeUpdate() > 0;
        }
    }
}
