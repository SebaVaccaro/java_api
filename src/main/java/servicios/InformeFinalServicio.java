package servicios;

import DAO.InformeFinalDAOImpl;
import DAO.SeguimientoDAOImpl;
import modelo.InformeFinal;
import modelo.Seguimiento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InformeFinalServicio {

    private final InformeFinalDAOImpl informeDAO;
    private final SeguimientoDAOImpl seguimientoDAO;

    // Constructor: inicializa DAO
    public InformeFinalServicio() throws SQLException {
        this.informeDAO = new InformeFinalDAOImpl();
        this.seguimientoDAO = new SeguimientoDAOImpl();
    }

    // Crear nuevo informe final
    public InformeFinal crearInforme(int idSeguimiento, String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        // Validaciones de datos
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del informe no puede estar vacío.");
        }
        if (fecCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula.");
        }
        if (valoracion < 1 || valoracion > 10) {
            throw new IllegalArgumentException("La valoración debe estar entre 1 y 10.");
        }

        // Validar existencia del seguimiento
        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("No existe un seguimiento con ID " + idSeguimiento + ".");
        }

        if (seguimiento.getIdInforme() != null) {
            throw new IllegalStateException("El seguimiento ya tiene un informe final asociado.");
        }

        // Crear y asociar el informe
        InformeFinal informe = new InformeFinal(contenido, valoracion, fecCreacion);
        InformeFinal informeGuardado = informeDAO.crearInformeFinal(informe);

        seguimiento.setIdInforme(informeGuardado.getIdInfFinal());
        seguimientoDAO.actualizar(seguimiento);

        return informeGuardado;
    }

    // Obtener informe por ID
    public InformeFinal obtenerInforme(int idInfFinal) throws SQLException {
        InformeFinal informe = informeDAO.obtenerInformeFinal(idInfFinal);
        if (informe == null) {
            throw new IllegalArgumentException("No se encontró un informe con ID " + idInfFinal + ".");
        }
        return informe;
    }

    // Listar todos los informes
    public List<InformeFinal> listarInformes() throws SQLException {
        List<InformeFinal> informes = informeDAO.listarInformesFinales();
        if (informes == null || informes.isEmpty()) {
            throw new IllegalStateException("No existen informes finales registrados en el sistema.");
        }
        return informes;
    }

    // Actualizar informe (verifica existencia antes de actualizar)
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        InformeFinal existente = informeDAO.obtenerInformeFinal(idInfFinal);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede actualizar: el informe con ID " + idInfFinal + " no existe.");
        }

        InformeFinal informe = new InformeFinal(idInfFinal, contenido, valoracion, fecCreacion);
        return informeDAO.actualizarInformeFinal(informe);
    }

    public boolean eliminarInforme(int idSeguimiento) throws SQLException {


        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);
        if (seguimiento == null) {
            throw new IllegalArgumentException("No existe un seguimiento con ID " + idSeguimiento + ".");
        }

        Integer idInforme = seguimiento.getIdInforme();

        if (idInforme == null) {
            throw new IllegalArgumentException("El seguimiento no tiene Informe Final");
        }

        InformeFinal existente = informeDAO.obtenerInformeFinal(idInforme);
        if (existente == null) {
            throw new IllegalArgumentException("No se puede eliminar: el informe con ID " + idInforme + " no existe.");
        }

        boolean res = informeDAO.eliminarInformeFinal(idInforme);
        if(res) {
            seguimiento.setIdInforme(null);
            seguimientoDAO.actualizar(seguimiento);
        }else{
            throw new IllegalArgumentException("No se pudo eliminar el informe, intentelo nuevamente.");
        }
        return true;
    }
}
