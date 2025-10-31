package PROXY;

import modelo.Funcionario;
import servicios.FuncionarioServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioProxy {

    private final FuncionarioServicio funcionarioServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de funcionarios y el validador de usuario
    public FuncionarioProxy() throws Exception {
        this.funcionarioServicio = new FuncionarioServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear funcionario (solo administradores)
    public Funcionario crearFuncionario(String cedula, String nombre, String apellido, String username,
                                        String password, int idRol, LocalDate fechaNacimiento)
            throws Exception {

        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear funcionarios.");
        }

        try {
            return funcionarioServicio.registrarFuncionario(cedula, nombre, apellido, username, password, idRol, fechaNacimiento);
        } catch (Exception e) {
            throw new SQLException("Error al registrar funcionario: " + e.getMessage(), e);
        }
    }

    // Obtener funcionario por ID (sin restricción de permisos)
    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        return funcionarioServicio.obtenerPorId(idUsuario);
    }

    // Listar todos los funcionarios (sin restricción de permisos)
    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioServicio.listarTodos();
    }

    // Actualizar funcionario (sin restricción de permisos)
    public boolean actualizarFuncionario(int idUsuario, String cedula, String nombre, String apellido,
                                         String username, String password, String correo,
                                         int idRol, boolean estActivo) throws SQLException {
        try {
            return funcionarioServicio.actualizarFuncionario(idUsuario, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
        } catch (Exception e) {
            throw new SQLException("Error al actualizar funcionario: " + e.getMessage(), e);
        }
    }

    // Desactivar funcionario (solo administradores)
    public boolean desactivarFuncionario(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden desactivar funcionarios.");
        }
        return funcionarioServicio.desactivarFuncionario(idUsuario);
    }

    // Verificar si un funcionario está activo (sin restricción de permisos)
    public boolean estaActivo(int idUsuario) throws SQLException {
        return funcionarioServicio.estaActivo(idUsuario);
    }
}
