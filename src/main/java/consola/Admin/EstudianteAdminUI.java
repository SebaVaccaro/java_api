package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.EstudianteProxy;
import modelo.Estudiante;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EstudianteAdminUI extends UIBase {

    private final EstudianteProxy proxy;

    public EstudianteAdminUI() throws Exception {
        this.proxy = new EstudianteProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ESTUDIANTES =====");
        System.out.println("1. Crear estudiante");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar estudiante");
        System.out.println("5. Desactivar estudiante");
        System.out.println("0. Volver al menú anterior");
        System.out.println("=============================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearEstudiante();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarEstudiante();
                case 5 -> desactivarEstudiante();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    private void crearEstudiante() {
        String cedula = leerTexto("Cédula: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String password = leerTexto("Password: ");
        int idGrupo = leerEntero("ID de grupo: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");
        try {
            Estudiante e = proxy.crearEstudiante(cedula, nombre, apellido, password, idGrupo, fechaNacimiento);
            mostrarExito("Estudiante creado: " + e);
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al crear estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al crear estudiante: " + ex.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Estudiante> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay estudiantes registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException ex) {
            mostrarError("Error al listar estudiantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar estudiantes: " + ex.getMessage());
        }
    }

    private void buscarPorId() {
        int idEstudiante = leerEntero("ID del estudiante: ");
        try {
            Estudiante e = proxy.obtenerPorId(idEstudiante);
            if (e != null) System.out.println(e);
            else mostrarInfo("Estudiante no encontrado.");
        } catch (SQLException ex) {
            mostrarError("Error al buscar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar estudiante: " + ex.getMessage());
        }
    }

    private void modificarEstudiante() {
        int idEstudiante = leerEntero("ID del estudiante a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        int idGrupo = leerEntero("Nuevo ID de grupo: ");
        boolean activo = leerBoolean("¿Activo? (true/false): ");
        try {
            Estudiante e = new Estudiante(idEstudiante, cedula, nombre, apellido, username, password, null, idGrupo, activo);
            boolean exito = proxy.actualizarEstudiante(e);
            if (exito) mostrarExito("Estudiante modificado correctamente.");
            else mostrarError("No se pudo modificar el estudiante.");
        } catch (SQLException ex) {
            mostrarError("Error al modificar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar estudiante: " + ex.getMessage());
        }
    }

    private void desactivarEstudiante() {
        int idEstudiante = leerEntero("ID del estudiante a desactivar: ");
        try {
            boolean exito = proxy.desactivarEstudiante(idEstudiante);
            if (exito) mostrarExito("Estudiante desactivado correctamente.");
            else mostrarError("No se pudo desactivar el estudiante.");
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al desactivar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al desactivar estudiante: " + ex.getMessage());
        }
    }
}
