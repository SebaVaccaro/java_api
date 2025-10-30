package consola.Estudiante;

import consola.interfaz.UIBase;
import PROXY.SeguimientoProxy;
import SINGLETON.LoginSingleton;
import modelo.Seguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoEstudianteUI extends UIBase {

    private final SeguimientoProxy seguimientoProxy;
    private final int idEstudiante;

    public SeguimientoEstudianteUI() throws SQLException {
        if (!LoginSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("❌ No hay sesión activa. Por favor inicia sesión.");
        }
        this.idEstudiante = LoginSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.seguimientoProxy = new SeguimientoProxy();
    }

    @Override
    protected void mostrarMenu() {
        System.out.println("\n--- MENÚ DE SEGUIMIENTOS DEL ESTUDIANTE ---");
        System.out.println("1. Ver mis seguimientos");
        System.out.println("2. Buscar seguimiento por ID");
        System.out.println("3. Cerrar seguimiento");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarMisSeguimientos();
            case 2 -> buscarPorId();
            case 3 -> cerrarSeguimiento();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    private void listarMisSeguimientos() {
        try {
            List<Seguimiento> lista = seguimientoProxy.listarTodos();
            boolean encontrado = false;

            for (Seguimiento s : lista) {
                if (s.getIdEstudiante() == idEstudiante && s.isEstActivo()) {
                    System.out.println(s);
                    encontrado = true;
                }
            }

            if (!encontrado) mostrarInfo("No tienes seguimientos activos.");

        } catch (SQLException e) {
            mostrarError("Error al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            Seguimiento s = seguimientoProxy.buscarPorId(id);
            if (s == null) {
                mostrarError("Seguimiento no encontrado.");
                return;
            }

            if (s.getIdEstudiante() != idEstudiante) {
                mostrarError("No puedes acceder a un seguimiento que no es tuyo.");
                return;
            }

            mostrarInfo("📄 Detalles del seguimiento:");
            System.out.println(s);

        } catch (SQLException e) {
            mostrarError("Error al buscar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void cerrarSeguimiento() {
        int id = leerEntero("Ingrese el ID del seguimiento a cerrar: ");
        try {
            Seguimiento s = seguimientoProxy.buscarPorId(id);
            if (s == null) {
                mostrarError("Seguimiento no encontrado.");
                return;
            }

            if (s.getIdEstudiante() != idEstudiante) {
                mostrarError("No puedes cerrar un seguimiento que no te pertenece.");
                return;
            }

            if (!s.isEstActivo()) {
                mostrarInfo("Este seguimiento ya está cerrado.");
                return;
            }

            LocalDate fechaCierre = LocalDate.now();
            boolean exito = seguimientoProxy.actualizarSeguimiento(
                    s.getIdSeguimiento(),
                    s.getIdInforme(),
                    s.getIdEstudiante(),
                    s.getFecInicio(),
                    fechaCierre,
                    false
            );

            if (exito) mostrarExito("Seguimiento cerrado correctamente el " + fechaCierre);
            else mostrarError("No se pudo cerrar el seguimiento.");

        } catch (SQLException e) {
            mostrarError("Error al cerrar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}

