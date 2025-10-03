package service;

import java.time.LocalDate;
import DAO.FuncionarioDAO;
import algoritmos.Encriptador;
import algoritmos.ValidadorCI;
import algoritmos.ValidadorEdad;
import algoritmos.ValidadorPassword;
import modelo.Estudiante;
import modelo.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioServicio {

    private FuncionarioDAO dao;

    public FuncionarioServicio() throws SQLException {
        this.dao = new FuncionarioDAO();
    }

    /**
     * Registra un funcionario nuevo con validaciones y generación de correo.
     */
    public Funcionario registrarFuncionario(Funcionario.Rol rol, String ci, String username, String password, String nombre, String apellido, LocalDate fechaNacimiento) throws Exception {

        // Validar CI
        if (!ValidadorCI.validarCI(ci)) {
            throw new Exception("CI inválida");
        }

        // Validar edad
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) {
            throw new Exception("El funcionario debe ser mayor de 18 años");
        }

// Validar longitud de password
        if (!ValidadorPassword.validar(password)) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres");
        }


        // Generar correo institucional automáticamente
        String correo = (nombre + "." + apellido).toLowerCase().replaceAll("\\s+", "");
        correo += "@utec.edu.uy";

        // Encriptar contraseña
        String passEnc = Encriptador.encriptar(password);

        // Crear objeto funcionario
        int dummyId = 0;
        Funcionario fun = new Funcionario(rol, dummyId, ci, username, passEnc, nombre, apellido, correo, null, null);

        // Insertar en la base
        return dao.crearFuncionario(fun);
    }

    public Estudiante crearEstudianteDesdeFuncionario(Funcionario f, String ci, String username, String password,
                                                      String nombre, String apellido, LocalDate fechaNacimiento,
                                                      int idGrupo) throws Exception {
        if (!puedeCrearUsuario(f)) {
            throw new Exception("❌ No tiene permisos para crear estudiantes.");
        }

        EstudianteServicio estService = new EstudianteServicio();
        return estService.registrarEstudiante(ci, username, password, nombre, apellido, fechaNacimiento, idGrupo);
    }

    /** Valida si un funcionario puede modificar usuarios */
    public boolean puedeModificar(Funcionario funcionario) {
        return funcionario.getRol() == Funcionario.Rol.ADMINISTRADOR;
    }

    /** Valida si un funcionario puede consultar información confidencial */
    public boolean puedeConsultarConfidencial(Funcionario funcionario) {
        return funcionario.getRol() == Funcionario.Rol.ADMINISTRADOR
                || funcionario.getRol() == Funcionario.Rol.PSICOPEDAGOGO;
    }

    /**
     * Obtiene un funcionario por ID
     */
    public Funcionario obtenerFuncionario(int id) throws SQLException {
        return dao.obtenerFuncionario(id);
    }

    /**
     * Lista todos los funcionarios
     */
    public List<Funcionario> listarFuncionarios() throws SQLException {
        return dao.listarFuncionarios();
    }
    public boolean puedeCrearUsuario(Funcionario funcionario) {
        return funcionario.getRol() == Funcionario.Rol.ADMINISTRADOR;
    }

// Actualizar Funcionario

    public boolean actualizarFuncionario(Funcionario f) throws SQLException {
        // Opcional: validar campos antes de actualizar
        if (!ValidadorCI.validarCI(f.getCi())) {
            throw new SQLException("CI inválida");
        }
        if (!ValidadorPassword.validar(f.getPassword())) {
            throw new SQLException("Contraseña demasiado corta");
        }

        // Llamada al DAO
        return dao.actualizarFuncionario(f);
    }


    /**
     * Baja lógica de funcionario
     */
    public boolean eliminarFuncionario(int id) throws SQLException {
        return dao.eliminarFuncionario(id);
    }
}
