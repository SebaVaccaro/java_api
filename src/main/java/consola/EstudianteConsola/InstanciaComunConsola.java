package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.InstanciaComunProxy;
import SINGLETON.SesionSingleton;
import modelo.InstanciaComun;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunConsola extends UIBase {

    private final InstanciaComunProxy instanciaProxy;
    private final int idEstudiante;

    // Constructor: valida la sesión y obtiene el usuario autenticado
    public InstanciaComunConsola() throws SQLException {
        if (!SesionSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("No hay sesión activa. Por favor inicia sesión.");
        }
        this.idEstudiante = SesionSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.instanciaProxy = new InstanciaComunProxy();
    }

    // Mostrar el menú principal de gestión de instancias comunes
    @Override
    protected void mostrarMenu() {
        System.out.println("\n=== MENÚ DE INSTANCIAS COMUNES ===");
        System.out.println("1. Listar todas las instancias");
        System.out.println("2. Listar por seguimiento");
        System.out.println("3. Buscar por ID");
        System.out.println("0. Volver al menú principal");
    }

    // Controla las acciones seleccionadas en el menú
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarInstancias();
            case 2 -> listarPorSeguimiento();
            case 3 -> buscarPorId();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Listar todas las instancias comunes existentes
    private void listarInstancias() {
        try {
            List<InstanciaComun> lista = instanciaProxy.listarPorEstudiante(idEstudiante);
            if (lista.isEmpty() || lista == null) {
                mostrarInfo("No hay instancias comunes registradas.");
                return;
            }
            for(InstanciaComun i: lista){
                System.out.println(i);
            }
        } catch (SQLException e) {
            mostrarError("Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Listar las instancias comunes asociadas a un seguimiento específico
    private void listarPorSeguimiento() {
        int idSeg = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            List<InstanciaComun> lista = instanciaProxy.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) {
                mostrarInfo("No hay instancias comunes para este seguimiento.");
                return;
            }
            mostrarInfo("Instancias Comunes del seguimiento " + idSeg + ":");
            lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar por seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }catch (IllegalArgumentException e){
            mostrarError(e.getMessage());
        }
    }

    // Buscar y mostrar los detalles de una instancia común por su ID
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID de la instancia: ");
        try {
            InstanciaComun ic = instanciaProxy.obtenerInstanciaComun(id);
            if (ic != null) {
                mostrarInfo("Detalles de la instancia común:");
                System.out.println(ic);
            } else {
                mostrarError("No se encontró la instancia común con ID " + id);
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e){
            mostrarError(e.getMessage());
        }
    }
}
