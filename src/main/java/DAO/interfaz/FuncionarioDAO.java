package DAO.interfaz;

import modelo.Funcionario;

import java.sql.SQLException;
import java.util.List;

public interface FuncionarioDAO {
    void insertarFuncionario(Funcionario f) throws SQLException;
    Funcionario obtenerFuncionario(int idUsuario) throws SQLException;
    List<Funcionario> listarFuncionarios() throws SQLException;
    boolean actualizarFuncionario(Funcionario f) throws SQLException;
    boolean eliminarFuncionario(int idUsuario) throws SQLException;
    boolean estaActivo(int idUsuario) throws SQLException;
}