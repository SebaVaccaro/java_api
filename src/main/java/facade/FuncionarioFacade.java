package facade;

import modelo.Funcionario;
import servicios.FuncionarioService;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioFacade {

    private final FuncionarioService funcionarioService;

    public FuncionarioFacade() throws SQLException {
        this.funcionarioService = new FuncionarioService();
    }

    // ============================================================
    // CREAR FUNCIONARIO
    // ============================================================
    public Funcionario crearFuncionario(String cedula, String nombre, String apellido, String username,
                                        String password, String correo, int idRol, boolean estActivo) throws SQLException {
        return funcionarioService.crearFuncionario(cedula, nombre, apellido, username, password, correo, idRol, estActivo);
    }

    // ============================================================
    // OBTENER FUNCIONARIO
    // ============================================================
    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        return funcionarioService.obtenerPorId(idUsuario);
    }

    // ============================================================
    // LISTAR FUNCIONARIOS
    // ============================================================
    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioService.listarTodos();
    }

    // ============================================================
    // ACTUALIZAR FUNCIONARIO
    // ============================================================
    public boolean actualizarFuncionario(int idUsuario, String cedula, String nombre, String apellido, String username,
                                         String password, String correo, int idRol, boolean estActivo) throws SQLException {
        return funcionarioService.actualizarFuncionario(idUsuario, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
    }

    // ============================================================
    // BAJA LÓGICA (DESACTIVAR FUNCIONARIO)
    // ============================================================
    public boolean desactivarFuncionario(int idUsuario) throws SQLException {
        return funcionarioService.desactivarFuncionario(idUsuario);
    }

    // ============================================================
    // VERIFICAR ESTADO
    // ============================================================
    public boolean estaActivo(int idUsuario) throws SQLException {
        return funcionarioService.estaActivo(idUsuario);
    }
}
