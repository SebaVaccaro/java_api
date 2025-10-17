package consola.Admin;

import facade.ITRFacade;
import modelo.ITR;
import util.CapturadoraDeErrores; // ✅ Importación
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ITRAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ITRFacade facade;

    public ITRAdminUI() throws SQLException {
        this.facade = new ITRFacade();
    }

    public void menuITR() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ITR ---");
            System.out.println("1. Crear ITR");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar ITR");
            System.out.println("5. Eliminar ITR");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearITR();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarITR();
                case 5 -> eliminarITR();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearITR() {
        int idDireccion = leerEntero("ID de dirección: ");
        try {
            ITR itr = facade.crearITR(new ITR(idDireccion));
            System.out.println("✅ ITR creado: " + itr);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear ITR: " + msg);
        }
    }

    private void listarTodos() {
        try {
            List<ITR> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay ITRs registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar ITRs: " + msg);
        }
    }

    private void buscarPorId() {
        int idItr = leerEntero("ID de ITR: ");
        try {
            ITR itr = facade.obtenerITR(idItr);
            if (itr != null) System.out.println(itr);
            else System.out.println("❌ ITR no encontrado.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar ITR: " + msg);
        }
    }

    private void modificarITR() {
        int idItr = leerEntero("ID de ITR a modificar: ");
        int idDireccion = leerEntero("Nuevo ID de dirección: ");
        try {
            boolean exito = facade.actualizarITR(new ITR(idItr, idDireccion));
            if (exito) System.out.println("✅ ITR modificado.");
            else System.out.println("❌ No se pudo modificar el ITR.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar ITR: " + msg);
        }
    }

    private void eliminarITR() {
        int idItr = leerEntero("ID de ITR a eliminar: ");
        try {
            boolean exito = facade.eliminarITR(idItr);
            if (exito) System.out.println("✅ ITR eliminado.");
            else System.out.println("❌ No se pudo eliminar el ITR.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar ITR: " + msg);
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
}
