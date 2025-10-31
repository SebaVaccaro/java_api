package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PerteneceProxy;
import modelo.Pertenece;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PerteneceConsola extends UIBase {

    private final PerteneceProxy facade;

    public PerteneceConsola() throws SQLException {
        this.facade = new PerteneceProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n=== Gestión de Pertenece (Carrera ↔ ITR) ===");
        System.out.println("1. Agregar relación");
        System.out.println("2. Eliminar relación");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar ITRs de una carrera");
        System.out.println("5. Listar carreras de un ITR");
        System.out.println("0. Salir");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarRelacion();
            case 2 -> eliminarRelacion();
            case 3 -> listarTodos();
            case 4 -> listarItrPorCarrera();
            case 5 -> listarCarrerasPorItr();
            case 0 -> mostrarInfo("Saliendo...");
            default -> mostrarError("Opción no válida.");
        }
    }

    private void agregarRelacion() {
        int idCarrera = leerEntero("ID de carrera: ");
        int idItr = leerEntero("ID de ITR: ");
        try {
            boolean exito = facade.agregarPertenece(idCarrera, idItr);
            if (exito) mostrarExito("Relación agregada.");
            else mostrarError("No se pudo agregar la relación.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void eliminarRelacion() {
        int idCarrera = leerEntero("ID de carrera: ");
        int idItr = leerEntero("ID de ITR: ");
        try {
            boolean exito = facade.eliminarPertenece(idCarrera, idItr);
            if (exito) mostrarExito("Relación eliminada.");
            else mostrarError("No se pudo eliminar la relación.");
        } catch (SQLException e) {
            mostrarError("Error de base de datos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodos() {
        try {
            List<Pertenece> relaciones = facade.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarItrPorCarrera() {
        int idCarrera = leerEntero("ID de carrera: ");
        try {
            List<Integer> itrs = facade.listarItrPorCarrera(idCarrera);
            mostrarInfo("ITRs de la carrera: " + itrs);
        } catch (SQLException e) {
            mostrarError("Error al listar ITRs: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarCarrerasPorItr() {
        int idItr = leerEntero("ID de ITR: ");
        try {
            List<Integer> carreras = facade.listarCarrerasPorItr(idItr);
            mostrarInfo("Carreras del ITR: " + carreras);
        } catch (SQLException e) {
            mostrarError("Error al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}
