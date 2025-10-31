package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PartSeguimientoProxy;
import modelo.PartSeguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoConsola extends UIBase {

    private final PartSeguimientoProxy facade;

    // Constructor: inicializa el proxy de participante-seguimiento
    public PartSeguimientoConsola() throws SQLException {
        this.facade = new PartSeguimientoProxy();
    }

    // Muestra el menú principal del módulo
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

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarParticipante();              // Agregar participante
            case 2 -> eliminarParticipante();             // Eliminar participante
            case 3 -> listarTodos();                      // Listar todas las relaciones
            case 4 -> listarSeguimientosPorParticipante();// Listar seguimientos por participante
            case 5 -> listarParticipantesPorSeguimiento();// Listar participantes por seguimiento
            case 0 -> mostrarInfo("Saliendo...");
            default -> mostrarError("Opción no válida.");
        }
    }

    // Agrega un participante a un seguimiento
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

    // Elimina un participante de un seguimiento
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

    // Lista todas las relaciones entre participantes y seguimientos
    private void listarTodos() {
        try {
            List<PartSeguimiento> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Lista los seguimientos asociados a un participante
    private void listarSeguimientosPorParticipante() {
        int idPart = leerEntero("ID del participante: ");
        try {
            List<Integer> seguimientos = facade.listarSeguimientosPorParticipante(idPart);
            mostrarInfo("Seguimientos del participante: " + seguimientos);
        } catch (SQLException e) {
            mostrarError("Error al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Lista los participantes asociados a un seguimiento
    private void listarParticipantesPorSeguimiento() {
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            List<Integer> participantes = facade.listarParticipantesPorSeguimiento(idSeg);
            mostrarInfo("Participantes del seguimiento: " + participantes);
        } catch (SQLException e) {
            mostrarError("Error al listar participantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Permite ejecutar el módulo directamente
    public static void main(String[] args) throws SQLException {
        PartSeguimientoConsola ui = new PartSeguimientoConsola();
        ui.iniciar();
    }
}
