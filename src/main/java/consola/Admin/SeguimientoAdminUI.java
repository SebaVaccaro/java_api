package consola.Admin;

import facade.SeguimientoFacade;
import modelo.Seguimiento;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class SeguimientoAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final SeguimientoFacade facade;

    public SeguimientoAdminUI() throws SQLException {
        this.facade = new SeguimientoFacade();
    }

    public void menuSeguimientos() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ SEGUIMIENTOS ---");
            System.out.println("1. Agregar seguimiento");
            System.out.println("2. Listar todos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar seguimiento");
            System.out.println("5. Eliminar seguimiento");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> agregarSeguimiento();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarSeguimiento();
                case 5 -> eliminarSeguimiento();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ==========================================================
    // AGREGAR SEGUIMIENTO
    // ==========================================================
    private void agregarSeguimiento() {
        int idEstudiante = leerEntero("ID Estudiante: ");
        LocalDate fecInicio = leerFecha("Fecha de inicio (yyyy-MM-dd): ");
        boolean estActivo = leerBoolean("¿Está activo? (true/false): ");

        try {
            boolean exito = facade.agregarSeguimiento(null, idEstudiante, fecInicio, null, estActivo);
            if (exito)
                System.out.println("✅ Seguimiento agregado correctamente.");
            else
                System.out.println("❌ No se pudo agregar el seguimiento.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    // ==========================================================
    // LISTAR SEGUIMIENTOS
    // ==========================================================
    private void listarTodos() {
        try {
            List<Seguimiento> list = facade.listarTodos();
            if (list.isEmpty()) System.out.println("No hay seguimientos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del seguimiento: ");
        try {
            Seguimiento s = facade.buscarPorId(id);
            if (s != null) System.out.println(s);
            else System.out.println("No se encontró seguimiento con ese ID.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ==========================================================
    // MODIFICAR SEGUIMIENTO
    // ==========================================================
    private void modificarSeguimiento() {
        int id = leerEntero("ID del seguimiento a modificar: ");
        try {
            Seguimiento s = facade.buscarPorId(id);
            if (s == null) {
                System.out.println("❌ No se encontró seguimiento con ese ID.");
                return;
            }

            System.out.println("Seguimiento actual: " + s);
            System.out.println("Campos modificables: idInforme, idEstudiante, fecInicio, fecCierre, estActivo");
            String campo = leerTexto("Campo a modificar: ");

            boolean exito = false;
            switch (campo.toLowerCase()) {
                case "idinforme" -> {
                    int nuevo = leerEntero("Nuevo ID Informe (0 si no aplica): ");
                    s.setIdInforme(nuevo == 0 ? null : nuevo);
                    exito = facade.actualizarSeguimiento(s.getIdSeguimiento(), s.getIdInforme(), s.getIdEstudiante(), s.getFecInicio(), s.getFecCierre(), s.isEstActivo());
                }
                case "idestudiante" -> {
                    int nuevo = leerEntero("Nuevo ID Estudiante: ");
                    s.setIdEstudiante(nuevo);
                    exito = facade.actualizarSeguimiento(s.getIdSeguimiento(), s.getIdInforme(), s.getIdEstudiante(), s.getFecInicio(), s.getFecCierre(), s.isEstActivo());
                }
                case "fecinicio" -> {
                    LocalDate fecha = leerFecha("Nueva fecha de inicio (yyyy-MM-dd): ");
                    s.setFecInicio(fecha);
                    exito = facade.actualizarSeguimiento(s.getIdSeguimiento(), s.getIdInforme(), s.getIdEstudiante(), s.getFecInicio(), s.getFecCierre(), s.isEstActivo());
                }
                case "feccierre" -> {
                    LocalDate fecha = leerFechaOpcional("Nueva fecha de cierre (yyyy-MM-dd o vacío): ");
                    s.setFecCierre(fecha);
                    exito = facade.actualizarSeguimiento(s.getIdSeguimiento(), s.getIdInforme(), s.getIdEstudiante(), s.getFecInicio(), s.getFecCierre(), s.isEstActivo());
                }
                case "estactivo" -> {
                    boolean activo = leerBoolean("Está activo? (true/false): ");
                    s.setEstActivo(activo);
                    exito = facade.actualizarSeguimiento(s.getIdSeguimiento(), s.getIdInforme(), s.getIdEstudiante(), s.getFecInicio(), s.getFecCierre(), s.isEstActivo());
                }
                default -> System.out.println("Campo inválido.");
            }

            if (exito) System.out.println("✅ Seguimiento modificado.");
            else System.out.println("❌ No se pudo modificar el seguimiento.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    // ==========================================================
    // ELIMINAR SEGUIMIENTO
    // ==========================================================
    private void eliminarSeguimiento() {
        int id = leerEntero("ID del seguimiento a eliminar: ");
        try {
            if (facade.eliminarSeguimiento(id)) System.out.println("✅ Seguimiento eliminado.");
            else System.out.println("❌ No se pudo eliminar el seguimiento.");
        } catch (SQLException e) {
            System.out.println("❌ Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }

    // ==========================================================
    // MÉTODOS AUXILIARES
    // ==========================================================
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

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            String input = leerTexto(mensaje);
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use yyyy-MM-dd.");
            }
        }
    }

    private LocalDate leerFechaOpcional(String mensaje) {
        String input = leerTexto(mensaje);
        if (input.isBlank()) return null;
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Formato inválido. Use yyyy-MM-dd. Valor ignorado.");
            return null;
        }
    }

    private boolean leerBoolean(String mensaje) {
        while (true) {
            String input = leerTexto(mensaje).toLowerCase();
            if (input.equals("true") || input.equals("t")) return true;
            if (input.equals("false") || input.equals("f")) return false;
            System.out.println("Ingrese true o false.");
        }
    }
}
