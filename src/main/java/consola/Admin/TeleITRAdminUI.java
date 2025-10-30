package consola.Admin;

import PROXY.TeleITRProxy;
import modelo.TeleITR;
import utils.CapturadoraDeErrores;
import consola.interfaz.UIBase;

import java.sql.SQLException;
import java.util.List;

public class TeleITRAdminUI extends UIBase {

    private final TeleITRProxy proxy;

    public TeleITRAdminUI() throws Exception {
        this.proxy = new TeleITRProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ TELÉFONOS ITR ---");
        System.out.println("1. Agregar teléfono");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar teléfono");
        System.out.println("5. Eliminar teléfono");
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
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // ============================================================
    // CREAR TELÉFONO
    // ============================================================
    private void agregarTelefono() {
        String numero = leerTexto("Número de teléfono: ");
        int idItr = leerEntero("ID del ITR: ");

        try {
            boolean exito = proxy.agregarTelefono(numero, idItr);
            if (exito) mostrarExito("Teléfono agregado correctamente.");
            else mostrarError("No se pudo agregar el teléfono.");
        } catch (SecurityException e) {
            mostrarInfo(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al agregar teléfono: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR TODOS
    // ============================================================
    private void listarTodos() {
        try {
            List<TeleITR> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay teléfonos registrados.");
            else lista.forEach(System.out::println);
        } catch (SecurityException e) {
            mostrarInfo(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al listar teléfonos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar teléfonos: " + e.getMessage());
        }
    }

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del teléfono: ");
        try {
            TeleITR t = proxy.buscarPorId(id);
            if (t != null) System.out.println(t);
            else mostrarError("No se encontró teléfono con ese ID.");
        } catch (SecurityException e) {
            mostrarInfo(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar teléfono: " + e.getMessage());
        }
    }

    // ============================================================
    // MODIFICAR TELÉFONO
    // ============================================================
    private void modificarTelefono() {
        int id = leerEntero("ID del teléfono a modificar: ");
        try {
            TeleITR t = proxy.buscarPorId(id);
            if (t == null) {
                mostrarError("No se encontró teléfono con ese ID.");
                return;
            }

            mostrarInfo("Campos actuales:");
            System.out.println(t);

            // Leer nuevos valores usando métodos opcionales de UIBase
            String numero = leerTexto("Nuevo número (vacío para no cambiar): ", t.getNumero());
            int idItr = leerEntero("Nuevo ID ITR (vacío para no cambiar): ", t.getIdItr());

            t.setNumero(numero);
            t.setIdItr(idItr);

            boolean exito = proxy.actualizarTelefono(t.getIdTelefono(), t.getNumero(), t.getIdItr());

            if (exito) mostrarExito("Teléfono modificado correctamente.");
            else mostrarError("No se pudo modificar el teléfono.");

        } catch (SecurityException e) {
            mostrarInfo(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar teléfono: " + e.getMessage());
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
        } catch (SecurityException e) {
            mostrarInfo(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar teléfono: " + e.getMessage());
        }
    }
}

