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
        this.conn = ConexionSingleton.getInstance().getConexion();
    }

    public Estudiante registrarEstudiante(String ci, String password,
                                          String nombre, String apellido, LocalDate fechaNacimiento,
                                          int idGrupo) throws Exception {

        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
        if (!ValidadorPassword.validar(password)) throw new Exception("La contraseña debe tener al menos 8 caracteres");

        String correo = generarCorreoEstudiante(nombre, apellido);
        String passEnc = Encriptador.encriptar(password);

        Estudiante est = new Estudiante(0, ci, nombre, apellido, nombre + "." + apellido, passEnc, correo, idGrupo, false);

        try {
            conn.setAutoCommit(false);

            int idUsuario = usuarioDAO.insertarUsuario(est);
            est.setIdUsuario(idUsuario);

            estudianteDAO.insertarEstudiante(est);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }

        return est;
    }

    public Estudiante obtenerPorId(int idUsuario) throws SQLException {
        Estudiante est = estudianteDAO.obtenerEstudiante(idUsuario);
        if (est == null) throw new IllegalArgumentException("No se encontró estudiante con esa ID.");
        return est;
    }

    public List<Estudiante> listarTodos() throws SQLException {
        return estudianteDAO.listarEstudiantes();
    }

    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        Estudiante existente = estudianteDAO.obtenerEstudiante(est.getIdUsuario());
        est.setUsername(est.getNombre() + "." +est.getApellido());
        est.setCorreo(est.getUsername() + "@estudiantes.utec.edu.uy");
        if (existente == null) throw new IllegalArgumentException("No se encontró estudiante para actualizar.");

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
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
        return exito;
    }

    public boolean desactivarEstudiante(int idUsuario) throws SQLException {
        Estudiante existente = estudianteDAO.obtenerEstudiante(idUsuario);
        if (existente == null) throw new IllegalArgumentException("No se encontró estudiante para desactivar.");
        return estudianteDAO.eliminarEstudiante(idUsuario);
    }

    public boolean estaActivo(int idUsuario) throws SQLException {
        Estudiante existente = estudianteDAO.obtenerEstudiante(idUsuario);
        if (existente == null) throw new IllegalArgumentException("No se encontró estudiante con esa ID.");
        return estudianteDAO.estaActivo(idUsuario);
    }

    private String generarCorreoEstudiante(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@estudiantes.utec.edu.uy";
    }
}
