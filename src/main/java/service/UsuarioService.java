package service;

import DAO.UsuarioDAO;
import main.*;
import modelo.Funcionario;
import modelo.Usuario;
import algoritmos.Encriptador;
import modelo.Estudiante;

import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO dao;
    private Usuario usuarioActual; // sesión actual

    public UsuarioService() throws SQLException {
        this.dao = new UsuarioDAO();
    }

    /**
     * Login y guarda usuario actual
     */
    public Usuario login(String username, String password) throws Exception {

        // Encriptar la contraseña ingresada para compararla con la base
        String passwordEncriptada = Encriptador.encriptar(password);

        Usuario user = dao.login(username, passwordEncriptada);
        if (user == null) throw new Exception("Usuario no encontrado o contraseña incorrecta.");

        // ===== Lógica de aceptación de políticas =====
        if (!user.isActivo()) {
            System.out.println("Debes aceptar las políticas de UTEC para continuar.");
            java.util.Scanner sc = new java.util.Scanner(System.in);
            String opcion = "";

            while (true) {
                System.out.print("¿Aceptas las políticas? (SI/NO): ");
                opcion = sc.nextLine().trim().toUpperCase();

                if (opcion.equals("SI")) {
                    user.setActivo(true);

                    // Actualizamos el estado en la base según tipo de usuario
                    if (user instanceof Estudiante) {
                        new EstudianteServicio().actualizarEstudiante((Estudiante) user);
                    } else if (user instanceof Funcionario) {
                        new FuncionarioServicio().actualizarFuncionario((Funcionario) user);
                    }

                    System.out.println("¡Políticas aceptadas! Bienvenido.");
                    break; // salimos del bucle y login continúa
                } else if (opcion.equals("NO")) {
                    System.out.println("No puedes acceder al sistema sin aceptar las políticas.");
                    return null; // cancelamos login
                } else {
                    System.out.println("Opción inválida. Debes ingresar SI o NO.");
                }
            }
        }
        // ============================================

        this.usuarioActual = user;
        return user;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Validaciones de permisos para funcionarios
     */
    public boolean puedeCrearUsuario() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR;
        }
        return false;
    }

    public boolean puedeModificarUsuario() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR;
        }
        return false;
    }

    public boolean puedeConsultarConfidencial() {
        if (usuarioActual instanceof Funcionario f) {
            return f.getRol() == Funcionario.Rol.ADMINISTRADOR ||
                    f.getRol() == Funcionario.Rol.PSICOPEDAGOGO;
        }
        return false;
    }
}