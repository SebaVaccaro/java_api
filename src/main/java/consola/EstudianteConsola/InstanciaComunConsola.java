package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.InstanciaComunProxy;
import SINGLETON.LoginSingleton;
import modelo.InstanciaComun;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunConsola extends UIBase {

    private final InstanciaComunProxy instanciaFacade;
    private final int idFuncionario;

    // Constructor: valida la sesión y obtiene el usuario autenticado
    public InstanciaComunConsola() throws SQLException {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("❌ No hay sesión activa. Por favor inicia sesión.");
        }
        this.idFuncionario = LoginSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.instanciaFacade = new InstanciaComunProxy();
    }

    // Mostrar el menú principal de gestión de instancias comunes
    @Override
    protected void mostrarMenu() {
        System.out.println("\n=== MENÚ DE INSTANCIAS COMUNES ===");
        System.out.println("1. Listar todas las instancias");
        System.out.println("2. Listar por seguimiento");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Crear nueva instancia común");
        System.out.println("5. Actualizar instancia común");
        System.out.println("6. Eliminar (desactivar) instancia común");
        System.out.println("0. Volver al menú principal");
    }

    // Controla las acciones seleccionadas en el menú
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarInstancias();         // Listar todas las instancias comunes
            case 2 -> listarPorSeguimiento();     // Listar instancias por ID de seguimiento
            case 3 -> buscarPorId();              // Buscar una instancia común por su ID
            case 4 -> crearInstanciaComun();      // Crear una nueva instancia común
            case 5 -> actualizarInstanciaComun(); // Actualizar los datos de una instancia
            case 6 -> eliminarInstanciaComun();   // Eliminar (desactivar) una instancia común
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Listar todas las instancias comunes existentes
    private void listarInstancias() {
        try {
            List<InstanciaComun> lista = instanciaFacade.listarInstanciasComunes();
            if (lista.isEmpty()) {
                mostrarInfo("No hay instancias comunes registradas.");
                return;
            }
            mostrarInfo("📋 Instancias Comunes:");
            lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Listar las instancias comunes asociadas a un seguimiento específico
    private void listarPorSeguimiento() {
        int idSeg = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            List<InstanciaComun> lista = instanciaFacade.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) {
                mostrarInfo("No hay instancias comunes para este seguimiento.");
                return;
            }
            mostrarInfo("📋 Instancias Comunes del seguimiento " + idSeg + ":");
            lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar por seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar y mostrar los detalles de una instancia común por su ID
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID de la instancia: ");
        try {
            InstanciaComun ic = instanciaFacade.obtenerInstanciaComun(id);
            if (ic != null) {
                mostrarInfo("📄 Detalles de la instancia común:");
                System.out.println(ic);
            } else {
                mostrarError("No se encontró la instancia común con ID " + id);
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Crear una nueva instancia común asociada a un seguimiento
    private void crearInstanciaComun() {
        mostrarInfo("🆕 Crear nueva instancia común");
        String titulo = leerTexto("Título: ");
        String descripcion = leerTexto("Descripción: ");
        int idSeg = leerEntero("ID de seguimiento: ");
        boolean estActivo = true;

        try {
            OffsetDateTime fecha = OffsetDateTime.now();
            InstanciaComun nueva = instanciaFacade.crearInstanciaComun(
                    titulo, fecha, descripcion, estActivo, idFuncionario, idSeg
            );
            mostrarExito("Instancia común creada correctamente:");
            System.out.println(nueva);
        } catch (SQLException e) {
            mostrarError("Error al crear instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Actualizar la información de una instancia común existente
    private void actualizarInstanciaComun() {
        int id = leerEntero("Ingrese el ID de la instancia a actualizar: ");
        String titulo = leerTexto("Nuevo título: ");
        String descripcion = leerTexto("Nueva descripción: ");
        boolean estActivo = leerBoolean("¿Está activa? (true/false): ");
        int idSeg = leerEntero("Nuevo ID de seguimiento: ");

        try {
            boolean exito = instanciaFacade.actualizarInstanciaComun(
                    id, titulo, OffsetDateTime.now(), descripcion, estActivo, idFuncionario, idSeg
            );
            if (exito) mostrarExito("Instancia actualizada correctamente.");
            else mostrarError("No se pudo actualizar la instancia.");
        } catch (SQLException e) {
            mostrarError("Error al actualizar instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Eliminar (desactivar) una instancia común por su ID
    private void eliminarInstanciaComun() {
        int id = leerEntero("Ingrese el ID de la instancia a eliminar: ");
        try {
            boolean exito = instanciaFacade.eliminarInstanciaComun(id);
            if (exito) mostrarExito("Instancia común desactivada correctamente.");
            else mostrarError("No se pudo eliminar la instancia.");
        } catch (SQLException e) {
            mostrarError("Error al eliminar instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}
