package consola.Admin;

import consola.interfaz.UIBase;
import PROXY.FuncionarioProxy;
import modelo.Funcionario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FuncionarioAdminUI extends UIBase {

    private final FuncionarioProxy proxy;

    public FuncionarioAdminUI() throws Exception {
        this.proxy = new FuncionarioProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ FUNCIONARIOS =====");
        System.out.println("1. Crear funcionario");
        System.out.println("2. Listar todos");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Modificar funcionario");
        System.out.println("5. Desactivar funcionario");
        System.out.println("0. Volver al menú anterior");
        System.out.println("==============================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearFuncionario();
                case 2 -> listarTodos();
                case 3 -> buscarPorId();
                case 4 -> modificarFuncionario();
                case 5 -> desactivarFuncionario();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    private void crearFuncionario() {
        String cedula = leerTexto("Cédula: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String username = leerTexto("Username: ");
        String password = leerTexto("Password: ");
        int idRol = leerEntero("ID de rol: ");
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");
        try {
            Funcionario f = proxy.crearFuncionario(cedula, nombre, apellido, username, password, idRol, fechaNacimiento);
            mostrarExito("Funcionario creado: " + f);
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al crear funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al crear funcionario: " + ex.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Funcionario> lista = proxy.listarTodos();
            if (lista.isEmpty()) mostrarInfo("No hay funcionarios registrados.");
            else lista.forEach(System.out::println);
        } catch (SQLException ex) {
            mostrarError("Error al listar funcionarios: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar funcionarios: " + ex.getMessage());
        }
    }

    private void buscarPorId() {
        int idFuncionario = leerEntero("ID del funcionario: ");
        try {
            Funcionario f = proxy.obtenerPorId(idFuncionario);
            if (f != null) System.out.println(f);
            else mostrarInfo("Funcionario no encontrado.");
        } catch (SQLException ex) {
            mostrarError("Error al buscar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar funcionario: " + ex.getMessage());
        }
    }

    private void modificarFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        String correo = leerTexto("Nuevo correo: ");
        int idRol = leerEntero("Nuevo ID de rol: ");
        boolean estActivo = leerBoolean("Activo (true/false): ");
        try {
            boolean exito = proxy.actualizarFuncionario(idFuncionario, cedula, nombre, apellido, username, password, correo, idRol, estActivo);
            if (exito) mostrarExito("Funcionario modificado correctamente.");
            else mostrarError("No se pudo modificar el funcionario.");
        } catch (SQLException ex) {
            mostrarError("Error al modificar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al modificar funcionario: " + ex.getMessage());
        }
    }

    private void desactivarFuncionario() {
        int idFuncionario = leerEntero("ID del funcionario a desactivar: ");
        try {
            boolean exito = proxy.desactivarFuncionario(idFuncionario);
            if (exito) mostrarExito("Funcionario desactivado correctamente.");
            else mostrarError("No se pudo desactivar el funcionario.");
        } catch (SecurityException ex) {
            mostrarError(ex.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al desactivar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al desactivar funcionario: " + ex.getMessage());
        }
    }
}
