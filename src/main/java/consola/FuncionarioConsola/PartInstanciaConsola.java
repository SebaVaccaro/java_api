package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PartInstanciaProxy;
import modelo.PartInstancia;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PartInstanciaConsola extends UIBase {

    private final PartInstanciaProxy facade;

    // Constructor: inicializa el proxy de participante-instancia
    public PartInstanciaConsola() throws SQLException {
        this.facade = new PartInstanciaProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    protected void mostrarMenu() {
        System.out.println("\n=== Gestión de Participantes en Instancias ===");
        System.out.println("1. Agregar participante a instancia");
        System.out.println("2. Eliminar participante de instancia");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar instancias de un participante");
        System.out.println("5. Listar participantes de una instancia");
        System.out.println("0. Salir");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarParticipante();              // Agregar participante
            case 2 -> eliminarParticipante();             // Eliminar participante
            case 3 -> listarTodos();                      // Listar todas las relaciones
            case 4 -> listarInstanciasPorParticipante();  // Listar instancias de un participante
            case 5 -> listarParticipantesPorInstancia();  // Listar participantes de una instancia
            case 0 -> mostrarInfo("Saliendo...");
            default -> mostrarError("Opción no válida.");
        }
    }

    // Agrega un participante a una instancia
    private void agregarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idInst = leerEntero("ID de la instancia: ");

        try {
            boolean exito = facade.agregarParticipante(idPart, idInst);
            if (exito) mostrarExito("Participante agregado correctamente.");
            else mostrarError("No se pudo agregar el participante.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Elimina un participante de una instancia
    private void eliminarParticipante() {
        int idPart = leerEntero("ID del participante: ");
        int idInst = leerEntero("ID de la instancia: ");

        try {
            boolean exito = facade.eliminarParticipante(idPart, idInst);
            if (exito) mostrarExito("Participante eliminado correctamente.");
            else mostrarError("No se pudo eliminar el participante.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Lista todas las relaciones entre participantes e instancias
    public void listarTodos() {
        try {
            List<PartInstancia> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Lista las instancias asociadas a un participante
    public void listarInstanciasPorParticipante() {
        int idPart = leerEntero("ID del participante: ");
        try {
            List<Integer> instancias = facade.listarInstanciasPorParticipante(idPart);
            mostrarInfo("Instancias del participante: " + instancias);
        } catch (SQLException e) {
            mostrarError("Error al listar instancias: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Lista los participantes asociados a una instancia
    private void listarParticipantesPorInstancia() {
        int idInst = leerEntero("ID de la instancia: ");
        try {
            List<Integer> participantes = facade.listarParticipantesPorInstancia(idInst);
            mostrarInfo("Participantes de la instancia: " + participantes);
        } catch (SQLException e) {
            mostrarError("Error al listar participantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}

