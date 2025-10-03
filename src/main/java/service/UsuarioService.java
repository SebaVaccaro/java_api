package service;

import DAO.UsuarioDAO;
import main.*;
import modelo.Funcionario;
import modelo.Usuario;

import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO dao;
    private Usuario usuarioActual; // sesión actual

    public UsuarioService() throws SQLException {
        this.dao = new UsuarioDAO();
    }

    /**
     * Login y guarda usuario actual
     */
    public Usuario login(String username, String password) throws Exception {
        Usuario user = dao.login(username, password);
        if (user == null) throw new Exception("Usuario no encontrado o contraseña incorrecta.");
        this.usuarioActual = user;
        return user;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Validaciones de permisos para funcionarios
     */
    public boolean puedeCrearUsuario() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR;
        }
        return false;
    }

    public boolean puedeModificarUsuario() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR;
        }
        return false;
    }

    public boolean puedeConsultarConfidencial() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR ||
                    f.getRol() == Funcionario.Rol.PSICOPEDAGOGO;
        }
        return false;
    }
}