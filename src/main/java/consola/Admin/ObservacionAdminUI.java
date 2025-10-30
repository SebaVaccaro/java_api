package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.ObservacionProxy;
import modelo.Observacion;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class ObservacionAdminUI extends UIBase {

    private final ObservacionProxy facade;

    public ObservacionAdminUI() throws SQLException {
        this.facade = new ObservacionProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ OBSERVACIONES ---");
        System.out.println("1. Crear observación");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar observación");
        System.out.println("5. Desactivar observación");
        System.out.println("0. Volver al menú principal");
    }

    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearObservacion();
            case 2 -> listarTodas();
            case 3 -> buscarPorId();
            case 4 -> modificarObservacion();
            case 5 -> desactivarObservacion();
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // ==== CRUD ====
    private void crearObservacion() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        int idEstudiante = leerEntero("ID del estudiante: ");
        String titulo = leerTexto("Título: ");
        String contenido = leerTexto("Contenido: ");
        OffsetDateTime fecHora = OffsetDateTime.now();

        try {
            Observacion obs = facade.crearObservacion(idFuncionario, idEstudiante, titulo, contenido, fecHora);
            mostrarExito("Observación creada: " + obs);
        } catch (SQLException e) {
            mostrarError("Error al crear observación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void listarTodas() {
        try {
            List<Observacion> lista = facade.listarTodas();
            if (lista.isEmpty()) mostrarInfo("No hay observaciones registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar observaciones: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void buscarPorId() {
        int id = leerEntero("ID de observación: ");
        try {
            Observacion obs = facade.obtenerObservacion(id);
            if (obs != null) mostrarInfo(obs.toString());
            else mostrarError("Observación no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar observación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void modificarObservacion() {
        int id = leerEntero("ID de observación a modificar: ");
        int idFuncionario = leerEntero("Nuevo ID funcionario: ");
        int idEstudiante = leerEntero("Nuevo ID estudiante: ");
        String titulo = leerTexto("Nuevo título: ");
        String contenido = leerTexto("Nuevo contenido: ");
        OffsetDateTime fecHora = OffsetDateTime.now();

        try {
            Observacion obs = new Observacion(id, idFuncionario, idEstudiante, titulo, contenido, fecHora, true);
            boolean exito = facade.actualizarObservacion(obs);
            if (exito) mostrarExito("Observación modificada.");
            else mostrarError("No se pudo modificar la observación.");
        } catch (SQLException e) {
            mostrarError("Error al modificar observación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    private void desactivarObservacion() {
        int id = leerEntero("ID de observación a desactivar: ");
        try {
            boolean exito = facade.desactivarObservacion(id);
            if (exito) mostrarExito("Observación desactivada.");
            else mostrarError("No se pudo desactivar la observación.");
        } catch (SQLException e) {
            mostrarError("Error al desactivar observación: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }
}
