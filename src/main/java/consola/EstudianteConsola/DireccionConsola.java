package consola.EstudianteConsola;

import PROXY.DireccionProxy;
import SINGLETON.SesionSingleton;
import consola.InterfazConsola.UIBase;
import modelo.Direccion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class DireccionConsola extends UIBase {

    private final DireccionProxy direccionProxy;
    private final int idEstudiante; // usuario autenticado

    // Inicialización de la consola del estudiante (direcciones)
    public DireccionConsola() throws SQLException {

        if (!SesionSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("No hay sesión activa. Por favor inicia sesión.");
        }
        this.idEstudiante = SesionSingleton.getInstance().getUsuarioActual().getIdUsuario();

        this.direccionProxy = new DireccionProxy();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ DE MIS DIRECCIONES =====");
        System.out.println("1. Crear nueva dirección");
        System.out.println("2. Listar mis direcciones");
        System.out.println("3. Modificar dirección existente");
        System.out.println("4. Eliminar dirección");
        System.out.println("0. Volver al menú principal");
        System.out.println("==================================");
    }


    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearDireccion();       // Crear una nueva dirección
            case 2 -> listarMisDirecciones(); // Listar todas las direcciones del usuario
            case 3 -> modificarDireccion();   // Modificar una dirección existente
            case 4 -> eliminarDireccion();    // Eliminar una dirección existente
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear una nueva dirección para el usuario autenticado
    private void crearDireccion() {
        int idCiudad = leerEntero("ID de ciudad: ");
        String calle = leerTexto("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento (opcional): ");

        try {
            Direccion d = direccionProxy.crearDireccion(
                    idEstudiante,
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

    // Listar todas las direcciones activas pertenecientes al usuario autenticado
    private void listarMisDirecciones() {
        try {
            List<Direccion> lista = direccionProxy.listarPorUsuario(idEstudiante);
            if (lista.isEmpty()) {
                mostrarInfo("No tienes direcciones registradas.");
            } else {
                for(Direccion d: lista){
                    System.out.println(d);
                }
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Permitir al usuario modificar un campo específico de una dirección existente
    private void modificarDireccion() {
        int idDireccion = leerEntero("ID de la dirección a modificar: ");
        try {
            Direccion d = direccionProxy.obtenerDireccion(idDireccion);
            if (d == null) {
                mostrarError("Dirección no encontrada.");
                return;
            }

            mostrarInfo("Campos modificables: calle, numPuerta, numApto, idCiudad");
            String campo = leerTexto("Campo a modificar: ").toLowerCase();

            boolean exito = switch (campo) {
                case "calle" -> direccionProxy.actualizarDireccion(idEstudiante, idDireccion, leerTexto("Nueva calle: "), null, null, 0);
                case "numpuerta", "nupuerta" -> direccionProxy.actualizarDireccion(idEstudiante, idDireccion, null, leerTexto("Nuevo número de puerta: "), null, 0);
                case "numapto", "napto" -> direccionProxy.actualizarDireccion(idEstudiante, idDireccion, null, null, leerTexto("Nuevo número de apto: "), 0);
                case "idciudad" -> direccionProxy.actualizarDireccion(idEstudiante, idDireccion, null, null, null, leerEntero("Nuevo ID de ciudad: "));
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
            mostrarError("Error al modificar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // Eliminar una dirección del usuario (validando propiedad y existencia)
    private void eliminarDireccion() {
        int idDireccion = leerEntero("ID de la dirección a eliminar: ");
        try {
            Direccion d = direccionProxy.obtenerDireccion(idDireccion);
            if (d == null) {
                mostrarError("Dirección no encontrada.");
                return;
            }

            boolean exito = direccionProxy.eliminarDireccion(idDireccion);
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
