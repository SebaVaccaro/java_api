package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.RecibeProxy;
import modelo.Recibe;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class RecibeConsola extends UIBase {

    private final RecibeProxy facade;

    public RecibeConsola() throws SQLException {
        this.facade = new RecibeProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== Gestión de Recibe (Notificación ↔ Usuario) ===");
        System.out.println("1. Agregar relación");
        System.out.println("2. Eliminar relación");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar usuarios de una notificación");
        System.out.println("5. Listar notificaciones de un usuario");
        System.out.println("0. Salir");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarRelacion();
            case 2 -> eliminarRelacion();
            case 3 -> listarTodos();
            case 4 -> listarUsuariosPorNotificacion();
            case 5 -> listarNotificacionesPorUsuario();
            case 0 -> mostrarInfo("Saliendo...");
            default -> mostrarError("Opción no válida.");
        }
    }

    private void agregarRelacion() {
        int idNot = leerEntero("ID de notificación: ");
        int idUsu = leerEntero("ID de usuario: ");
        try {
            boolean exito = facade.agregarRecibe(idNot, idUsu);
            if (exito) mostrarExito("Relación agregada.");
            else mostrarError("No se pudo agregar la relación.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarRelacion() {
        int idNot = leerEntero("ID de notificación: ");
        int idUsu = leerEntero("ID de usuario: ");
        try {
            boolean exito = facade.eliminarRecibe(idNot, idUsu);
            if (exito) mostrarExito("Relación eliminada.");
            else mostrarError("No se pudo eliminar la relación.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<Recibe> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarUsuariosPorNotificacion() {
        int idNot = leerEntero("ID de notificación: ");
        try {
            List<Integer> usuarios = facade.listarUsuariosPorNotificacion(idNot);
            mostrarInfo("Usuarios: " + usuarios);
        } catch (SQLException e) {
            mostrarError("Error al listar usuarios: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarNotificacionesPorUsuario() {
        int idUsu = leerEntero("ID de usuario: ");
        try {
            List<Integer> notificaciones = facade.listarNotificacionesPorUsuario(idUsu);
            mostrarInfo("Notificaciones: " + notificaciones);
        } catch (SQLException e) {
            mostrarError("Error al listar notificaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}

