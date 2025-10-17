package consola.Admin;

import facade.InstanciaComunFacade;
import modelo.InstanciaComun;
import util.CapturadoraDeErrores; // ✅ Importación

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InstanciaComunAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final InstanciaComunFacade facade;

    public InstanciaComunAdminUI() throws SQLException {
        this.facade = new InstanciaComunFacade();
    }

    public void menuInstanciasComunes() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ INSTANCIAS COMUNES ---");
            System.out.println("1. Crear vínculo");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por instancia");
            System.out.println("4. Listar por seguimiento");
            System.out.println("5. Eliminar vínculo por instancia");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearVinculo();
                case 2 -> listarTodos();
                case 3 -> buscarPorInstancia();
                case 4 -> listarPorSeguimiento();
                case 5 -> eliminarPorInstancia();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearVinculo() {
        int idInstancia = leerEntero("ID de instancia: ");
        int idSeguimiento = leerEntero("ID de seguimiento: ");
        try {
            InstanciaComun v = facade.crearVinculo(idInstancia, idSeguimiento);
            System.out.println("✅ Vínculo creado: " + v);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear vínculo: " + msg);
        }
    }

    private void listarTodos() {
        try {
            List<InstanciaComun> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay vínculos registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar vínculos: " + msg);
        }
    }

    private void buscarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia: ");
        try {
            InstanciaComun v = facade.obtenerPorInstancia(idInstancia);
            if (v != null) System.out.println(v);
            else System.out.println("❌ Vínculo no encontrado.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar vínculo: " + msg);
        }
    }

    private void listarPorSeguimiento() {
        int idSeguimiento = leerEntero("ID de seguimiento: ");
        try {
            List<InstanciaComun> lista = facade.listarPorSeguimiento(idSeguimiento);
            if (lista.isEmpty()) System.out.println("No hay vínculos para este seguimiento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar vínculos por seguimiento: " + msg);
        }
    }

    private void eliminarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia a eliminar: ");
        try {
            boolean exito = facade.eliminarPorInstancia(idInstancia);
            if (exito) System.out.println("✅ Vínculo eliminado.");
            else System.out.println("❌ No se pudo eliminar el vínculo.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al eliminar vínculo: " + msg);
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
