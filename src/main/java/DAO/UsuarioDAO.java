package DAO;
import modelo.Funcionario;
import DAO.EstudianteDAO;
import DAO.FuncionarioDAO;
import modelo.Estudiante;
import modelo.Usuario;

import java.sql.SQLException;

public class UsuarioDAO {

    private EstudianteDAO estudianteDAO;
    private FuncionarioDAO funcionarioDAO;

    public UsuarioDAO() throws SQLException {
        this.estudianteDAO = new EstudianteDAO();
        this.funcionarioDAO = new FuncionarioDAO();
    }

    /**
     * Valida credenciales y devuelve el objeto Usuario correspondiente
     */
    public Usuario login(String username, String password) throws SQLException {
        // Buscar en estudiantes
        for (Estudiante e : estudianteDAO.listarEstudiantes()) {
            if (e.getUsername().equals(username) && e.getPassword().equals(password)) {
                return e;
            }
        }

        // Buscar en funcionarios
        for (Funcionario f : funcionarioDAO.listarFuncionarios()) {
            if (f.getUsername().equals(username) && f.getPassword().equals(password)) {
                return f;
            }
        }

        // Si no se encuentra
        return null;
    }
}