package consola.Psicopedagogo;

import consola.interfaz.UIBase;
import PROXY.FuncionarioProxy;
import modelo.Funcionario;
import utils.CapturadoraDeErrores;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioPsicoUI extends UIBase {

    private final FuncionarioProxy proxy;

    public FuncionarioPsicoUI() throws Exception {
        this.proxy = new FuncionarioProxy();
    }

    @Override
    public void mostrarMenu() {
        System.out.println("\n===== MENÚ FUNCIONARIOS (PSICOPEDAGOGO) =====");
        System.out.println("1. Listar todos");
        System.out.println("2. Buscar por ID");
        System.out.println("3. Modificar funcionario");
        System.out.println("4. Desactivar funcionario");
        System.out.println("0. Volver al menú principal");
        System.out.println("=============================================");
    }

    @Override
    public void manejarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> listarTodos();
                case 2 -> buscarPorId();
                case 3 -> modificarFuncionario();
                case 4 -> desactivarFuncionario();
                case 0 -> mostrarInfo("Volviendo al menú principal...");
                default -> mostrarError("Opción inválida. Intente nuevamente.");
            }
        } catch (Exception e) {
            mostrarError("Error al ejecutar la opción: " + e.getMessage());
        }
    }

    // ============================================================
    // LISTAR FUNCIONARIOS
    // ============================================================
    private void listarTodos() {
        try {
            List<Funcionario> lista = proxy.listarTodos();
            if (lista.isEmpty()) {
                mostrarInfo("No hay funcionarios registrados.");
            } else {
                lista.forEach(System.out::println);
            }
        } catch (SQLException ex) {
            mostrarError("Error al listar funcionarios: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al listar funcionarios: " + ex.getMessage());
        }
    }

    // ============================================================
    // BUSCAR FUNCIONARIO POR ID
    // ============================================================
    private void buscarPorId() {
        int id = leerEntero("ID del funcionario: ");
        try {
            Funcionario f = proxy.obtenerPorId(id);
            if (f != null) {
                System.out.println(f);
            } else {
                mostrarInfo("Funcionario no encontrado.");
            }
        } catch (SQLException ex) {
            mostrarError("Error al buscar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception ex) {
            mostrarError("Error general al buscar funcionario: " + ex.getMessage());
        }
    }

    // ============================================================
    // MODIFICAR FUNCIONARIO
    // ============================================================
    private void modificarFuncionario() {
        int id = leerEntero("ID del funcionario a modificar: ");
        String cedula = leerTexto("Nueva cédula: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String apellido = leerTexto("Nuevo apellido: ");
        String username = leerTexto("Nuevo username: ");
        String password = leerTexto("Nuevo password: ");
        String correo = leerTexto("Nuevo correo: ");
        int idRol = leerEntero("Nuevo ID de rol: ");
        boolean estActivo = leerBoolean("Activo (true/false): ");

        try {
            boolean exito = proxy.actualizarFuncionario(
                    id, cedula, nombre, apellido, username, password, correo, idRol, estActivo
            );
            if (exito) {
                mostrarExito("Funcionario modificado correctamente.");
            } else {
                mostrarError("No se pudo modificar el funcionario.");
            }
        } catch (SecurityException se) {
            mostrarError("Permiso denegado: " + se.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al modificar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    // ============================================================
    // DESACTIVAR FUNCIONARIO
    // ============================================================
    private void desactivarFuncionario() {
        int id = leerEntero("ID del funcionario a desactivar: ");
        try {
            boolean exito = proxy.desactivarFuncionario(id);
            if (exito) {
                mostrarExito("Funcionario desactivado correctamente.");
            } else {
                mostrarError("No se pudo desactivar el funcionario.");
            }
        } catch (SecurityException se) {
            mostrarError("Permiso denegado: " + se.getMessage());
        } catch (SQLException ex) {
            mostrarError("Error SQL al desactivar funcionario: " + CapturadoraDeErrores.obtenerMensajeAmigable(ex));
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }
}