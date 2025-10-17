package consola.Admin;

import facade.IncidenciaFacade;
import modelo.Incidencia;
import util.CapturadoraDeErrores; // ✅ Importación para manejo de errores amigables

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class IncidenciaAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final IncidenciaFacade facade;

    public IncidenciaAdminUI() throws SQLException {
        this.facade = new IncidenciaFacade();
    }

    public void menuIncidencias() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ INCIDENCIAS ---");
            System.out.println("1. Crear incidencia");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por instancia");
            System.out.println("4. Listar por funcionario");
            System.out.println("5. Modificar incidencia");
            System.out.println("6. Eliminar incidencia");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearIncidencia();
                case 2 -> listarTodas();
                case 3 -> buscarPorInstancia();
                case 4 -> listarPorFuncionario();
                case 5 -> modificarIncidencia();
                case 6 -> eliminarIncidencia();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearIncidencia() {
        int idInstancia = leerEntero("ID de instancia: ");
        int idFuncionario = leerEntero("ID de funcionario: ");
        String lugar = leerTexto("Lugar: ");

        try {
            Incidencia i = facade.crearIncidencia(idInstancia, idFuncionario, lugar);
            System.out.println("✅ Incidencia creada: " + i);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al crear incidencia: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Incidencia> lista = facade.listarTodas();
            if (lista.isEmpty()) System.out.println("No hay incidencias registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar incidencias: " + msg);
        }
    }

    private void buscarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia: ");
        try {
            Incidencia i = facade.obtenerPorInstancia(idInstancia);
            if (i != null) System.out.println(i);
            else System.out.println("❌ Incidencia no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al buscar incidencia: " + msg);
        }
    }

    private void listarPorFuncionario() {
        int idFuncionario = leerEntero("ID de funcionario: ");
        try {
            List<Incidencia> lista = facade.listarPorFuncionario(idFuncionario);
            if (lista.isEmpty()) System.out.println("No hay incidencias para este funcionario.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al listar incidencias por funcionario: " + msg);
        }
    }

    private void modificarIncidencia() {
        int idInstancia = leerEntero("ID de incidencia a modificar: ");
        int idFuncionario = leerEntero("Nuevo ID de funcionario: ");
        String lugar = leerTexto("Nuevo lugar: ");

        try {
            boolean exito = facade.actualizarIncidencia(idInstancia, idFuncionario, lugar);
            if (exito) System.out.println("✅ Incidencia modificada.");
            else System.out.println("❌ No se pudo modificar la incidencia.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al modificar incidencia: " + msg);
        }
    }

    private void eliminarIncidencia() {
        int idInstancia = leerEntero("ID de incidencia a eliminar: ");
        try {
            boolean exito = facade.eliminarIncidencia(idInstancia);
            if (exito) System.out.println("✅ Incidencia eliminada.");
            else System.out.println("❌ No se pudo eliminar la incidencia.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e); // ✅
            System.out.println("❌ Error al eliminar incidencia: " + msg);
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
