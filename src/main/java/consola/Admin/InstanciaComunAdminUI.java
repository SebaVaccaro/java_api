package consola.Admin;

import facade.InstanciaComunFacade;
import modelo.InstanciaComun;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
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
            System.out.println("1. Crear instancia común");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por instancia");
            System.out.println("4. Listar por seguimiento");
            System.out.println("5. Eliminar por instancia");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearInstanciaComun();
                case 2 -> listarTodos();
                case 3 -> buscarPorInstancia();
                case 4 -> listarPorSeguimiento();
                case 5 -> eliminarPorInstancia();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearInstanciaComun() {
        try {
            String titulo = leerTexto("Título: ");
            OffsetDateTime fecHora = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
            String descripcion = leerTexto("Descripción: ");
            boolean estActivo = leerBoolean("Activo (true/false): ");
            int idFuncionario = leerEntero("ID de funcionario: ");
            int idSeguimiento = leerEntero("ID de seguimiento: ");

            InstanciaComun ic = facade.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            System.out.println("✅ Instancia común creada: " + ic);

        } catch (SQLException e) {
            System.out.println("❌ Error al crear instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato de fecha inválido. Use YYYY-MM-DDTHH:MM");
        }
    }

    private void listarTodos() {
        try {
            List<InstanciaComun> lista = facade.listarTodos();
            if (lista.isEmpty()) System.out.println("No hay instancias registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia: ");
        try {
            InstanciaComun ic = facade.obtenerPorInstancia(idInstancia);
            if (ic != null) System.out.println(ic);
            else System.out.println("❌ Instancia no encontrada.");
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarPorSeguimiento() {
        int idSeguimiento = leerEntero("ID de seguimiento: ");
        try {
            List<InstanciaComun> lista = facade.listarPorSeguimiento(idSeguimiento);
            if (lista.isEmpty()) System.out.println("No hay instancias para este seguimiento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar por seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia a eliminar: ");
        try {
            boolean exito = facade.eliminarPorInstancia(idInstancia);
            if (exito) System.out.println("✅ Instancia eliminada.");
            else System.out.println("❌ No se pudo eliminar la instancia.");
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
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

    private boolean leerBoolean(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.print("Ingrese true o false: ");
        }
    }

    private OffsetDateTime leerFechaHora(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        return OffsetDateTime.parse(input + ":00+00:00"); // añade segundos y zona UTC
    }
}
