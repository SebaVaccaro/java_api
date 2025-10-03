package service;

import DAO.EstudianteDAO;
import DAO.SeguimientoDAO;
import algoritmos.Encriptador;
import algoritmos.ValidadorCI;
import algoritmos.ValidadorEdad;
import algoritmos.ValidadorPassword;
import modelo.Estudiante;
import modelo.Grupo;
import modelo.Seguimiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EstudianteServicio {

    private EstudianteDAO estudianteDAO;
    private SeguimientoDAO seguimientoDAO; // Para deducir estado del estudiante

    public EstudianteServicio() throws SQLException {
        this.estudianteDAO = new EstudianteDAO();
        this.seguimientoDAO = new SeguimientoDAO();
    }

    // =====================================================
    // Crear estudiante
    // =====================================================
    public Estudiante registrarEstudiante(String ci, String username, String password, String nombre, String apellido, LocalDate fechaNacimiento, int idGrupo)
            throws Exception {
        // Validaciones
        if (!ValidadorCI.validarCI(ci)) throw new Exception("CI inválida");
        if (!ValidadorEdad.esMayorDe18(fechaNacimiento)) throw new Exception("Debe ser mayor de 18 años");
// Validar longitud de password
        if (!ValidadorPassword.validar(password)) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres");
        }


        // Generar correo institucional automáticamente
        String correo = generarCorreoEstudiante(nombre, apellido);

        // Crear objeto Estudiante
        int dummyId = 0; // placeholder, el DAO lo reemplaza
        String passEnc = Encriptador.encriptar(password);

        Estudiante est = new Estudiante(dummyId, ci, username, passEnc, nombre, apellido, correo, null, null, new Grupo(idGrupo, null, null));



        // Insertar en base de datos
        return estudianteDAO.crearEstudiante(est);
    }

    // =====================================================
    // Obtener estudiante
    // =====================================================
    public Estudiante obtenerPorId(int idEstudiante) throws SQLException {
        return estudianteDAO.obtenerEstudiante(idEstudiante);
    }

    public List<Estudiante> listarEstudiantes() throws SQLException {
        return estudianteDAO.listarEstudiantes();
    }

    // =====================================================
    // Actualizar estudiante
    // =====================================================
    public boolean actualizarEstudiante(Estudiante est) throws SQLException {
        // Aquí se pueden agregar validaciones adicionales
        return estudianteDAO.actualizarEstudiante(est);
    }

    // =====================================================
    // Baja lógica
    // =====================================================
    public boolean eliminarEstudiante(int idEstudiante) throws SQLException {
        return estudianteDAO.eliminarEstudiante(idEstudiante);
    }

    // =====================================================
    // Generación de correo institucional
    // =====================================================
    private String generarCorreoEstudiante(String nombre, String apellido) {
        return nombre.toLowerCase() + "." + apellido.toLowerCase() + "@estudiantes.utec.edu.uy";
    }

    // =====================================================
    // Deducción del estado del estudiante según seguimientos
    // =====================================================
    public String calcularEstadoEstudiante(int idEstudiante) throws SQLException {
        Estudiante estudiante = estudianteDAO.obtenerEstudiante(idEstudiante);

        if (!estudiante.isActivo()) {
            return "ELIMINADO";
        }

        List<Seguimiento> seguimientos = seguimientoDAO.obtenerPorEstudiante(idEstudiante);

        if (seguimientos.isEmpty()) {
            return "CREADO";
        }

        for (Seguimiento s : seguimientos) {
            if (s.isActivo()) {
                return "EN_SEGUIMIENTO";
            }
        }

        return "FINALIZADO";
    }
}
