package servicios;

import DAO.EstudianteDAOImpl;
import DAO.UsuarioDAOImpl;
import DAO.interfaz.EstudianteDAO;
import DAO.interfaz.UsuarioDAO;
import SINGLETON.ConexionSingleton;
import algoritmos.Encriptador;
import algoritmos.ValidadorCI;
import algoritmos.ValidadorEdad;
import algoritmos.ValidadorPassword;
import modelo.Estudiante;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EstudianteServicio {

    private final UsuarioDAO usuarioDAO;
    private final EstudianteDAO estudianteDAO;
    private final Connection conn;

    public EstudianteServicio() throws SQLException {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.estudianteDAO = new EstudianteDAOImpl();
        this.conn = ConexionSingleton.getInstance().getConexion();// Conexión manejada internamente por los DAOs
    }

    // Crear estudiante coordinando inserción en usuario y estudiante
    public Estudiante registrarEstudiante(String ci, String password,
                                          String nombre, String apellido, LocalDate fechaNacimiento,
                                          int idGrupo) throws Exception {

        // Validaciones
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        String correo = generarCorreoEstudiante(nombre, apellido);
        String passEnc = Encriptador.encriptar(password);

        Estudiante est = new Estudiante(0, ci, nombre, apellido, nombre + "." + apellido, passEnc, correo, idGrupo, false);

        // Coordinación de inserción atómica
        try {
            conn.setAutoCommit(false); // inicio transacción

            int idUsuario = usuarioDAO.insertarUsuario(est);
            est.setIdUsuario(idUsuario);

            estudianteDAO.insertarEstudiante(est);

            conn.commit(); // commit de ambas tablas
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Error al crear estudiante: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }

        return est;
    }

    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteDAO.obtenerEstudiante(idUsuario);
    }

    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteDAO.listarEstudiantes();
    }

    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        boolean exito = false;
        try {
            conn.setAutoCommit(false);

            boolean ok1 = usuarioDAO.actualizarUsuario(est);
            boolean ok2 = estudianteDAO.actualizarEstudiante(est);

            if (ok1 && ok2) {
                conn.commit();
                exito = true;
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
        return exito;
    }

    public boolean desactivarEstudiante(int idUsuario) throws SQLException {
        return estudianteDAO.eliminarEstudiante(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteDAO.estaActivo(idUsuario);
    }

    private String generarCorreoEstudiante(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@estudiantes.utec.edu.uy";
    }
}