package consola.Admin;

import facade.RolFacade;
import modelo.Rol;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class RolAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final RolFacade rolFacade;

    public RolAdminUI() throws SQLException {
        this.rolFacade = new RolFacade();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Roles ===");
            System.out.println("1. Agregar rol");
            System.out.println("2. Actualizar rol");
            System.out.println("3. Eliminar rol");
            System.out.println("4. Listar todos los roles");
            System.out.println("5. Buscar rol por ID");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarRol();
                case 2 -> actualizarRol();
                case 3 -> eliminarRol();
                case 4 -> listarTodos();
                case 5 -> buscarPorId();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agregarRol() {
        System.out.print("Nombre del rol: ");
        String nombre = scanner.nextLine();
        boolean estActivo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = rolFacade.agregarRol(nombre, estActivo);
            System.out.println(exito ? "✅ Rol agregado correctamente." : "❌ No se pudo agregar el rol.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
        }
    }

    private void actualizarRol() {
        int idRol = leerEntero("ID del rol a actualizar: ");
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        boolean estActivo = leerBoolean("¿Activo? (true/false): ");

        try {
            boolean exito = rolFacade.actualizarRol(idRol, nombre, estActivo);
            System.out.println(exito ? "✅ Rol actualizado correctamente." : "❌ No se pudo actualizar el rol.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
        }
    }

    private void eliminarRol() {
        int idRol = leerEntero("ID del rol a eliminar: ");
        try {
            boolean exito = rolFacade.eliminarRol(idRol);
            System.out.println(exito ? "✅ Rol eliminado correctamente." : "❌ No se pudo eliminar el rol.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Rol> roles = rolFacade.listarTodos();
            if (roles.isEmpty()) System.out.println("No hay roles registrados.");
            else roles.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar roles: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        int idRol = leerEntero("ID del rol: ");
        try {
            Rol rol = rolFacade.buscarPorId(idRol);
            if (rol != null) System.out.println(rol);
            else System.out.println("❌ Rol no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + e.getMessage());
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

    private boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("t") || input.equals("si") || input.equals("s")) return true;
            if (input.equals("false") || input.equals("f") || input.equals("no") || input.equals("n")) return false;
            System.out.print("Ingrese true/false: ");
        }
    }

    public static void main(String[] args) throws SQLException {
        RolAdminUI ui = new RolAdminUI();
        ui.menu();
    }
}
