package consola.Estudiante;

import facade.SeguimientoFacade;
import modelo.Seguimiento;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class SeguimientoEstudianteUI {

    private final Scanner scanner = new Scanner(System.in);
    private final SeguimientoFacade seguimientoFacade;
    private final int idEstudiante;

    public SeguimientoEstudianteUI(int idEstudiante) throws SQLException {
        this.idEstudiante = idEstudiante;
        this.seguimientoFacade = new SeguimientoFacade();
    }

    public void menuSeguimientoEstudiante() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ DE SEGUIMIENTOS DEL ESTUDIANTE ---");
            System.out.println("1. Ver mis seguimientos");
            System.out.println("2. Buscar seguimiento por ID");
            System.out.println("3. Cerrar seguimiento");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> listarMisSeguimientos();
                case 2 -> buscarPorId();
                case 3 -> cerrarSeguimiento();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ============================================================
    // LISTAR SEGUIMIENTOS DEL ESTUDIANTE
    // ============================================================
    private void listarMisSeguimientos() {
        try {
            List<Seguimiento> lista = seguimientoFacade.listarTodos();
            boolean encontrado = false;

            for (Seguimiento s : lista) {
                if (s.getIdEstudiante() == idEstudiante && s.isEstActivo()) {
                    System.out.println(s);
                    encontrado = true;
                }
            }

            if (!encontrado)
                System.out.println("📭 No tienes seguimientos activos.");

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar seguimientos: " + msg);
        }
    }

    // ============================================================
    // BUSCAR UN SEGUIMIENTO POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            Seguimiento s = seguimientoFacade.buscarPorId(id);
            if (s == null) {
                System.out.println("❌ Seguimiento no encontrado.");
                return;
            }

            if (s.getIdEstudiante() != idEstudiante) {
                System.out.println("❌ No puedes acceder a un seguimiento que no es tuyo.");
                return;
            }

            System.out.println("\n📄 Detalles del seguimiento:");
            System.out.println(s);

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar seguimiento: " + msg);
        }
    }

    // ============================================================
    // CERRAR UN SEGUIMIENTO (MARCAR INACTIVO Y FECHA DE CIERRE)
    // ============================================================
    private void cerrarSeguimiento() {
        int id = leerEntero("Ingrese el ID del seguimiento a cerrar: ");
        try {
            Seguimiento s = seguimientoFacade.buscarPorId(id);
            if (s == null) {
                System.out.println("❌ Seguimiento no encontrado.");
                return;
            }

            if (s.getIdEstudiante() != idEstudiante) {
                System.out.println("❌ No puedes cerrar un seguimiento que no te pertenece.");
                return;
            }

            if (!s.isEstActivo()) {
                System.out.println("ℹ️ Este seguimiento ya está cerrado.");
                return;
            }

            LocalDate fechaCierre = LocalDate.now();
            boolean exito = seguimientoFacade.actualizarSeguimiento(
                    s.getIdSeguimiento(),
                    s.getIdInforme(),
                    s.getIdEstudiante(),
                    s.getFecInicio(),
                    fechaCierre,
                    false
            );

            if (exito)
                System.out.println("✅ Seguimiento cerrado correctamente el " + fechaCierre);
            else
                System.out.println("❌ No se pudo cerrar el seguimiento.");

        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al cerrar seguimiento: " + msg);
        }
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}
