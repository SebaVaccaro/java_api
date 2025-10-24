package servicios;

import DAO.InformeFinalDAOImpl;
import modelo.InformeFinal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InformeFinalService {

    private final InformeFinalDAOImpl informeDAO;

    public InformeFinalService() throws SQLException {
        this.informeDAO = new InformeFinalDAOImpl();
    }

    // 🔹 Crear informe final
    public InformeFinal crearInforme(String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        InformeFinal informe = new InformeFinal(contenido, valoracion, fecCreacion);
        return informeDAO.crearInformeFinal(informe);
    }

    // 🔹 Obtener informe por ID
    public InformeFinal obtenerInforme(int idInfFinal) throws SQLException {
        return informeDAO.obtenerInformeFinal(idInfFinal);
    }

    // 🔹 Listar todos los informes
    public List<InformeFinal> listarInformes() throws SQLException {
        return informeDAO.listarInformesFinales();
    }

    // 🔹 Actualizar informe
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        InformeFinal informe = new InformeFinal(idInfFinal, contenido, valoracion, fecCreacion);
        return informeDAO.actualizarInformeFinal(informe);
    }

    // 🔹 Eliminar informe
    public boolean eliminarInforme(int idInfFinal) throws SQLException {
        return informeDAO.eliminarInformeFinal(idInfFinal);
    }
}
