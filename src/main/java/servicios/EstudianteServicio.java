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

    // Constructor: inicializa DAOs y conexión
    public EstudianteServicio() throws SQLException {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.estudianteDAO = new EstudianteDAOImpl();
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    // Registrar estudiante con inserción en usuario y estudiante
    public Estudiante registrarEstudiante(String ci, String password,
                                          String nombre, String apellido, LocalDate fechaNacimiento,
                                          int idGrupo) throws Exception {

        // Validar CI, edad y contraseña
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        String correo = generarCorreoEstudiante(nombre, apellido);
        String passEnc = Encriptador.encriptar(password);

        Estudiante est = new Estudiante(0, ci, nombre, apellido, nombre + "." + apellido, passEnc, correo, idGrupo, false);

        // Transacción atómica
        try {
            conn.setAutoCommit(false);

            int idUsuario = usuarioDAO.insertarUsuario(est);
            est.setIdUsuario(idUsuario);

            estudianteDAO.insertarEstudiante(est);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw new SQLException("Error al crear estudiante: " + e.getMessage(), e);
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }

        return est;
    }

    // Obtener estudiante por ID
    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        return estudianteDAO.obtenerEstudiante(idUsuario);
    }

    // Listar todos los estudiantes
    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteDAO.listarEstudiantes();
    }

    // Actualizar estudiante con transacción
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

    // Desactivar estudiante
    public boolean desactivarEstudiante(int idUsuario) throws SQLException {
        return estudianteDAO.eliminarEstudiante(idUsuario);
    }

    // Verificar si el estudiante está activo
    public boolean estaActivo(int idUsuario) throws SQLException {
        return estudianteDAO.estaActivo(idUsuario);
    }

    // Generar correo institucional para el estudiante
    private String generarCorreoEstudiante(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@estudiantes.utec.edu.uy";
    }
}
