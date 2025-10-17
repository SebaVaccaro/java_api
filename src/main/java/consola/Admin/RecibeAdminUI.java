package consola.Admin;

import facade.RecibeFacade;
import modelo.Recibe;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RecibeAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final RecibeFacade facade;

    public RecibeAdminUI() throws SQLException {
        this.facade = new RecibeFacade();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Recibe (Notificación ↔ Usuario) ===");
            System.out.println("1. Agregar relación");
            System.out.println("2. Eliminar relación");
            System.out.println("3. Listar todas las relaciones");
            System.out.println("4. Listar usuarios de una notificación");
            System.out.println("5. Listar notificaciones de un usuario");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarRelacion();
                case 2 -> eliminarRelacion();
                case 3 -> listarTodos();
                case 4 -> listarUsuariosPorNotificacion();
                case 5 -> listarNotificacionesPorUsuario();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agregarRelacion() {
        int idNot = leerEntero("ID de notificación: ");
        int idUsu = leerEntero("ID de usuario: ");

        try {
            boolean exito = facade.agregarRecibe(idNot, idUsu);
            System.out.println(exito ? "✅ Relación agregada." : "❌ No se pudo agregar la relación.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
        }
    }

    private void eliminarRelacion() {
        int idNot = leerEntero("ID de notificación: ");
        int idUsu = leerEntero("ID de usuario: ");

        try {
            boolean exito = facade.eliminarRecibe(idNot, idUsu);
            System.out.println(exito ? "✅ Relación eliminada." : "❌ No se pudo eliminar la relación.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Recibe> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) System.out.println("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar relaciones: " + e.getMessage());
        }
    }

    private void listarUsuariosPorNotificacion() {
        int idNot = leerEntero("ID de notificación: ");
        try {
            List<Integer> usuarios = facade.listarUsuariosPorNotificacion(idNot);
            System.out.println("Usuarios: " + usuarios);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar usuarios: " + e.getMessage());
        }
    }

    private void listarNotificacionesPorUsuario() {
        int idUsu = leerEntero("ID de usuario: ");
        try {
            List<Integer> notificaciones = facade.listarNotificacionesPorUsuario(idUsu);
            System.out.println("Notificaciones: " + notificaciones);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar notificaciones: " + e.getMessage());
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
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    public static void main(String[] args) throws SQLException {
        RecibeAdminUI ui = new RecibeAdminUI();
        ui.menu();
    }
}
