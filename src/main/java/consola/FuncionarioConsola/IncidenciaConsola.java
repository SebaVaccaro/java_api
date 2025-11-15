package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.IncidenciaProxy;
import modelo.Incidencia;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class IncidenciaConsola extends UIBase {

    private final IncidenciaProxy proxy;

    // Constructor: inicializa el proxy encargado de las operaciones de incidencias
    public IncidenciaConsola() throws Exception {
        this.proxy = new IncidenciaProxy();
    }

    // Mostrar el menú principal del módulo de incidencias
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ INCIDENCIAS =====");
        System.out.println("1. Crear incidencia");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar por funcionario");
        System.out.println("5. Modificar incidencia");
        System.out.println("6. Eliminar incidencia");
        System.out.println("0. Volver al menú principal");
        System.out.println("=============================");
    }

    // Manejar la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearIncidencia();
                case 2 -> listarTodas();
                case 3 -> buscarPorId();
                case 4 -> listarPorFuncionario();
                case 5 -> modificarIncidencia();
                case 6 -> eliminarIncidencia();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crear una nueva incidencia
    private void crearIncidencia() {
        String titulo = leerTexto("Título: ");
        OffsetDateTime fecha = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Descripción: ");
        boolean activo = leerBoolean("¿Está activa? (true/false): ");
        int idFuncionario = leerEntero("ID del funcionario: ");
        String lugar = leerTexto("Lugar: ");

        try {
            Incidencia incidencia = proxy.crearIncidencia(titulo, fecha, descripcion, activo, idFuncionario, lugar);
            mostrarExito("Incidencia creada correctamente: " + incidencia);
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al crear incidencia: " + ex.getMessage());
        }
    }

    // Listar todas las incidencias registradas
    private void listarTodas() {
        try {
            List<Incidencia> lista = proxy.listarIncidencias();
            if (lista.isEmpty()) {
                mostrarInfo("No hay incidencias registradas.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Buscar una incidencia por su instancia asociada
    private void buscarPorId() {
        int idInstancia = leerEntero("ID de la incidencia: ");
        try {
            Incidencia i = proxy.obtenerIncidencia(idInstancia);
            if (i != null) {
                System.out.println(i);
            } else {
                mostrarInfo("Incidencia no encontrada.");
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Listar incidencias de un funcionario específico
    private void listarPorFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        try {
            List<Incidencia> lista = proxy.listarPorFuncionario(idFuncionario);
            if (lista.isEmpty()) {
                mostrarInfo("No hay incidencias registradas para este funcionario.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SecurityException se) {
            mostrarError(se.getMessage());
        } catch (SQLException e) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // Modificar los datos de una incidencia existente
    private void modificarIncidencia() {
        int id = leerEntero("ID de la incidencia a modificar: ");
        String titulo = leerTexto("Nuevo título: ");
        String descripcion = leerTexto("Nueva descripción: ");
        boolean activo = leerBoolean("¿Está activa? (true/false): ");
        String lugar = leerTexto("Nuevo lugar: ");

        try {
            boolean exito = proxy.actualizarIncidencia(id, titulo, descripcion, activo, lugar);
            if (exito) {
                mostrarExito("Incidencia modificada correctamente.");
            } else {
                mostrarError("No se pudo modificar la incidencia.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar incidencia: " + ex.getMessage());
        }
    }

    // Eliminar una incidencia del sistema
    private void eliminarIncidencia() {
        int id = leerEntero("ID de la incidencia a eliminar: ");
        try {
            boolean exito = proxy.eliminarIncidencia(id);
            if (exito) {
                mostrarExito("Incidencia eliminada correctamente.");
            } else {
                mostrarError("No se pudo eliminar la incidencia.");
            }
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError(CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al eliminar incidencia: " + ex.getMessage());
        }
    }
}
