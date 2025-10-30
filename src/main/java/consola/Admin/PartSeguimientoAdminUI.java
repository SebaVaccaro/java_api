package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.PartSeguimientoProxy;
import modelo.PartSeguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoAdminUI extends UIBase {

    private final PartSeguimientoProxy facade;

    public PartSeguimientoAdminUI() throws SQLException {
        this.facade = new PartSeguimientoProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== Gestión de Participantes en Seguimientos ===");
        System.out.println("1. Agregar participante a seguimiento");
        System.out.println("2. Eliminar participante de seguimiento");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar seguimientos de un participante");
        System.out.println("5. Listar participantes de un seguimiento");
        System.out.println("0. Salir");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarParticipante();
            case 2 -> eliminarParticipante();
            case 3 -> listarTodos();
            case 4 -> listarSeguimientosPorParticipante();
            case 5 -> listarParticipantesPorSeguimiento();
            case 0 -> mostrarInfo("Saliendo...");
            default -> mostrarError("Opción no válida.");
        }
    }

    private void agregarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            boolean exito = facade.agregarParticipante(idPart, idSeg);
            if (exito) mostrarExito("Participante agregado correctamente.");
            else mostrarError("No se pudo agregar el participante.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            boolean exito = facade.eliminarParticipante(idPart, idSeg);
            if (exito) mostrarExito("Participante eliminado correctamente.");
            else mostrarError("No se pudo eliminar el participante.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<PartSeguimiento> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarSeguimientosPorParticipante() {
        int idPart = leerEntero("ID del participante: ");
        try {
            List<Integer> seguimientos = facade.listarSeguimientosPorParticipante(idPart);
            mostrarInfo("Seguimientos del participante: " + seguimientos);
        } catch (SQLException e) {
            mostrarError("Error al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarParticipantesPorSeguimiento() {
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            List<Integer> participantes = facade.listarParticipantesPorSeguimiento(idSeg);
            mostrarInfo("Participantes del seguimiento: " + participantes);
        } catch (SQLException e) {
            mostrarError("Error al listar participantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Para ejecutar directamente
    public static void main(String[] args) throws SQLException {
        PartSeguimientoAdminUI ui = new PartSeguimientoAdminUI();
        ui.iniciar();
    }
}
