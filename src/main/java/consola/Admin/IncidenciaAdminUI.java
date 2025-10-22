package consola.Admin;

import facade.IncidenciaFacade;
import modelo.Incidencia;
import util.CapturadoraDeErrores; // manejo de errores amigables

import java.sql.SQLException;
import java.time.OffsetDateTime;
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
        System.out.println("--- CREAR INCIDENCIA ---");
        String titulo = leerTexto("Título: ");
        OffsetDateTime fecha = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Descripción: ");
        boolean activo = leerBoolean("Está activa? (true/false): ");
        int idFuncionario = leerEntero("ID de funcionario: ");
        String lugar = leerTexto("Lugar: ");

        Incidencia incidencia = new Incidencia(
                titulo, fecha, descripcion, activo, idFuncionario, lugar
        );

        try {
            incidencia = facade.crearIncidencia(incidencia);
            System.out.println("✅ Incidencia creada: " + incidencia);
        } catch (SQLException e) {
            System.out.println("❌ Error al crear incidencia: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodas() {
        try {
            List<Incidencia> lista = facade.listarIncidencias();
            if (lista.isEmpty()) System.out.println("No hay incidencias registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar incidencias: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorInstancia() {
        int idInstancia = leerEntero("ID de instancia: ");
        try {
            Incidencia i = facade.obtenerIncidencia(idInstancia);
            if (i != null) System.out.println(i);
            else System.out.println("❌ Incidencia no encontrada.");
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar incidencia: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarPorFuncionario() {
        int idFuncionario = leerEntero("ID de funcionario: ");
        try {
            List<Incidencia> lista = facade.listarPorFuncionario(idFuncionario);
            if (lista.isEmpty()) System.out.println("No hay incidencias para este funcionario.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar incidencias por funcionario: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarIncidencia() {
        int idInstancia = leerEntero("ID de incidencia a modificar: ");
        String titulo = leerTexto("Nuevo título: ");
        OffsetDateTime fecha = leerFechaHora("Nueva fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Nueva descripción: ");
        boolean activo = leerBoolean("Está activa? (true/false): ");
        int idFuncionario = leerEntero("Nuevo ID de funcionario: ");
        String lugar = leerTexto("Nuevo lugar: ");

        Incidencia incidencia = new Incidencia(
                idInstancia, titulo, fecha, descripcion, activo, idFuncionario, lugar
        );

        try {
            boolean exito = facade.actualizarIncidencia(incidencia);
            if (exito) System.out.println("✅ Incidencia modificada.");
            else System.out.println("❌ No se pudo modificar la incidencia.");
        } catch (SQLException e) {
            System.out.println("❌ Error al modificar incidencia: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarIncidencia() {
        int idInstancia = leerEntero("ID de incidencia a eliminar: ");
        try {
            boolean exito = facade.eliminarIncidencia(idInstancia);
            if (exito) System.out.println("✅ Incidencia eliminada.");
            else System.out.println("❌ No se pudo eliminar la incidencia.");
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar incidencia: " +
                    CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
        while (!scanner.hasNextBoolean()) {
            System.out.print("Ingrese true o false: ");
            scanner.next();
        }
        boolean valor = scanner.nextBoolean();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private OffsetDateTime leerFechaHora(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                String input = scanner.nextLine();
                return OffsetDateTime.parse(input);
            } catch (Exception e) {
                System.out.print("Formato inválido. Use YYYY-MM-DDTHH:MM : ");
            }
        }
    }
}
