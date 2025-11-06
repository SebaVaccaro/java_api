package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PartSeguimientoProxy;
import modelo.PartSeguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PartSeguimientoConsola extends UIBase {

    private final PartSeguimientoProxy proxy;

    // Constructor: inicializa el proxy de relaciones participante-seguimiento
    public PartSeguimientoConsola() throws Exception {
        this.proxy = new PartSeguimientoProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ PARTICIPANTES EN SEGUIMIENTOS =====");
        System.out.println("1. Agregar participante a seguimiento");
        System.out.println("2. Eliminar participante de seguimiento");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar seguimientos de un participante");
        System.out.println("5. Listar participantes de un seguimiento");
        System.out.println("0. Volver al menú principal");
        System.out.println("===============================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarParticipante();
                case 2 -> eliminarParticipante();
                case 3 -> listarTodos();
                case 4 -> listarSeguimientosPorParticipante();
                case 5 -> listarParticipantesPorSeguimiento();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al procesar la opción: " + e.getMessage());
        }
    }

    // Agrega un participante a un seguimiento
    private void agregarParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        int idSeguimiento = leerEntero("ID del seguimiento: ");
        try {
            boolean exito = proxy.agregarParticipante(idParticipante, idSeguimiento);
            if (exito) mostrarExito("Participante agregado correctamente al seguimiento.");
            else mostrarError("No se pudo agregar el participante.");
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar participante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al agregar participante: " + e.getMessage());
        }
    }

    // Elimina un participante de un seguimiento
    private void eliminarParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        int idSeguimiento = leerEntero("ID del seguimiento: ");
        try {
            boolean exito = proxy.eliminarParticipante(idParticipante, idSeguimiento);
            if (exito) mostrarExito("Participante eliminado correctamente del seguimiento.");
            else mostrarError("No se pudo eliminar el participante.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar participante: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar participante: " + e.getMessage());
        }
    }

    // Lista todas las relaciones entre participantes y seguimientos
    private void listarTodos() {
        try {
            List<PartSeguimiento> relaciones = proxy.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar relaciones: " + e.getMessage());
        }
    }

    // Lista los seguimientos asociados a un participante
    private void listarSeguimientosPorParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        try {
            List<Integer> seguimientos = proxy.listarSeguimientosPorParticipante(idParticipante);
            if (seguimientos.isEmpty()) mostrarInfo("El participante no tiene seguimientos asociados.");
            else mostrarInfo("Seguimientos del participante: " + seguimientos);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar seguimientos: " + e.getMessage());
        }
    }

    // Lista los participantes asociados a un seguimiento
    private void listarParticipantesPorSeguimiento() {
        int idSeguimiento = leerEntero("ID del seguimiento: ");
        try {
            List<Integer> participantes = proxy.listarParticipantesPorSeguimiento(idSeguimiento);
            if (participantes.isEmpty()) mostrarInfo("No hay participantes registrados para este seguimiento.");
            else mostrarInfo("Participantes del seguimiento: " + participantes);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar participantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar participantes: " + e.getMessage());
        }
    }
}
