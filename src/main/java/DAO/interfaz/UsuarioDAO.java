package DAO.interfaz;

import modelo.Usuario;

import java.sql.SQLException;

public interface UsuarioDAO {
    int insertarUsuario(Usuario usuario) throws SQLException;
    boolean actualizarUsuario(Usuario usuario) throws SQLException;
}