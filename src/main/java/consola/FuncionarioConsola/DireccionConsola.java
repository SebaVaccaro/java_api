package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.DireccionProxy;
import SINGLETON.SesionSingleton;
import modelo.Direccion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class DireccionConsola extends UIBase {

    private final DireccionProxy proxy;
    private final SesionSingleton sesionSingleton;

    // Constructor: inicializa el proxy y la sesión del usuario
    public DireccionConsola() throws Exception {
        this.proxy = new DireccionProxy();
        this.sesionSingleton = SesionSingleton.getInstance();
    }

    // Mostrar menú principal
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ DE GESTIÓN DE DIRECCIONES =====");
        System.out.println("1. Crear nueva dirección");
        System.out.println("2. Listar todas las direcciones");
        System.out.println("3. Buscar dirección por ID");
        System.out.println("4. Listar direcciones por usuario");
        System.out.println("5. Listar direcciones por ciudad");
        System.out.println("6. Modificar dirección existente");
        System.out.println("7. Eliminar dirección");
        System.out.println("0. Volver al menú principal");
        System.out.println("==========================================");
    }


    // Manejar opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearDireccion();
            case 2 -> listarTodas();
            case 3 -> buscarPorId();
            case 4 -> listarPorUsuario();
            case 5 -> listarPorCiudad();
            case 6 -> modificarDireccion();
            case 7 -> eliminarDireccion();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear nueva dirección
    private void crearDireccion() {
        String calle = leerTextoNoNull("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento: ");
        int idCiudad = leerEntero("ID de la ciudad: ");
        int idUsuario = leerEntero("ID del propietario: ");
        try {
            Direccion direccion = proxy.crearDireccion(
                    idUsuario,
                    calle, numPuerta, numApto, idCiudad
            );

            mostrarExito("Dirección creada con éxito: " + direccion);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar todas las direcciones
    private void listarTodas() {
        try {
            List<Direccion> lista = proxy.listarDirecciones();

            if (lista.isEmpty())
                mostrarInfo("No hay direcciones registradas.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar dirección por ID
    private void buscarPorId() {
        int idDireccion = leerEntero("ID de la dirección: ");

        try {
            Direccion d = proxy.obtenerDireccion(
                    idDireccion
            );

            if (d != null)
                System.out.println(d);
            else
                mostrarInfo("No se encontró ninguna dirección con ese ID.");

        } catch (SQLException e) {
            mostrarError("Error SQL al buscar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar direcciones de un usuario
    private void listarPorUsuario() {
        int idUsuarioObjetivo = leerEntero("ID del usuario: ");

        try {
            List<Direccion> lista = proxy.listarPorUsuario(
                    idUsuarioObjetivo
            );

            if (lista.isEmpty())
                mostrarInfo("No hay direcciones registradas para este usuario.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones por usuario: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar direcciones por ciudad
    private void listarPorCiudad() {
        int idCiudad = leerEntero("ID de la ciudad: ");

        try {
            List<Direccion> lista = proxy.listarPorCiudad(
                    sesionSingleton.getUsuarioActual().getIdUsuario(),
                    idCiudad
            );

            if (lista.isEmpty())
                mostrarInfo("No hay direcciones registradas en esta ciudad.");
            else
                lista.forEach(System.out::println);

        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones por ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modificar dirección existente (versión mejorada)
    private void modificarDireccion() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idDireccion = leerEntero("Ingrese el ID de la dirección a modificar: ");

        try {
            Direccion d = proxy.obtenerDireccion(idDireccion);

            if (d == null) {
                mostrarInfo("No existe una dirección con ese ID.");
                return;
            }

            System.out.println("\nDirección seleccionada:");
            System.out.println(d);

            System.out.println("\n--- Campos actuales ---");
            System.out.println("1. Calle: " + d.getCalle());
            System.out.println("2. Número de puerta: " + d.getNumPuerta());
            System.out.println("3. Número de apartamento: " + d.getNumApto());
            System.out.println("4. ID de ciudad: " + d.getIdCiudad());
            System.out.println("0. Cancelar");
            System.out.println("------------------------");

            int opcion = leerEntero("Seleccione el campo a modificar: ");
            boolean exito = false;

            switch (opcion) {
                case 1 -> {
                    d.setCalle(leerTextoNoNull("Nueva calle: "));
                    exito = proxy.actualizarDireccion(
                            sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(),
                            d.getCalle(),
                            d.getNumPuerta(),
                            d.getNumApto(),
                            d.getIdCiudad()
                    );
                }
                case 2 -> {
                    d.setNumPuerta(leerTexto("Nuevo número de puerta: "));
                    exito = proxy.actualizarDireccion(
                            sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(),
                            d.getCalle(),
                            d.getNumPuerta(),
                            d.getNumApto(),
                            d.getIdCiudad()
                    );
                }
                case 3 -> {
                    d.setNumApto(leerTexto("Nuevo número de apartamento: "));
                    exito = proxy.actualizarDireccion(
                            sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(),
                            d.getCalle(),
                            d.getNumPuerta(),
                            d.getNumApto(),
                            d.getIdCiudad()
                    );
                }
                case 4 -> {
                    d.setIdCiudad(leerEntero("Nuevo ID de ciudad: "));
                    exito = proxy.actualizarDireccion(
                            sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(),
                            d.getCalle(),
                            d.getNumPuerta(),
                            d.getNumApto(),
                            d.getIdCiudad()
                    );
                }
                case 0 -> {
                    mostrarInfo("Operación cancelada.");
                    return;
                }
                default -> {
                    mostrarError("Opción inválida.");
                    return;
                }
            }

            if (exito)
                mostrarExito("Dirección modificada correctamente.");
            else
                mostrarError("No se pudo modificar la dirección.");

        } catch (SecurityException s) {
            mostrarError(s.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }


    // Eliminar dirección
    private void eliminarDireccion() {
        int idDireccion = leerEntero("ID de la dirección a eliminar: ");

        try {
            boolean eliminado = proxy.eliminarDireccion(
                    idDireccion
            );

            if (eliminado)
                mostrarExito("Dirección eliminada correctamente.");
            else
                mostrarError("No se pudo eliminar la dirección.");

        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
