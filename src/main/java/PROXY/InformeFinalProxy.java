package PROXY;

import modelo.InformeFinal;
import servicios.InformeFinalServicio;
import servicios.PartSeguimientoServicio;
import utils.ValidarUsuario;

import java.time.LocalDate;
import java.util.List;

public class InformeFinalProxy {

    private final InformeFinalServicio informeService;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de informes finales y el validador de usuario
    public InformeFinalProxy() throws Exception {
        this.informeService = new InformeFinalServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Crear informe final (solo psicopedagogos o administradores)
    public InformeFinal crearInforme(int idSeguimiento, String contenido, int valoracion, LocalDate fecCreacion) throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo el administrador o psicopedagogo puede crear informes finales.");
        }

        return informeService.crearInforme(idSeguimiento, contenido, valoracion, fecCreacion);
    }

    // Obtener informe por ID (administrador, psicopedagogo)
    public InformeFinal obtenerInforme(int idInfFinal) throws Exception {
        if (!validarUsuario.esAdminOPsico()){
            throw new SecurityException("Solo el administrador o psicopedagogo puede ver este informe.");
        }
        return informeService.obtenerInforme(idInfFinal);
    }

    // Listar todos los informes (solo administradores o psicopedagogos)
    public List<InformeFinal> listarInformes() throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden listar informes.");
        }
        return informeService.listarInformes();
    }

    // Actualizar informe final (solo administradores o psicopedagogos)
    public boolean actualizarInforme(int idInfFinal, String contenido, int valoracion, LocalDate fecCreacion) throws Exception {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo administradores o psicopedagogos pueden actualizar este informe.");
        }
        return informeService.actualizarInforme(idInfFinal, contenido, valoracion, fecCreacion);
    }

    // Eliminar informe final (solo administradores)
    public boolean eliminarInforme(int idInfFinal) throws Exception {
        if (!validarUsuario.esAdministrador()) {
            throw new SecurityException("Solo administradores pueden eliminar este informe.");
        }
        return informeService.eliminarInforme(idInfFinal);
    }
}

