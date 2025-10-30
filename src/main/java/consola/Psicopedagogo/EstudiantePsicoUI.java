package consola.Psicopedagogo;

import consola.interfaz.UIBase;
import PROXY.EstudianteProxy;
import modelo.Estudiante;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class EstudiantePsicoUI extends UIBase {

    private final EstudianteProxy proxy;

    public EstudiantePsicoUI() throws Exception {
        this.proxy = new EstudianteProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ ESTUDIANTES (PSICOPEDAGOGO) =====");
        System.out.println("1. Listar todos");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Modificar estudiante");
        System.out.println("4. Desactivar estudiante");
        System.out.println("0. Volver al menú principal");
        System.out.println("============================================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> listarTodos();
                case 2 -> buscarPorId();
                case 3 -> modificarEstudiante();
                case 4 -> desactivarEstudiante();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR ESTUDIANTES
    // ============================================================
    private void listarTodos() {
        try {
            List<Estudiante> lista = proxy.listarTodos();
            if (lista.isEmpty()) {
                mostrarInfo("No hay estudiantes registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            mostrarError("Error al listar estudiantes: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar estudiantes: " + ex.getMessage());
        }
    }

    // ============================================================
    // BUSCAR ESTUDIANTE POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del estudiante: ");
        try {
            Estudiante e = proxy.obtenerPorId(id);
            if (e != null) {
                System.out.println(e);
            } else {
                mostrarInfo("Estudiante no encontrado.");
            }
        } catch (SQLException ex) {
            mostrarError("Error al buscar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar estudiante: " + ex.getMessage());
        }
    }

    // ============================================================
    // MODIFICAR ESTUDIANTE
    // ============================================================
    private void modificarEstudiante() {
        int id = leerEntero("ID del estudiante a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        int idGrupo = leerEntero("Nuevo ID de grupo: ");
        boolean activo = leerBoolean("¿Activo? (true/false): ");

        try {
            Estudiante e = new Estudiante(id, cedula, nombre, apellido, username, password, null, idGrupo, activo);
            boolean exito = proxy.actualizarEstudiante(e);

            if (exito) {
                mostrarExito("Estudiante modificado correctamente.");
            } else {
                mostrarError("No se pudo modificar el estudiante.");
            }
        } catch (SecurityException se) {
            mostrarError("Permiso denegado: " + se.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error al modificar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // ============================================================
    // DESACTIVAR ESTUDIANTE
    // ============================================================
    private void desactivarEstudiante() {
        int id = leerEntero("ID del estudiante a desactivar: ");
        try {
            boolean exito = proxy.desactivarEstudiante(id);
            if (exito) {
                mostrarExito("Estudiante desactivado correctamente.");
            } else {
                mostrarError("No se pudo desactivar el estudiante.");
            }
        } catch (SecurityException se) {
            mostrarError("Permiso denegado: " + se.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al desactivar estudiante: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}
