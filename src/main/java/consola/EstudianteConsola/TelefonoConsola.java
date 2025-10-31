package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.TeleUsuarioProxy;
import SINGLETON.LoginSingleton;
import modelo.TeleUsuario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class TelefonoConsola extends UIBase {

    private final TeleUsuarioProxy teleUsuarioProxy;
    private final int idUsuario;

    public TelefonoConsola() throws SQLException {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("❌ No hay sesión activa. Por favor inicia sesión.");
        }
        this.idUsuario = LoginSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.teleUsuarioProxy = new TeleUsuarioProxy();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n📱 GESTIÓN DE TELÉFONOS DEL USUARIO ID: " + idUsuario);
        System.out.println("1. Crear teléfono");
        System.out.println("2. Listar mis teléfonos");
        System.out.println("3. Actualizar teléfono");
        System.out.println("4. Eliminar teléfono");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearTelefono();
                case 2 -> listarTelefonos();
                case 3 -> actualizarTelefono();
                case 4 -> eliminarTelefono();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida.");
            }
        } catch (SQLException e) {
            mostrarError("Error SQL: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // ============================================================
    // CRUD DE TELÉFONOS
    // ============================================================
    private void crearTelefono() throws SQLException {
        String numero = leerTexto("Ingrese el número de teléfono: ");
        TeleUsuario nuevo = teleUsuarioProxy.crearTelefono(numero, idUsuario);
        mostrarExito("Teléfono creado con éxito. ID generado: " + nuevo.getIdTelefono());
    }

    private void listarTelefonos() throws SQLException {
        List<TeleUsuario> lista = teleUsuarioProxy.listarTelefonosPorUsuario(idUsuario);

        if (lista.isEmpty()) {
            mostrarInfo("No tienes teléfonos registrados.");
        } else {
            System.out.println("\n📋 MIS TELÉFONOS:");
            lista.forEach(t -> System.out.println(
                    "ID: " + t.getIdTelefono() + " | Número: " + t.getNumero()
            ));
        }
    }

    private void actualizarTelefono() throws SQLException {
        int idTel = leerEntero("Ingrese el ID del teléfono a actualizar: ");
        String numero = leerTexto("Ingrese el nuevo número: ");

        boolean actualizado = teleUsuarioProxy.actualizarTelefono(idTel, numero, idUsuario);
        if (actualizado)
            mostrarExito("Teléfono actualizado correctamente.");
        else
            mostrarError("No se pudo actualizar el teléfono. Verifica que el ID te pertenezca.");
    }

    private void eliminarTelefono() throws SQLException {
        int idTel = leerEntero("Ingrese el ID del teléfono a eliminar: ");
        boolean eliminado = teleUsuarioProxy.eliminarTelefono(idTel);

        if (eliminado)
            mostrarExito("Teléfono eliminado correctamente.");
        else
            mostrarError("No se encontró el teléfono o no pertenece a ti.");
    }
}
