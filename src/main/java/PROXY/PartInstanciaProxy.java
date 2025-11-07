package PROXY;

import modelo.PartInstancia;
import servicios.PartInstanciaServicio;
import utils.ValidarUsuario;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaProxy {

    private final PartInstanciaServicio partInstanciaServicio;
    private final ValidarUsuario validarUsuario;

    // Constructor: inicializa el servicio de participante-instancia
    public PartInstanciaProxy() throws SQLException {
        this.partInstanciaServicio = new PartInstanciaServicio();
        this.validarUsuario = new ValidarUsuario();
    }

    // Agregar participante a una instancia
    public boolean agregarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede agregar participantes a una instancia.");
        }
        return partInstanciaServicio.agregarParticipante(idParticipante, idInstancia);
    }

    // Eliminar participante de una instancia
    public boolean eliminarParticipante(int idParticipante, int idInstancia) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede eliminar participantes de una instancia.");
        }
        return partInstanciaServicio.eliminarParticipante(idParticipante, idInstancia);
    }

    // Listar todas las relaciones participante-instancia
    public List<PartInstancia> listarTodos() throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede listar todas las relaciones participante-instancia.");
        }
        return partInstanciaServicio.listarTodos();
    }

    // Listar instancias de un participante
    public List<Integer> listarInstanciasPorParticipante(int idParticipante) throws SQLException {
        if (!validarUsuario.tienePermisoAdminPsicoOPropietario(idParticipante)) {
            throw new SecurityException("Permisos insuficientes: se requiere ser administrador, psicopedagogo o el propietario del participante.");
        }
        return partInstanciaServicio.listarInstanciasPorParticipante(idParticipante);
    }

    // Listar participantes de una instancia
    public List<Integer> listarParticipantesPorInstancia(int idInstancia) throws SQLException {
        if (!validarUsuario.esAdminOPsico()) {
            throw new SecurityException("Solo un administrador o psicopedagogo puede listar los participantes de una instancia.");
        }
        return partInstanciaServicio.listarParticipantesPorInstancia(idInstancia);
    }

}

