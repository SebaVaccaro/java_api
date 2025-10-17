package consola.Estudiante;

import facade.DireccionFacade;
import modelo.Direccion;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DireccionUI {

    private final Scanner scanner = new Scanner(System.in);
    private final DireccionFacade direccionFacade;
    private final int idUsuario; // ID del usuario asociado

    public DireccionUI(int idUsuario) throws SQLException {
        this.idUsuario = idUsuario;
        this.direccionFacade = new DireccionFacade();
    }

    public void menuDirecciones() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ MIS DIRECCIONES ---");
            System.out.println("1. Crear dirección");
            System.out.println("2. Listar mis direcciones");
            System.out.println("3. Modificar dirección");
            System.out.println("4. Eliminar dirección");
            System.out.println("0. Volver al menú principal");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> crearDireccion();
                case 2 -> listarMisDirecciones();
                case 3 -> modificarDireccion();
                case 4 -> eliminarDireccion();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ============================================================
    // CREAR DIRECCIÓN
    // ============================================================
    private void crearDireccion() {
        int idCiudad = leerEntero("ID de ciudad: ");
        String calle = leerTexto("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento (opcional): ");

        try {
            Direccion nueva = direccionFacade.crearDireccion(
                    calle,
                    numPuerta,
                    numApto.isBlank() ? null : numApto,
                    idCiudad,
                    idUsuario
            );
            System.out.println("✅ Dirección creada correctamente: " + nueva);
        } catch (SQLException e) {
            System.out.println("❌ Error al crear la dirección: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR MIS DIRECCIONES
    // ============================================================
    private void listarMisDirecciones() {
        try {
            List<Direccion> direcciones = direccionFacade.listarPorUsuario(idUsuario);
            if (direcciones.isEmpty()) {
                System.out.println("No tienes direcciones registradas.");
            } else {
                System.out.println("\n--- MIS DIRECCIONES ---");
                for (Direccion d : direcciones) {
                    System.out.println("ID: " + d.getIdDireccion() +
                            " | CiudadID: " + d.getIdCiudad() +
                            " | Calle: " + d.getCalle() +
                            " | Puerta: " + d.getNumPuerta() +
                            " | Apto: " + (d.getNumApto() == null ? "-" : d.getNumApto()));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar direcciones: " + e.getMessage());
        }
    }

    // ============================================================
    // MODIFICAR DIRECCIÓN
    // ============================================================
    private void modificarDireccion() {
        int id = leerEntero("ID de la dirección a modificar: ");
        try {
            Direccion dir = direccionFacade.obtenerDireccion(id);
            if (dir == null || dir.getIdUsuario() != idUsuario) {
                System.out.println("❌ No puedes modificar una dirección que no te pertenece.");
                return;
            }

            String campo = leerTexto("Campo a modificar (idCiudad, calle, numPuerta, numApto): ").toLowerCase();

            boolean exito = switch (campo) {
                case "idciudad" -> direccionFacade.actualizarDireccion(id, null, null, null, leerEntero("Nuevo ID de ciudad: "), idUsuario);
                case "calle" -> direccionFacade.actualizarDireccion(id, leerTexto("Nueva calle: "), null, null, 0, idUsuario);
                case "nupuerta", "numpuerta" -> direccionFacade.actualizarDireccion(id, null, leerTexto("Nuevo número de puerta: "), null, 0, idUsuario);
                case "napto", "numapto" -> direccionFacade.actualizarDireccion(id, null, null, leerTexto("Nuevo número de apto: "), 0, idUsuario);
                default -> {
                    System.out.println("Campo inválido.");
                    yield false;
                }
            };

            if (exito) System.out.println("✅ Dirección modificada correctamente.");
            else System.out.println("❌ No se pudo modificar la dirección.");

        } catch (SQLException e) {
            System.out.println("❌ Error al modificar la dirección: " + e.getMessage());
        }
    }

    // ============================================================
    // ELIMINAR DIRECCIÓN
    // ============================================================
    private void eliminarDireccion() {
        int id = leerEntero("ID de la dirección a eliminar: ");
        try {
            Direccion dir = direccionFacade.obtenerDireccion(id);
            if (dir == null || dir.getIdUsuario() != idUsuario) {
                System.out.println("❌ No puedes eliminar una dirección que no te pertenece.");
                return;
            }

            if (direccionFacade.eliminarDireccion(id)) {
                System.out.println("✅ Dirección eliminada correctamente.");
            } else {
                System.out.println("❌ No se pudo eliminar la dirección.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar la dirección: " + e.getMessage());
        }
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

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
