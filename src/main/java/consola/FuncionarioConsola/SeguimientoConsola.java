package consola.FuncionarioConsola;

import PROXY.SeguimientoProxy;
import modelo.Seguimiento;
import utils.CapturadoraDeErrores;
import consola.InterfazConsola.UIBase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoConsola extends UIBase {

    private final SeguimientoProxy facade;

    // Constructor: inicializa el proxy de seguimientos
    public SeguimientoConsola() throws SQLException {
        this.facade = new SeguimientoProxy();
    }

    // Muestra el menú principal de opciones
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ SEGUIMIENTOS ---");
        System.out.println("1. Agregar seguimiento");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar seguimiento");
        System.out.println("5. Eliminar seguimiento");
        System.out.println("0. Volver al menú principal");
    }

    // Ejecuta la opción seleccionada
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarSeguimiento();
            case 2 -> listarTodos();
            case 3 -> buscarPorId();
            case 4 -> modificarSeguimiento();
            case 5 -> eliminarSeguimiento();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Agrega un nuevo seguimiento
    private void agregarSeguimiento() {
        int idEstudiante = leerEntero("ID Estudiante: ");
        LocalDate fecInicio = leerFecha("Fecha de inicio (yyyy-MM-dd): ");
        boolean estActivo = leerBoolean("¿Está activo? (true/false): ");

        try {
            boolean exito = facade.agregarSeguimiento(null, idEstudiante, fecInicio, null, estActivo);
            if (exito) mostrarExito("Seguimiento agregado correctamente.");
            else mostrarError("No se pudo agregar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    // Lista todos los seguimientos registrados
    private void listarTodos() {
        try {
            List<Seguimiento> list = facade.listarTodos();
            if (list.isEmpty()) mostrarInfo("No hay seguimientos registrados.");
            else list.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Busca un seguimiento por su ID
    private void buscarPorId() {
        int id = leerEntero("ID del seguimiento: ");
        try {
            Seguimiento s = facade.buscarPorId(id);
            if (s != null) System.out.println(s);
            else mostrarError("No se encontró seguimiento con ese ID.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Modifica los datos de un seguimiento existente
    private void modificarSeguimiento() {
        int id = leerEntero("ID del seguimiento a modificar: ");
        try {
            Seguimiento s = facade.buscarPorId(id);
            if (s == null) {
                mostrarError("No se encontró seguimiento con ese ID.");
                return;
            }

            mostrarInfo("Campos actuales:");
            System.out.println(s);

            // Leer nuevos valores (pueden dejarse vacíos para mantener los actuales)
            Integer idInforme = leerEntero("Nuevo ID Informe (vacío = sin cambios): ", s.getIdInforme());
            int idEstudiante = leerEntero("Nuevo ID Estudiante (vacío = sin cambios): ", s.getIdEstudiante());
            LocalDate fecInicio = leerFecha("Nueva fecha de inicio (vacío = sin cambios): ", s.getFecInicio());
            LocalDate fecCierre = leerFecha("Nueva fecha de cierre (vacío = sin cambios): ", s.getFecCierre());
            boolean estActivo = leerBoolean("¿Está activo? (vacío = sin cambios): ", s.isEstActivo());

            // Actualizar el objeto
            s.setIdInforme(idInforme);
            s.setIdEstudiante(idEstudiante);
            s.setFecInicio(fecInicio);
            s.setFecCierre(fecCierre);
            s.setEstActivo(estActivo);

            boolean exito = facade.actualizarSeguimiento(
                    s.getIdSeguimiento(),
                    s.getIdInforme(),
                    s.getIdEstudiante(),
                    s.getFecInicio(),
                    s.getFecCierre(),
                    s.isEstActivo()
            );

            if (exito) mostrarExito("Seguimiento modificado correctamente.");
            else mostrarError("No se pudo modificar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    // Elimina un seguimiento existente
    private void eliminarSeguimiento() {
        int id = leerEntero("ID del seguimiento a eliminar: ");
        try {
            if (facade.eliminarSeguimiento(id)) mostrarExito("Seguimiento eliminado.");
            else mostrarError("No se pudo eliminar el seguimiento.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }
}
