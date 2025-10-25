package consola.Estudiante;

import facade.InstanciaComunFacade;
import modelo.InstanciaComun;
import util.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;

public class InstanciaComunEstUI {

    private final Scanner scanner = new Scanner(System.in);
    private final InstanciaComunFacade instanciaFacade;
    private final int idFuncionario;

    public InstanciaComunEstUI(int idFuncionario) throws SQLException {
        this.idFuncionario = idFuncionario;
        this.instanciaFacade = new InstanciaComunFacade();
    }

    public void menuInstanciasComunes() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ DE INSTANCIAS COMUNES ===");
            System.out.println("1. Listar todas las instancias");
            System.out.println("2. Listar por seguimiento");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Crear nueva instancia común");
            System.out.println("5. Actualizar instancia común");
            System.out.println("6. Eliminar (desactivar) instancia común");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> listarInstancias();
                case 2 -> listarPorSeguimiento();
                case 3 -> buscarPorId();
                case 4 -> crearInstanciaComun();
                case 5 -> actualizarInstanciaComun();
                case 6 -> eliminarInstanciaComun();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ============================================================
    // LISTAR TODAS LAS INSTANCIAS
    // ============================================================
    private void listarInstancias() {
        try {
            List<InstanciaComun> lista = instanciaFacade.listarInstanciasComunes();
            if (lista.isEmpty()) {
                System.out.println("📭 No hay instancias comunes registradas.");
                return;
            }
            System.out.println("\n📋 Instancias Comunes:");
            for (InstanciaComun ic : lista) {
                System.out.println(ic);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // LISTAR POR SEGUIMIENTO
    // ============================================================
    private void listarPorSeguimiento() {
        int idSeg = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            List<InstanciaComun> lista = instanciaFacade.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) {
                System.out.println("📭 No hay instancias comunes para este seguimiento.");
                return;
            }
            System.out.println("\n📋 Instancias Comunes del seguimiento " + idSeg + ":");
            for (InstanciaComun ic : lista) {
                System.out.println(ic);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar por seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID de la instancia: ");
        try {
            InstanciaComun ic = instanciaFacade.obtenerInstanciaComun(id);
            if (ic != null) {
                System.out.println("\n📄 Detalles de la instancia común:");
                System.out.println(ic);
            } else {
                System.out.println("❌ No se encontró la instancia común con ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // CREAR NUEVA INSTANCIA COMÚN
    // ============================================================
    private void crearInstanciaComun() {
        System.out.println("\n🆕 Crear nueva instancia común");
        String titulo = leerTexto("Título: ");
        String descripcion = leerTexto("Descripción: ");
        int idSeg = leerEntero("ID de seguimiento: ");
        boolean estActivo = true;

        try {
            OffsetDateTime fecha = OffsetDateTime.now();
            InstanciaComun nueva = instanciaFacade.crearInstanciaComun(titulo, fecha, descripcion, estActivo, idFuncionario, idSeg);
            System.out.println("✅ Instancia común creada correctamente:");
            System.out.println(nueva);
        } catch (SQLException e) {
            System.out.println("❌ Error al crear instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // ACTUALIZAR INSTANCIA COMÚN
    // ============================================================
    private void actualizarInstanciaComun() {
        int id = leerEntero("Ingrese el ID de la instancia a actualizar: ");
        String titulo = leerTexto("Nuevo título: ");
        String descripcion = leerTexto("Nueva descripción: ");
        boolean estActivo = leerBooleano("¿Está activa? (true/false): ");
        int idSeg = leerEntero("Nuevo ID de seguimiento: ");

        try {
            boolean exito = instanciaFacade.actualizarInstanciaComun(id, titulo, OffsetDateTime.now(), descripcion, estActivo, idFuncionario, idSeg);
            if (exito) {
                System.out.println("✅ Instancia actualizada correctamente.");
            } else {
                System.out.println("❌ No se pudo actualizar la instancia.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // ELIMINAR / DESACTIVAR
    // ============================================================
    private void eliminarInstanciaComun() {
        int id = leerEntero("Ingrese el ID de la instancia a eliminar: ");
        try {
            boolean exito = instanciaFacade.eliminarInstanciaComun(id);
            if (exito) {
                System.out.println("✅ Instancia común desactivada correctamente.");
            } else {
                System.out.println("❌ No se pudo eliminar la instancia.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar instancia común: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // MÉTODOS DE ENTRADA DE DATOS
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

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private boolean leerBooleano(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextBoolean()) {
            System.out.print("Ingrese 'true' o 'false': ");
            scanner.next();
        }
        boolean valor = scanner.nextBoolean();
        scanner.nextLine();
        return valor;
    }
}
