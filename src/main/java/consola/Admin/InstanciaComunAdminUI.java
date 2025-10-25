package consola.Admin;

import facade.InstanciaComunFacade;
import modelo.InstanciaComun;

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
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por instancia");
            System.out.println("4. Listar por seguimiento");
            System.out.println("5. Actualizar instancia común");
            System.out.println("6. Eliminar instancia por ID");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearInstanciaComun();
                case 2 -> listarTodos();
                case 3 -> buscarPorInstancia();
                case 4 -> listarPorSeguimiento();
                case 5 -> actualizarInstanciaComun();
                case 6 -> eliminarPorInstancia();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ==== CRUD ====
    private void crearInstanciaComun() {
        try {
            String titulo = leerTexto("Título: ");
            OffsetDateTime fecHora = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
            String descripcion = leerTexto("Descripción: ");
            boolean estActivo = leerBoolean("Activa (true/false): ");
            int idFuncionario = leerEntero("ID de funcionario: ");
            int idSeguimiento = leerEntero("ID de seguimiento: ");

            InstanciaComun ic = facade.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            System.out.println("✅ Instancia común creada: " + ic);

        } catch (SQLException e) {
            System.out.println("❌ Error al crear instancia común: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Fecha inválida. Use formato YYYY-MM-DDTHH:MM");
        }
    }

    private void listarTodos() {
        try {
            List<InstanciaComun> lista = facade.listarInstanciasComunes();
            if (lista.isEmpty()) System.out.println("No hay instancias comunes registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar: " + e.getMessage());
        }
    }

    private void buscarPorInstancia() {
        int id = leerEntero("ID de instancia: ");
        try {
            InstanciaComun ic = facade.obtenerInstanciaComun(id);
            if (ic != null) System.out.println(ic);
            else System.out.println("❌ Instancia no encontrada.");
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar: " + e.getMessage());
        }
    }

    private void listarPorSeguimiento() {
        int idSeg = leerEntero("ID de seguimiento: ");
        try {
            List<InstanciaComun> lista = facade.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) System.out.println("No hay instancias para este seguimiento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("❌ Error al listar por seguimiento: " + e.getMessage());
        }
    }

    private void actualizarInstanciaComun() {
        int id = leerEntero("ID de instancia a actualizar: ");
        try {
            InstanciaComun ic = facade.obtenerInstanciaComun(id);
            if (ic == null) {
                System.out.println("⚠️ Instancia no encontrada.");
                return;
            }

            String titulo = leerTexto("Nuevo título [" + ic.getTitulo() + "]: ");
            OffsetDateTime fecHora = leerFechaHoraOpcional("Nueva fecha y hora [" + ic.getFecHora() + "]: ", ic.getFecHora());
            String descripcion = leerTextoOpcional("Nueva descripción [" + ic.getDescripcion() + "]: ", ic.getDescripcion());
            boolean estActivo = leerBooleanOpcional("Activa (true/false) [" + ic.isEstActivo() + "]: ", ic.isEstActivo());
            int idFuncionario = leerEnteroOpcional("ID de funcionario [" + ic.getIdFuncionario() + "]: ", ic.getIdFuncionario());
            int idSeguimiento = leerEnteroOpcional("ID de seguimiento [" + ic.getIdSeguimiento() + "]: ", ic.getIdSeguimiento());

            boolean actualizado = facade.actualizarInstanciaComun(id,
                    titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            System.out.println(actualizado ? "✅ Actualización exitosa" : "⚠️ No se pudo actualizar");
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarPorInstancia() {
        int id = leerEntero("ID de instancia a eliminar: ");
        try {
            boolean eliminado = facade.eliminarInstanciaComun(id);
            System.out.println(eliminado ? "🗑️ Instancia eliminada" : "⚠️ No se pudo eliminar");
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
        }
    }

    // ==== MÉTODOS AUXILIARES ====
    private String leerTexto(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private String leerTextoOpcional(String msg, String valorActual) {
        System.out.print(msg);
        String input = scanner.nextLine();
        return input.isBlank() ? valorActual : input;
    }

    private int leerEntero(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private int leerEnteroOpcional(String msg, int valorActual) {
        System.out.print(msg);
        String input = scanner.nextLine();
        if (input.isBlank()) return valorActual;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return valorActual;
        }
    }

    private boolean leerBoolean(String msg) {
        System.out.print(msg);
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.print("Ingrese true o false: ");
        }
    }

    private boolean leerBooleanOpcional(String msg, boolean valorActual) {
        System.out.print(msg);
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.isBlank()) return valorActual;
        return input.equals("true");
    }

    private OffsetDateTime leerFechaHora(String msg) {
        System.out.print(msg);
        return OffsetDateTime.parse(scanner.nextLine() + ":00+00:00");
    }

    private OffsetDateTime leerFechaHoraOpcional(String msg, OffsetDateTime valorActual) {
        System.out.print(msg);
        String input = scanner.nextLine();
        if (input.isBlank()) return valorActual;
        return OffsetDateTime.parse(input + ":00+00:00");
    }
}
