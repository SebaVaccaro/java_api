package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.GrupoProxy;
import modelo.Grupo;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class GrupoAdminUI extends UIBase {

    private final GrupoProxy proxy;

    public GrupoAdminUI() throws Exception {
        this.proxy = new GrupoProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ GRUPOS =====");
        System.out.println("1. Crear grupo");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar por carrera");
        System.out.println("5. Modificar grupo");
        System.out.println("6. Eliminar grupo");
        System.out.println("0. Volver al menú anterior");
        System.out.println("========================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearGrupo();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> listarPorCarrera();
                case 5 -> modificarGrupo();
                case 6 -> eliminarGrupo();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    private void crearGrupo() {
        String nombre = leerTexto("Nombre del grupo: ");
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            Grupo g = proxy.crearGrupo(nombre, idCarrera);
            mostrarExito("Grupo creado: " + g);
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al crear grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear grupo: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Grupo> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay grupos registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar grupos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar grupos: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID del grupo: ");
        try {
            Grupo g = proxy.obtenerPorId(id);
            if (g != null) System.out.println(g);
            else mostrarInfo("Grupo no encontrado.");
        } catch (SQLException e) {
            mostrarError("Error al buscar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar grupo: " + e.getMessage());
        }
    }

    private void listarPorCarrera() {
        int idCarrera = leerEntero("ID de la carrera: ");
        try {
            List<Grupo> lista = proxy.listarPorCarrera(idCarrera);
            if (lista.isEmpty()) mostrarInfo("No hay grupos para esta carrera.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar grupos por carrera: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar grupos por carrera: " + e.getMessage());
        }
    }

    private void modificarGrupo() {
        int id = leerEntero("ID del grupo a modificar: ");
        String nombre = leerTexto("Nuevo nombre: ");
        int idCarrera = leerEntero("Nuevo ID de carrera: ");
        try {
            boolean exito = proxy.actualizarGrupo(id, nombre, idCarrera);
            if (exito) mostrarExito("Grupo modificado correctamente.");
            else mostrarError("No se pudo modificar el grupo.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar grupo: " + e.getMessage());
        }
    }

    private void eliminarGrupo() {
        int id = leerEntero("ID del grupo a eliminar: ");
        try {
            boolean exito = proxy.eliminarGrupo(id);
            if (exito) mostrarExito("Grupo eliminado correctamente.");
            else mostrarError("No se pudo eliminar el grupo.");
        } catch (SecurityException e) {
            mostrarError(e.getMessage());
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar grupo: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar grupo: " + e.getMessage());
        }
    }
}
