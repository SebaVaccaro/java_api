package consola.FuncionarioConsola;

import PROXY.TeleITRProxy;
import modelo.TeleITR;
import utils.CapturadoraDeErrores;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.util.List;

public class TeleITRConsola extends UIBase {

    private final TeleITRProxy proxy;

    // Constructor: inicializa el proxy
    public TeleITRConsola() throws SQLException, Exception {
        this.proxy = new TeleITRProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE TELÉFONOS ITR =====");
        System.out.println("1. Agregar teléfono");
        System.out.println("2. Listar todos los teléfonos");
        System.out.println("3. Buscar teléfono por ID");
        System.out.println("4. Modificar teléfono");
        System.out.println("5. Eliminar teléfono");
        System.out.println("0. Volver al menú principal");
        System.out.println("====================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarTelefono();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarTelefono();
                case 5 -> eliminarTelefono();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Agrega un nuevo teléfono
    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idItr = leerEntero("ID del ITR: ");

        try {
            boolean exito = proxy.agregarTelefono(numero, idItr);
            if (exito) mostrarExito("Teléfono agregado correctamente.");
            else mostrarError("No se pudo agregar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al agregar teléfono: " + e.getMessage());
        }
    }

    // Lista todos los teléfonos registrados
    private void listarTodos() {
        try {
            List<TeleITR> telefonos = proxy.listarTodos();
            if (telefonos.isEmpty()) {
                mostrarInfo("No hay teléfonos registrados.");
            } else {
                mostrarInfo("=== LISTA DE TELÉFONOS ITR ===");
                telefonos.forEach(System.out::println);
            }
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar teléfonos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al listar teléfonos: " + e.getMessage());
        }
    }

    // Busca un teléfono por su ID
    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleITR telefono = proxy.buscarPorId(id);
            if (telefono != null) {
                mostrarInfo("=== TELÉFONO ENCONTRADO ===");
                System.out.println(telefono);
            } else {
                mostrarError("No se encontró teléfono con ese ID.");
            }
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al buscar teléfono: " + e.getMessage());
        }
    }

    // Modifica los datos de un teléfono existente
    private void modificarTelefono() {
        int id = leerEntero("ID del teléfono a modificar: ");
        try {
            TeleITR telefono = proxy.buscarPorId(id);
            if (telefono == null) {
                mostrarError("No se encontró teléfono con ese ID.");
                return;
            }

            mostrarInfo("=== DATOS ACTUALES ===");
            System.out.println(telefono);

            // Nuevos valores (ENTER para mantener los actuales)
            String nuevoNumero = leerTexto("Nuevo número (ENTER para mantener): ", telefono.getNumero());
            int nuevoIdItr = leerEntero("Nuevo ID de ITR (ENTER para mantener): ", telefono.getIdItr());

            telefono.setNumero(nuevoNumero);
            telefono.setIdItr(nuevoIdItr);

            boolean exito = proxy.actualizarTelefono(telefono.getIdTelefono(), telefono.getNumero(), telefono.getIdItr());
            if (exito) mostrarExito("Teléfono modificado correctamente.");
            else mostrarError("No se pudo modificar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al modificar teléfono: " + e.getMessage());
        }
    }

    // Elimina un teléfono existente
    private void eliminarTelefono() {
        int id = leerEntero("ID del teléfono a eliminar: ");
        try {
            boolean exito = proxy.eliminarTelefono(id);
            if (exito) mostrarExito("Teléfono eliminado correctamente.");
            else mostrarError("No se pudo eliminar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar teléfono: " + e.getMessage());
        }
    }
}
