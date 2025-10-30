package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.ITRProxy;
import modelo.ITR;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class ITRAdminUI extends UIBase {

    private final ITRProxy proxy;

    public ITRAdminUI() throws Exception {
        this.proxy = new ITRProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ ITR ---");
        System.out.println("1. Crear ITR");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar ITR");
        System.out.println("5. Eliminar ITR");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearITR();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> modificarITR();
            case 5 -> eliminarITR();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // ==== CRUD ====
    private void crearITR() {
        int idDireccion = leerEntero("ID de dirección: ");
        try {
            ITR nuevoITR = new ITR(idDireccion);
            ITR creado = proxy.crearITR(nuevoITR);
            mostrarExito("ITR creado correctamente: " + creado);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear ITR: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear ITR: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<ITR> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay ITRs registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar ITRs: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar ITRs: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        int idItr = leerEntero("ID del ITR: ");
        try {
            ITR itr = proxy.obtenerITR(idItr);
            if (itr != null) mostrarInfo(itr.toString());
            else mostrarError("ITR no encontrado.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar ITR: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar ITR: " + e.getMessage());
        }
    }

    private void modificarITR() {
        int idItr = leerEntero("ID del ITR a modificar: ");
        int idDireccion = leerEntero("Nuevo ID de dirección: ");
        try {
            ITR itrModificado = new ITR(idItr, idDireccion);
            boolean exito = proxy.actualizarITR(itrModificado);
            if (exito) mostrarExito("ITR modificado correctamente.");
            else mostrarError("No se pudo modificar el ITR.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar ITR: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar ITR: " + e.getMessage());
        }
    }

    private void eliminarITR() {
        int idItr = leerEntero("ID del ITR a eliminar: ");
        try {
            boolean exito = proxy.eliminarITR(idItr);
            if (exito) mostrarExito("ITR eliminado correctamente.");
            else mostrarError("No se pudo eliminar el ITR.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar ITR: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar ITR: " + e.getMessage());
        }
    }
}
