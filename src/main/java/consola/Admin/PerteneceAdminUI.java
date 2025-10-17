package consola.Admin;

import facade.PerteneceFacade;
import modelo.Pertenece;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PerteneceAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final PerteneceFacade facade;

    public PerteneceAdminUI() throws SQLException {
        this.facade = new PerteneceFacade();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Pertenece (Carrera ↔ ITR) ===");
            System.out.println("1. Agregar relación");
            System.out.println("2. Eliminar relación");
            System.out.println("3. Listar todas las relaciones");
            System.out.println("4. Listar ITRs de una carrera");
            System.out.println("5. Listar carreras de un ITR");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarRelacion();
                case 2 -> eliminarRelacion();
                case 3 -> listarTodos();
                case 4 -> listarItrPorCarrera();
                case 5 -> listarCarrerasPorItr();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agregarRelacion() {
        int idCarrera = leerEntero("ID de carrera: ");
        int idItr = leerEntero("ID de ITR: ");

        try {
            boolean exito = facade.agregarPertenece(idCarrera, idItr);
            System.out.println(exito ? "✅ Relación agregada." : "❌ No se pudo agregar la relación.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarRelacion() {
        int idCarrera = leerEntero("ID de carrera: ");
        int idItr = leerEntero("ID de ITR: ");

        try {
            boolean exito = facade.eliminarPertenece(idCarrera, idItr);
            System.out.println(exito ? "✅ Relación eliminada." : "❌ No se pudo eliminar la relación.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<Pertenece> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) System.out.println("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarItrPorCarrera() {
        int idCarrera = leerEntero("ID de carrera: ");
        try {
            List<Integer> itrs = facade.listarItrPorCarrera(idCarrera);
            System.out.println("ITRs de la carrera: " + itrs);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar ITRs: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarCarrerasPorItr() {
        int idItr = leerEntero("ID de ITR: ");
        try {
            List<Integer> carreras = facade.listarCarrerasPorItr(idItr);
            System.out.println("Carreras del ITR: " + carreras);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
        PerteneceAdminUI ui = new PerteneceAdminUI();
        ui.menu();
    }
}
