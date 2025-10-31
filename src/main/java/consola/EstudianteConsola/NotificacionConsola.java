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

    public NotificacionConsola() throws Exception {
        this.notiFacade = new NotificacionProxy();
        this.recibeProxy = new RecibeProxy();
    }

    @Override
    protected void mostrarMenu() {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            mostrarError("No hay un usuario logueado. Por favor inicia sesión.");
            return;
        }

        System.out.println("\n--- MENÚ DE NOTIFICACIONES ---");
        System.out.println("1. Ver todas mis notificaciones");
        System.out.println("2. Buscar notificación por ID");
        System.out.println("3. Eliminar (desactivar) notificación");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarMisNotificaciones();
            case 2 -> buscarPorId();
            case 3 -> eliminarNotificacion();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

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

