package consola.Admin;

import facade.PartInstanciaFacade;
import modelo.PartInstancia;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PartInstanciaAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final PartInstanciaFacade facade;

    public PartInstanciaAdminUI() throws SQLException {
        this.facade = new PartInstanciaFacade();
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Participantes en Instancias ===");
            System.out.println("1. Agregar participante a instancia");
            System.out.println("2. Eliminar participante de instancia");
            System.out.println("3. Listar todas las relaciones");
            System.out.println("4. Listar instancias de un participante");
            System.out.println("5. Listar participantes de una instancia");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarParticipante();
                case 2 -> eliminarParticipante();
                case 3 -> listarTodos();
                case 4 -> listarInstanciasPorParticipante();
                case 5 -> listarParticipantesPorInstancia();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void agregarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idInst = leerEntero("ID de la instancia: ");

        try {
            boolean exito = facade.agregarParticipante(idPart, idInst);
            System.out.println(exito ? "✅ Participante agregado correctamente." : "❌ No se pudo agregar el participante.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idInst = leerEntero("ID de la instancia: ");

        try {
            boolean exito = facade.eliminarParticipante(idPart, idInst);
            System.out.println(exito ? "✅ Participante eliminado correctamente." : "❌ No se pudo eliminar el participante.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<PartInstancia> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) System.out.println("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarInstanciasPorParticipante() {
        int idPart = leerEntero("ID del participante: ");
        try {
            List<Integer> instancias = facade.listarInstanciasPorParticipante(idPart);
            System.out.println("Instancias del participante: " + instancias);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarParticipantesPorInstancia() {
        int idInst = leerEntero("ID de la instancia: ");
        try {
            List<Integer> participantes = facade.listarParticipantesPorInstancia(idInst);
            System.out.println("Participantes de la instancia: " + participantes);
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

    // Para probar de forma directa
    public static void main(String[] args) throws SQLException {
        PartInstanciaAdminUI ui = new PartInstanciaAdminUI();
        ui.menu();
    }
}
