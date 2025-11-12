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
        Seguimiento seguimiento = seguimientoDAO.buscarPorId(idSeguimiento);

        if (seguimiento.getIdInforme() != null) {
            throw new IllegalStateException("El seguimiento ya tiene un informe final asociado.");
        }

        InformeFinal informe = new InformeFinal(contenido, valoracion, fecCreacion);
        InformeFinal informeGuardado = informeDAO.crearInformeFinal(informe);

        seguimiento.setIdInforme(informeGuardado.getIdInfFinal());
        seguimientoDAO.actualizar(seguimiento);

        return informeGuardado;
    }


    // Obtener informe por ID
    public InformeFinal obtenerInforme(int idInfFinal) throws SQLException {
        return informeDAO.obtenerInformeFinal(idInfFinal);
    }

    // Listar todos los informes
    public List<InformeFinal> listarInformes() throws SQLException {
        return informeDAO.listarInformesFinales();
    }

    // Actualizar informe
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        InformeFinal informe = new InformeFinal(idInfFinal, contenido, valoracion, fecCreacion);
        return informeDAO.actualizarInformeFinal(informe);
    }

    // Eliminar informe
    public boolean eliminarInforme(int idInfFinal) throws SQLException {
        return informeDAO.eliminarInformeFinal(idInfFinal);
    }
}
