package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.DireccionProxy;
import modelo.Direccion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class DireccionConsola extends UIBase {

    private final DireccionProxy proxy;

    public DireccionConsola() throws Exception {
        this.proxy = new DireccionProxy();
    }

    // ==========================================================
    // MENÚ PRINCIPAL
    // ==========================================================
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ DIRECCIONES =====");
        System.out.println("1. Crear dirección");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar por usuario");
        System.out.println("5. Listar por ciudad");
        System.out.println("6. Modificar dirección");
        System.out.println("7. Eliminar dirección");
        System.out.println("0. Volver al menú anterior");
        System.out.println("=============================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearDireccion();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> listarPorUsuario();
                case 5 -> listarPorCiudad();
                case 6 -> modificarDireccion();
                case 7 -> eliminarDireccion();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // ==========================================================
    // CRUD DIRECCIONES
    // ==========================================================

    private void crearDireccion() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        String calle = leerTexto("Calle: ");
        String numPuerta = leerTexto("Número de puerta: ");
        String numApto = leerTexto("Número de apartamento: ");
        int idCiudad = leerEntero("ID de ciudad: ");

        try {
            Direccion d = proxy.crearDireccion(idUsuarioActual, calle, numPuerta, numApto, idCiudad);
            mostrarExito("Dirección creada: " + d);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear dirección: " + e.getMessage());
        }
    }

    private void listarTodas() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        try {
            List<Direccion> lista = proxy.listarDirecciones(idUsuarioActual);
            if (lista.isEmpty()) mostrarInfo("No hay direcciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar direcciones: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        int idDireccion = leerEntero("ID de la dirección: ");
        try {
            Direccion d = proxy.obtenerDireccion(idUsuarioActual, idDireccion);
            if (d != null) System.out.println(d);
            else mostrarInfo("Dirección no encontrada.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar dirección: " + e.getMessage());
        }
    }

    private void listarPorUsuario() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        int idUsuarioObjetivo = leerEntero("ID del usuario cuyas direcciones se listarán: ");
        try {
            List<Direccion> lista = proxy.listarPorUsuario(idUsuarioActual, idUsuarioObjetivo);
            if (lista.isEmpty()) mostrarInfo("No hay direcciones para este usuario.");
            else lista.forEach(System.out::println);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones por usuario: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar direcciones por usuario: " + e.getMessage());
        }
    }

    private void listarPorCiudad() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        int idCiudad = leerEntero("ID de la ciudad: ");
        try {
            List<Direccion> lista = proxy.listarPorCiudad(idUsuarioActual, idCiudad);
            if (lista.isEmpty()) mostrarInfo("No hay direcciones para esta ciudad.");
            else lista.forEach(System.out::println);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar direcciones por ciudad: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar direcciones por ciudad: " + e.getMessage());
        }
    }

    private void modificarDireccion() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        int idDireccion = leerEntero("ID de la dirección a modificar: ");
        String calle = leerTexto("Nueva calle: ");
        String numPuerta = leerTexto("Nuevo número de puerta: ");
        String numApto = leerTexto("Nuevo número de apartamento: ");
        int idCiudad = leerEntero("Nuevo ID de ciudad: ");

        try {
            boolean exito = proxy.actualizarDireccion(idUsuarioActual, idDireccion, calle, numPuerta, numApto, idCiudad);
            if (exito) mostrarExito("Dirección modificada correctamente.");
            else mostrarError("No se pudo modificar la dirección.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar dirección: " + e.getMessage());
        }
    }

    private void eliminarDireccion() {
        int idUsuarioActual = leerEntero("ID del usuario que realiza la acción: ");
        int idDireccion = leerEntero("ID de la dirección a eliminar: ");
        try {
            boolean exito = proxy.eliminarDireccion(idUsuarioActual, idDireccion);
            if (exito) mostrarExito("Dirección eliminada correctamente.");
            else mostrarError("No se pudo eliminar la dirección.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar dirección: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar dirección: " + e.getMessage());
        }
    }
}
