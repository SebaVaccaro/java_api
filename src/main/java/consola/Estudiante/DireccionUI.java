package consola.Estudiante;

import PROXY.DireccionProxy;
import SINGLETON.LoginSingleton;
import consola.interfaz.UIBase;
import modelo.Direccion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class DireccionUI extends UIBase {

    private final DireccionProxy direccionProxy;
    private final int idUsuario; // usuario autenticado

    public DireccionUI() throws SQLException {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("❌ No hay sesión activa. Por favor inicia sesión.");
        }
        this.idUsuario = LoginSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.direccionProxy = new DireccionProxy();
    }

    // ============================================================
    // Implementación de UIBase
    // ============================================================
    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENÚ MIS DIRECCIONES ---");
        System.out.println("1. Crear dirección");
        System.out.println("2. Listar mis direcciones");
        System.out.println("3. Modificar dirección");
        System.out.println("4. Eliminar dirección");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearDireccion();
            case 2 -> listarMisDirecciones();
            case 3 -> modificarDireccion();
            case 4 -> eliminarDireccion();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
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
            Direccion d = direccionProxy.crearDireccion(
                    idUsuario,
                    calle,
                    numPuerta,
                    numApto.isBlank() ? null : numApto,
                    idCiudad
            );
            mostrarExito("Dirección creada: " + d);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // ============================================================
    // LISTAR DIRECCIONES
    // ============================================================
    private void listarMisDirecciones() {
        try {
            List<Direccion> lista = direccionProxy.listarPorUsuario(idUsuario, idUsuario);
            if (lista.isEmpty()) {
                mostrarInfo("No tienes direcciones registradas.");
            } else {
                lista.forEach(d -> System.out.println(
                        "ID: " + d.getIdDireccion() +
                                " | CiudadID: " + d.getIdCiudad() +
                                " | Calle: " + d.getCalle() +
                                " | Puerta: " + d.getNumPuerta() +
                                " | Apto: " + (d.getNumApto() == null ? "-" : d.getNumApto())
                ));
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // ============================================================
    // MODIFICAR DIRECCIÓN
    // ============================================================
    private void modificarDireccion() {
        int idDireccion = leerEntero("ID de la dirección a modificar: ");
        try {
            Direccion d = direccionProxy.obtenerDireccion(idUsuario, idDireccion);
            if (d == null) {
                mostrarError("Dirección no encontrada.");
                return;
            }

            mostrarInfo("Campos modificables: calle, numPuerta, numApto, idCiudad");
            String campo = leerTexto("Campo a modificar: ").toLowerCase();

            boolean exito = switch (campo) {
                case "calle" -> direccionProxy.actualizarDireccion(idUsuario, idDireccion, leerTexto("Nueva calle: "), null, null, 0);
                case "numpuerta", "nupuerta" -> direccionProxy.actualizarDireccion(idUsuario, idDireccion, null, leerTexto("Nuevo número de puerta: "), null, 0);
                case "numapto", "napto" -> direccionProxy.actualizarDireccion(idUsuario, idDireccion, null, null, leerTexto("Nuevo número de apto: "), 0);
                case "idciudad" -> direccionProxy.actualizarDireccion(idUsuario, idDireccion, null, null, null, leerEntero("Nuevo ID de ciudad: "));
                default -> {
                    mostrarError("Campo inválido.");
                    yield false;
                }
            };

            if (exito) mostrarExito("Dirección modificada correctamente.");
            else mostrarError("No se pudo modificar la dirección.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // ============================================================
    // ELIMINAR DIRECCIÓN
    // ============================================================
    private void eliminarDireccion() {
        int idDireccion = leerEntero("ID de la dirección a eliminar: ");
        try {
            Direccion d = direccionProxy.obtenerDireccion(idUsuario, idDireccion);
            if (d == null) {
                mostrarError("Dirección no encontrada.");
                return;
            }

            boolean exito = direccionProxy.eliminarDireccion(idUsuario, idDireccion);
            if (exito) mostrarExito("Dirección eliminada correctamente.");
            else mostrarError("No se pudo eliminar la dirección.");

        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }
}
