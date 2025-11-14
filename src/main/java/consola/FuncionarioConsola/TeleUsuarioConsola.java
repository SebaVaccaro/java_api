package consola.FuncionarioConsola;

import PROXY.TeleUsuarioProxy;
import modelo.TeleUsuario;
import utils.CapturadoraDeErrores;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioConsola extends UIBase {

    private final TeleUsuarioProxy proxy;

    // Constructor: inicializa el proxy de teléfonos de usuario
    public TeleUsuarioConsola() throws SQLException {
        this.proxy = new TeleUsuarioProxy();
    }

    // Muestra el menú principal
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE TELÉFONOS DE USUARIO =====");
        System.out.println("1. Agregar teléfono");
        System.out.println("2. Listar todos los teléfonos");
        System.out.println("3. Buscar teléfono por ID");
        System.out.println("4. Modificar teléfono");
        System.out.println("5. Eliminar teléfono");
        System.out.println("6. Listar teléfonos por usuario");
        System.out.println("0. Volver al menú principal");
        System.out.println("============================================");
    }

    // Ejecuta la opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarTelefono();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarTelefono();
                case 5 -> eliminarTelefono();
                case 6 -> listarPorUsuario();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Agrega un nuevo teléfono
    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idUsuario = leerEntero("ID del usuario: ");

        try {
            TeleUsuario t = proxy.crearTelefono(numero, idUsuario);
            if (t != null) mostrarExito("Teléfono agregado correctamente: " + t);
            else mostrarError("No se pudo agregar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al agregar teléfono: " + e.getMessage());
        }
    }

    // Lista todos los teléfonos registrados
    private void listarTodos() {
        try {
            List<TeleUsuario> lista = proxy.listarTelefonos();
            if (lista == null || lista.isEmpty()) {
                mostrarInfo("No hay teléfonos registrados.");
            } else {
                mostrarInfo("=== LISTA DE TELÉFONOS DE USUARIO ===");
                lista.forEach(System.out::println);
            }
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError( CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al listar teléfonos: " + e.getMessage());
        }
    }

    // Busca un teléfono por su ID
    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleUsuario t = proxy.obtenerTelefono(id);
            if (t != null) {
                mostrarInfo("=== TELÉFONO ENCONTRADO ===");
                System.out.println(t);
            } else {
                mostrarError("No se encontró teléfono con ese ID.");
            }
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al buscar teléfono: " + e.getMessage());
        }
    }

    // Modificar los datos de un teléfono
    private void modificarTelefono() {
        int id = leerEntero("ID del teléfono a modificar: ");
        try {
            TeleUsuario t = proxy.obtenerTelefono(id);
            if (t == null) {
                mostrarError("No se encontró teléfono con ese ID.");
                return;
            }

            mostrarInfo("Campos actuales:");
            System.out.println(t);

            String numero = leerTexto("Nuevo número (ENTER para mantener): ", t.getNumero());
            int idUsuario = leerEntero("Nuevo ID de usuario (ENTER para mantener): ", t.getIdUsuario());

            t.setNumero(numero);
            t.setIdUsuario(idUsuario);

            boolean exito = proxy.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdUsuario());
            if (exito) mostrarExito("Teléfono modificado correctamente.");
            else mostrarError("No se pudo modificar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
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
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al eliminar teléfono: " + e.getMessage());
        }
    }

    // Lista teléfonos pertenecientes a un usuario específico
    private void listarPorUsuario() {
        int idUsuario = leerEntero("ID del usuario: ");
        try {
            List<TeleUsuario> lista = proxy.listarTelefonosPorUsuario(idUsuario);
            if (lista == null || lista.isEmpty()) {
                mostrarInfo("No hay teléfonos registrados para este usuario.");
            } else {
                mostrarInfo("=== TELÉFONOS DEL USUARIO " + idUsuario + " ===");
                lista.forEach(System.out::println);
            }
        } catch (SecurityException e) {
            mostrarInfo("Permiso denegado: " + e.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado al listar teléfonos: " + e.getMessage());
        }
    }
}
