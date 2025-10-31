package DAO.interfaz;

import modelo.Funcionario;
import java.sql.SQLException;
import java.util.List;

public interface FuncionarioDAO {

    // Insertar nuevo funcionario en la base de datos
    void insertarFuncionario(Funcionario f) throws SQLException;

    // Obtener un funcionario por su ID de usuario
    Funcionario obtenerFuncionario(int idUsuario) throws SQLException;

    // Listar todos los funcionarios registrados
    List<Funcionario> listarFuncionarios() throws SQLException;

    // Actualizar los datos de un funcionario existente
    boolean actualizarFuncionario(Funcionario f) throws SQLException;

    // Eliminar (baja lógica o física) un funcionario según su ID
    boolean eliminarFuncionario(int idUsuario) throws SQLException;

    // Verificar si un funcionario está activo en el sistema
    boolean estaActivo(int idUsuario) throws SQLException;
}
