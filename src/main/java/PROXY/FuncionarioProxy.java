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

    public FuncionarioProxy() throws Exception {
        this.funcionarioServicio = new FuncionarioServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    public Funcionario crearFuncionario(String cedula, String nombre, String apellido,
                                        String password, int idRol, LocalDate fechaNacimiento)
            throws Exception {

        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden crear funcionarios.");
        }
        return funcionarioServicio.registrarFuncionario(cedula, nombre, apellido, password, idRol, fechaNacimiento);
    }

    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("No tiene permiso para obtener este funcionario.");
        }
        return funcionarioServicio.obtenerPorId(idUsuario);
    }

    public List<Funcionario> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogo pueden listar funcionarios.");
        }
        return funcionarioServicio.listarTodos();
    }

    public boolean actualizarFuncionario(int idUsuario, String cedula, String nombre, String apellido,
                                         String username, String password, String correo,
                                         int idRol, boolean estActivo) throws Exception {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idUsuario)) {
            throw new SecurityException("No tiene permiso para actualizar funcionario.");
        }
        return funcionarioServicio.actualizarFuncionario(idUsuario, cedula, nombre, apellido,
                username, password, correo, idRol, estActivo);
    }

    public boolean desactivarFuncionario(int idUsuario) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden desactivar funcionarios.");
        }
        return funcionarioServicio.desactivarFuncionario(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("No tiene permiso para ver el estado de este funcionario.");
        }
        return funcionarioServicio.estaActivo(idUsuario);
    }
}


