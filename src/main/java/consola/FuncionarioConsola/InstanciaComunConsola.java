package consola.FuncionarioConsola;

import consola.InterfazConsola.UIBase;
import PROXY.InstanciaComunProxy;
import modelo.InstanciaComun;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

public class InstanciaComunConsola extends UIBase {

    private final InstanciaComunProxy proxy;

    // Constructor: inicializa el proxy que gestiona las operaciones de instancias comunes
    public InstanciaComunConsola() throws Exception {
        this.proxy = new InstanciaComunProxy();
    }

    // Muestra el menú principal del módulo de instancias comunes
    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ INSTANCIAS COMUNES =====");
        System.out.println("1. Crear instancia común");
        System.out.println("2. Listar todas");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar por seguimiento");
        System.out.println("5. Modificar instancia común");
        System.out.println("6. Eliminar instancia común");
        System.out.println("0. Volver al menú principal");
        System.out.println("===================================");
    }

    // Maneja la opción seleccionada por el usuario
    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearInstanciaComun();      // Crear nueva instancia común
                case 2 -> listarTodas();              // Listar todas las instancias
                case 3 -> buscarPorId();              // Buscar por ID
                case 4 -> listarPorSeguimiento();     // Listar por seguimiento
                case 5 -> modificarInstanciaComun();  // Modificar instancia existente
                case 6 -> eliminarInstanciaComun();   // Eliminar por ID
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // Crea una nueva instancia común
    private void crearInstanciaComun() {
        String titulo = leerTexto("Título: ");
        OffsetDateTime fecHora = leerFechaHora("Fecha y hora (YYYY-MM-DDTHH:MM): ");
        String descripcion = leerTexto("Descripción: ");
        boolean estActivo = leerBoolean("¿Está activa? (true/false): ");
        int idFuncionario = leerEntero("ID del funcionario: ");
        int idSeguimiento = leerEntero("ID del seguimiento: ");

        try {
            InstanciaComun ic = proxy.crearInstanciaComun(titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            mostrarExito("Instancia común creada: " + ic);
        } catch (SQLException e) {
            mostrarError("Error SQL al crear instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al crear instancia: " + e.getMessage());
        }
    }

    // Lista todas las instancias comunes
    private void listarTodas() {
        try {
            List<InstanciaComun> lista = proxy.listarInstanciasComunes();
            if (lista.isEmpty()) mostrarInfo("No hay instancias comunes registradas.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar instancias: " + e.getMessage());
        }
    }

    // Busca una instancia común por su ID
    private void buscarPorId() {
        int id = leerEntero("ID de la instancia común: ");
        try {
            InstanciaComun ic = proxy.obtenerInstanciaComun(id);
            if (ic != null) System.out.println(ic);
            else mostrarInfo("Instancia no encontrada.");
        } catch (SQLException e) {
            mostrarError("Error SQL al buscar instancia: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al buscar instancia: " + e.getMessage());
        }
    }

    // Lista todas las instancias asociadas a un seguimiento
    private void listarPorSeguimiento() {
        int idSeg = leerEntero("ID del seguimiento: ");
        try {
            List<InstanciaComun> lista = proxy.listarPorSeguimiento(idSeg);
            if (lista.isEmpty()) mostrarInfo("No hay instancias para este seguimiento.");
            else lista.forEach(System.out::println);
        } catch (SQLException e) {
            mostrarError("Error SQL al listar por seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al listar por seguimiento: " + e.getMessage());
        }
    }

    // Modifica los datos de una instancia común existente
    private void modificarInstanciaComun() {
        int id = leerEntero("ID de la instancia a modificar: ");
        try {
            InstanciaComun ic = proxy.obtenerInstanciaComun(id);
            if (ic == null) {
                mostrarInfo("Instancia no encontrada.");
                return;
            }

            String titulo = leerTexto("Nuevo título [" + ic.getTitulo() + "]: ", ic.getTitulo());
            OffsetDateTime fecHora = leerFechaHora("Nueva fecha y hora [" + ic.getFecHora() + "]: ", ic.getFecHora());
            String descripcion = leerTexto("Nueva descripción [" + ic.getDescripcion() + "]: ", ic.getDescripcion());
            boolean estActivo = leerBoolean("¿Está activa? (true/false) [" + ic.isEstActivo() + "]: ", ic.isEstActivo());
            int idFuncionario = leerEntero("Nuevo ID de funcionario [" + ic.getIdFuncionario() + "]: ", ic.getIdFuncionario());
            int idSeguimiento = leerEntero("Nuevo ID de seguimiento [" + ic.getIdSeguimiento() + "]: ", ic.getIdSeguimiento());

            boolean exito = proxy.actualizarInstanciaComun(id, titulo, fecHora, descripcion, estActivo, idFuncionario, idSeguimiento);
            if (exito) mostrarExito("Instancia común modificada correctamente.");
            else mostrarError("No se pudo modificar la instancia común.");
        } catch (SQLException e) {
            mostrarError("Error SQL al modificar: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al modificar instancia: " + e.getMessage());
        }
    }

    // Elimina una instancia común por ID
    private void eliminarInstanciaComun() {
        int id = leerEntero("ID de la instancia a eliminar: ");
        try {
            boolean exito = proxy.eliminarInstanciaComun(id);
            if (exito) mostrarExito("Instancia común eliminada correctamente.");
            else mostrarError("No se pudo eliminar la instancia común.");
        } catch (SQLException e) {
            mostrarError("Error SQL al eliminar: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch (Exception e) {
            mostrarError("Error general al eliminar instancia: " + e.getMessage());
        }
    }
}
