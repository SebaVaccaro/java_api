package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.TeleUsuarioProxy;
import SINGLETON.SesionSingleton;
import modelo.TeleUsuario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class TelefonoConsola extends UIBase {

    private final TeleUsuarioProxy teleUsuarioProxy;
    private final int idUsuario;

    // Constructor: inicializa el proxy y valida la sesión activa
    public TelefonoConsola() throws Exception {
        if (!SesionSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("No hay sesión activa. Por favor, inicia sesión.");
        }
        this.teleUsuarioProxy = new TeleUsuarioProxy();
        this.idUsuario = SesionSingleton.getInstance().getUsuarioActual().getIdUsuario();
    }

    // Mostrar menú principal de gestión de teléfonos
    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ DE GESTIÓN DE TELÉFONOS =====");
        System.out.println("Usuario ID: " + idUsuario);
        System.out.println("1. Crear nuevo teléfono");
        System.out.println("2. Listar mis teléfonos");
        System.out.println("3. Actualizar teléfono existente");
        System.out.println("4. Eliminar teléfono");
        System.out.println("0. Volver al menú principal");
        System.out.println("=========================================");
    }

    // Manejar opción seleccionada por el usuario
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearTelefono();
            case 2 -> listarTelefonos();
            case 3 -> actualizarTelefono();
            case 4 -> eliminarTelefono();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crear un nuevo teléfono
    private void crearTelefono() {
        String numero = leerTexto("Ingrese el número de teléfono: ");
        try {
            TeleUsuario nuevo = teleUsuarioProxy.crearTelefono(numero, idUsuario);
            mostrarExito("Teléfono creado con éxito. ID generado: " + nuevo.getIdTelefono());
        } catch (SQLException e) {
            mostrarError("Error al crear teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar todos los teléfonos del usuario autenticado
    private void listarTelefonos() {
        try {
            List<TeleUsuario> lista = teleUsuarioProxy.listarTelefonosPorUsuario(idUsuario);

            if (lista.isEmpty()) {
                mostrarInfo("No tienes teléfonos registrados.");
                return;
            }

            mostrarInfo("Tus teléfonos registrados:");
            for (TeleUsuario t : lista) {
                System.out.println(t);
            }

        } catch (SQLException e) {
            mostrarError("Error al listar teléfonos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Actualizar un teléfono existente
    private void actualizarTelefono() {
        int idTel = leerEntero("Ingrese el ID del teléfono a actualizar: ");
        String numero = leerTexto("Ingrese el nuevo número: ");

        try {
            boolean actualizado = teleUsuarioProxy.actualizarTelefono(idTel, numero, idUsuario);
            if (actualizado)
                mostrarExito("Teléfono actualizado correctamente.");
            else
                mostrarError("No se pudo actualizar el teléfono. Verifica que el ID te pertenezca.");
        } catch (SQLException e) {
            mostrarError("Error al actualizar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Eliminar (desactivar) un teléfono del usuario autenticado
    private void eliminarTelefono() {
        int idTel = leerEntero("Ingrese el ID del teléfono a eliminar: ");

        try {
            boolean eliminado = teleUsuarioProxy.eliminarTelefono(idTel);
            if (eliminado)
                mostrarExito("Teléfono eliminado correctamente.");
            else
                mostrarError("No se encontró el teléfono o no pertenece a ti.");
        } catch (SQLException e) {
            mostrarError("Error al eliminar teléfono: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
