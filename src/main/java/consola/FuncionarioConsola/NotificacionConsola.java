package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.NotificacionProxy;
import modelo.Notificacion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificacionConsola extends UIBase {

    private final NotificacionProxy proxy;

    // Constructor: inicializa el proxy de notificaciones
    public NotificacionConsola() throws Exception {
        this.proxy = new NotificacionProxy();
    }

    // Muestra el menú principal de notificaciones
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ NOTIFICACIONES ---");
        System.out.println("1. Crear notificación");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar notificación");
        System.out.println("5. Desactivar notificación");
        System.out.println("0. Volver al menú principal");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearNotificacion();       // Crear una nueva notificación
            case 2 -> listarTodas();             // Listar todas las notificaciones
            case 3 -> buscarPorId();             // Buscar una notificación por su ID
            case 4 -> modificarNotificacion();   // Modificar una notificación existente
            case 5 -> desactivarNotificacion();  // Desactivar una notificación
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crea una nueva notificación solicitando los datos al usuario
    private void crearNotificacion() {
        int idInstancia = leerEntero("ID de instancia: ");
        String asunto = leerTexto("Asunto: ");
        String mensaje = leerTexto("Mensaje: ");
        String destinatario = leerTexto("Destinatario: ");
        LocalDate fecEnvio = leerFecha("Fecha de envío (YYYY-MM-DD): ");

        try {
            Notificacion nueva = proxy.crearNotificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio);
            mostrarExito("Notificación creada correctamente: " + nueva);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear notificación: " + e.getMessage());
        }
    }

    // Lista todas las notificaciones registradas
    private void listarTodas() {
        try {
            List<Notificacion> lista = proxy.listarTodas();
            if (lista.isEmpty()) mostrarInfo("No hay notificaciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar notificaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar notificaciones: " + e.getMessage());
        }
    }

    // Busca una notificación por su ID
    private void buscarPorId() {
        int id = leerEntero("ID de notificación: ");
        try {
            Notificacion n = proxy.obtenerNotificacion(id);
            if (n != null) mostrarInfo(n.toString());
            else mostrarError("Notificación no encontrada.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar notificación: " + e.getMessage());
        }
    }

    // Modifica una notificación existente
    private void modificarNotificacion() {
        int id = leerEntero("ID de notificación a modificar: ");
        String asunto = leerTexto("Nuevo asunto: ");
        String mensaje = leerTexto("Nuevo mensaje: ");
        String destinatario = leerTexto("Nuevo destinatario: ");
        LocalDate fecEnvio = leerFecha("Nueva fecha de envío (YYYY-MM-DD): ");
        boolean estActivo = true;

        try {
            Notificacion n = new Notificacion(id, 0, asunto, mensaje, destinatario, fecEnvio, estActivo);
            boolean exito = proxy.actualizarNotificacion(n);
            if (exito) mostrarExito("Notificación modificada correctamente.");
            else mostrarError("No se pudo modificar la notificación.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar notificación: " + e.getMessage());
        }
    }

    // Desactiva una notificación por su ID
    private void desactivarNotificacion() {
        int id = leerEntero("ID de notificación a desactivar: ");
        try {
            boolean exito = proxy.desactivarNotificacion(id);
            if (exito) mostrarExito("Notificación desactivada correctamente.");
            else mostrarError("No se pudo desactivar la notificación.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al desactivar notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al desactivar notificación: " + e.getMessage());
        }
    }
}
