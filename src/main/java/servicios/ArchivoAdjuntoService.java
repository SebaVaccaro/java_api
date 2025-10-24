package servicios;

import DAO.ArchivoAdjuntoDAOImpl;
import modelo.ArchivoAdjunto;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoService {

    private final ArchivoAdjuntoDAOImpl archivoDAO;
    private final EstudianteService estudianteService;
    private final FuncionarioService funcionarioService; // Suponiendo que tengas un servicio para usuarios/funcionarios

    public ArchivoAdjuntoService() throws SQLException {
        this.archivoDAO = new ArchivoAdjuntoDAOImpl();
        this.estudianteService = new EstudianteService();
        this.funcionarioService = new FuncionarioService(); // Inicializamos el servicio
    }

    // ==========================================================
    // 🔹 CREAR ARCHIVO ADJUNTO
    // ==========================================================
    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws SQLException {
        // Validaciones
        validarEstudianteExiste(idEstudiante);
        validarFuncionarioExiste(idUsuario);

        ArchivoAdjunto archivo = new ArchivoAdjunto(idUsuario, idEstudiante, ruta, categoria, true);
        return archivoDAO.crearArchivoAdjunto(archivo);
    }

    // ==========================================================
    // 🔹 OBTENER ARCHIVO
    // ==========================================================
    public ArchivoAdjunto obtenerArchivo(int idArchivo) throws SQLException {
        return archivoDAO.obtenerArchivoAdjunto(idArchivo);
    }

    // ==========================================================
    // 🔹 LISTAR ARCHIVOS
    // ==========================================================
    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return archivoDAO.listarArchivosAdjuntosActivos();
    }

    public List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException {
        return archivoDAO.listarPorEstudiante(idEstudiante);
    }

    // ==========================================================
    // 🔹 ACTUALIZAR ARCHIVO
    // ==========================================================
    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws SQLException {
        // Validar que el archivo exista
        validarArchivoExiste(archivo.getIdArchivoAdjunto());
        return archivoDAO.actualizarArchivoAdjunto(archivo);
    }

    // ==========================================================
    // 🔹 ELIMINAR / BAJA LÓGICA
    // ==========================================================
    public boolean eliminarArchivo(int idArchivo) throws SQLException {
        return archivoDAO.eliminarArchivoAdjunto(idArchivo);
    }

    public boolean eliminarFisico(int idArchivo) throws SQLException {
        return archivoDAO.eliminarFisico(idArchivo);
    }

    // ==========================================================
    // 🔹 MÉTODOS PRIVADOS DE VALIDACIÓN
    // ==========================================================
    private void validarEstudianteExiste(int idEstudiante) throws SQLException {
        if (estudianteService.obtenerPorId(idEstudiante) == null) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }

    private void validarFuncionarioExiste(int idUsuario) throws SQLException {
        if (funcionarioService.obtenerPorId(idUsuario) == null) {
            throw new IllegalArgumentException("El usuario/funcionario no existe.");
        }
    }

    private void validarArchivoExiste(int idArchivo) throws SQLException {
        if (archivoDAO.obtenerArchivoAdjunto(idArchivo) == null) {
            throw new IllegalArgumentException("El archivo no existe.");
        }
    }
}
