package servicios;

import DAO.FuncionarioDAO;
import modelo.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioService() throws SQLException {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // 🔹 Crear funcionario
    public Funcionario crearFuncionario(String cedula, String nombre, String apellido, String username,
                                        String password, String correo, int idRol, boolean estActivo) throws SQLException {
        Funcionario f = new Funcionario();
        f.setCedula(cedula);
        f.setNombre(nombre);
        f.setApellido(apellido);
        f.setUsername(username);
        f.setPassword(password);
        f.setCorreo(correo);
        f.setIdRol(idRol);
        f.setEstActivo(estActivo);

        return funcionarioDAO.crearFuncionario(f);
    }

    // 🔹 Obtener funcionario por ID
    public Funcionario obtenerPorId(int idUsuario) throws SQLException {
        return funcionarioDAO.obtenerFuncionario(idUsuario);
    }

    // 🔹 Listar todos los funcionarios
    public List<Funcionario> listarTodos() throws SQLException {
        return funcionarioDAO.listarFuncionarios();
    }

    // 🔹 Actualizar funcionario
    public boolean actualizarFuncionario(int idUsuario, String cedula, String nombre, String apellido, String username,
                                         String password, String correo, int idRol, boolean estActivo) throws SQLException {
        Funcionario f = new Funcionario(idUsuario, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
        return funcionarioDAO.actualizarFuncionario(f);
    }

    // 🔹 Baja lógica (desactivar funcionario)
    public boolean desactivarFuncionario(int idUsuario) throws SQLException {
        return funcionarioDAO.eliminarFuncionario(idUsuario);
    }

    // 🔹 Verificar si un funcionario está activo
    public boolean estaActivo(int idUsuario) throws SQLException {
        return funcionarioDAO.estaActivo(idUsuario);
    }
}
