package consola.FuncionarioConsola;

import PROXY.TeleUsuarioProxy;
import modelo.TeleUsuario;
import utils.CapturadoraDeErrores;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.util.List;

public class TeleUsuarioConsola extends UIBase {

    private final TeleUsuarioProxy proxy;

    public TeleUsuarioConsola() throws Exception {
        this.proxy = new TeleUsuarioProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ TELÉFONOS DE USUARIO ---");
        System.out.println("1. Agregar teléfono");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar teléfono");
        System.out.println("5. Eliminar teléfono");
        System.out.println("6. Listar por usuario");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarTelefono();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> modificarTelefono();
            case 5 -> eliminarTelefono();
            case 6 -> listarPorUsuario();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // ============================================================
    // CREAR TELÉFONO
    // ============================================================
    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idUsuario = leerEntero("ID del usuario: ");

        try {
            TeleUsuario t = proxy.crearTelefono(numero, idUsuario);
            if (t != null) mostrarExito("Teléfono agregado: " + t);
            else mostrarError("No se pudo agregar el teléfono.");
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // LISTAR TODOS
    // ============================================================
    private void listarTodos() {
        try {
            List<TeleUsuario> list = proxy.listarTelefonos();
            if (list == null || list.isEmpty()) mostrarInfo("No hay teléfonos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleUsuario t = proxy.obtenerTelefono(id);
            if (t != null) System.out.println(t);
            else mostrarError("No se encontró teléfono con ese ID.");
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // MODIFICAR TELÉFONO
    // ============================================================
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

            // Usar métodos opcionales de UIBase
            String numero = leerTexto("Nuevo número (vacío para no cambiar): ", t.getNumero());
            int idUsuario = leerEntero("Nuevo ID de usuario (vacío para no cambiar): ", t.getIdUsuario());

            t.setNumero(numero);
            t.setIdUsuario(idUsuario);

            boolean exito = proxy.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdUsuario());

            if (exito) mostrarExito("Teléfono modificado correctamente.");
            else mostrarError("No se pudo modificar el teléfono.");

        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // ELIMINAR TELÉFONO
    // ============================================================
    private void eliminarTelefono() {
        int id = leerEntero("ID del teléfono a eliminar: ");
        try {
            boolean exito = proxy.eliminarTelefono(id);
            if (exito) mostrarExito("Teléfono eliminado correctamente.");
            else mostrarError("No se pudo eliminar el teléfono.");
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // LISTAR POR USUARIO
    // ============================================================
    private void listarPorUsuario() {
        int idUsuario = leerEntero("ID del usuario: ");
        try {
            List<TeleUsuario> list = proxy.listarTelefonosPorUsuario(idUsuario);
            if (list == null || list.isEmpty()) mostrarInfo("No hay teléfonos para este usuario.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}

