package consola;

import consola.Estudiante.EstudianteUI;
import consola.Login.LoginUI;
import consola.Admin.AdminUI;
import consola.Psicopedagogo.PsicopedagogoUI;
import consola.Analista.AnalistaUI;
import consola.Funcionario.FuncionarioUI;
import modelo.Usuario;
import modelo.Funcionario;
import modelo.Estudiante;
import modelo.Rol;
import facade.RolFacade;

import java.sql.SQLException;

public class MainUI {

    private RolFacade rolFacade;

    public MainUI() {
        try {
            this.rolFacade = new RolFacade();
        } catch (SQLException e) {
            System.out.println("❌ Error al inicializar RolFacade: " + e.getMessage());
            e.printStackTrace();
            // Si no se puede inicializar RolFacade, se detiene la aplicación
            System.exit(1);
        }
    }

    public void iniciar() {

        LoginUI loginUI = new LoginUI();
        Usuario usuario = null;

        while (true) {

            usuario = loginUI.iniciar(); // Llamada al login
            if (usuario == null) {
                System.out.println("🔒 Sesión cerrada por el usuario.");
                return; // Salir si el login devuelve null
            }

            if (usuario instanceof Estudiante est) {
                System.out.println("\n🎓 Bienvenido, estudiante " + est.getNombre() + "!");
                EstudianteUI estudianteUI = new EstudianteUI(est);
                estudianteUI.iniciar();
            }
            else if (usuario instanceof Funcionario func) {
                System.out.println("\n🏢 Bienvenido, funcionario " + func.getNombre() + "!");

                try {
                    Rol rol = rolFacade.buscarPorId(func.getIdRol());

                    if (rol == null) {
                        System.out.println("⚠️ No se encontró el rol del funcionario. Acceso denegado.");
                        continue;
                    }

                    if (!rol.isEstActivo()) {
                        System.out.println("⚠️ El rol del funcionario está inactivo. Acceso denegado.");
                        continue;
                    }

                    String nombreRol = rol.getNombre().toUpperCase();
                    System.out.println(nombreRol);
                    switch (nombreRol) {
                        case "ADMINISTRADOR" -> {
                            AdminUI adminUI = new AdminUI(func);
                            adminUI.iniciar();
                        }
                        case "PSICOPEDAGOGO" -> {
                            PsicopedagogoUI psicopedagogoUI = new PsicopedagogoUI(func);
                            psicopedagogoUI.iniciar();
                        }
                        case "ANALISTA EDUCATIVO" -> {
                            AnalistaUI analistaUI = new AnalistaUI(func);
                            analistaUI.iniciar();
                        }
                        case "RESPONSABLE EDUCATIVO" -> {
                            FuncionarioUI funcionarioUI = new FuncionarioUI(func);
                            funcionarioUI.iniciar();
                        }
                        case "ÁREA LEGAL" -> {
                            FuncionarioUI funcionarioUI = new FuncionarioUI(func);
                            funcionarioUI.iniciar();
                        }
                        default -> {
                            System.out.println("⚠️ Rol no reconocido. Acceso denegado.");
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("❌ Error al obtener el rol del funcionario: " + e.getMessage());
                    e.printStackTrace();
                    System.out.println("⚠️ No se puede continuar con la sesión de este usuario.");
                }

            }
            else {
                System.out.println("\n⚠️ Tipo de usuario desconocido. No se puede continuar.");
            }

            usuario = null; // Limpiar usuario
            System.out.println("\n🔁 Cerrando sesión...\n");
        }
    }
}
