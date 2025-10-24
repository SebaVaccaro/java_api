package facade;

import modelo.Funcionario;
import servicios.FuncionarioService;

import java.sql.SQLException;
import java.time.LocalDate;
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
                                        String password, int idRol, LocalDate fechaNacimiento)
            throws SQLException {
        try {
            return funcionarioService.registrarFuncionario(cedula, nombre, apellido, username, password, idRol, fechaNacimiento);
        } catch (Exception e) {
            // Convertimos las validaciones en SQLException para mantener uniformidad en la capa superior (UI)
            throw new SQLException("Error al registrar funcionario: " + e.getMessage(), e);
        }
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
    public boolean actualizarFuncionario(int idUsuario, String cedula, String nombre, String apellido,
                                         String username, String password, String correo,
                                         int idRol, boolean estActivo) throws SQLException {
        try {
            return funcionarioService.actualizarFuncionario(idUsuario, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
        } catch (Exception e) {
            throw new SQLException("Error al actualizar funcionario: " + e.getMessage(), e);
        }
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
