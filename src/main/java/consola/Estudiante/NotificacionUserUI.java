package consola.Estudiante;

import facade.NotificacionFacade;
import facade.RecibeFacade;
import modelo.Notificacion;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class NotificacionUserUI {

    private final Scanner scanner = new Scanner(System.in);
    private final NotificacionFacade notiFacade;
    private final RecibeFacade recibeFacade;
    private final int idUsuario;

    public NotificacionUserUI(int idUsuario) throws SQLException {
        this.idUsuario = idUsuario;
        this.notiFacade = new NotificacionFacade();
        this.recibeFacade = new RecibeFacade();
    }

    public void menuNotificaciones() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DE NOTIFICACIONES ---");
            System.out.println("1. Ver todas mis notificaciones");
            System.out.println("2. Buscar notificación por ID");
            System.out.println("3. Eliminar (desactivar) notificación");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> listarMisNotificaciones();
                case 2 -> buscarPorId();
                case 3 -> eliminarNotificacion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void listarMisNotificaciones() {
        try {
            List<Integer> idsNotificaciones = recibeFacade.listarNotificacionesPorUsuario(idUsuario);

            if (idsNotificaciones.isEmpty()) {
                System.out.println("📭 No tienes notificaciones.");
                return;
            }

            System.out.println("\n📋 Tus notificaciones:");
            for (int idNoti : idsNotificaciones) {
                Notificacion n = notiFacade.obtenerNotificacion(idNoti);
                if (n != null && n.isEstActivo()) {
                    System.out.println(n);
                }
            }

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar notificaciones: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID de la notificación: ");
        try {
            List<Integer> ids = recibeFacade.listarNotificacionesPorUsuario(idUsuario);
            if (!ids.contains(id)) {
                System.out.println("❌ No tienes permiso para ver esta notificación.");
                return;
            }

            Notificacion n = notiFacade.obtenerNotificacion(id);
            if (n != null && n.isEstActivo()) {
                System.out.println("\n📨 Detalles de la notificación:");
                System.out.println(n);
            } else {
                System.out.println("❌ La notificación no existe o está desactivada.");
            }

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar notificación: " + msg);
        }
    }

    private void eliminarNotificacion() {
        int id = leerEntero("Ingrese el ID de la notificación a eliminar: ");
        try {
            List<Integer> ids = recibeFacade.listarNotificacionesPorUsuario(idUsuario);
            if (!ids.contains(id)) {
                System.out.println("❌ No puedes eliminar una notificación que no es tuya.");
                return;
            }

            boolean exito = notiFacade.desactivarNotificacion(id);
            if (exito) {
                System.out.println("✅ Notificación desactivada correctamente.");
            } else {
                System.out.println("❌ No se pudo desactivar la notificación.");
            }

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar notificación: " + msg);
        }
    }

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
}
