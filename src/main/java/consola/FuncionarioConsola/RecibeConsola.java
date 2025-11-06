package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.RecibeProxy;
import modelo.Recibe;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class RecibeConsola extends UIBase {

    private final RecibeProxy proxy;

    // Constructor: inicializa el proxy
    public RecibeConsola() throws Exception {
        this.proxy = new RecibeProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ RELACIÓN NOTIFICACIÓN ↔ USUARIO =====");
        System.out.println("1. Agregar relación");
        System.out.println("2. Eliminar relación");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar usuarios de una notificación");
        System.out.println("5. Listar notificaciones de un usuario");
        System.out.println("0. Volver al menú principal");
        System.out.println("================================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarRelacion();
                case 2 -> eliminarRelacion();
                case 3 -> listarTodos();
                case 4 -> listarUsuariosPorNotificacion();
                case 5 -> listarNotificacionesPorUsuario();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al procesar la opción: " + e.getMessage());
        }
    }

    // Agrega una relación entre notificación y usuario
    private void agregarRelacion() {
        int idNotificacion = leerEntero("ID de la notificación: ");
        int idUsuario = leerEntero("ID del usuario: ");

        try {
            boolean exito = proxy.agregarRecibe(idNotificacion, idUsuario);
            if (exito) mostrarExito("Relación agregada correctamente.");
            else mostrarError("No se pudo agregar la relación.");
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar relación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al agregar relación: " + e.getMessage());
        }
    }

    // Elimina una relación existente
    private void eliminarRelacion() {
        int idNotificacion = leerEntero("ID de la notificación: ");
        int idUsuario = leerEntero("ID del usuario: ");

        try {
            boolean exito = proxy.eliminarRecibe(idNotificacion, idUsuario);
            if (exito) mostrarExito("Relación eliminada correctamente.");
            else mostrarError("No se pudo eliminar la relación.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar relación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar relación: " + e.getMessage());
        }
    }

    // Lista todas las relaciones notificación ↔ usuario
    private void listarTodos() {
        try {
            List<Recibe> relaciones = proxy.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar relaciones: " + e.getMessage());
        }
    }

    // Lista los usuarios asociados a una notificación
    private void listarUsuariosPorNotificacion() {
        int idNotificacion = leerEntero("ID de la notificación: ");
        try {
            List<Integer> usuarios = proxy.listarUsuariosPorNotificacion(idNotificacion);
            if (usuarios.isEmpty()) mostrarInfo("La notificación no tiene usuarios asociados.");
            else mostrarInfo("Usuarios asociados: " + usuarios);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar usuarios: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar usuarios: " + e.getMessage());
        }
    }

    // Lista las notificaciones recibidas por un usuario
    private void listarNotificacionesPorUsuario() {
        int idUsuario = leerEntero("ID del usuario: ");
        try {
            List<Integer> notificaciones = proxy.listarNotificacionesPorUsuario(idUsuario);
            if (notificaciones.isEmpty()) mostrarInfo("El usuario no tiene notificaciones asociadas.");
            else mostrarInfo("Notificaciones recibidas: " + notificaciones);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar notificaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar notificaciones: " + e.getMessage());
        }
    }
}
