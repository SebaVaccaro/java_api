package consola.FuncionarioConsola;

import PROXY.SeguimientoProxy;
import modelo.Seguimiento;
import utils.CapturadoraDeErrores;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoConsola extends UIBase {

    private final SeguimientoProxy proxy;

    // Constructor: inicializa el proxy
    public SeguimientoConsola() throws SQLException {
        this.proxy = new SeguimientoProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE SEGUIMIENTOS =====");
        System.out.println("1. Agregar seguimiento");
        System.out.println("2. Listar todos los seguimientos");
        System.out.println("3. Buscar seguimiento por ID");
        System.out.println("4. Modificar seguimiento");
        System.out.println("5. Eliminar seguimiento");
        System.out.println("0. Volver al menú principal");
        System.out.println("===================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarSeguimiento();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarSeguimiento();
                case 5 -> eliminarSeguimiento();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción no válida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Agrega un nuevo seguimiento
    private void agregarSeguimiento() {
        int idEstudiante = leerEntero("ID del estudiante: ");
        LocalDate fecInicio = leerFecha("Fecha de inicio (yyyy-MM-dd): ");
        boolean estActivo = leerBoolean("¿Está activo? (true/false): ");

        try {
            boolean exito = proxy.agregarSeguimiento(null, idEstudiante, fecInicio, null, estActivo);
            if (exito) mostrarExito("Seguimiento agregado correctamente.");
            else mostrarError("No se pudo agregar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            mostrarError("Datos inválidos: " + e.getMessage());
        }
    }

    // Lista todos los seguimientos registrados
    private void listarTodos() {
        try {
            List<Seguimiento> seguimientos = proxy.listarTodos();
            if (seguimientos.isEmpty()) {
                mostrarInfo("No hay seguimientos registrados.");
            } else {
                mostrarInfo("=== LISTA DE SEGUIMIENTOS ===");
                seguimientos.forEach(System.out::println);
            }
        } catch (SQLException e) {
            mostrarError("Error SQL al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Busca un seguimiento por su ID
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            Seguimiento seguimiento = proxy.buscarPorId(id);
            if (seguimiento != null) {
                mostrarInfo("=== SEGUIMIENTO ENCONTRADO ===");
                System.out.println(seguimiento);
            } else {
                mostrarError("No se encontró un seguimiento con ese ID.");
            }
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Modifica un seguimiento existente
    private void modificarSeguimiento() {
        int id = leerEntero("ID del seguimiento a modificar: ");
        try {
            Seguimiento seguimiento = proxy.buscarPorId(id);
            if (seguimiento == null) {
                mostrarError("No se encontró seguimiento con ese ID.");
                return;
            }

            mostrarInfo("=== DATOS ACTUALES DEL SEGUIMIENTO ===");
            System.out.println(seguimiento);

            // Leer nuevos valores (opcionalmente mantener los actuales)
            Integer nuevoInforme = leerEntero("Nuevo ID de informe (ENTER para mantener): ", seguimiento.getIdInforme());
            int nuevoEstudiante = leerEntero("Nuevo ID de estudiante (ENTER para mantener): ", seguimiento.getIdEstudiante());
            LocalDate nuevaFecInicio = leerFecha("Nueva fecha de inicio (ENTER para mantener): ", seguimiento.getFecInicio());
            LocalDate nuevaFecCierre = leerFecha("Nueva fecha de cierre (ENTER para mantener): ", seguimiento.getFecCierre());
            boolean nuevoEstado = leerBoolean("¿Activo? (ENTER para mantener): ", seguimiento.isEstActivo());

            // Actualizar el objeto en memoria
            seguimiento.setIdInforme(nuevoInforme);
            seguimiento.setIdEstudiante(nuevoEstudiante);
            seguimiento.setFecInicio(nuevaFecInicio);
            seguimiento.setFecCierre(nuevaFecCierre);
            seguimiento.setEstActivo(nuevoEstado);

            boolean exito = proxy.actualizarSeguimiento(
                    seguimiento.getIdSeguimiento(),
                    seguimiento.getIdInforme(),
                    seguimiento.getIdEstudiante(),
                    seguimiento.getFecInicio(),
                    seguimiento.getFecCierre(),
                    seguimiento.isEstActivo()
            );

            if (exito) mostrarExito("Seguimiento actualizado correctamente.");
            else mostrarError("No se pudo actualizar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Elimina un seguimiento existente
    private void eliminarSeguimiento() {
        int id = leerEntero("ID del seguimiento a eliminar: ");
        try {
            boolean exito = proxy.eliminarSeguimiento(id);
            if (exito) mostrarExito("Seguimiento eliminado correctamente.");
            else mostrarError("No se pudo eliminar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Permite ejecutar directamente el módulo
    public static void main(String[] args) {
        try {
            SeguimientoConsola ui = new SeguimientoConsola();
            ui.iniciar();
        } catch (Exception e) {
            System.err.println("Error al iniciar consola de Seguimientos: " + e.getMessage());
        }
    }
}
