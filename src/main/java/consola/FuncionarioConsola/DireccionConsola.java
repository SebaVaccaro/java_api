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
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        String calle = leerTexto("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento: ");
        int idCiudad = leerEntero("ID de la ciudad: ");

        try {
            Direccion direccion = proxy.crearDireccion(
                    sesionSingleton.getUsuarioActual().getIdUsuario(),
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

    // Modificar dirección existente
    private void modificarDireccion() {
        if (!sesionSingleton.haySesionActiva()) {
            mostrarError("No hay sesión activa.");
            return;
        }

        int idDireccion = leerEntero("ID de la dirección a modificar: ");

        try {
            Direccion d = proxy.obtenerDireccion(
                    idDireccion
            );

            if (d == null) {
                mostrarInfo("La dirección no existe.");
                return;
            }

            System.out.println("Dirección seleccionada: " + d);
            System.out.println("\nCampos modificables: calle, numPuerta, numApto, idCiudad");

            String campo = leerTexto("Campo a modificar: ");
            boolean exito = false;

            switch (campo.toLowerCase()) {
                case "calle" -> {
                    d.setCalle(leerTexto("Nueva calle: "));
                    exito = proxy.actualizarDireccion(sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(), d.getCalle(), d.getNumPuerta(), d.getNumApto(), d.getIdCiudad());
                }
                case "numpuerta" -> {
                    d.setNumPuerta(leerTexto("Nuevo número de puerta: "));
                    exito = proxy.actualizarDireccion(sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(), d.getCalle(), d.getNumPuerta(), d.getNumApto(), d.getIdCiudad());
                }
                case "numapto" -> {
                    d.setNumApto(leerTexto("Nuevo número de apartamento: "));
                    exito = proxy.actualizarDireccion(sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(), d.getCalle(), d.getNumPuerta(), d.getNumApto(), d.getIdCiudad());
                }
                case "idciudad" -> {
                    d.setIdCiudad(leerEntero("Nuevo ID de ciudad: "));
                    exito = proxy.actualizarDireccion(sesionSingleton.getUsuarioActual().getIdUsuario(),
                            d.getIdDireccion(), d.getCalle(), d.getNumPuerta(), d.getNumApto(), d.getIdCiudad());
                }
                default -> mostrarError("Campo inválido.");
            }

            if (exito)
                mostrarExito("Dirección modificada correctamente.");
            else
                mostrarError("No se pudo modificar la dirección.");

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
