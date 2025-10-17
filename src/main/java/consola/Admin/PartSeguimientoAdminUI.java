package consola.Admin;

import facade.PartSeguimientoFacade;
import modelo.PartSeguimiento;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PartSeguimientoAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final PartSeguimientoFacade facade;

    public PartSeguimientoAdminUI() throws SQLException {
        this.facade = new PartSeguimientoFacade();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Participantes en Seguimientos ===");
            System.out.println("1. Agregar participante a seguimiento");
            System.out.println("2. Eliminar participante de seguimiento");
            System.out.println("3. Listar todas las relaciones");
            System.out.println("4. Listar seguimientos de un participante");
            System.out.println("5. Listar participantes de un seguimiento");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarParticipante();
                case 2 -> eliminarParticipante();
                case 3 -> listarTodos();
                case 4 -> listarSeguimientosPorParticipante();
                case 5 -> listarParticipantesPorSeguimiento();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agregarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idSeg = leerEntero("ID del seguimiento: ");

        try {
            boolean exito = facade.agregarParticipante(idPart, idSeg);
            System.out.println(exito ? "✅ Participante agregado correctamente." : "❌ No se pudo agregar el participante.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idSeg = leerEntero("ID del seguimiento: ");

        try {
            boolean exito = facade.eliminarParticipante(idPart, idSeg);
            System.out.println(exito ? "✅ Participante eliminado correctamente." : "❌ No se pudo eliminar el participante.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<PartSeguimiento> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) System.out.println("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarSeguimientosPorParticipante() {
        int idPart = leerEntero("ID del participante: ");
        try {
            List<Integer> seguimientos = facade.listarSeguimientosPorParticipante(idPart);
            System.out.println("Seguimientos del participante: " + seguimientos);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarParticipantesPorSeguimiento() {
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            List<Integer> participantes = facade.listarParticipantesPorSeguimiento(idSeg);
            System.out.println("Participantes del seguimiento: " + participantes);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar participantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
        PartSeguimientoAdminUI ui = new PartSeguimientoAdminUI();
        ui.menu();
    }
}
