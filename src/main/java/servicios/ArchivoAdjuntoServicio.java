package servicios;

import DAO.ArchivoAdjuntoDAOImpl;
import modelo.ArchivoAdjunto;

import java.sql.SQLException;
import java.util.List;

public class ArchivoAdjuntoServicio {

    private final ArchivoAdjuntoDAOImpl archivoDAO;
    private final EstudianteServicio estudianteServicio;
    private final FuncionarioServicio funcionarioServicio;

    // Constructor: inicializa los DAOs y servicios
    public ArchivoAdjuntoServicio() throws SQLException {
        this.archivoDAO = new ArchivoAdjuntoDAOImpl();
        this.estudianteServicio = new EstudianteServicio();
        this.funcionarioServicio = new FuncionarioServicio();
    }

    // Crear archivo adjunto
    public ArchivoAdjunto crearArchivo(int idUsuario, int idEstudiante, String ruta, String categoria) throws SQLException {
        validarEstudianteExiste(idEstudiante);
        validarFuncionarioExiste(idUsuario);

        ArchivoAdjunto archivo = new ArchivoAdjunto(idUsuario, idEstudiante, ruta, categoria, true);
        return archivoDAO.crearArchivoAdjunto(archivo);
    }

    // Obtener archivo por ID
    public ArchivoAdjunto obtenerArchivo(int idArchivo) throws SQLException {
        ArchivoAdjunto archivo = archivoDAO.obtenerArchivoAdjunto(idArchivo);
        if (archivo == null) {
            throw new IllegalArgumentException("No se encontró archivo para esa ID.");
        }
        return archivo;
    }

    // Listar archivos activos
    public List<ArchivoAdjunto> listarActivos() throws SQLException {
        return archivoDAO.listarArchivosAdjuntosActivos();
    }

    // Listar archivos por estudiante
    public List<ArchivoAdjunto> listarPorEstudiante(int idEstudiante) throws SQLException {
        return archivoDAO.listarPorEstudiante(idEstudiante);
    }

    // Actualizar archivo existente
    public boolean actualizarArchivo(ArchivoAdjunto archivo) throws SQLException {
        ArchivoAdjunto existente = archivoDAO.obtenerArchivoAdjunto(archivo.getIdArchivoAdjunto());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró archivo para esa ID.");
        }
        return archivoDAO.actualizarArchivoAdjunto(archivo);
    }

    // Eliminar archivo (baja lógica)
    public boolean eliminarArchivo(int idArchivo) throws SQLException {
        ArchivoAdjunto archivo = archivoDAO.obtenerArchivoAdjunto(idArchivo);
        if (archivo == null) {
            throw new IllegalArgumentException("No se encontró archivo para esa ID.");
        }
        return archivoDAO.eliminarArchivoAdjunto(idArchivo);
    }



    // Validar que el estudiante exista
    private void validarEstudianteExiste(int idEstudiante) throws SQLException {
        if (estudianteServicio.obtenerPorId(idEstudiante) == null) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }

    // Validar que el funcionario exista
    private void validarFuncionarioExiste(int idUsuario) throws SQLException {
        if (funcionarioServicio.obtenerPorId(idUsuario) == null) {
            throw new IllegalArgumentException("El usuario/funcionario no existe.");
        }
    }
}
