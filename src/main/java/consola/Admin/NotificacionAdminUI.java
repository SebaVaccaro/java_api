package consola.Admin;

import facade.NotificacionFacade;
import modelo.Notificacion;
import util.CapturadoraDeErrores; // ✅ Importación
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class NotificacionAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final NotificacionFacade facade;

    public NotificacionAdminUI() throws SQLException {
        this.facade = new NotificacionFacade();
    }

    public void menuNotificaciones() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ NOTIFICACIONES ---");
            System.out.println("1. Crear notificación");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar notificación");
            System.out.println("5. Desactivar notificación");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearNotificacion();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> modificarNotificacion();
                case 5 -> desactivarNotificacion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearNotificacion() {
        int idInstancia = leerEntero("ID de instancia: ");
        String asunto = leerTexto("Asunto: ");
        String mensaje = leerTexto("Mensaje: ");
        String destinatario = leerTexto("Destinatario: ");
        LocalDate fecEnvio = leerFecha("Fecha de envío (YYYY-MM-DD): ");

        try {
            Notificacion n = facade.crearNotificacion(idInstancia, asunto, mensaje, destinatario, fecEnvio);
            System.out.println("✅ Notificación creada: " + n);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear notificación: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Notificacion> lista = facade.listarTodas();
            if (lista.isEmpty()) System.out.println("No hay notificaciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar notificaciones: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de notificación: ");
        try {
            Notificacion n = facade.obtenerNotificacion(id);
            if (n != null) System.out.println(n);
            else System.out.println("❌ Notificación no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar notificación: " + msg);
        }
    }

    private void modificarNotificacion() {
        int id = leerEntero("ID de notificación a modificar: ");
        String asunto = leerTexto("Nuevo asunto: ");
        String mensaje = leerTexto("Nuevo mensaje: ");
        String destinatario = leerTexto("Nuevo destinatario: ");
        LocalDate fecEnvio = leerFecha("Nueva fecha de envío (YYYY-MM-DD): ");
        boolean estActivo = true;

        try {
            Notificacion n = new Notificacion(id, 0, asunto, mensaje, destinatario, fecEnvio, estActivo);
            boolean exito = facade.actualizarNotificacion(n);
            if (exito) System.out.println("✅ Notificación modificada.");
            else System.out.println("❌ No se pudo modificar la notificación.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar notificación: " + msg);
        }
    }

    private void desactivarNotificacion() {
        int id = leerEntero("ID de notificación a desactivar: ");
        try {
            boolean exito = facade.desactivarNotificacion(id);
            if (exito) System.out.println("✅ Notificación desactivada.");
            else System.out.println("❌ No se pudo desactivar la notificación.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al desactivar notificación: " + msg);
        }
    }

    // ==== Métodos auxiliares ====
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Formato inválido. Ingrese YYYY-MM-DD: ");
            }
        }
    }
}
