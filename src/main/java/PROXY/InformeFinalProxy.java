package PROXY;

import modelo.InformeFinal;
import servicios.InformeFinalServicio;
import utils.ValidarUsuario;

import java.time.LocalDate;
import java.util.List;

public class InformeFinalProxy {

    private final InformeFinalServicio informeService;
    private final ValidarUsuario validarUsuario;

    public InformeFinalProxy() throws Exception {
        this.informeService = new InformeFinalServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // ============================================================
    // CREAR INFORME FINAL
    // ============================================================
    public InformeFinal crearInforme(String contenido, int valoracion, LocalDate fecCreacion, int idUsuarioPropietario) throws Exception {
        if (!validarUsuario.esPropietario(idUsuarioPropietario)) {
            throw new SecurityException("No tiene permiso para crear este informe");
        }
        return informeService.crearInforme(contenido, valoracion, fecCreacion);
    }

    // ============================================================
    // OBTENER INFORME FINAL - adm, psico o propietario
    // ============================================================
    public InformeFinal obtenerInforme(int idInfFinal) throws Exception {
        return informeService.obtenerInforme(idInfFinal);
    }

    // ============================================================
    // LISTAR INFORMES FINALES - solo adm o psico
    // ============================================================
    public List<InformeFinal> listarInformes() throws Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tiene permiso para listar informes");
        }
        return informeService.listarInformes();
    }

    // ============================================================
    // ACTUALIZAR INFORME FINAL - solo adm o psico
    // ============================================================
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws Exception {
        if (!validarUsuario.esAdministrador() && !validarUsuario.esPsicopedagogo()) {
            throw new SecurityException("No tiene permiso para actualizar este informe");
        }
        return informeService.actualizarInforme(idInfFinal, contenido, valoracion, fecCreacion);
    }

    // ============================================================
    // ELIMINAR INFORME FINAL - solo adm
    // ============================================================
    public boolean eliminarInforme(int idInfFinal) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("No tiene permiso para eliminar este informe");
        }
        return informeService.eliminarInforme(idInfFinal);
    }
}
