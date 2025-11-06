package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.PerteneceProxy;
import modelo.Pertenece;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class PerteneceConsola extends UIBase {

    private final PerteneceProxy proxy;

    // Constructor: inicializa el proxy
    public PerteneceConsola() throws Exception {
        this.proxy = new PerteneceProxy();
    }

    // Muestra el menú principal del módulo
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ RELACIÓN CARRERA ↔ ITR =====");
        System.out.println("1. Agregar relación");
        System.out.println("2. Eliminar relación");
        System.out.println("3. Listar todas las relaciones");
        System.out.println("4. Listar ITRs de una carrera");
        System.out.println("5. Listar carreras de un ITR");
        System.out.println("0. Volver al menú principal");
        System.out.println("=======================================");
    }

    // Maneja la opción elegida por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> agregarRelacion();
                case 2 -> eliminarRelacion();
                case 3 -> listarTodos();
                case 4 -> listarItrPorCarrera();
                case 5 -> listarCarrerasPorItr();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error inesperado al procesar la opción: " + e.getMessage());
        }
    }

    // Agrega una nueva relación entre carrera e ITR
    private void agregarRelacion() {
        int idCarrera = leerEntero("ID de la carrera: ");
        int idItr = leerEntero("ID del ITR: ");

        try {
            boolean exito = proxy.agregarPertenece(idCarrera, idItr);
            if (exito) mostrarExito("Relación agregada correctamente.");
            else mostrarError("No se pudo agregar la relación.");
        } catch (SQLException e) {
            mostrarError("Error SQL al agregar relación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al agregar relación: " + e.getMessage());
        }
    }

    // Elimina una relación existente entre carrera e ITR
    private void eliminarRelacion() {
        int idCarrera = leerEntero("ID de la carrera: ");
        int idItr = leerEntero("ID del ITR: ");

        try {
            boolean exito = proxy.eliminarPertenece(idCarrera, idItr);
            if (exito) mostrarExito("Relación eliminada correctamente.");
            else mostrarError("No se pudo eliminar la relación.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar relación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar relación: " + e.getMessage());
        }
    }

    // Lista todas las relaciones carrera ↔ ITR
    private void listarTodos() {
        try {
            List<Pertenece> relaciones = proxy.listarTodos();
            if (relaciones.isEmpty()) mostrarInfo("No hay relaciones registradas.");
            else relaciones.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar relaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar relaciones: " + e.getMessage());
        }
    }

    // Lista los ITRs asociados a una carrera
    private void listarItrPorCarrera() {
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            List<Integer> itrs = proxy.listarItrPorCarrera(idCarrera);
            if (itrs.isEmpty()) mostrarInfo("La carrera no tiene ITRs asociados.");
            else mostrarInfo("ITRs asociados a la carrera: " + itrs);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar ITRs: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar ITRs: " + e.getMessage());
        }
    }

    // Lista las carreras asociadas a un ITR
    private void listarCarrerasPorItr() {
        int idItr = leerEntero("ID del ITR: ");
        try {
            List<Integer> carreras = proxy.listarCarrerasPorItr(idItr);
            if (carreras.isEmpty()) mostrarInfo("El ITR no tiene carreras asociadas.");
            else mostrarInfo("Carreras asociadas al ITR: " + carreras);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar carreras: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar carreras: " + e.getMessage());
        }
    }
}
