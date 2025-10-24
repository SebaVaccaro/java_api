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

public class FuncionarioService {

    private final UsuarioDAO usuarioDAO;
    private final FuncionarioDAO funcionarioDAO;
    private final Connection conn;

    public FuncionarioService() throws SQLException {
        this.conn = ConexionSingleton.getInstance().getConexion();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.funcionarioDAO = new FuncionarioDAOImpl(conn);
    }

    // =====================================================
    // CREAR FUNCIONARIO
    // =====================================================
    public Funcionario registrarFuncionario(String ci, String nombre, String apellido,
                                            String username, String password, int idRol,
                                            LocalDate fechaNacimiento) throws Exception {

        // Validaciones
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        // Generar correo y encriptar contraseña
        String correo = generarCorreoFuncionario(nombre, apellido);
        String passEnc = Encriptador.encriptar(password);

        // Crear objeto Funcionario
        Funcionario f = new Funcionario(0, ci, nombre, apellido, username, passEnc, correo, idRol, false);

        // Transacción atómica
        try {
            conn.setAutoCommit(false);

            int idUsuario = usuarioDAO.insertarUsuario(f);
            f.setIdUsuario(idUsuario);

            funcionarioDAO.insertarFuncionario(f);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Error al crear funcionario: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }

        return f;
    }

    // =====================================================
    // ACTUALIZAR FUNCIONARIO
    // =====================================================
    public boolean actualizarFuncionario(int idUsuario, String ci, String nombre, String apellido,
                                         String username, String password, String correo,
                                         int idRol, boolean activo) throws Exception {

        // Validaciones
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        // Encriptar contraseña
        String passEnc = Encriptador.encriptar(password);

        // Crear objeto Funcionario
        Funcionario f = new Funcionario(idUsuario, ci, nombre, apellido, username, passEnc, correo, idRol, activo);

        // Transacción atómica
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

    // =====================================================
    // OBTENER FUNCIONARIO POR ID
    // =====================================================
    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        return funcionarioDAO.obtenerFuncionario(idUsuario);
    }

    // =====================================================
    // LISTAR FUNCIONARIOS
    // =====================================================
    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioDAO.listarFuncionarios();
    }

    // =====================================================
    // DESACTIVAR FUNCIONARIO
    // =====================================================
    public boolean desactivarFuncionario(int idUsuario) throws SQLException {
        return funcionarioDAO.eliminarFuncionario(idUsuario);
    }

    // =====================================================
    // VERIFICAR SI ESTÁ ACTIVO
    // =====================================================
    public boolean estaActivo(int idUsuario) throws SQLException {
        return funcionarioDAO.estaActivo(idUsuario);
    }

    // =====================================================
    // UTILIDAD: generar correo institucional
    // =====================================================
    private String generarCorreoFuncionario(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@funcionarios.utec.edu.uy";
    }
}