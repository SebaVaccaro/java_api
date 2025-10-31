package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.InstanciaComunProxy;
import modelo.InstanciaComun;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunConsola extends UIBase {

    private final InstanciaComunProxy proxy;

    // Constructor: inicializa el proxy que gestiona las operaciones de instancias comunes
    public InstanciaComunConsola() throws SQLException {
        this.proxy = new InstanciaComunProxy();
    }

    // Muestra el menú principal del módulo de instancias comunes
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ INSTANCIAS COMUNES ---");
        System.out.println("1. Crear instancia común");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por instancia");
        System.out.println("4. Listar por seguimiento");
        System.out.println("5. Actualizar instancia común");
        System.out.println("6. Eliminar instancia por ID");
        System.out.println("0. Volver al menú principal");
    }

    // Maneja la opción seleccionada por el usuario en el menú
    @Override
    public void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> crearInstanciaComun();       // Crear nueva instancia común
            case 2 -> listarTodos();               // Listar todas las instancias comunes
            case 3 -> buscarPorInstancia();        // Buscar una instancia por su ID
            case 4 -> listarPorSeguimiento();      // Listar instancias según un seguimiento
            case 5 -> actualizarInstanciaComun();  // Actualizar una instancia existente
            case 6 -> eliminarPorInstancia();      // Eliminar una instancia
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }

    // Crea una nueva instancia común solicitando los datos al usuario
    private void crearInstanciaComun() {
        try {
            String titulo = leerTexto("Título: ");
            OffsetDateTime fecHora = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
            String descripcion = leerTexto("Descripción: ");
            boolean estActivo = leerBoolean("Activa (true/false): ");
            int idFuncionario = leerEntero("ID de funcionario: ");
            int idSeguimiento = leerEntero("ID de seguimiento: ");

            InstanciaComun ic = proxy.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            mostrarExito("Instancia común creada: " + ic);
        } catch (SQLException e) {
            mostrarError("Error al crear instancia común: " + e.getMessage());
        }
    }

    // Lista todas las instancias comunes registradas
    private void listarTodos() {
        try {
            List<InstanciaComun> lista = proxy.listarInstanciasComunes();
            if (lista.isEmpty()) mostrarInfo("No hay instancias comunes registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar: " + e.getMessage());
        }
    }

    // Busca una instancia común por su ID
    private void buscarPorInstancia() {
        int id = leerEntero("ID de instancia: ");
        try {
            InstanciaComun ic = proxy.obtenerInstanciaComun(id);
            if (ic != null) mostrarInfo(ic.toString());
            else mostrarError("Instancia no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error al buscar: " + e.getMessage());
        }
    }

    // Lista todas las instancias comunes asociadas a un seguimiento
    private void listarPorSeguimiento() {
        int idSeg = leerEntero("ID de seguimiento: ");
        try {
            List<InstanciaComun> lista = proxy.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) mostrarInfo("No hay instancias para este seguimiento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error al listar por seguimiento: " + e.getMessage());
        }
    }

    // Actualiza los datos de una instancia común existente
    private void actualizarInstanciaComun() {
        int id = leerEntero("ID de instancia a actualizar: ");
        try {
            InstanciaComun ic = proxy.obtenerInstanciaComun(id);
            if (ic == null) {
                mostrarError("Instancia no encontrada.");
                return;
            }

            String titulo = leerTexto("Nuevo título [" + ic.getTitulo() + "]: ", ic.getTitulo());
            OffsetDateTime fecHora = leerFechaHora("Nueva fecha y hora [" + ic.getFecHora() + "]: ", ic.getFecHora());
            String descripcion = leerTexto("Nueva descripción [" + ic.getDescripcion() + "]: ", ic.getDescripcion());
            boolean estActivo = leerBoolean("Activa (true/false) [" + ic.isEstActivo() + "]: ", ic.isEstActivo());
            int idFuncionario = leerEntero("ID de funcionario [" + ic.getIdFuncionario() + "]: ", ic.getIdFuncionario());
            int idSeguimiento = leerEntero("ID de seguimiento [" + ic.getIdSeguimiento() + "]: ", ic.getIdSeguimiento());

            boolean actualizado = proxy.actualizarInstanciaComun(id, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            if (actualizado) mostrarExito("Actualización exitosa.");
            else mostrarError("No se pudo actualizar la instancia.");
        } catch (SQLException e) {
            mostrarError("Error al actualizar: " + e.getMessage());
        }
    }

    // Elimina una instancia común según su ID
    private void eliminarPorInstancia() {
        int id = leerEntero("ID de instancia a eliminar: ");
        try {
            boolean eliminado = proxy.eliminarInstanciaComun(id);
            if (eliminado) mostrarExito("Instancia eliminada.");
            else mostrarError("No se pudo eliminar la instancia.");
        } catch (SQLException e) {
            mostrarError("Error al eliminar: " + e.getMessage());
        }
    }
}
