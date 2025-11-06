package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.NotificacionProxy;
import PROXY.RecibeProxy;
import SINGLETON.LoginSingleton;
import modelo.Notificacion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class NotificacionConsola extends UIBase {

    private final NotificacionProxy notiFacade;
    private final RecibeProxy recibeProxy;

    // Constructor: inicializa los proxies de notificaciones y recepción
    public NotificacionConsola() throws Exception {
        this.notiFacade = new NotificacionProxy();
        this.recibeProxy = new RecibeProxy();
    }

    // Mostrar el menú principal de gestión de notificaciones
    @Override
    protected void mostrarMenu() {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            mostrarError("No hay un usuario logueado. Por favor, inicia sesión.");
            return;
        }

        System.out.println("\n===== MENÚ DE NOTIFICACIONES =====");
        System.out.println("1. Ver todas mis notificaciones");
        System.out.println("2. Buscar notificación por ID");
        System.out.println("3. Eliminar o desactivar notificación");
        System.out.println("0. Volver al menú principal");
        System.out.println("==================================");
    }


    // Controla las acciones del menú según la opción seleccionada
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarMisNotificaciones(); // Listar todas las notificaciones del usuario actual
            case 2 -> buscarPorId();             // Buscar una notificación específica por su ID
            case 3 -> eliminarNotificacion();    // Desactivar una notificación existente
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Listar todas las notificaciones activas pertenecientes al usuario autenticado
    private void listarMisNotificaciones() {
        try {
            int idUsuario = LoginSingleton.getInstance().getUsuarioActual().getIdUsuario();
            List<Integer> idsNotificaciones = recibeProxy.listarNotificacionesPorUsuario(idUsuario);

            if (idsNotificaciones.isEmpty()) {
                mostrarInfo("No tienes notificaciones.");
                return;
            }

            mostrarInfo("📋 Tus notificaciones:");
            for (int idNoti : idsNotificaciones) {
                try {
                    Notificacion n = notiFacade.obtenerNotificacion(idNoti);
                    if (n != null && n.isEstActivo()) {
                        System.out.println(n);
                    }
                } catch (SecurityException se) {
                    mostrarError("No tienes permiso para ver la notificación ID " + idNoti);
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al listar notificaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar una notificación por su ID y mostrar sus detalles
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID de la notificación: ");
        try {
            Notificacion n = notiFacade.obtenerNotificacion(id);
            if (n != null && n.isEstActivo()) {
                mostrarInfo("📨 Detalles de la notificación:");
                System.out.println(n);
            } else {
                mostrarError("La notificación no existe o está desactivada.");
            }

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error al buscar notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Desactivar (eliminar lógicamente) una notificación existente
    private void eliminarNotificacion() {
        int id = leerEntero("Ingrese el ID de la notificación a eliminar: ");
        try {
            boolean exito = notiFacade.desactivarNotificacion(id);
            if (exito) mostrarExito("Notificación desactivada correctamente.");
            else mostrarError("No se pudo desactivar la notificación o no existe.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error al eliminar notificación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
