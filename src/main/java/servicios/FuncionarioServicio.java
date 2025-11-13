package servicios;

import DAO.UsuarioDAOImpl;
import DAO.FuncionarioDAOImpl;
import DAO.interfaz.UsuarioDAO;
import DAO.interfaz.FuncionarioDAO;
import SINGLETON.ConexionSingleton;
import algoritmos.Encriptador;
import algoritmos.ValidadorCI;
import algoritmos.ValidadorPassword;
import algoritmos.ValidadorEdad;
import modelo.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioServicio {

    private final UsuarioDAO usuarioDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final Connection conn;

    public FuncionarioServicio() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.funcionarioDAO = new FuncionarioDAOImpl(conn);
    }

    // Registrar funcionario con transacción
    public Funcionario registrarFuncionario(String ci, String nombre, String apellido,
                                            String password, int idRol,
                                            LocalDate fechaNacimiento) throws Exception {

        // Validaciones
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        String correo = generarCorreoFuncionario(nombre, apellido);
        String passEnc = Encriptador.encriptar(password);

        Funcionario f = new Funcionario(0, ci, nombre, apellido, nombre + "." + apellido, passEnc, correo, idRol, false);

        try {
            conn.setAutoCommit(false);

            int idUsuario = usuarioDAO.insertarUsuario(f);
            f.setIdUsuario(idUsuario);

            funcionarioDAO.insertarFuncionario(f);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }

        return f;
    }

    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        Funcionario funcionario = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (funcionario == null) {
            throw new IllegalArgumentException("No se encontró funcionario con esa ID.");
        }
        return funcionario;
    }

    public boolean actualizarFuncionario(int idUsuario, String ci, String nombre, String apellido,
                                         String username, String password, String correo,
                                         int idRol, boolean activo) throws Exception {

        Funcionario existente = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (existente == null) throw new IllegalArgumentException("No se encontró funcionario para actualizar.");

        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        String passEnc = Encriptador.encriptar(password);
        Funcionario f = new Funcionario(idUsuario, ci, nombre, apellido, username, passEnc, correo, idRol, activo);

        boolean exito = false;
        try {
            conn.setAutoCommit(false);

            boolean ok1 = usuarioDAO.actualizarUsuario(f);
            boolean ok2 = funcionarioDAO.actualizarFuncionario(f);

            if (ok1 && ok2) {
                conn.commit();
                exito = true;
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return exito;
    }

    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioDAO.listarFuncionarios();
    }

    public boolean desactivarFuncionario(int idUsuario) throws SQLException {
        Funcionario existente = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (existente == null) throw new IllegalArgumentException("No se encontró funcionario para desactivar.");
        return funcionarioDAO.eliminarFuncionario(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        Funcionario existente = funcionarioDAO.obtenerFuncionario(idUsuario);
        if (existente == null) throw new IllegalArgumentException("No se encontró funcionario con esa ID.");
        return funcionarioDAO.estaActivo(idUsuario);
    }

    private String generarCorreoFuncionario(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@utec.edu.uy";
    }
}
