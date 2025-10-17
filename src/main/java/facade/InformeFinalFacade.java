package facade;

import modelo.InformeFinal;
import servicios.InformeFinalService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InformeFinalFacade {

    private final InformeFinalService informeService;

    public InformeFinalFacade() throws SQLException {
        this.informeService = new InformeFinalService();
    }

    // ============================================================
    // CREAR INFORME FINAL
    // ============================================================
    public InformeFinal crearInforme(String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        return informeService.crearInforme(contenido, valoracion, fecCreacion);
    }

    // ============================================================
    // OBTENER INFORME FINAL
    // ============================================================
    public InformeFinal obtenerInforme(int idInfFinal) throws SQLException {
        return informeService.obtenerInforme(idInfFinal);
    }

    // ============================================================
    // LISTAR INFORMES FINALES
    // ============================================================
    public List<InformeFinal> listarInformes() throws SQLException {
        return informeService.listarInformes();
    }

    // ============================================================
    // ACTUALIZAR INFORME FINAL
    // ============================================================
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws SQLException {
        return informeService.actualizarInforme(idInfFinal, contenido, valoracion, fecCreacion);
    }

    // ============================================================
    // ELIMINAR INFORME FINAL
    // ============================================================
    public boolean eliminarInforme(int idInfFinal) throws SQLException {
        return informeService.eliminarInforme(idInfFinal);
    }
}
