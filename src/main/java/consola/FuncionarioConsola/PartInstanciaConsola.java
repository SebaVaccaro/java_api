package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PartInstanciaProxy;
import modelo.PartInstancia;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaConsola extends UIBase {

    private final PartInstanciaProxy proxy;

    // Constructor: inicializa el proxy de relaciones participante-instancia
    public PartInstanciaConsola() throws Exception {
        this.proxy = new PartInstanciaProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ PARTICIPANTES EN INSTANCIAS =====");
        System.out.println("1. Agregar participante a instancia");
        System.out.println("2. Eliminar participante de instancia");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar instancias de un participante");
        System.out.println("5. Listar participantes de una instancia");
        System.out.println("0. Volver al menú principal");
        System.out.println("============================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarParticipante();
                case 2 -> eliminarParticipante();
                case 3 -> listarTodos();
                case 4 -> listarInstanciasPorParticipante();
                case 5 -> listarParticipantesPorInstancia();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al procesar la opción: " + e.getMessage());
        }
    }

    // Agrega un participante a una instancia
    private void agregarParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        int idInstancia = leerEntero("ID de la instancia: ");

        try {
            boolean exito = proxy.agregarParticipante(idParticipante, idInstancia);
            if (exito) mostrarExito("Participante agregado correctamente a la instancia.");
            else mostrarError("No se pudo agregar el participante.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Elimina un participante de una instancia
    private void eliminarParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        int idInstancia = leerEntero("ID de la instancia: ");

        try {
            boolean exito = proxy.eliminarParticipante(idParticipante, idInstancia);
            if (exito) mostrarExito("Participante eliminado correctamente de la instancia.");
            else mostrarError("No se pudo eliminar el participante.");
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Lista todas las relaciones entre participantes e instancias
    private void listarTodos() {
        try {
            List<PartInstancia> relaciones = proxy.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Lista las instancias asociadas a un participante
    private void listarInstanciasPorParticipante() {
        int idParticipante = leerEntero("ID del participante: ");
        try {
            List<Integer> instancias = proxy.listarInstanciasPorParticipante(idParticipante);
            if (instancias.isEmpty()) mostrarInfo("El participante no tiene instancias asociadas.");
            else mostrarInfo("Instancias del participante: " + instancias);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Lista los participantes asociados a una instancia
    private void listarParticipantesPorInstancia() {
        int idInstancia = leerEntero("ID de la instancia: ");
        try {
            List<Integer> participantes = proxy.listarParticipantesPorInstancia(idInstancia);
            if (participantes.isEmpty()) mostrarInfo("No hay participantes registrados para esta instancia.");
            else mostrarInfo("Participantes de la instancia: " + participantes);
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
