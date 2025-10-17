package consola.Admin;

import facade.ObservacionFacade;
import modelo.Observacion;
import util.CapturadoraDeErrores; // ✅ Importación
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;

public class ObservacionAdminUI {

    private final Scanner scanner = new Scanner(System.in);
    private final ObservacionFacade facade;

    public ObservacionAdminUI() throws SQLException {
        this.facade = new ObservacionFacade();
    }

    public void menuObservaciones() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ OBSERVACIONES ---");
            System.out.println("1. Crear observación");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Modificar observación");
            System.out.println("5. Desactivar observación");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearObservacion();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> modificarObservacion();
                case 5 -> desactivarObservacion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearObservacion() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        int idEstudiante = leerEntero("ID del estudiante: ");
        String titulo = leerTexto("Título: ");
        String contenido = leerTexto("Contenido: ");
        OffsetDateTime fecHora = OffsetDateTime.now();

        try {
            Observacion obs = facade.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
            System.out.println("✅ Observación creada: " + obs);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al crear observación: " + msg);
        }
    }

    private void listarTodas() {
        try {
            List<Observacion> lista = facade.listarTodas();
            if (lista.isEmpty()) System.out.println("No hay observaciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al listar observaciones: " + msg);
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de observación: ");
        try {
            Observacion obs = facade.obtenerObservacion(id);
            if (obs != null) System.out.println(obs);
            else System.out.println("❌ Observación no encontrada.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al buscar observación: " + msg);
        }
    }

    private void modificarObservacion() {
        int id = leerEntero("ID de observación a modificar: ");
        int idFuncionario = leerEntero("Nuevo ID funcionario: ");
        int idEstudiante = leerEntero("Nuevo ID estudiante: ");
        String titulo = leerTexto("Nuevo título: ");
        String contenido = leerTexto("Nuevo contenido: ");
        OffsetDateTime fecHora = OffsetDateTime.now();

        try {
            Observacion obs = new Observacion(id, idFuncionario, idEstudiante, titulo, contenido, fecHora, true);
            boolean exito = facade.actualizarObservacion(obs);
            if (exito) System.out.println("✅ Observación modificada.");
            else System.out.println("❌ No se pudo modificar la observación.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al modificar observación: " + msg);
        }
    }

    private void desactivarObservacion() {
        int id = leerEntero("ID de observación a desactivar: ");
        try {
            boolean exito = facade.desactivarObservacion(id);
            if (exito) System.out.println("✅ Observación desactivada.");
            else System.out.println("❌ No se pudo desactivar la observación.");
        } catch (SQLException e) {
            String msg = CapturadoraDeErrores.obtenerMensajeAmigable(e);
            System.out.println("❌ Error al desactivar observación: " + msg);
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
        scanner.nextLine();
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
