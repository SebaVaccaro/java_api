package consola.EstudianteConsola;

import consola.InterfazConsola.UIBase;
import PROXY.SeguimientoProxy;
import SINGLETON.SesionSingleton;
import modelo.Seguimiento;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SeguimientoConsola extends UIBase {

    private final SeguimientoProxy seguimientoProxy;
    private final int idEstudiante;

    // Constructor: valida sesión activa y obtiene el ID del estudiante autenticado
    public SeguimientoConsola() throws SQLException {
        if (!SesionSingleton.getInstance().haySesionActiva()) {
            throw new IllegalStateException("No hay sesión activa. Por favor inicia sesión.");
        }
        this.idEstudiante = SesionSingleton.getInstance().getUsuarioActual().getIdUsuario();
        this.seguimientoProxy = new SeguimientoProxy();
    }

    // Mostrar el menú principal del módulo de seguimientos del estudiante
    @Override
    protected void mostrarMenu() {
        System.out.println("\n===== MENÚ DE SEGUIMIENTOS DEL ESTUDIANTE =====");
        System.out.println("1. Ver mis seguimientos");
        System.out.println("2. Buscar seguimiento por ID");
        System.out.println("0. Volver al menú principal");
        System.out.println("===============================================");
    }


    // Gestionar la opción seleccionada por el estudiante
    @Override
    protected void manejarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> listarPorEstudiante(); // Mostrar todos los seguimientos del estudiante actual
            case 2 -> buscarPorId();           // Consultar un seguimiento específico
            case 0 -> mostrarInfo("Volviendo al menú principal...");
            default -> mostrarError("Opción inválida.");
        }
    }


    private void listarPorEstudiante(){
        try {
            List<Seguimiento> lista = seguimientoProxy.listarPorEstudiante(idEstudiante);
            if(lista.isEmpty()){
                mostrarInfo("No tienes seguimientos");
            }
            for(Seguimiento s: lista){
                System.out.println(s.toString());
            }
        } catch (SQLException e) {
            mostrarError("Error al listar seguimientos: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        }
    }

    // Buscar un seguimiento por su ID y mostrar sus detalles
    private void buscarPorId() {
        int id = leerEntero("Ingrese el ID del seguimiento: ");
        try {
            Seguimiento s = seguimientoProxy.buscarPorId(id);
            if(s == null){
                mostrarInfo("No se encontro seguimiento");
            }else {
                mostrarInfo("Detalles del seguimiento:");
                System.out.println(s);
            }
        } catch (SQLException e) {
            mostrarError("Error al buscar seguimiento: " + CapturadoraDeErrores.obtenerMensajeAmigable(e));
        } catch(Exception e){
            mostrarError(e.getMessage());
        }
    }
}
