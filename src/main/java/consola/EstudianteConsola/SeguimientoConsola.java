package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.SeguimientoProxy;
import SINGLETON.SesionSingleton;
import modelo.Seguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoConsola extends UIBase {

    private final SeguimientoProxy seguimientoProxy;
    private final int idEstudiante;

    // Constructor: valida sesión activa y obtiene el ID del estudiante autenticado
    public SeguimientoConsola() throws SQLException {
        if (!SesionSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("❌ No hay sesión activa. Por favor inicia sesión.");
        }
        this.idEstudiante = SesionSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.seguimientoProxy = new SeguimientoProxy();
    }

    // Mostrar el menú principal del módulo de seguimientos del estudiante
    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ DE SEGUIMIENTOS DEL ESTUDIANTE =====");
        System.out.println("1. Ver mis seguimientos");
        System.out.println("2. Buscar seguimiento por ID");
        System.out.println("3. Cerrar seguimiento");
        System.out.println("0. Volver al menú principal");
        System.out.println("===============================================");
    }


    // Gestionar la opción seleccionada por el estudiante
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarMisSeguimientos(); // Mostrar todos los seguimientos del estudiante actual
            case 2 -> buscarPorId();           // Consultar un seguimiento específico
            case 3 -> cerrarSeguimiento();     // Cerrar un seguimiento activo
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Listar todos los seguimientos activos del estudiante autenticado
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

    // Buscar un seguimiento por su ID y mostrar sus detalles
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

    // Cerrar un seguimiento activo perteneciente al estudiante autenticado
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
