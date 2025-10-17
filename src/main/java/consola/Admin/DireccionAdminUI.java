package consola.Admin;

import facade.DireccionFacade;
import modelo.Direccion;
import util.CapturadoraDeErrores; // ✅ Importar para manejo de errores amigables

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DireccionAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final DireccionFacade facade;

    public DireccionAdminUI() throws SQLException {
        this.facade = new DireccionFacade();
    }

    public void menuDirecciones() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DIRECCIONES ---");
            System.out.println("1. Crear dirección");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Listar por usuario");
            System.out.println("5. Listar por ciudad");
            System.out.println("6. Modificar dirección");
            System.out.println("7. Eliminar dirección");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearDireccion();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> listarPorUsuario();
                case 5 -> listarPorCiudad();
                case 6 -> modificarDireccion();
                case 7 -> eliminarDireccion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearDireccion() {
        String calle = leerTexto("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento: ");
        int idCiudad = leerEntero("ID de ciudad: ");
        int idUsuario = leerEntero("ID de usuario: ");
        try {
            Direccion d = facade.crearDireccion(calle, numPuerta, numApto, idCiudad, idUsuario);
            System.out.println("✅ Dirección creada: " + d);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al crear dirección: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Direccion> lista = facade.listarDirecciones();
            if (lista.isEmpty()) System.out.println("No hay direcciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar direcciones: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de la dirección: ");
        try {
            Direccion d = facade.obtenerDireccion(id);
            if (d != null) System.out.println(d);
            else System.out.println("❌ Dirección no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al buscar dirección: " + msg);
        }
    }

    private void listarPorUsuario() {
        int idUsuario = leerEntero("ID de usuario: ");
        try {
            List<Direccion> lista = facade.listarPorUsuario(idUsuario);
            if (lista.isEmpty()) System.out.println("No hay direcciones para este usuario.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar direcciones por usuario: " + msg);
        }
    }

    private void listarPorCiudad() {
        int idCiudad = leerEntero("ID de ciudad: ");
        try {
            List<Direccion> lista = facade.listarPorCiudad(idCiudad);
            if (lista.isEmpty()) System.out.println("No hay direcciones para esta ciudad.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar direcciones por ciudad: " + msg);
        }
    }

    private void modificarDireccion() {
        int id = leerEntero("ID de la dirección a modificar: ");
        String calle = leerTexto("Nueva calle: ");
        String numPuerta = leerTexto("Nuevo número de puerta: ");
        String numApto = leerTexto("Nuevo número de apartamento: ");
        int idCiudad = leerEntero("Nuevo ID de ciudad: ");
        int idUsuario = leerEntero("Nuevo ID de usuario: ");

        try {
            boolean exito = facade.actualizarDireccion(id, calle, numPuerta, numApto, idCiudad, idUsuario);
            if (exito) System.out.println("✅ Dirección modificada.");
            else System.out.println("❌ No se pudo modificar la dirección.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al modificar dirección: " + msg);
        }
    }

    private void eliminarDireccion() {
        int id = leerEntero("ID de la dirección a eliminar: ");
        try {
            boolean exito = facade.eliminarDireccion(id);
            if (exito) System.out.println("✅ Dirección eliminada.");
            else System.out.println("❌ No se pudo eliminar la dirección.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al eliminar dirección: " + msg);
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

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
